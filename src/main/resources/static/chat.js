$(function() {
	var ar = Array();
	setInterval(function() {
		$.ajax({
			type: "GET",
			url: "chat?time=" + document.getElementById("video").currentTime,
			datatype: "json"
		}).done(function(data) {
			//chat.removeChild(document.getElementsByClassName("chat_element")[0]);
			var jdata = JSON.parse(data);
			for (var i = 0; i < jdata.length; i++) {
				if (!ar.includes(jdata[i]["time"])) {
					console.log(ar);
					console.log(jdata[i]);
					$("#chat").append("<div time=" + jdata[i]["time"] + " class=\"chat_element\"><span style=\"color:gray;\">" + jdata[i]["name"] + "</span><span style=\"color:green;\">: </span>" + jdata[i]["message"] + "</div>");
					ar.push(jdata[i]["time"]);
				}
			}
			//delete chat element if it paste 60 sec
			var chat_elements = document.getElementsByClassName("chat_element");
			for (var i = 0; i < chat_elements.length; i++) {
				if (chat_elements[i].getAttribute("time") > document.getElementById("video").currentTime) {
					chat_elements[i].remove();
					ar.pop()
				}
			}
			//scroll to bottom
			var chat = document.getElementById("chat");
			chat.scrollTop = chat.scrollHeight;

		});
	}, 1000);
});
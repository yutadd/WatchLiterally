$(function() {
	var ar = Array();
	var chat = document.getElementById("chat");
	chat.scrollTop = chat.scrollHeight;
	document.getElementsByClassName("submit")[0].addEventListener('click', evt => {
		$.post('chat', 'message=' + document.getElementsByClassName("chat_input")[0].value+'&time=' + document.getElementById("video").currentTime, data => {console.log(data)})
	})
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

		});
	}, 1000);
});
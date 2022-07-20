$(function () {
	setInterval(function () {
		$.ajax({
			type: "GET",
			url: "chat",
			datatype: "json"
		}).done(function (data) {

			//chat.removeChild(document.getElementsByClassName("chat_element")[0]);
			var jdata = JSON.parse(data);
			for (var i = 0; i < jdata.length; i++) {
				$("#chat").append("<div time=" + Date.now() + " class=\"chat_element\"><span style=\"color:gray;\">" + jdata[i]["name"] + "</span><span style=\"color:green;\">: </span>" + jdata[i]["message"] + "</div>");
			}
			//delete chat element if it paste 60 sec
			var chat_elements = document.getElementsByClassName("chat_element");
			for (var i = 0; i < chat_elements.length; i++) {
				if (Date.now() - chat_elements[i].getAttribute("time") > 60000) {
					chat_elements[i].remove();
				}
			}
			//scroll to bottom
			var chat = document.getElementById("chat");
			chat.scrollTop = chat.scrollHeight;

		});
	}, 1000);
});
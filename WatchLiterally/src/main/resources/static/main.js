$(function () {
	$.ajax({
		type: "GET",
		url: "/streamsdata",
		datatype: "json"
	}).done(function (data) {
		console.log(data);
		var jdata = JSON.parse(data);
		for (var i = 0; i < jdata.length; i++) {
			$(".sidebar").append(""
			+"<div class=\"side_elems\">"
			+"	<div class=\"side_elems_in\">"
			+"		<img class=\"icon\" width=\"40\" alt=\"icon\" src=\" " + jdata[i]["icon"] + "\">"
			+"		<div class=\"side_title_space\">"
			+"			<span class=\"side_title\">"
			+ 			jdata[i]["name"] 
			+"			</span>"
			+"			<br>"
			+"			<span>" 
			+ 				jdata[i]["title"] 
			+"			</span>"
			+"		</div>"
			+"	</div>"
			+"</div>");
		$(".recoms").append("<div style=\"padding:30px\">\n"
		+ "          <div class=\"recom_child\">\n"
		+ "            <img src=\""+jdata[0]["thumb"]+"\" class=\"thumb\">\n"
		+ "            <div class=\"thumb_link\">\n"
		+ "              <a href=\"live/yuta-alive\" style=\"color:white;\">\n"
		+ "                <span style=\"font-size:30px\">"+jdata[i]["title"]+"</span>\n"
		+ "                <br>\n"
		+ "                <span style=\"font-size:15px\">"+jdata[i]["name"]+"</span>\n"
		+ "              </a>\n"
		+ "            </div>\n"
		+ "            <p>"+jdata[i]["date"]+"</p>\n"
		+ "          </div>\n"
		+ "        </div>");
		}
	}
	);

});

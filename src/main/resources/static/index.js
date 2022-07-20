$(function() {
	$.ajax({
		type: "GET",
		url: "/enumstreams",
		datatype: "json"
	}).done(function(data) {
		console.log(data);
		var jdata = JSON.parse(data);
		for (var i = 0; i < jdata.length; i++) {
			$(".sidebar").append(""
				+ "<div class=\"side_elems\">"
				+ "	<div class=\"side_elems_in\">"
				+ "		<img class=\"icon\" width=\"40\" alt=\"icon\" src=\" " + jdata[i]["icon"] + "\">"
				+ "		<div class=\"side_title_space\">"
				+ "			<span class=\"side_name\">"
				+ jdata[i]["name"]
				+ "			</span>"
				+ "			<br>"
				+ "			<span class=\"side_title\">"
				+ jdata[i]["title"]
				+ "			</span>"
				+ "		</div>"
				+ "	</div>"
				+ "</div>");
			$(".recoms").append("<div style=\"padding:30px\">\n"
				+ "          <div class=\"recom_child\">\n"
				+ "	           <a class=\"recom_character\"href=" + jdata[i]["link"] + ">"
				+ "            <img src=\"" + jdata[i]["thumb"] + "\" class=\"thumb\">\n"
				+ "            <div class=\"thumb_link\">\n"
				+ "                <span  style=\"font-size:20px\">" + jdata[i]["title"] + "</span>\n"
				+ "                <br>\n"
				+ "                <span  style=\"font-size:15px;\">" + jdata[i]["name"] + "</span>\n"
				+ "            </div>\n"
				+ "            <p class=\"recom_character\">" + jdata[i]["date"] + "</p>\n"
				+ "             </a>"
				+ "          </div>\n"
				+ "        </div>");
		}
	}
	);
});



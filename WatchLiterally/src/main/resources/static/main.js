$(function () {
	$.ajax({
		type: "GET",
		url: "/streamsdata",
		datatype: "json"
	}).done(function (data) {
		console.log(data);
		var jdata = JSON.parse(data);
		for (var i = 0; i < jdata.length; i++) {
			$(".sidebar").append("<div class=\"side_elems\"><div class=\"side_elems_in\"><img class=\"icon\" width=\"40\" alt=\"icon\" src=\" " + jdata[i]["icon"] + "\"><div class=\"side_title_space\"><span class=\"side_title\">" + jdata[i]["name"] + "</span><br><span>" + jdata[i]["title"] + "</span></div></div></div>");
		}
	}
	);

});

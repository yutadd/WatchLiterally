$(function(){
	$.ajax({
		type:"GET",
		url:"/enumstreams",
		datatype:"json"
	}).done(function(data){
		var jdata=JSON.parse(data);
		for(var i=0;i<jdata.length;i++){
		$(".sidebar").append("<div class=\"side_elems\"><div class=\"side_elems_in\"><img class=\"icon\" width=\"40\" alt=\"icon\" src=\" " + jdata[i]["icon"]+"\"><div class=\"side_title_space\"><span class=\"side_title\">"+jdata[i]["name"]+"</span><br><span>"+jdata[i]["title"]+"</span></div></div></div>");
		$(".recoms").append("<div style=\"padding:30px\"><div class=\"recom_child\"><img src="+jdata[i]["thumb"]+" class=\"thumb\"><div class=\"thumb_link\"><a href\=\"/"+jdata[i]["name"]+"/"+jdata[i]["title"]+"\" style=\"color:white;\"><span style=\"font-size:30px\">"+jdata[i]["title"]+"</span><br><span style=\"font-size:15px\">"+jdata[i]["name"]+"</span></a></div><p>2020/6/10</p></div></div>")
	}
	}
	);
});
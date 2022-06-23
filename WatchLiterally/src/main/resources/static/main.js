$(function(){
	$.ajax({
		type:"GET",
		url:"/streamsdata",
		datatype:"json"
	}).done(function(data){
		console.log(data);
		var jdata=JSON.parse(data);
		for(var i=0;i<jdata.length;i++){
		$(".sidebar").append("<div class=\"side_elems\"><div class=\"side_elems_in\"><a class=\"side_text\"><img width=\"40\" alt=\"icon\" src=\" " + jdata[i]["icon"]+"\">"+jdata[i]["title"]+"</a></div></div>");
	}
	}
	);
	
});
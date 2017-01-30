/**
 * The function below loads tags on page load
 */
function loadTags(){
		
	var tagString = "";

	$.ajax({					
		type: "GET",
		url: "/tagcloud",
		error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
			alert("Failed to load tags");
			$(".load #1").hide("fade",500);
			
		}}).done(function( msg ) { 
		 			
		 	$(".load #1").hide("fade",500);
		 	obj = JSON.parse(msg);
		 			
		 	for(var t in obj.tags){
		 				
		 		tagString = tagString + " <a href=\""+ obj.tags[t].link + 
		 		"\" style=\"color:#22C1AC; font-size:"+ obj.tags[t].weight + "px;\">" + obj.tags[t].name + "</a>";		
		 	}
		 	$( "#tagcloud" ).tagString;
		 	document.getElementById("tagcloud").innerHTML = "<p>" + tagString + "</p>";	 			
	}); 
}
/**
 * The function below creates the upload popup
 */
function popup(){
  window.open("/admin/createCarousel.jsp", "Create Carousel", "location=no,width=700,height=700"); 
}

/**
 * This function is how comments are posted to the servlet.
 * It is all handled through an ajax request where we send 
 * json to the server for processing.
 */
function sendMessage(type){
	
	var name = $('#name').val(); 
	var des = $('#descrip').val();
	var address = $('#address').val();
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");		

	if(formValidation(name, des, address)){ // If all fields filled out
		
		var query = "{ \"name\": \"" + name + "\", \"des\": \"" + des.replace(/"/g, '&#8220') +  "\", \"address\": \"" + address.replace(/"/g, '&#8220') +  "\"}";
		
		$.ajax({				
			type: "POST",
			url: "/adminInter/carouselActionUpdate?delete=n",
			data: query, 
			beforeSend: function(xhr) {
	  				xhr.setRequestHeader(header, token);
	  			},
	  			error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
	  				alert("Post failed, please try again later.");
	  				
	  		}}).done(function( msg ) { // Message completed
	 		
		 		obj = JSON.parse(msg);
		 		
		 		if(obj.status == 0){ // If status successful
		 			alert("Created Slide");
		 			window.close ();
		 			window.opener.location.reload();
		 		} 
		 		else if(obj.status == 1){ // Else if comment failed to post to server
		 			alert("Error Creating Slide");
		 		} 
		 		else{ // Else captcha not validated 
		 			alert("Unkown Error");
		 			
		 		} 
	 	}); 
	} 	
}

/**
 * Delete images delets images from the server
 */
function deleteImage(id){
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");		
		
	var query = "{ \"delete\": \"" + id +  "\"}";
		
		$.ajax({					 
			type: "POST",
			url: "/adminInter/carouselActionUpdate?delete=y",
			data: query, 
			beforeSend: function(xhr) {
	  				// here it is
	  				xhr.setRequestHeader(header, token);
	  			},
	  			error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
	  				alert("Post failed, please try again later.");
	  				
	  		}}).done(function( msg ) { // Message completed
	 		
		 		obj = JSON.parse(msg);
		 		
		 		if(obj.status == 0){ // If status successful
		 		
		 			alert("Deleted Slide");
		 			location.reload();
	
		 		} // End if status successful
		 		else if(obj.status == 1){ // Else if comment failed to post to server
		 			
		 			alert("Error Deleting Slide");
		 		} 
		 		else{ // Else captcha not validated 
		 			
		 			alert("Unkown Error");
		 		} 
	 }); 
}

/**
 * This function validates required fields. For Name, Email and Body
 * we build the proper message for the user depending on what is not
 * filled out. 
 */		
function formValidation(name, des, address){
			
	if(name == null || name == ""){ // If email not filled out
	
		alert("Please provide a photo");
		return false;
	} 
	
	if(des == null || des == ""){ // If email not filled out
		
		alert("Please provide a description");
		return false;
	} 
	
	if(address == null || address == ""){ // If email not filled out
		
		alert("Please provide an address");
		return false;
	} 
	
	return true;			
} 
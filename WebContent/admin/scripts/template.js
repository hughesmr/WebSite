	/**
	 * This function is how comments are posted to the servlet.
	 * It is all handled through an ajax request where we send 
	 * json to the server for processing.
	 */
	function sendMessage(type){
				  					 								
		var val = $('#templateToUpdate').val().replace(/\r/g,"&#09;").replace(/\n/g,"&#13;").replace(/ /g,"&#32;");
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");		
		
		if(formValidation(val)){ // If all fields filled out
			
			var query = "{ \"type\": \"" + type + "\", \"val\": \"" + val.replace(/"/g, '&#8220') +  "\"}";
			
			$.ajax({					 
				type: "POST",
				url: "/adminInter/templateUpdate",
				data: query, 
				beforeSend: function(xhr) {
		  				
		  				xhr.setRequestHeader(header, token);
		  			},
		  			error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
		  				alert("Post failed, please try again later.");
		  				
		  			}}).done(function( msg ) { // Message completed
		 		
		  				obj = JSON.parse(msg);
		 		
			 		if(obj.status == 0){ // If status successful
			 			alert("Template Updated Successfully");
			 			window.location.reload();
	
			 		} 
			 		else if(obj.status == 1){ // Else if comment failed to post to server
			 			alert("Bummer Sauce, Unable to Update Template");
			 		} 
			 		else{ // Else captcha not validated 
			 			alert("Unknown Error.. Probably bring it up with the developer.. He might care, he might not. Depends on the day.");
			 		} 
		 	}); 	
		} 		
	}
	
	/**
	 * The function below resets templates to default settings
	 */
	function resetTemplate(type){

		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");		
		var query = "{ \"type\": \"" + type + "\", \"val\": \"resetTemplate\"}";
			
			$.ajax({					 
				type: "POST",
				url: "/adminInter/templateUpdate",
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
			 			alert("Template Reset");
			 			window.location.reload();
	
			 		} 
			 		else if(obj.status == 1){ // Else if comment failed to post to server
			 			alert("Unable to Reset Template. Template Already Default");
			 		}
			 		else{ // Else captcha not validated 
			 			alert("Unknown Error.. Probably bring it up with the developer.. He might care, he might not. Depends on the day.");
			 		} 
		 }); 
	}
	
	/**
	 * This function validates required fields. For Name, Email and Body
	 * we build the proper message for the user depending on what is not
	 * filled out. 
	 */
	function formValidation(val){
					
		if(val == null || val == ""){ // If email not filled out
		
			alert("Please provide a value for the template.");
			return false;
		} 
		
		return true;		
	} 
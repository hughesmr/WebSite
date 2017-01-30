	/**
	 * This function is how comments are posted to the servlet.
	 * It is all handled through an ajax request where we send 
	 * json to the server for processing.
	 */
	function sendMessage(type){
		
		var val = $('#' + type.replace(/\./g , '-')).val();    					 								
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");		
	
		if(formValidation(val)){ 
			
			var query = "{ \"type\": \"" + type + "\", \"val\": \"" + val.replace(/"/g, '&#8220') +  "\"}";
			
			$.ajax({					 
				type: "POST",
				url: "/adminInter/settingsUpdate",
				data: query, 
				beforeSend: function(xhr) {
		  			
		  				xhr.setRequestHeader(header, token);
		  			},
		  			error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
		  				alert("Post failed, please try again later.");
		  				
		  			}}).done(function( msg ) { // Message completed
		 		
				 		obj = JSON.parse(msg);
				 		
				 		if(obj.status == 0){ // If status successful
				 			alert("Property Updated Successfully");
				 			loadSettings();
		
				 		} 
				 		else if(obj.status == 1){ // Else if comment failed to post to server
				 			alert("Property Already Set");
				 		} 
				 		else{ // Else captcha not validated 
				 			alert("Property Deleted");
				 			loadSettings();
				 		} 
		 	}); 
		} 
	}
	
	/**
	 * This function validates required fields. For Name, Email and Body
	 * we build the proper message for the user depending on what is not
	 * filled out. 
     */
	function formValidation(val){
					
		if(val == null || val == ""){ // If email not filled out
		
			alert("Please provide a value for the system property.");
			return false;
		} 
		
		return true;		
	} 	
	
	/**
	 * The function below is used to load banned words
	 */
	function loadSettings(){
		
		$( "#loadableSettings" ).empty();
		$( "#loadableSettings" ).load( "/admin/loadableSettings");
	} 
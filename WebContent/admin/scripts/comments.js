	/**
	 * This function is how comments are posted to the servlet.
	 * It is all handled through an ajax request where we send 
     * json to the server for processing.
	 */
	function sendMessage(){
		
		var app = "";
		var del = "";

		var queryString = getCaptchaString(); // Variable to hold captcha string
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		for(var i = 0; i < $('#count').val(); i++){
			var value = $('input[name="approved' +i +'"]:checked').val();

			if(value){
				if(value.indexOf("app") > -1){
					app = app + value.replace("app", "") + ",";
				}
				else if(value.indexOf("del") > -1){
					del = del + value.replace("del", "") + ",";
				}
			}
			
		}		
		
		if(formValidation(queryString)){ // If all fields filled out
			
			var query = "{ \"app\": \"" + app + "\", \"del\": \"" + del + "\", \"captcha\":\"" 
  			+ queryString + "\"}";
			
			$.ajax({					 
				type: "POST",
				url: "/adminInter/commentApproval",
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
		 			alert("Comments Moderated");
		 			loadComment(0);

		 		} // End if status successful
		 		else if(obj.status == 1){ // Else if comment failed to post to server
		 			
		 			alert("Comment Mod Failed to post, Please Try Again");
		 		}
		 		else{ // Else captcha not validated 
		 			
		 			alert("Failed to validate, please try to post again later");
		 		}
		 	}); 	
		} 
		grecaptcha.reset();			
	}
	
	/**
	 * getCaptchaString gets the captcha string for processing
	 */
	function getCaptchaString(){
		
		var token = $('#preventRe').serialize();
		var index = token.lastIndexOf("g-recaptcha-response")
		
		return (token.substring(index, index.length)).replace('g-recaptcha-response=', '');	
	}
	
	/**
     * This function validates required fields. For Name, Email and Body
	 * we build the proper message for the user depending on what is not
	 * filled out. 
	 */
	function formValidation(queryString){
				
		if(queryString == ''){ // If captcha not filled out
			
			alert("Please fill out captcha.");
			return false;
		} 
		
		return true;
	}

	/**
     * The function below is used to load banned words
	 */
	function loadComment(page){

		$( "#comments" ).empty();
		$( "#comments" ).load( "/adminInter/loadCommentsMod?comPageNum=" + page);
	} 
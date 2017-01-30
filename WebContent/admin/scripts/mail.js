	/**
	 * This function is how comments are posted to the servlet.
 	 * It is all handled through an ajax request where we send 
	 * json to the server for processing.
	 */
	function sendMessage(){
				
		var subject = $('#subject').val();    					
		var body = tinyMCE.get('message_body').getContent(); 
		var date = $('#_datetimepicker3').val();
		var queryString = $('#preventRe').serialize().replace('g-recaptcha-response=', '');
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

	
		if(date == ""){
			date = (new Date).getTime();
		}
		else{
			date = (new Date(date)).getTime();
		}
		
		if(formValidation(subject, body, queryString)){
			
			var query = "{ \"subject\": \"" + subject + "\", \"body\": \"" + body.replace(/"/g, '&#8220') + "\", \"date\": \"" + date +
			"\", \"captcha\":\"" + queryString + "\"}";
			
			$.ajax({					 
				type: "POST",
				url: "/adminInter/mailUpdateReciever",
				data: query, 
				beforeSend: function(xhr) {
		  				
		  				xhr.setRequestHeader(header, token);
		  			},
		  			error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
		  				alert("Post failed, please try again later.");	
		  				
		  	}}).done(function( msg ) {
		 		
		 		obj = JSON.parse(msg);
		 		
		 		if(obj.status == 0){ // If status successful
		 			
		 			alert("NewsLetter Posted Successfully");
		 			document.getElementById('subject').value = ""; 
		    		tinymce.get('message_body').setContent('');
		 		} 
		 		else if(obj.status == 1){ // Else if comment failed to post to server
		 			
		 			alert("NewsLetter Failed to post, Please Try Again");
		 		} 
		 		else{ // Else captcha not validated 
		 			
		 			alert("Failed to validate, please try to post again later");
		 		}
		 	});		
		} 
		grecaptcha.reset();			
	}
	
	/**
	 * This function validates required fields. For Name, Email and Body
	 * we build the proper message for the user depending on what is not
	 * filled out. 
	 */
	function formValidation(subject, body, queryString){
				
		var message = "Please fill out the following:"; 
		var test = true; 							    
				
		if(queryString == ''){ // If captcha not filled out
			
			alert("Please fill out captcha.");
			return false;
		} 
				
		if(subject == null || subject == ""){ // If email not filled out
			
			test = false;
			message = message + " subject";
		} 
		
		if(body == null || body == ""){ // If name not filled out
			test = false;
			if(message.indexOf("subject") != -1){	
				
				message = message + ", body";		
			}
			else{
				
				message = message + " body";
			}
		} 
				
		if(test == false){ // If tests fail show user message
			
			alert(message + ".");
			return false;
		}
		else{ // Else tests pass
			
			return true;
		} 		
	} 
	
	/**
	 * Used to format date data
	 */
	jQuery(function(){
		var date = new Date();
		jQuery('#_datetimepicker3').datetimepicker({
	     startDate: date,

	     format:'m.d.Y H:i',
		 inline:true,
		 lang:'en'
		});
	});
	
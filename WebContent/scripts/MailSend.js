/**
 * The function below sends user messages to the server
 */
function sendMessage(){
				
	var from = $('#address').val();
	var subject = $('#subject').val();
	var body = tinyMCE.get('body').getContent(); 
	var queryString = $('#preventRe').serialize().replace('g-recaptcha-response=', '');

	if(formValidation(from, subject, body, queryString)){

	 
		$("#load, #imgLoad").fadeIn(500);
		$.ajax({
		  	type: "POST",
		 	url: "/mailReceiver",
		  	data: "{ \"from\": \""+from+"\", \"subject\": \""+subject+"\", \"body\": \""+body+"\", \"captcha\":\""+queryString+"\"}"
		})
		.done(function( msg ) {
			$("#load, #imgLoad").fadeOut(500);
		 		obj = JSON.parse(msg);
		 		if(obj.status == 0){
		    		alert("Message Sent Successfully");
		    		document.getElementById('address').value = ""; // Reset email field
	    			document.getElementById('subject').value = "";  // Reset name field
	    			tinymce.get('body').setContent('');  // Reset comment field
		 		}
		 		else if(obj.status == 2){
		 			alert("Failed to validate captcha. Please try again later.");
		 		}
		 		else{
		 			alert("Message Failed to Send, Please Try Again");
		 		}		
		 });
	}
	grecaptcha.reset();			
}

/**
 * The function below validates the form was filled out correctly
 */
function formValidation(from, subject, body, queryString){
				
	var message = "Please fill out the following:";
	var test = true;
				
	if(queryString == null || queryString == ""){
		alert("Please fill out captcha.");
		return false;
	}
				
	if(from == null || from == ""){
		test = false;
		message = message + " from address";
	}
	if(subject == null || subject == ""){
		test = false;
		if(message.indexOf("address") != -1){
						
			message = message + ", subject";
						
		}
		else{
			message = message + " subject";
		}
	}
	if(body == null || body == ""){
		test = false;

		if(message.indexOf("address") != -1 || message.indexOf("subject") != -1){
			message = message + ", message";
		}
		else{
			message = message + " message";
		}
	}
				
	if(test == false){
		alert(message + ".");
		return false;
	}
	else{
		return true;
	}			
}


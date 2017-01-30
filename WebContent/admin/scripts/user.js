	/**
	 * This function is how comments are posted to the servlet.
	 * It is all handled through an ajax request where we send 
	 * json to the server for processing.
	 */
	function sendMessage(){
				
		var oldPass = $('#oldPass').val();						
		var newPass = $('#newPass').val();    					
		var reNewPass = $('#reNewPass').val();  
		var uName = $("meta[name='uName']").attr("content");
		var queryString = $('#passUp').serialize().replace('g-recaptcha-response=', ''); 
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");	
				
		if(formValidation(oldPass, newPass, reNewPass, queryString) && comparePass(newPass, reNewPass)){ // If all fields filled out
			
			$(".load #1").show("fade",500);
			
				$.ajax({					
					type: "POST",
		 			url: "/adminInter/passUpdate",
		  			data: "{ \"oldPass\": \"" + oldPass + "\", \"newPass\": \"" + newPass + "\", \"uName\": \"" + uName + "\", \"captcha\":\"" + queryString + "\"}",
		  			beforeSend: function(xhr) {
		  				// here it is
		  				xhr.setRequestHeader(header, token);
		  			},
		  			error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
		  				alert("Post failed, please try again later.");
		  				$(".load #1").hide("fade",500);
		  				
		  		}}).done(function( msg ) { // Message completed
		 			$(".load #1").hide("fade",500);
		 			obj = JSON.parse(msg);
		 			if(obj.status == 0){ // If status successful
		 				alert("Password Updated Successfully");
		 				document.getElementById('oldPass').value = "";  
		    			document.getElementById('newPass').value = "";  
		    			document.getElementById('reNewPass').value = "";  
		    	
		 			} // End if status successful
		 			else if(obj.status == 1){ // Else if comment failed to post to server
		 				
		 				alert("Password Update Failed Due to Incorrect Password, Please Try Again");
		 			} 
		 			else{ // Else captcha not validated 
		 				
		 				alert("Failed to validate, please try to update again later");
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
	function formValidation(oldPass, newPass, reNewPass, queryString){
				
		var message = "Please fill out the following:"; 
		var test = true; 							    
				
		if(queryString == ''){ // If captcha not filled out
			
			alert("Please fill out captcha.");
			return false;
		} 
				
		if(oldPass == null || oldPass == ""){ // If email not filled out
			
			test = false;
			message = message + " Old Password";
		} 
		
		if(newPass == null || newPass == ""){ // If name not filled out
			
			test = false;
			if(message.indexOf("Old Password") != -1){		
				message = message + ", New Password";		
			}
			else{
				message = message + " New Password";
			}
		} 
		
		if(reNewPass == null || reNewPass == ""){ // If body not filled out
			test = false;

			if(message.indexOf("Old Password") != -1 || message.indexOf("New Password") != -1){
				message = message + ", ReTyped Password";
			}
			else{
				message = message + " ReTyped Password";
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
	 * The function below determines if the two entered passwords are equal	
	 */
	function comparePass(newPass, reNewPass){
		
		if(newPass != reNewPass){ // If passwords not equal
			
				alert("New Passwords Are Not The Same!!");
				return false;
		} 
		
		if(newPass.length < 8){
			
			alert("Bummers, password too short.");
			return false;
		}
			
		return true;	
	}	

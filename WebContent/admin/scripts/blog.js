	/**
	 * This function is how comments are posted to the servlet.
     * It is all handled through an ajax request where we send  
     * json to the server for processing.
	 */
	function sendMessage(){
				
		var title = $('#title').val();    					
		var body = tinyMCE.get('blog_body').getContent(); 
		var tags = $('#tag').val();    					 
		var emailNot = $('#emailNoti').val(); 
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
	
		if(formValidation(title, body, emailNot, queryString)){ // If all fields filled out
			
			var query = "{ \"title\": \"" + title + "\", \"body\": \"" + body.replace(/"/g, '&#8220') + 
			"\",\"emailNot\":\"" + emailNot + "\", \"captcha\":\"" + queryString + "\", \"date\": \"" + date;
			
			if(tags != null || tags != ""){
				query = query + "\", \"tags\":\"" + tags;
			}
			
			query = query + "\"}";
			
			$.ajax({				
				type: "POST",
				url: "/adminInter/blogReceiver",
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
			 			alert("Blog Posted Successfully");
			 			document.getElementById('title').value = ""; 
			 			document.getElementById('tag').value = ""; 
			    		tinymce.get('blog_body').setContent('');  
			    		document.getElementById('emailNoti').value = "";
			 		} // End if status successful
			 		else if(obj.status == 1){ // Else if comment failed to post to server
			 			alert("Blog Failed to post, Please Try Again");
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
	function formValidation(title, body, emailNot, queryString){
				
		var message = "Please fill out the following:"; 
		var test = true; 							  
				
		if(queryString == ''){ // If captcha not filled out
			alert("Please fill out captcha.");
			return false;
		} 
				
		if(emailNot == ''){ // If captcha not filled out
			alert("Fill in email notification text.");
			return false;
		}
		
		if(title == null || title == ""){ // If email not filled out
			test = false;
			message = message + " title";
		} 
		
		if(body == null || body == ""){ // If name not filled out
			test = false;
			if(message.indexOf("title") != -1){		
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
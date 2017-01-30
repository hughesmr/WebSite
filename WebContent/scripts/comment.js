	/**
     * This function is how comments are posted to the servlet.
     * It is all handled through an ajax request where we send 
     * json to the server for processing.
	 */
	function sendMessage(){
				
		var email = $('#email').val();						 
		var name = $('#name').val();    					
		var receiveEmail = (document.getElementById('recEmail')).checked;    		 
		var body = tinyMCE.get('comment_body').getContent(); 
		var queryString = $('#preventRe').serialize().replace('g-recaptcha-response=', ''); 
		
		if(formValidation(email, name, body, queryString)){ // If all fields filled out
			
			$(".load #1").show("fade",500);
			
			$.ajax({					
				type: "POST",
		 		url: "/commentReceiver?blogId="+$('#blogId').val(),
		  		data: "{ \"email\": \"" + email + "\", \"name\": \"" + name + "\", \"recEmail\": \"" + receiveEmail + 
		  			"\", \"body\": \"" + body.replace(/"/g, '&#8220') + "\", \"captcha\":\"" 
		  			+ queryString + "\"}",
		  		error: function(XMLHttpRequest, textStatus, errorThrown) { // Error for if server can't be reached
		  			alert("Post failed, please try again later.");
		  			$(".load #1").hide("fade",500);
		  				
		  	}}).done(function( msg ) { // Message completed
		 		$(".load #1").hide("fade",500);
		 		obj = JSON.parse(msg);
		 		if(obj.status == 0){ // If status successful
		 			
		 			alert("Comment Posted Successfully, it will appear after it is moderated.");
		 			document.getElementById('email').value = ""; 
		    		document.getElementById('name').value = "";  
		    		tinymce.get('comment_body').setContent('');  
		    		showHideCommentBox('createComment');	     
		 		} 
		 		else if(obj.status == 1){ // Else if comment failed to post to server
		 				
		 			alert("Comment Failed to post, Please Try Again");
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
	function formValidation(email, name, body, queryString){
				
		var message = "Please fill out the following:"; 
		var test = true; 							  
				
		if(queryString == ''){ // If captcha not filled out
			
			alert("Please fill out captcha.");
			return false;
		} 
				
		if(email == null || email == ""){ // If email not filled out
			
			test = false;
			message = message + " email";
		} 
		
		if(name == null || name == ""){ // If name not filled out
			
			test = false;
			if(message.indexOf("email") != -1){		
				message = message + ", name";		
			}
			else{
				message = message + " name";
			}
		}
		
		if(body == null || body == ""){ // If body not filled out
			test = false;

			if(message.indexOf("email") != -1 || message.indexOf("name") != -1){
				message = message + ", comment";
			}
			else{
				message = message + " comment";
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
	 * This function is used to show and hide the comment creation boxes.	
	 */
	function showHideCommentBox(id) {
		
    	if (document.getElementById(id).style.display == 'none') { // If display = none
        	
    		$( "#createComment" ).toggle( "slow", function() {
    			    // Animation complete.
    		});
    	} 
    	else { // Else display != none
        	document.getElementById(id).style.display = 'none';
    	} 
	} 
	
	/**
	 * The function below is used to load comments from the servlet
	 */
	function loadComment(page){
		
		$( "#comments" ).empty();
		$( "#comments" ).load( "/loadComment?blogId=" + $('#blogId').val() 
				+ "&comPageNum=" + page);
	} 
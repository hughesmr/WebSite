
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<script type="text/javascript" src="/scripts/MailSend.js"> </script>
		<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
		<link href="/styleSheets/contact.css" rel="stylesheet" type="text/css">
		<link href="/styleSheets/modal.css" rel="stylesheet" type="text/css">
		
		<title>Contact</title> 
	
	    <h2 class="headers">Contact</h2>
	    <p>Use the form below to send me a message. <a class="links" href="#privacy">Privacy</a></p>
	   

	 	<div id="privacy" class="modal">
			<div>
				<h2 class="headers">Privacy</h2>
				<p>Your information will only be used to contact you, after that it will be deleted.</p>
				<a href="#close" class="closeLink">Close</a>
			</div>
		</div>
	    
		<div id="load" class="load">
	   		
	    </div>
	  
	    	<img id="imgLoad" class="img" src="/images/load1.gif" />
	  
    	<div class="mdata">
    		<form action="commentReceiver" method="POST" id="preventRe">

 		     	<p>Email: </p>
 		     	<textarea id="address" cols="40" rows="1" class="small_text_box" required></textarea>

    	     	<p>Subject</p>
 		     	<textarea id="subject" cols="80" rows="1" class="small_text_box"></textarea>
 		     				
    			<p>Message</p>
    			<textarea id="body" class="textBox"></textarea>
    			<script>tinymce.init({width: "900", height: "250", selector:'#body'});</script>
				<br>
    			<div class="g-recaptcha" data-sitekey="6Le2UgcTAAAAAO75S5fxzlxWYK03zZkiSR3L2_Qq"></div>

    	
    			<p style="text-align:center;"><button type="button" onclick="sendMessage()">Send</button></p>
    	 	</form>
 		</div>  

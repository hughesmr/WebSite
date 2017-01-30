<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<link href="/admin/styles/mail.css" rel="stylesheet" type="text/css">	
		<link href="/admin/styles/base.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<script type="text/javascript" src="/admin/scripts/tinymce/tinymce.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/plupload/js/plupload.full.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/admin/scripts/datetimepicker-master/jquery.datetimepicker.css"/>
		<script src="/admin/scripts/datetimepicker-master/jquery.datetimepicker.js"></script>
		<script type="text/javascript" src="/admin/scripts/mail.js"> </script>

		<title>Email Update</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
	</head>
	<body class="background_img">
		<h1>Email Update</h1>
		
		<form action="/admin/j_spring_security_logout" method="post" id="logoutForm">
	  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
 
	<p class="logoutPosition"><a href="javascript:formSubmit()">Logout</a></p>
		<table class="formatTable" bgcolor="#FFFFFF">
			<tr>
				<td width="110px" class="cell">
					<div class="linkDiv">
						<h3>Menu</h3>
						<p><a href="/admin/main" class="links"><p>Main</p></a></p>
						<p><a href="/admin/createBlog" class="links">Create Blog</a></p>
						<p><a href="/admin/commentMod" class="links">Comments</a></p>
						<p><a href="/admin/settings" class="links">Settings</a></p>
						<p>Email Update</p>
						<p><a href="/admin/template" class="links">Template Update</a></p>
						<p><a href="/admin/carousel" class="links">Carousel</a></p>
					</div>
				</td>
				<td width="1000px" class="body_create">
				
					<form action="/adminInter/mailUpdateReceiver" method="POST" id="preventRe">
        				<p>Subject</p>
        				<textarea id="subject" cols="80" rows="1" class="textBox"></textarea>
        				<br>
        				<p>Message</p>
						<textarea id="message_body" class="tiny"></textarea>
						<script>tinymce.init({selector:'#message_body',plugins:["mike code"],toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image jbimages",relative_urls: false});</script>
						<br>	
						<p>Post Time And Date</p>
						<input id="_datetimepicker3" type="text">
						<br>	
						<br>
						<div class="g-recaptcha" data-sitekey="6Le2UgcTAAAAAO75S5fxzlxWYK03zZkiSR3L2_Qq"></div>
						<p style="text-align:center;"><button type="button" onclick="sendMessage()">Send</button></p> 
   		 			</form>

   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
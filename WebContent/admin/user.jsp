<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

        <meta name="uName" content="<%= request.getUserPrincipal().getName() %>"/>    
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<link href="/admin/styles/user.css" rel="stylesheet" type="text/css">	
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/user.js"> </script>
		<script src='https://www.google.com/recaptcha/api.js'></script>

		<title>Update Password</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
	</head>
	<body class="background_img">
		<h1>Update Password</h1>
		
		<form action="/admin/j_spring_security_logout" method="post" id="logoutForm">
	  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
 
		<a href="javascript:formSubmit()">Logout</a>
		
		<table class="formatTable">
			<tr>
				<td width="110px" class="cell">
					<div class="linkDiv">
						<h3>Main</h3>
						<p><a href="/admin/main" class="links"><p>Main</p></a>
						<p><a href="/admin/createBlog" class="links">Create Blog</a></p>
						<p><a href="/admin/commentMod" class="links">Comments</a></p>
						<p>Settings</p>
						<p><a href="/admin/mailUpdate" class="links">Email Update</a></p>
						<p><a href="/admin/template" class="links">Template Update</a></p>
						<p><a href="/admin/carousel" class="links">Carousel</a></p>
					</div>
				</td>
				<td width="1000px" class="body_create">
				
					<form action="passUpdate" method="POST" id="passUp">
        				<p>Old Password</p>
        				<input id="oldPass" class="textBox" type="password">
        				<br>
        				<p>New Password (length of 8 characters or more)</p>
        				<input id="newPass" class="textBox" type="password">
        				<br>
        				<p>Re-Type New Password</p>	
						<input id="reNewPass" class="textBox" type="password">
						<br>
						<br>
						<div class="g-recaptcha" data-sitekey="6Le2UgcTAAAAAO75S5fxzlxWYK03zZkiSR3L2_Qq"></div>
						<p style="text-align:center;"><button type="button" onclick="sendMessage()">Submit</button></p> 
   		 			</form>

   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
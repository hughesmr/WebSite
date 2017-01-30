<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<link href="/admin/styles/template.css" rel="stylesheet" type="text/css">
		<link href="/admin/styles/base.css" rel="stylesheet" type="text/css">	

		<title>Template Update</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
	</head>
	<body class="background_img">
		<h1 class="color">Template Update</h1>
		
		
		<form action="/admin/j_spring_security_logout" method="post" id="logoutForm">
	  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
 
		<p class="logoutPosition"><a href="javascript:formSubmit()">Logout</a></p>
		
		<table class="formatTable" bgcolor="#FFFFFF">
			<tr>
				<td width="110px" class="cell">
					<div class="linkDiv">
						<h3>Menu</h3>
						<p><a href="/admin/main" class="links">Main</a></p>
						<p><a href="/admin/createBlog" class="links">Create Blog</a></p>
						<p><a href="/admin/commentMod" class="links">Comments</a></p>
						<p><a href="/admin/settings" class="links">Settings</a></p>
						<p><a href="/admin/mailUpdate" class="links">Email Update</a></p>
						<p>Template Update</p>
						<p><a href="/admin/carousel" class="links">Carousel</a></p>
					</div>
				</td>
				<td width="1000px" class="body_create">

					<p>Select the template to update</p>
					<form action="/admin/templateUpdate">
						<input type="hidden" name="type" value="newBlog" />
    					<input type="submit" value="Update Blog Template">
					</form>
					<form action="/admin/templateUpdate">
						<input type="hidden" name="type" value="newsLetter" />
    					<input type="submit" value="Update NewsLetter Template">
					</form>
   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<link href="/admin/styles/settings.css" rel="stylesheet" type="text/css">
		<link href="/admin/styles/base.css" rel="stylesheet" type="text/css">	
		<script type="text/javascript" src="/admin/scripts/settings.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		
		<title>Settings</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
			
		</script>
		
	</head>
	<body class="background_img">
		<h1>Settings</h1>
		
		<form action="/admin/j_spring_security_logout" method="post" id="logoutForm">
	  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
 
		<p class="logoutPosition"><a href="javascript:formSubmit()">Logout</a></p>
		
		<table class="formatTable" bgcolor="#FFFFFF">
			<tr>
				<td width="110px" class="cell">
					<div class="linkDiv">
						<h3>Menu</h3>
						<p><a href="/admin/main" class="links"><p>Main</p></a>
						<p><a href="/admin/createBlog" class="links">Create Blog</a></p>
						<p><a href="/admin/commentMod" class="links">Comments</a></p>
						<p style="color: #190707">Settings</p>
						<p><a href="/admin/mailUpdate" class="links">Email Update</a></p>
						<p><a href="/admin/template" class="links">Template Update</a></p>
						<p><a href="/admin/carousel" class="links">Carousel</a></p>
					</div>
				</td>
				<td width="1000px" class="body_create">
        			<div id="loadableSettings"></div>
        			<script type="text/javascript">loadSettings();</script>
   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
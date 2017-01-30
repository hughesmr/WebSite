<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<link href="/admin/styles/carousel.css" rel="stylesheet" type="text/css">
		<link href="/admin/styles/base.css" rel="stylesheet" type="text/css">	
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/carousel.js"> </script>
		<script type="text/javascript" src="/admin/scripts/plupload/js/plupload.full.min.js"></script>

		
		<title>Carousel</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
			
		</script>
		
	</head>
	<body class="background_img">
		<h1>Carousel</h1>
		
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
						<p><a href="/admin/template" class="links">Template Update</a></p>
						<p>Carousel</p>
					</div>
				</td>
				<td width="1000px" class="body_create">
				
					<table class="settingTable" >
				 		<s:iterator value="images" var="im">
				 			<tr>
				 				<td class="settingTable">
    								<img style ="display: block; margin-left: auto; margin-right: auto;" src="/imageStore/images/<s:property value="#im[0]" />">
      								<p>Description: <s:property value="#im[1]" /></p>
      								<p>Relative URL: <s:property value="#im[3]" /></p>    
      								<form>
										<p style="text-align:center;"><button type="button" onclick="deleteImage(<s:property value="#im[2]" />)">Delete</button></p> 
									</form>  
      								<br>  
      							</td>
  							</tr>
  						</s:iterator>
  								
					</table>
					<form>
						<p style="text-align:center;"><button type="button" onclick="popup()">New Frame</button></p> 
					</form>
					
   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
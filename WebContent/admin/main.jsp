<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
   <%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<link href="/admin/styles/main.css" rel="stylesheet" type="text/css">	
		<link href="/admin/styles/base.css" rel="stylesheet" type="text/css">

		<title>Main</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
	</head>
	<body class="background_img">
		<h1>Main</h1>
		
		
		<form action="/admin/j_spring_security_logout" method="post" id="logoutForm">
	  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
 
		<p class="logoutPosition"><a href="javascript:formSubmit()">Logout</a></p>
		
		<table class="formatTable" bgcolor="#FFFFFF">
			<tr>
				<td width="110px" class="cell">
					<div class="linkDiv">
						<h3>Menu</h3>
						<p>Main</p>
						<p><a href="/admin/createBlog" class="links">Create Blog</a></p>
						<p><a href="/admin/commentMod" class="links">Comments</a></p>
						<p><a href="/admin/settings" class="links">Settings</a></p>
						<p><a href="/admin/mailUpdate" class="links">Email Update</a></p>
						<p><a href="/admin/template" class="links">Template Update</a></p>
						<p><a href="/admin/carousel" class="links">Carousel</a></p>
					</div>
				</td>
				<td width="1000px" class="body_create">
					<h3>Current Scheduled tasks</h3>
					<table class="settingTable" bgcolor="#00FF00">
				 		<s:iterator value="data" var="da">
				 			<tr>
				 				<td class="settingTable">
      								<p>Task Type: <s:property value="#da[2]" /></p>	
      							</td>
      							<td class="settingTable">
      								<p>Task Title: <s:property value="#da[1]" /></p>
      							</td>
      							<td class="settingTable">
      								<p>Task Start Date: <s:property value="#da[0]" /></p>
      							</td>
  							</tr>
  						</s:iterator>
  								
					</table>

   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
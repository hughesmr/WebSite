<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<link href="/admin/styles/emailTemplate.css" rel="stylesheet" type="text/css">	
		<link href="/admin/styles/base.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/template.js"> </script>

		
		<title>Template Update</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
			
		</script>
		
	</head>
	<body class="background_img">
		<h1><s:property value="type" /></h1>
		
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
						<p><a href="/admin/carousel" class="links">Carousel</a></p>
					</div>
				</td>
				<td width="1000px" class="body_create">
					<p>Tokens you can use: <s:property value="tokens" /></p>
	
					<form action="/adminInter/templateUpdate" method="POST">
    					<textarea id="templateToUpdate" rows="40" cols="100"><s:property value="body" /></textarea>
    					<script>
    						document.getElementById("templateToUpdate").onkeydown = function(e) {
    						  if (!e && event.keyCode == 9)
    						  {
    						    event.returnValue = false;
    						    insertAtCursor(document.getElementById("templateToUpdate"), "\t");
    						  }
    						  else if (e.keyCode == 9)
    						  {
    						    e.preventDefault();
    						    insertAtCursor(document.getElementById("templateToUpdate"), "\t");
    						  }
    							};

    						//http://alexking.org/blog/2003/06/02/inserting-at-the-cursor-using-javascript#comment-3817
    						function insertAtCursor(myField, myValue) {
    						  //IE support
    						  if (document.selection) {
    						    var temp;
    						    myField.focus();
    						    sel = document.selection.createRange();
    						    temp = sel.text.length;
    						    sel.text = myValue;
    						    if (myValue.length == 0) {
    						      sel.moveStart('character', myValue.length);
    						      sel.moveEnd('character', myValue.length);
    						    } else {
    						      sel.moveStart('character', -myValue.length + temp);
    						    }
    						    sel.select();
    						  }
    						  //MOZILLA/NETSCAPE support
    						  else if (myField.selectionStart || myField.selectionStart == '0') {
    						    var startPos = myField.selectionStart;
    						    var endPos = myField.selectionEnd;
    						    myField.value = myField.value.substring(0, startPos) + myValue + myField.value.substring(endPos, myField.value.length);
    						    myField.selectionStart = startPos + myValue.length;
    						    myField.selectionEnd = startPos + myValue.length;
    						  } else {
    						    myField.value += myValue;
    						  }
    						}
    						</script>
    					<br>
    					<p><button type="button" onclick="sendMessage('<s:property value="rawtype" />')">Update</button>
    					<button type="button" onclick="resetTemplate('<s:property value="rawtype" />')">Reset Template</button></p> 
					</form>
   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
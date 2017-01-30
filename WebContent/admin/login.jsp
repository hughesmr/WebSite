<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>Login Page</title>
		<link href="/styleSheets/login.css" rel="stylesheet" type="text/css">	
		<script type="text/javascript" src="/scripts/login.js"> </script>
	</head>
	<body onload='document.loginForm.username.focus();'>
 
		<div id="login">
 
			<h2>Login</h2>
 
			<div id="invalid" style="display:none;">
				<div class="ERROR">Wrong Creds</div>
			</div>
			<div id="logout" style="display:none;">
				<div class="msg">Logged Out</div>
			</div>
			<script>
				getUrlParameter();
			</script>
 
			<form name='loginForm' action="/admin/login" method="POST">
		  		<table>
					<tr>
						<td>User:</td>
						<td><input type='text' name='username' value=''></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type='password' name='password' /></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" type="submit" value="submit" /></td>
					</tr>
		  		</table>
 
		  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</body>
</html>
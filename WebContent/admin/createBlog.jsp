<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<link href="/admin/styles/createBlog.css" rel="stylesheet" type="text/css">	
		<link href="/admin/styles/base.css" rel="stylesheet" type="text/css">
		<link href="/admin/styles/prism.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script src='https://www.google.com/recaptcha/api.js'></script>
<!--  	 <script src="//tinymce.cachefly.net/4.0/tinymce.min.js"></script> -->
		 <script type="text/javascript" src="/admin/scripts/blog.js"> </script>
		<script type="text/javascript" src="/admin/scripts/tinymce/tinymce.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/prism.js"> </script>
		<script type="text/javascript" src="/admin/scripts/plupload/js/plupload.full.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/admin/scripts/datetimepicker-master/jquery.datetimepicker.css"/>
		<script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js?autoload=true&amp;skin=sunburst&amp;"></script>
		<script src="/admin/scripts/datetimepicker-master/jquery.datetimepicker.js"></script>


		<title>Create Blog</title>
		
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
		
	</head>
	<body class="background_img">
		<h1>Create Blog</h1>

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
						<p>Create Blog</p>
						<p><a href="/admin/commentMod" class="links">Comments</a></p>
						<p><a href="/admin/settings" class="links">Settings</a></p>
						<p><a href="/admin/mailUpdate" class="links">Email Update</a></p>
						<p><a href="/admin/template" class="links">Template Update</a></p>
						<p><a href="/admin/carousel" class="links">Carousel</a></p>
					</div>
				</td>
				<td width="1000px" class="bodyCreate">	 
					
					<form action="/adminInter/blogReceiver" method="POST" id="preventRe">
        				<p>Title</p>
        				<textarea id="title" cols="80" rows="1" class="textBox"></textarea>
        				<br>
        				<p>Tags (comma delimited without space)</p>
        				<textarea id="tag" cols="80" rows="1" class="textBox"></textarea>
        				<br>
        				<p>Blog</p> <p>To add code snippet: &lt;pre class="prettyprint" style="border:2px solid #BDBDBD"&gt; &lt;/pre&gt;</p>
						<textarea id="blog_body" class="tiny"></textarea>
						<script>tinymce.init({selector:'#blog_body',plugins:["mike code codesample"],
							toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image jbimages",
							relative_urls: false});</script>
						<br>
						<p>Email Notification Text</p>
						<textarea id="emailNoti" rows="20" cols="80"></textarea>
						<br>
						<p>Post Time And Date (IF YOU DON'T SELECT A TIME THE CURRENT TIME IS SELECTED BY DEFAULT)</p>
						<input id="_datetimepicker3" type="text">
						<br>	
						<br>
						<div class="g-recaptcha" data-sitekey="6Le2UgcTAAAAAO75S5fxzlxWYK03zZkiSR3L2_Qq"></div>
						<p style="text-align:center;"><button type="button" onclick="sendMessage()">Post</button></p> 
   		 			</form>
   		 		</td>
   		 	</tr>
		</table>
	</body>
</html>
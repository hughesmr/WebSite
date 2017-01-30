<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Carousel</title>
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<link href="/admin/styles/carousel.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/carousel.js"> </script>
		<script type="text/javascript" src="/admin/scripts/plupload/js/plupload.full.min.js"></script>
</head>
<body>
	<table>
		<tr>
			<td>
				<p> Current Carousel Image Size:	width:900px; height:364px;</p>
				<ul id="filelist"></ul>
					<br/> 
					<div id="container">
    					<a id="browse" href="javascript:;"><button type="button">Browse</button></a>
    					<a id="start-upload" href="javascript:;"><button type="button">Upload</button></a>
					</div>
 
					<br/>
					<pre id="console"></pre>
					<script type="text/javascript">

						var token = $("meta[name='_csrf']").attr("content");
	
						var uploader = new plupload.Uploader({
										required_features: true,
										runtimes : 'html5',
  										browse_button: 'browse', // this can be an id of a DOM element or the DOM element itself
  										url: '/adminInter/imageUpload',
  										headers:{'X-CSRF-TOKEN': token}
						});
 
						uploader.init();
						uploader.bind('FilesAdded', function(up, files) {
  							var html = '';
  							plupload.each(files, function(file) {
    							html += '<li id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></li>';
  							});
  							document.getElementById('filelist').innerHTML += html;
						});
 
						uploader.bind('UploadProgress', function(up, file) {
  							document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
						});
 
						uploader.bind('Error', function(up, err) {
  							document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
						});	
 
						document.getElementById('start-upload').onclick = function() {
  							uploader.start();
						};
		
						uploader.bind('FileUploaded', function(up, file, info) {
		   
							document.getElementById('newimage').innerHTML = '<img style ="display: block; margin-left: auto; margin-right: auto;" src="/imageStore/images/' + info.response +'">';
							$('#name').val(info.response);
						}

						);
		
				</script>
			</td>
		</tr>
		<tr>
			<td>
				<div id="newimage"></div>
				<form>
					<p>Create A Description</p>
					<input type="hidden" id="name" value="">
					<textarea id="descrip" rows="0" cols="100"></textarea>
					<p>Relative URL</p>
					<textarea id="address" rows="0" cols="100"></textarea>
					<p style="text-align:center;"><button type="button" onclick="sendMessage()">Post</button></p>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>
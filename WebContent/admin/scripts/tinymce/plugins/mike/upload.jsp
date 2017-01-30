<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
	<title>Upload an image</title>
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
				<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/tinymce/tinymce.min.js"> </script>
		<script type="text/javascript" src="/admin/scripts/plupload/js/plupload.full.min.js"></script>
		
		
		<style>
		.upload_browse_button {
  font: bold 11px Arial;
  text-decoration: none;
  background-color: #EEEEEE;
  color: #333333;
  padding: 2px 6px 2px 6px;
  border-top: 1px solid #CCCCCC;
  border-right: 1px solid #333333;
  border-bottom: 1px solid #333333;
  border-left: 1px solid #CCCCCC;
}
		</style>
		
		
		
	</head>
	<body>

		<ul id="filelist"></ul>
		<br /> 
		<div id="container">
    		<a id="browse" href="javascript:;"><button type="button">Browse</button></a>
    		<a id="start-upload" href="javascript:;"><button type="button">Upload</button></a>
		</div>
 
		<br />
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
		    

		    var w = (!window.frameElement && window.dialogArguments) || opener || parent || top;
		
			tinymce = w.tinymce;

			tinymce.EditorManager.activeEditor.insertContent('<img style ="display: block; margin-left: auto; margin-right: auto;" src="/imageStore/images/' + info.response +'">');
		    
		}


		);
		
 
	</script>

	</body>
</html>
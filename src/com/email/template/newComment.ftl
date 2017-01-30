<html>
	<head>
		<style>
			.title_date{
				display: inline; 
			}
			.date{
				float: right;
				display: inline;
			}
			p{ 
				font-size:15px;
				font-family: 'Josefin Sans', sans-serif;
    			font-weight: 100;
			}
		</style>
	</head>
	<body>
		<div class="title_date"> <p class="name">${name}</p>
    		<p class="date"><font size="2">${email}</font></p>
    	</div>
        <p>${comment}</p>
        <p><a href="${url}/blogview?blogId=${blogid}">Read Blog Here</a>
        <p><a href="${url}/admin/commentMod">Moderate Here</a>
	
	</body>
</html>
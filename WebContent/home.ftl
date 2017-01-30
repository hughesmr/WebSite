		<title>Home</title>
		<link href="/styleSheets/home.css" rel="stylesheet" type="text/css">

		<div>
			<div id="car" class="carousel">
				<#assign x = 1>
				<#list images as im>
					<a href="${im[3]}">
    					<img id="${x}" class="img" src="/imageStore/images/${im[0]}" border="0" alt="${im[1]}"/>
					</a> 
					<#assign x = x + 1> 
				</#list>
			</div>
			<div class="button_div">
				<ol class="carousel_indicators"> 
					<#list 1..Size as i>
						<#if i == 1>
  							<li onclick="display(${i})" id="1-${i}" class="active"></li>
  						<#else>
    						<li onclick="display(${i})" id="1-${i}"></li>
    					</#if>
					</#list>
  				</ol>
  			</div>
		</div>
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script type="text/javascript" src="/scripts/MainPage.js"> </script>
		<script>document.getElementById("car").onload=Carousel();</script>
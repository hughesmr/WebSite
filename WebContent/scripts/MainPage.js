var timeoutID = null;
var count = 1;
var sc = null;

/**
 * The function below controls the slideshow
 */
function Carousel(){
	
	$(".carousel #1").show("fade",900);
	timeoutID = setTimeout(trans, 5500);		    
	sc = $(".carousel img").size();		

	setInterval(function(){ 
				
		timeoutID = setTimeout(trans, 5500);
	},6500);				
}
	
/**
 * trans controls transitions int he slideshow
 */
function trans() {
	
	$("li").removeClass('active');
	$(".carousel #" + count).hide("slide", {direction:'left'}, 500);
				
	if(count == sc){
		
		count = 1;
	}
	else{
					
		count = count + 1;
	}
				
	$(".carousel #"+count).delay(500).show("slide",{direction:'right'}, 500);
	$("li#1-"+count).addClass('active');			
}
	
/**
 * Display displays an image in the slideshow
 */
function display(num){
				
	clearTimeout(timeoutID);
		
	if(num > count){
		
		$("li").removeClass('active');
		$(".carousel #" + count).hide("slide", {direction:'left'}, 500);
		$(".carousel #"+ num).delay(500).show("slide",{direction:'right'}, 1000);
		$("li#1-"+num).addClass('active');
	}
	else if (num < count){
		
		$("li").removeClass('active');
		$(".carousel #" + count).hide("slide", {direction:'right'}, 500);
		$(".carousel #"+ num).delay(500).show("slide",{direction:'left'}, 1000);
		$("li#1-"+num).addClass('active');							
	} 
	count = num;				
}
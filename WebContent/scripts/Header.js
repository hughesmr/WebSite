/**
 * The function below creates a dynaimcally resizing header
 */
$(function(){
			 
	$(window).scroll(function() {
		var position = currentPosition();
			if (position >= 90){
				$('.header1').addClass('shrink');
			    $('.ban').addClass('shrink');
			}
			else {
			     $('.header1').removeClass('shrink');
			     $('.ban').removeClass('shrink');
			}
	});
	function currentPosition() {
		return window.pageYOffset || document.documentElement.scrollTop;
	}
});
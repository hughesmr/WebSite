/**
 * The function below is used for logins
 */
function getUrlParameter(){
	
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == "error") 
        {
        	document.getElementById("invalid").style.display = "";
        }
        else if(sParameterName[0] == "logout"){
        	document.getElementById("logout").style.display = "";

        }
    }
} 
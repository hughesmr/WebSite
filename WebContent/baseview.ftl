<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!--  <title>My Super Sweet Title</title>-->
		<%@  include file="Header.jsp" %>
		
		<link href="/styleSheets/baseview.css" rel="stylesheet" type="text/css">

	</head>
	<body>
		<table style="width:100%;">
   			  <tr>
    		  </tr>
    		<tr>
        	  	<td class="cell"></td>
        		<td width="900px" class="main">
        		      
        		    <tiles:insertAttribute name="body"/>
        			<!-- Add Extra spacing at the bottom of page -->
        		
        		</td>
        		<td class="cell"></td> 
    		</tr>
    		<tr>
        		<td height="30" width="100" colspan="3">
        			<%@ include file="Footer.jsp" %>
        		</td>
    		</tr>
		</table>
	</body>
</html>
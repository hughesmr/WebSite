<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
	<head>
		<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
		<link href="/styleSheets/baseview.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div style="height:300px;">
			<%@  include file="Header.jsp" %>
		</div>
		<div style="height: 100%;">
			<table style="width:100%;">
   				<tr>
    		  	</tr>
    			<tr>
        	  		<td width="12%" class="left_cell"></td>
        			<td width="76%" class="main">
        		    	<tiles:insertAttribute name="body"/>
        			</td>
        			<td width="12%" class="right_cell">
        				<tiles:insertAttribute name="right"/>
        			</td> 
    			</tr>
    			<tr>
        			<td height="30" width="100" colspan="3">
        			</td>
    			</tr>
			</table>
			<%@ include file="Footer.jsp" %>
		</div>
	</body>
</html>
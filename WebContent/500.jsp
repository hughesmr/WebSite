<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/styleSheets/baseview.css" rel="stylesheet" type="text/css">
		<title>500 Server Error</title>
	</head>
	<body>
		<%@ include file="Header.jsp" %>
		<table style="width:100%;">
   			<tr>
    		</tr>
    		<tr>
        	  	<td width="12%" class="left_cell"></td>
        		<td width="76%" class="main">
        			<div style="height:360px;">
        		    	<p>Welp, server error. Try again later :(</p>
        		    </div>
        		</td>
        		<td width="12%" class="right_cell">
        			<!-- <tiles:insertAttribute name="right"/>-->
        		</td> 
    		</tr>
    		<tr>
        		<td height="30" width="100" colspan="3">
        			<!--  <%@ include file="Footer.jsp" %>-->
        		</td>
    		</tr>
		</table>
		<%@ include file="Footer.jsp" %>
	</body>
</html>
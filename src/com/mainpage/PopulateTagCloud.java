package com.mainpage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tags.TagCloud;

/**
 * PopulateTagCloud populates the tag cloud
 */
public class PopulateTagCloud extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * doGet gets the tag cloud data
	 * 
	 * @param request is the request
	 * @param response is the response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		TagCloud tags = new TagCloud();
		
		response.getWriter().write(tags.getCloud());
		
	} 
} 

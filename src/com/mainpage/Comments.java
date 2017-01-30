package com.mainpage;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import com.dbInterface.CommentData;
import com.dbInterface.LoadComment;

/**
 * This class is used as the interface for a servlet to get lists of comments.
 */
public class Comments extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	/**
	 * doGet is used to process get requests and return comment.ftl compiled to the end user
	 * 
	 * @param request is the request
	 * @param response is the response
	 */	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String blogId = null; 					
		String pageNum = null; 				
		Template view = null;
		CommentData comData = null;
		LoadComment comment = null;

		blogId = request.getParameter("blogId");
		pageNum = request.getParameter("comPageNum");
		if(pageNum.equals("")){
			pageNum = "1";
		}
	
		comment = new LoadComment(blogId, pageNum);
	
		comData = comment.loadCommentsDb();

		Configuration cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "");
		view = cfg.getTemplate("comment.ftl");
	
		Map<String, Object> input = new HashMap<String, Object>();    
        input.put("comments", comData.getComments());
        input.put("pages", comData.getPages());
        input.put("requestPg", (Integer.parseInt(pageNum)+1));

        Writer out = response.getWriter();

        try {
			view.process(input, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	} 
}
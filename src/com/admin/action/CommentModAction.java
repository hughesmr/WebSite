package com.admin.action;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dbInterface.CommentData;
import com.admin.dbinterface.LoadCommentsMod;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * This class is used to moderate comments
 */
public class CommentModAction extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	/**
	 * doGet gets handles comment moderaiton
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
		String pageNum = null; 					
		Template view = null;
		CommentData comData = null;
		LoadCommentsMod comment = null;
		pageNum = request.getParameter("comPageNum");
			
		if(pageNum.equals("")){
			pageNum = "1";
		}
		
		comment = new LoadCommentsMod(pageNum);
		comData = comment.loadComments();

		Configuration cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(), "");
		view = cfg.getTemplate("/admin/commentMod.ftl");
		
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

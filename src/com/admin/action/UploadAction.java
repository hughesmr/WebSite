package com.admin.action;

import java.io.File;  
import java.io.IOException;  
import java.util.Iterator;  
import java.util.List;    
  

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  

import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileUploadException;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload;  


/**
 * This class is used to upload images onto the server 
 */

public class UploadAction extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * doPost stores the actual image on the hard drive 
	 */
	@SuppressWarnings("unchecked")  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	
    	@SuppressWarnings("rawtypes")
		List fileList = null;
    	String name = "";  
        String extName = "";
        Iterator<FileItem> it = null;
        String savePath = this.getServletConfig().getServletContext().getRealPath("");  
        DiskFileItemFactory fac = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(fac);
        savePath = savePath + "/../imageStore/images/";  
       
        File f1 = new File(savePath); 
      
        if (!f1.exists()) {// If save directory does not exist 
            f1.mkdirs();  
        }
          
        upload.setHeaderEncoding("utf-8");  
          
        try {  
            fileList = upload.parseRequest(request);  
        } catch (FileUploadException ex) {  
            return;  
        }  
        
        it = fileList.iterator();  
        
        while (it.hasNext()) { 
        	
            FileItem item = it.next();  
            if (!item.isFormField()) {  
                name = item.getName();   
                 
                if (name == null || name.trim().equals("")) {  
                    continue;  
                }  
                    
                if (name.lastIndexOf(".") >= 0) {  
                    extName = name.substring(name.lastIndexOf("."));  
                }  
                name = name.replace(extName,"");
     
                
                String num = "";
                int i = 0;
                File file = null;  
                do {  
 
                    file = new File(savePath + name + num + extName);  
                    num = Integer.toString(i);
                    i++;
                } while (file.exists());  
                File saveFile = new File(savePath + file.getName());  
                name = file.getName();
                try {  
                    item.write(saveFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        response.getWriter().print(name);  
    }
}

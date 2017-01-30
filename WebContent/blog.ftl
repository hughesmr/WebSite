
		<link href="/styleSheets/blog.css" rel="stylesheet" type="text/css">	
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js?autoload=true&amp;skin=sunburst&amp;"></script>
		<title>Blog</title>

		<div style="margin-top:10px;">
			<#list BlogData.blogBody as b>
				<#if (b[0])?matches("There are no blogs yet")>
					<p>${b[0]}</p>
					<hr>
				<#else>
  					<div class="title_date"><p class="title">${b[1]}</p>
			    		<p class="date"><font size="2">${b[2]}</font></p>
			    	</div>
			    
			    	<#if (b[4])??>
			    		<p>${b[4]}</p>
			    		<span><span>${b[0]}</span>... <a href="blogview?blogId=${b[3]}" class="blog_links">View More..</a> </span>
			    	
			   	 	<#else>
			    		<span><span>${b[0]}</span>... <a href="blogview?blogId=${b[3]}" class="blog_links">View More..</a> </span>
				 	</#if>
						<div class="tags">
			    			<#if (b[5])??>	
			    				<p> Tags:
			    					<#list b[5] as tag>
			    						<a href="/tags?tag=${tag}" class="tagColor">${tag}</a>,
  									</#list>
  								</p>
  							</#if>
  						</div>
  					<hr>
  				</#if>
  			</#list>
  		</div>
  		<div class="button_alignment"> 

  			<#if (BlogData.pageNum > 1)>
      	 		<form action="blog">
      	 			<input type="hidden" value=${BlogData.pageNum} name="pageNum">
            		<button type="submit" class="next_prev_button" value="Next" name="buttonName">
            			<img src="/images/nextNoShadow.png" height="18px" width="18px">
            		</button>
       			</form> 
     		<#else>
     			<form action="blog">
     				<input type="hidden" value=${BlogData.pageNum} name="pageNum">
            		<button type="submit" class="next_prev_button" value="Next" name="buttonName" disabled="true">
            			<img src="/images/next.png" height="18px" width="18px">
            		</button>
       			</form>
     		</#if>
     
     		<p style="display:inline;">
            	<#list BlogData.pages as p>
            	      
  		    		<#if (BlogData.pageNum == (p+1))>
    					<font color="grey"> ${p + 1}</font>
    				<#else>
    					<a href="/blog?pageNum=${p}" class="blog_links">${p+1}</a>
    				</#if>
  				</#list>
  			</p>	
     		
     		<#if (BlogData.pageNum < BlogData.maxPage)>   
   				<form action="blog">
   					<input type="hidden" value=${BlogData.pageNum} name="pageNum">
            		<button type="submit" class="next_prev_button" value="Previous" name="buttonName">
            			<img src="/images/prevNoShadow.png" height="18px" width="18px">
            		</button>
       			</form>
     		<#else>
     			<form action="blog">
     				<input type="hidden" value=${BlogData.pageNum} name="pageNum">
            		<button type="submit" class="next_prev_button" value="Previous" name="buttonName" disabled="true">
            			<img src="/images/prev.png" height="18px" width="18px">
            		</button>
       			</form>
     		</#if>  	  
     	</div>	

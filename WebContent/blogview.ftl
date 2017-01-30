
		<link href="/styleSheets/blogview.css" rel="stylesheet" type="text/css">
		<link href="/styleSheets/modal.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"> </script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"> </script>
		<script type="text/javascript" src="/scripts/comment.js"> </script>
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
		<script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js?autoload=true&amp;skin=sunburst&amp;"></script>
	
		<title>${blogData.title}</title>

		<div style="margin-top:10px;">
			<div class="title_date"> 
				<p class="title">${blogData.title}</p>
		    	<p class="date"><font size="2">${blogData.date}</font></p>
		    </div>
		    
		    <p>${blogData.body}</p>
		    <br/>
		    <div class="tags">
		    	<#if (blogData.tags)??>
		    		<p> Tags: 
		    			<#list blogData.tags as b>
		    				<a href="/tags?tag=${b}" class="tagColor">${b}</a>,
		    			</#list>
		    		</p>
		    	</#if>
		    </div>   
	  	</div>

		<!-- Place buttons at bottom of right of screen -->
		
		<div class="button_alignment">	
  			
			<input type="hidden" name="blogId" value="${blogData.blogId}" id="blogId"/>
			
          	<#if (blogData.blogId < blogData.maxPage)>   
   				<form action="blogview">
                    <input type="hidden" value=${blogData.blogId} name="blogId">
           	 		<button type="submit" class="next_prev_button" value="Newer" name="buttonName">
            			<img src="/images/nextNoShadow.png" height="18px" width="18px">
            		</button>
       			</form>  
     		<#else>
     			<form action="blogview">
           		    <input type="hidden" value=${blogData.blogId} name="blogId">
           	 		<button type="submit" class="next_prev_button" value="Newer" name="buttonName" disabled="true">
        	    		<img src="/images/next.png" height="18px" width="18px">
        	    	</button>
       			</form>
     		</#if>
    		<#if (blogData.blogId > 1)>
      	 		<form action="blogview">
        	    	<input type="hidden" value=${blogData.blogId} name="blogId">
            		<button type="submit" class="next_prev_button" value="Older" name="buttonName">
            			<img src="/images/prevNoShadow.png" height="18px" width="18px">
            		</button>
       			</form> 
     		<#else>
     			<form action="blogview">
     				<input type="hidden" value=${blogData.blogId} name="blogId">
            		<button type="submit" class="next_prev_button" value="Older" name="buttonName" disabled="true">
        	    			<img src="/images/prev.png" height="18px" width="18px">
        	    	</button>
       			</form>
     		</#if>
          </div>
          	
          <hr>
          <div id="comments"></div>
          
          	<script type="text/javascript">loadComment(0);</script>	
			
			<input type="button" onclick="showHideCommentBox('createComment')" value="Add Comment"> <br/>
				<div id="createComment" style="display:none;">
				
        			<div class="load_gif">
	    				<img id="1" class="load_gif_image" src="/iamges/load1.gif" border="0" width="300px" height="100px"/>
	    			</div>
        		
        			<form action="commentReceiver" method="POST" id="preventRe">
        				<p>Note that your comment will first be moderated before posting.</p>
        				<p>Name</p>
        				<textarea id="name" cols="80" rows="1" class="textBox"></textarea>
        				</br>
        				<p>Email Address. <a class="links" href="#privacy">Privacy</a></p>
        				<div id="privacy" class="modal">
							<div>
								<h2 class="headers">Privacy</h2>
								<p>Your information will not be sold to anyone. I just use it as a unique identifier and use it if you want email updates.</p>
								<a href="#close" class="closeLink">Close</a>
							</div>
						</div>
        				<textarea id="email" cols="80" rows="1" class="textBox"></textarea>
        				</br>
        				<p>Comment</p>
						<textarea id="comment_body" style="height: 100%;"></textarea>
						<script>tinymce.init({selector:'#comment_body'});</script>
						<br>
						<p>Recieve Updates By Email? <input id="recEmail" type="checkbox"></p>
						<br>
						<div class="g-recaptcha" data-sitekey="6Le2UgcTAAAAAO75S5fxzlxWYK03zZkiSR3L2_Qq"></div>
						<p style="text-align:center;"><button type="button" onclick="sendMessage()">Post</button></p> 
   		 			</form>
   		 		</div>

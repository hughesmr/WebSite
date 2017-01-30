<div style="margin-top:10px;">

	<#assign rpg = requestPg>
	<#assign x = 0> 

		<#list comments as com>
			<div class="title_date"> <p class="name">${com[1]}</p>
    			<p class="date"><font size="2">${com[2]}</font></p>
    		</div>
        	<p>${com[0]}</p>
        	<#if (com[4])??>
        		<p> 
        			View the blog here.. or don't, I couldn't care less: 
        			<a href="/blogview?blogId=${com[4]}">${com[5]}</a>
        		</p>
        	</#if>
        	<#if (com[3])??>
        		<p style="text-align:right;">
        			<input type="radio" name="approved${x}" value="app${com[3]}">approve
        			<input type="radio" name="approved${x}" value="del${com[3]}">delete
        		</p>
  				<hr>
  			</#if>
  			<#assign x = x + 1>
		</#list>
	<input type="hidden" id="count" value=${x} name="count">
	
	<div>	
		<p style="display:inline;">
    		<#list pages as p>
    			<#if (rpg == (p+1))>
    				<font> ${p + 1}</font>
    			<#else>
    				<a href="#" onclick="loadComment(${p})"> ${p + 1}</a>
    			</#if>	
  			</#list>
  		</p>
  	</div>
</div>
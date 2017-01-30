		<div style="margin-top:10px;">

			<#assign rpg = requestPg>

			<#list comments as com>
				<div class="title_date"> <p class="name">${com[1]}</p>
    				<p class="date"><font size="2">${com[2]}</font></p>
    			</div>
        		<p>${com[0]}</p>
  				<hr>
			</#list>
	
			<p style="display:inline;">
    			<#list pages as p>
    				<#if (rpg == (p+1))>
    					<font color="grey"> ${p + 1}</font>
    				<#else>
    					<a href="#" onclick="loadComment(${p})" style="color:22C1AC;"> ${p + 1}</a>
    				</#if>	
  				</#list>
  			</p>
		</div>


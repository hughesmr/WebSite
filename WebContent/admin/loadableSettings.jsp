<%@ taglib prefix="s" uri="/struts-tags" %>

<h3>General Settings</h3>

<table class="settingTable">
	<tr>
		<td class="settingTable">
			<p style="color:#DF0101">
				<s:iterator value="banWords">
    				<s:property escapeHtml="false"/>,
    			</s:iterator> 
    		</p>
    		<div class="settingsDiv">
    			<p>bannedWord - Enter a word to ban, to un-ban enter a word that has already been entered</p>
    			<form>
    				<textarea id="bannedWord" rows="1" cols="50"></textarea>
    				<button type="button" onclick="sendMessage('bannedWord')">Update</button>
    			</form>
    		</div>
    	</td>
    </tr>
    <tr>
    	<td class="settingTable">
    		<div class="settingsDiv">
    			<p>instance.url - Set your instances url</p>
    			<form>
    				<textarea id="instance-url" rows="1" cols="50"><s:property value="Url" /></textarea>
    				<button type="button" onclick="sendMessage('instance.url')">Update</button>
    			</form>
    		</div>	
    	</td>
    </tr>
</table>

<h3>Mail Settings</h3>

<table class="settingTable">
    <tr>
    	<td class="settingTable">
    		<div class="settingsDiv">
    			<p>sleep.email - Set the time in between mail batchs</p>
    			<form>
    				<textarea id="sleep-email" rows="1" cols="50"><s:property value="Emailsleep" /></textarea>
    				<button type="button" onclick="sendMessage('sleep.email')">Update</button>
    			</form>
    		</div>
    	</td>
    </tr>
    <tr>
    	<td class="settingTable">
    		<div class="settingsDiv">
    			<form>
    				<p>from.email - Set the address that you want emails sent from</p>
    				<textarea id="from-email" rows="1" cols="50"><s:property value="Fromadd" /></textarea>
    				<button type="button" onclick="sendMessage('from.email')">Update</button>
    			</form>
    		</div>
    	</td>
    </tr>
    <tr>
    	<td class="settingTable">
    		<div class="settingsDiv">
    			<p>instance.admin.email - Set the email where you want comment messages sent</p>
    			<form>
    				<textarea id="instance-admin-email" rows="1" cols="50"><s:property value="Adminemail" /></textarea>
    			    <button type="button" onclick="sendMessage('instance.admin.email')">Update</button>
    			</form>
    		</div>
    	</td>
    </tr>
    <tr>
    	<td class="settingTable">
    		<div class="settingsDiv">
    			<p>batch.email - Set the number of emails to send at once</p>
    			<form>
    				<textarea id="batch-email" rows="1" cols="50"><s:property value="Mailbatch" /></textarea>
    				<button type="button" onclick="sendMessage('batch.email')">Update</button>
    			</form>
    		</div>
    	</td>
    </tr>
        <tr>
    	<td class="settingTable">
    		<div class="settingsDiv">
    			<p>unsubscribe.time.email - The number of weeks an email unsubscribe token is valid</p>
    			<form>
    				<textarea id="unsubscribe-time-email" rows="1" cols="50"><s:property value="unsubscribeEmail" /></textarea>
    				<button type="button" onclick="sendMessage('unsubscribe.time.email')">Update</button>
    			</form>
    		</div>
    	</td>
    </tr>
</table>

<h3>User Settings</h3>

<table class="settingTable">
	<tr>
		<td class="settingTable">
			<div class="settingsDiv">
				<p>Update Your Password</p>
				<form action="/admin/user">
					<input type="submit" value="Update Password">
				</form>
			</div>
		</td>
	</tr>
</table>

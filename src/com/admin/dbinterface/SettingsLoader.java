package com.admin.dbinterface;

import com.admin.properties.PropertyLoader;

/**
 * This class is used to get settings
 * for admin
 */
public class SettingsLoader {
	
	/**
	 * updateProperty is used to force an update of properties in memory
	 * 
	 * @param type is the type of update
	 */
	public static void updateProperty(String type){
		
		PropertiesLoader props = new PropertiesLoader();
		
		if(type.equals("bannedWord")){
			PropertyLoader.bannedWords = props.getBannedWords();
		}
		else if(type.equals("instance.url")){
			PropertyLoader.url = props.getUrl();
		}
		else if(type.equals("batch.email")){
			PropertyLoader.mailBatch = props.getMailBatch();
		}
		else if(type.equals("instance.admin.email")){
			PropertyLoader.adminEmail = props.getAdminEmail();
		}
		else if(type.equals("from.email")){
			PropertyLoader.fromAdress = props.getFromAdress();
		}
		else if(type.equals("sleep.email")){
			PropertyLoader.sleepTime = props.getSleepTime();
		}
	} 	
} 

package com.admin.action;

import java.util.List;

import com.admin.dbinterface.MainPageData;
import com.opensymphony.xwork2.ActionSupport;

public class MainPageAction extends ActionSupport{

	List<String[]> data = null;

	/**
	 * execute is ran during admin main page load
	 */
	public String execute() throws Exception {
		
		MainPageData d = new MainPageData();
		data = d.loadData();
		
		return  SUCCESS;
	}
	
	/**
	 * getData gets main page data
	 * 
	 * @return a list of mainpage data
	 */
	public List<String[]> getData(){
		
		return data;
	}
}

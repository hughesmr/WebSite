package com.admin.action;

import java.util.List;

import com.dbInterface.LoadCarousel;
import com.opensymphony.xwork2.ActionSupport;

/**
 * CarouselAction is called to load carousel data
 */
public class CarouselAction extends ActionSupport{

	private List<String[]> carousel;

	/**
	 * execute is called on page load
	 */
	public String execute() throws Exception {
		
		LoadCarousel l = new LoadCarousel();
		
		carousel = l.getImages();
		
		return  SUCCESS;
	}
	
	/**
	 * getImages gets the images
	 * 
	 * @return a list of image addresses
	 */
	public List<String[]> getImages(){
		
		return carousel;
	}
}

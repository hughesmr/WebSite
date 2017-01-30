package com.mainpage;

import java.util.List;

import com.dbInterface.LoadCarousel;
import com.opensymphony.xwork2.ActionSupport;

/**
 * MainPageAction is the action for the mainpage 
 */
public class MainPageAction extends ActionSupport{

	private List<String[]> carousel;
	
	/**
	 * execute is called during execution to render the page
	 * 
	 * @return success
	 */
	public String execute() throws Exception {
		
		LoadCarousel l = new LoadCarousel();
		
		carousel = l.getImages();
		
		return  SUCCESS;
		
	}
	
	/**
	 * getImages returns the address of the images
	 * 
	 * @return the image addresses
	 */
	public List<String[]> getImages(){
		
		return carousel;
	}
	
	/**
	 * getSize sets the size of stored carousel images
	 * 
	 * @return the size
	 */
	public int getSize(){
		
		int size = carousel.size();
		if(size == 0){
			return 1;
		}
		else{
			return size;
		}
	}
}

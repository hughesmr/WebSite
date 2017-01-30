package com.dbInterface;

import java.util.List;

/**
 * BlogDataAndPages stores the blog and associated page data
 */
public class BlogDataAndPages {
	
	private int pageNum = 0;
	private int maxPage = 0;
	private List<Object[]> blogBody = null;  // List of blogs
	private List<Integer> pages = null;      // List of pages

	/**
	 * Constructor
	 * 
	 * @param blogBody is the blog body
	 * @param pages are the pages
	 * @param pageNum the current page number
	 * @param maxPage the max page number
	 */
	public BlogDataAndPages(List<Object[]> blogBody, List<Integer> pages, int pageNum, int maxPage){
		
		this.blogBody = blogBody;
		this.pages = pages;
		this.pageNum = pageNum;
		this.maxPage = maxPage;
				
	}
	
	/**
	 * getBlogBody returns the blog body
	 * 
	 * @return the blog body
	 */
	public List<Object[]> getBlogBody(){
		
		return this.blogBody;	
	}

	/**
	 * getPages gets a list of pages 
	 * 
	 * @return the list of pages
	 */
	public List<Integer> getPages(){
		
		return this.pages;
	}
	
	/**
	 * getPageNum gets the current page number
	 * 
	 * @return the page number
	 */
	public int getPageNum(){
		
		return pageNum;
	}

	/**
	 * getMaxPage returns the max page number
	 * 
	 * @return the max page number
	 */
	public int getMaxPage(){
		
		return maxPage;
	}
}

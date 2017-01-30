package com.pageprocessing;

import java.util.ArrayList;
import java.util.List;
/**
 * This class is used to determine which page
 * numbers should be shown to the user
 */
public class PaginationProcessor {
	
	/**
	 * calculatePages calculates the page numbers for pagination
	 * 
	 * @param count is the count of items to create pages for
	 * @param page is the current page number
	 * @return a list of page numbers
	 */	
	public static List<Integer> calculatePages(int count, int page){
		
		int min = 0;									// Integer used to hold the min page num	
		int max = 0;									// Integer used to hold the max page num
		List<Integer> pages = new ArrayList<Integer>();	// List to hold page nums
		
		if(count < 10){ // If page count is less than 10
			
			for(int ii = 0; ii < count; ii++){ // For the number of pages
				
				pages.add(ii);
			} 
		} 
		else{ // Else more than 10 pages
		
			count--;

			if((page+5) > count && (page-5) > -1){ // This will output the last 10 pages
			
				max = count - page;
				min = page - 9 + max;
				max = page + max;
			
				for(int ii = min; ii <= max; ii++){ // For the number of pages
					
					pages.add(ii);
				} 
			} 
			else if((page+5) < count && (page-5) < 0){ // This will output 1 -> 10
			
				for(int ii = 0; ii < 10; ii++){	// For the number of pages	
					
					pages.add(ii);
				} 
			} 
			else{ // This will output the middle pages, for example with 12 pages it would output 2-11
				
				max = page+5;
				min = page-5;
				
				for(int ii = min; ii < max; ii++){ // For the number of pages
					
					pages.add(ii);
				} 
			} 
		} 

		return pages; 	
	} 
}
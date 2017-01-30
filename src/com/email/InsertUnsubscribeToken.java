package com.email;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * InsertUnsubscribetoken inserts the unsubscribe tokens into the db
 */
public class InsertUnsubscribeToken {
	
	private static final String INSERT_TOKEN = "INSERT INTO unsubscribe" +
			" (token, address, date) VALUES (?, ?, ?)";

	/**
	 * insertUnsub inserts the unsubscribe token
	 * 
	 * @param token is the token to insert
	 * @param address is the address associated with the token
	 * @param date is the date
	 * @param jdbcTemplateObject is the jdbc object
	 */
	public static void insertUnsub(String token, String address, long date, JdbcTemplate jdbcTemplateObject) {
		
		jdbcTemplateObject.update(INSERT_TOKEN, new Object[] {token, address, date});
		
	}
}

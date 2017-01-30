package com.token;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

import com.admin.properties.PropertyLoader;

/**
 * TokenGen generates tokens used for email unsubscribe links
 */
public class TokenGeneration {
	
	/**
	 * getToken gets the token
	 * 
	 * @param date is the data used to seed random
	 * @return the token
	 */
	public static String getToken(byte[] date){
		
		SecureRandom random = new SecureRandom(date);
		
		return new BigInteger(130, random).toString(32);
	}
	
	/**
	 * longToByte converts longs to byte array
	 * 
	 * @param val is the value you want to convert to byte array 
	 * @return a byte array
	 */
	public static byte[] longToBytes(long val) {
		
	    ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE);
	    buffer.putLong(val);
	    return buffer.array();
	}

	/**
	 * getFutureData gets the data in the future by getting the current date
	 * and adding the mailUnsubscibe property
	 * 
	 * @return the future date
	 */
	public static long getFutureDate(){
		
		Long date = System.currentTimeMillis();
		
		return (date + PropertyLoader.mailUnsubscribe);
	}
}

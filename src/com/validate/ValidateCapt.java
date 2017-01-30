package com.validate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * ValidateCapt validates re-captchas 
 */
public class ValidateCapt {
	
	private static final String url = "https://www.google.com/recaptcha/api/siteverify";
    private static final String secret = "6Le2UgcTAAAAAPAOEDHxzVi_HryUiQcDD84_FIxO";
    private static final String USER_AGENT = "Mozilla/5.0";
    
    /**
     * validate validates captchas
     * 
     * @param capt is the captcha string
     * @return true if the captcha is valid 
     * 
     * @throws IOException
     */
    public static boolean validate(String capt) throws IOException {
       
    	String stat = null;
    	String inputLine;
    	String postParams;
        URL add = new URL(url);
        StringBuffer res = new StringBuffer();
        HttpsURLConnection con = (HttpsURLConnection) add.openConnection();
 
        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        postParams = "secret=" + secret + "&response=" + capt;
 
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

 
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        
        while ((inputLine = in.readLine()) != null) {
            res.append(inputLine);
        }
        in.close();
        
        stat = res.toString();
       
        if(stat.contains("true")){   
        	return true;
        }

        return false;
            
    }
}

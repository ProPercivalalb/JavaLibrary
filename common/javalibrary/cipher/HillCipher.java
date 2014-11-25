package javalibrary.cipher;

import java.util.ArrayList;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class HillCipher {

	public static String decode(String cipherText, int[] keys) { 
	    
		
		
	    String plainText="";
	    for(int i  =0; i<cipherText.length(); i+=2){ 
	    	plainText += (char)((ikeys[0]*(cipherText.charAt(i)-65) + ikeys[1]*(cipherText.charAt(i+1)-65))%26 + 65); 
	    	plainText += (char)((ikeys[2]*(cipherText.charAt(i)-65) + ikeys[3]*(cipherText.charAt(i+1)-65))%26 + 65); 
	    } 
	    return plainText;
	}
}

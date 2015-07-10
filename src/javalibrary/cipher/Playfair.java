package javalibrary.cipher;

import javalibrary.math.MathHelper;


/**
 * @author Alex Barter (10AS)
 */
public class Playfair {

	public static String encode(String plainText, String keysquare) {
	    String cipherText = "";
	    
	    for(int i = 0; i < plainText.length(); i += 2){
	        char a = plainText.charAt(i);
	        char b = plainText.charAt(i + 1);
	        int i1 = keysquare.indexOf(a);
	        int i2 = keysquare.indexOf(b);
	        int row1 = (int)Math.floor(i1 / 5);
	        int col1 = i1 % 5;
	        int row2 = (int)Math.floor(i2 / 5);
	        int col2 = i2 % 5;
	        
	        char c, d;
	        
	        if(row1 == row2) {
	        	c = keysquare.charAt(row1 * 5 + MathHelper.mod(col1 + 1, 5));
	        	d = keysquare.charAt(row2 * 5 + MathHelper.mod(col2 + 1, 5));
	        }
	        else if(col1 == col2) {
	        	c = keysquare.charAt(MathHelper.mod(row1 + 1, 5) * 5 + col1);
	        	d = keysquare.charAt(MathHelper.mod(row2 + 1, 5) * 5 + col2);
	        }
	        else {
	            c = keysquare.charAt(row1 * 5 + col2);
	            d = keysquare.charAt(row2 * 5 + col1);
	        }
	        
	        cipherText += "" + c + "" + d;
	    }
	    return cipherText;
	}
	
	public static String decode(String cipherText, String keysquare) {
	    String plainText = "";
	    
	    for(int i = 0; i < cipherText.length(); i += 2){
	        char a = cipherText.charAt(i);
	        char b = cipherText.charAt(i + 1);
	        int i1 = keysquare.indexOf(a);
	        int i2 = keysquare.indexOf(b);
	        int row1 = (int)Math.floor(i1 / 5);
	        int col1 = i1 % 5;
	        int row2 = (int)Math.floor(i2 / 5);
	        int col2 = i2 % 5;
	        
	        char c, d;
	        
	        if(row1 == row2) {
	        	c = keysquare.charAt(row1 * 5 + MathHelper.mod(col1 - 1, 5));
	        	d = keysquare.charAt(row2 * 5 + MathHelper.mod(col2 - 1, 5));
	        }
	        else if(col1 == col2) {
	        	c = keysquare.charAt(MathHelper.mod(row1 - 1, 5) * 5 + col1);
	        	d = keysquare.charAt(MathHelper.mod(row2 - 1, 5) * 5 + col2);
	        }
	        else {
	            c = keysquare.charAt(row1 * 5 + col2);
	            d = keysquare.charAt(row2 * 5 + col1);
	        }
	        
	        plainText += "" + c + "" + d;
	    }
	    return plainText;
	}
	
}

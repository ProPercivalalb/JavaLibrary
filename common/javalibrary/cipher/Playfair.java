package javalibrary.cipher;

import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class Playfair {


	public static String encode(String plainText, String keySquare) {
		plainText = StringTransformer.removeEverythingButLetters(plainText).toLowerCase();
		keySquare = keySquare.toLowerCase();

		if(plainText.length() % 2 != 0) 
			plainText += "x";
	    
	    String cipherText = "";
	    for(int i = 0; i < plainText.length(); i += 2) {
	        char a = plainText.charAt(i);
	        char b = plainText.charAt(i + 1);
	        
	        if(a == b) b = 'x';
	        
	        int row1 = keySquare.indexOf(a) / 5;
	        int col1 = keySquare.indexOf(a) % 5;
	        int row2 = keySquare.indexOf(b) / 5;
	        int col2 = keySquare.indexOf(b) % 5;
	        
	        char c, d;
	        
	        if(row1 == row2) {
	            if(col1 == 4)
	            	c = keySquare.charAt(row1 * 5);
	            else 
	            	c = keySquare.charAt(row1 * 5 + col1 + 1);
	            if(col2 == 4) 
	            	d = keySquare.charAt(row2 * 5);
	            else 
	            	d = keySquare.charAt(row2 * 5 + col2 + 1);
	        } 
	        else if(col1 == col2) {
	            if(row1 == 4) 
	            	c = keySquare.charAt(col1);
	            else 
	            	c = keySquare.charAt((row1 + 1) * 5 + col1);
	            if(row2 == 4)
	            	d = keySquare.charAt(col2);
	            else 
	            	d = keySquare.charAt((row2 + 1)*5 + col2);
	        }
	        else {
	            c = keySquare.charAt(row1 * 5 + col2);
	            d = keySquare.charAt(row2 * 5 + col1);
	        }
	        
	        cipherText += "" + c + "" + d;
	    }
	    return cipherText.toUpperCase();
	}

	public static String decode(String cipherText, String keySquare) {
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		keySquare = keySquare.toUpperCase();

	    String plainText = "";
	    for(int i = 0; i < cipherText.length(); i += 2){
	        char a = cipherText.charAt(i); 
	        char b = cipherText.charAt(i + 1); 
	        int row1 = keySquare.indexOf(a) / 5;
	        int col1 = keySquare.indexOf(a) % 5;
	        int row2 = keySquare.indexOf(b) / 5;
	        int col2 = keySquare.indexOf(b) % 5;
	        
	        char c, d;
	        
	        if(row1 == row2) {
	            if(col1 == 0) 
	            	c = keySquare.charAt(row1 * 5 + 4);
	            else 
	            	c = keySquare.charAt(row1 * 5 + col1 - 1);
	            if(col2 == 0) 
	            	d = keySquare.charAt(row2 * 5 + 4);
	            else 
	            	d = keySquare.charAt(row2 * 5 + col2 - 1);
	        }
	        else if(col1 == col2) {
	            if(row1 == 0) 
	            	c = keySquare.charAt(20 + col1);
	            else 
	            	c = keySquare.charAt((row1 - 1) * 5 + col1);
	            if(row2 == 0) 
	            	d = keySquare.charAt(20 + col2);
	            else 
	            	d = keySquare.charAt((row2 - 1) * 5 + col2);
	        }
	        else {
	            c = keySquare.charAt(row1 * 5 + col2);
	            d = keySquare.charAt(row2 * 5 + col1);
	        }
	        
	        plainText += "" + c + "" + d;
	    }
	    return plainText;
	}

}

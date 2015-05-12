package javalibrary.cipher;


/**
 * @author Alex Barter (10AS)
 */
public class Playfair {

	public static String decode(String cipherText, String keySquare) {
		char[] characters = cipherText.toCharArray();
		char[] key = keySquare.toCharArray();
		
	    String plainText = "";
	    for(int i = 0; i < cipherText.length(); i += 2){
	        char a = characters[i]; 
	        char b = characters[i + 1]; 
	        int row1 = keySquare.indexOf(a) / 5;
	        int col1 = keySquare.indexOf(a) % 5;
	        int row2 = keySquare.indexOf(b) / 5;
	        int col2 = keySquare.indexOf(b) % 5;
	        
	        char c, d;
	        
	        if(row1 == row2) {
	            if(col1 == 0) 
	            	c = key[row1 * 5 + 4];
	            else 
	            	c = key[row1 * 5 + col1 - 1];
	            if(col2 == 0) 
	            	d = key[row2 * 5 + 4];
	            else 
	            	d = key[row2 * 5 + col2 - 1];
	        }
	        else if(col1 == col2) {
	            if(row1 == 0) 
	            	c = key[20 + col1];
	            else 
	            	c = key[(row1 - 1) * 5 + col1];
	            if(row2 == 0) 
	            	d = key[20 + col2];
	            else 
	            	d = key[(row2 - 1) * 5 + col2];
	        }
	        else {
	            c = key[row1 * 5 + col2];
	            d = key[row2 * 5 + col1];
	        }
	        
	        plainText += "" + c + "" + d;
	    }
	    return plainText;
	}
	
}

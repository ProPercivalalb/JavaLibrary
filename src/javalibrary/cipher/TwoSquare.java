package javalibrary.cipher;

public class TwoSquare {

	public static String encode(String cipherText, String keysquare1, String keysquare2) {
	    String plainText = "";
	    
	    for(int i = 0; i < cipherText.length(); i += 2){
	        char a = cipherText.charAt(i);
	        char b = cipherText.charAt(i + 1);
	        int aIndex = keysquare1.indexOf(a);
	        int bIndex = keysquare2.indexOf(b);
	        int aRow = (int)Math.floor(aIndex / 5);
	        int bRow = (int)Math.floor(bIndex / 5);
	        int aCol = aIndex % 5;
	        int bCol = bIndex % 5;
	        
	        if(aRow == bRow)
	        	plainText += "" + b + "" + a;
	        else {
		        plainText += keysquare2.charAt(5 * aRow + bCol);
	 	        plainText += keysquare1.charAt(5 * bRow + aCol);
	        }
	    }
	   
	    return plainText;
	}
	
	public static String decode(String cipherText, String keysquare1, String keysquare2) {
	    String plainText = "";
	    
	    for(int i = 0; i < cipherText.length(); i += 2){
	        char a = cipherText.charAt(i);
	        char b = cipherText.charAt(i + 1);
	        int aIndex = keysquare2.indexOf(a);
	        int bIndex = keysquare1.indexOf(b);
	        int aRow = (int)Math.floor(aIndex / 5);
	        int bRow = (int)Math.floor(bIndex / 5);
	        int aCol = aIndex % 5;
	        int bCol = bIndex % 5;
	        
	        if(aRow == bRow)
	        	plainText += "" + b + "" + a;
	        else {
		        plainText += keysquare1.charAt(5 * aRow + bCol);
	 	        plainText += keysquare2.charAt(5 * bRow + aCol);
	        }
	    }
	   
	    return plainText;
	}
}

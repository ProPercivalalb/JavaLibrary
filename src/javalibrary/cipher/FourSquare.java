package javalibrary.cipher;

public class FourSquare {

	public static String encode(String plainText, String keysquare1, String keysquare2) {
	    String cipherText = "";
	    
	    String shortAlpha = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	    
	    for(int i = 0; i < plainText.length(); i += 2){
	        char a = plainText.charAt(i);
	        char b = plainText.charAt(i + 1);
	        int aIndex = shortAlpha.indexOf(a);
	        int bIndex = shortAlpha.indexOf(b);
	        int aRow = (int)Math.floor(aIndex / 5);
	        int bRow = (int)Math.floor(bIndex / 5);
	        int aCol = aIndex % 5;
	        int bCol = bIndex % 5;
	        
	        cipherText += keysquare1.charAt(5 * aRow + bCol);
	        cipherText += keysquare2.charAt(5 * bRow + aCol);
	    }
	   
	    return cipherText;
	}
	
	public static String decode(String cipherText, String keysquare1, String keysquare2) {
	    String plainText = "";
	    
	    String shortAlpha = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	    
	    for(int i = 0; i < cipherText.length(); i += 2){
	        char a = cipherText.charAt(i);
	        char b = cipherText.charAt(i + 1);
	        int aIndex = keysquare1.indexOf(a);
	        int bIndex = keysquare2.indexOf(b);
	        int aRow = (int)Math.floor(aIndex / 5);
	        int bRow = (int)Math.floor(bIndex / 5);
	        int aCol = aIndex % 5;
	        int bCol = bIndex % 5;
	        
	        plainText += shortAlpha.charAt(5 * aRow + bCol);
	        plainText += shortAlpha.charAt(5 * bRow + aCol);
	    }
	   
	    return plainText;
	}
}

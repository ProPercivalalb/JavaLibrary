package javalibrary.cipher;

/**
 * @author Alex Barter (10AS)
 */
public class Bifid {

	public static String encode(String plainText, String keysquare, int period) {
		if(period == 0) period = plainText.length();
		
		int[] digits = new int[plainText.length() * 2];
		for(int i = 0; i < plainText.length(); i++) {
			char c = plainText.charAt(i);
			if(c == 'J') c = 'I';
			int charIndex = keysquare.indexOf(c);
		    int charRow = (int)Math.floor(charIndex / 5);
		    int charCol = charIndex % 5;
		    
		    int blockNo = (int)Math.floor(i / period);
		    int blockSize = Math.min(period, plainText.length() - blockNo * period);
		    int blockCol = (i - blockNo * period) % blockSize;
		    
		    digits[blockNo * period * 2 + blockCol] = charRow;
		    digits[blockNo * period * 2 + blockSize + blockCol] = charCol;
		}
		
		String cipherText = "";
	    
		for(int i = 0; i < digits.length; i += 2) {
		    int row = digits[i];
		    int column = digits[i + 1];
		    cipherText += keysquare.charAt(row * 5 + column);
		}
		
	    return cipherText;
	}
	
	public static char[] decode(char[] cipherText, String keysquare, int period) {
		if(period == 0)
			period = cipherText.length;
		
	    String numberText = "";
	    for(int i = 0; i < cipherText.length; i++){
	        int index = keysquare.indexOf(cipherText[i]);
	        int row = (int)Math.floor(index / 5);
	        int column = index % 5;
	        numberText += row + "" + column;
	    }
	    
	    int numbersDone = 0;
	    String numberRowText = "";
	    String numberColumnText = "";
	    while(numberText.length() - numbersDone >= 2 * period) {
	        numberRowText += numberText.substring(numbersDone, numbersDone + period);
	        numberColumnText += numberText.substring(numbersDone + period, numbersDone + period * 2);
	        numbersDone += 2 * period;
	    }
	    
	    int columnsLeft = (numberText.length() - numbersDone) / 2;
	    if(columnsLeft >= 1) {
	    	numberRowText += numberText.substring(numbersDone, numbersDone + columnsLeft); 
	    	numberColumnText += numberText.substring(numbersDone + columnsLeft, numbersDone + columnsLeft * 2);
	    }
	    
	    char[] plainText = new char[numberRowText.length()];
	    for(int i = 0; i < numberRowText.length(); i++) {
	    	int row = numberRowText.charAt(i) - '0';
	    	int column = numberColumnText.charAt(i) - '0';
	    	plainText[i] = keysquare.charAt(row * 5 + column);
	    }
	    
	    return plainText;
	}
}

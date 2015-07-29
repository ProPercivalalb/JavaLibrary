package javalibrary.cipher.wip;

/**
 * @author Alex Barter (10AS)
 */
public class BifidConjugated {

	public static String encode(String plainText, String keysquare1, String keysquare2, int period) {
		if(period == 0)
			period = plainText.length();
		
		int[] digits = new int[plainText.length() * 2];
		for(int i = 0; i < plainText.length(); i++) {
			char c = plainText.charAt(i);
			if(c == 'J') c = 'I';
			int charIndex = keysquare1.indexOf(c);
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
		    cipherText += keysquare2.charAt(row * 5 + column);
		}
		
	    return cipherText;
	}
	
	public static String decode(String cipherText, String keysquare1, String keysquare2, int period) {
		if(period == 0)
			period = cipherText.length();
		
	    String numberText = "";
	    for(int i = 0; i < cipherText.length(); i++){
	        int index = keysquare2.indexOf(cipherText.charAt(i));
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
	    
	    String plainText = "";
	    for(int i = 0; i < numberRowText.length(); i++) {
	    	int row = numberRowText.charAt(i) - '0';
	    	int column = numberColumnText.charAt(i) - '0';
	    	plainText += keysquare1.charAt(row * 5 + column);
	    }
	    
	    return plainText;
	}
}

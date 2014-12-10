package javalibrary.cipher.wip;

import javalibrary.string.StringTransformer;


/**
 * @author Alex Barter (10AS)
 */
public class Bifid {

	public static String decode(String cipherText, String keySquare, int period) {
		if(period == 0)
			period = cipherText.length();
		
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		keySquare = keySquare.toUpperCase();
		
	    String numberText = "";
	    for(int i = 0; i < cipherText.length(); i++){
	        int index = keySquare.indexOf(cipherText.charAt(i));
	        int row = (index - (index % 5)) / 5;
	        int column = index;
	        while(column >= 5)
	            column -= 5;
	        numberText += row + "" + column;
	    }
	    int i = 0;
	    String numberRowText = "";
	    String numberColumnText = "";
	    while(numberText.length() - i >= 2 * period) {
	        numberRowText += numberText.substring(i, i + period);
	        numberColumnText += numberText.substring(i + period, i + period * 2);
	        i += 2 * period;
	    }
	    int k = (numberText.length() - i) / 2;
	    if(k >= 1) {
	    	numberRowText += numberText.substring(i, i + k); 
	    	numberColumnText += numberText.substring(i + k, i + k * 2);
	    }
	    String plainText = "";
	    for(i = 0; i < numberRowText.length(); i++) {
	    	int row = Integer.parseInt("" + numberRowText.charAt(i));
	    	int column = Integer.parseInt("" + numberColumnText.charAt(i));
	    	plainText += keySquare.charAt(row * 5 + column);
	    }
	    return plainText;
	}
}

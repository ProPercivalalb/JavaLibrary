package javalibrary.cipher;

import java.util.Random;

/**
 * @author Alex Barter (10AS)
 */
public class KeySquareManipulation {

	private static Random rand = new Random();
	
	public static String exchange2letters(String keySquare){
		char[] chars = keySquare.toCharArray();
	    int i = rand.nextInt(100) % 25;
	    int j = rand.nextInt(100) % 25;
	    char temp = chars[i];
	    chars[i] = chars[j];
	    chars[j] = temp;
	    return new String(chars);
	}
	
	public static String exchange2lettersWithKnows(String keySquare, String knows){
		char[] chars = keySquare.toCharArray();
	    int i = 0;
	    int j = 0;
	    while(knows.charAt((i = rand.nextInt(100) % 25)) != '*');
	    while(knows.charAt((j = rand.nextInt(100) % 25)) != '*');
	    char temp = chars[i];
	    chars[i] = chars[j];
	    chars[j] = temp;
	    return new String(chars);
	}
	
	public static String swap2rows(String keySquare){
		char[] chars = keySquare.toCharArray();
		int i = rand.nextInt(100) % 5;
	    int j = rand.nextInt(100) % 5;
	    char temp;
	    int k;
	    for(k = 0; k < 5; k++){
	        temp = chars[i * 5 + k];
	        chars[i * 5 + k] = chars[j * 5 + k];
	        chars[j * 5 + k] = temp;
	    }
	    return new String(chars);
	}

	public static String swap2cols(String keySquare){
		char[] chars = keySquare.toCharArray();
		int i = rand.nextInt(100) % 5;
	    int j = rand.nextInt(100) % 5;
	    char temp;
	    int k;
	    for(k = 0; k < 5;k++){
	        temp = chars[k * 5 + i];
	        chars[k * 5 + i] = chars[k * 5 + j];
	        chars[k * 5 + j] = temp;
	    }
	    return new String(chars);
	}
	
	public static String modifyKey(String keySquare){
		char[] chars = keySquare.toCharArray();
		int k, j;
	    int i = rand.nextInt(100) % 50;
	    switch(i){
	        case 0: return swap2rows(keySquare);
	        case 1: return swap2cols(keySquare);      
	        //Reverse the keySquare
	        case 2: for(k = 0; k < 25; k++) 
	        			chars[k] = keySquare.charAt(24-k);
	        		return new String(chars);
	        case 3: for(k = 0; k < 5; k++) for(j = 0; j < 5; j++) chars[k*5 + j] = keySquare.charAt((4-k)*5+j); // swap rows up-down
	        		return new String(chars);
	        case 4: for(k = 0; k < 5; k++) for(j = 0; j < 5; j++) chars[j*5 + k] = keySquare.charAt((4-j)*5+k); // swap cols left-right
	        		return new String(chars);
	        default: return exchange2letters(keySquare);
	    }
	}
	
	public static String generateRandKeySquare() {
		String keySquare = "";
		
		while(keySquare.length() != 25) {
			char ch = (char)((int)Math.floor(26 * Math.random()) + 'A');
			if(ch != 'J' && !keySquare.contains("" + ch))
				keySquare += ch;
		}
		
		return keySquare;
	}
}

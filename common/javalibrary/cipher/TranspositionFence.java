package javalibrary.cipher;

import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class TranspositionFence {

	public static String decode(String cipherText, int rows) {
		String[] strings = new String[rows];
		for(int i = 0; i < rows; ++i) {
			strings[i] = cipherText.substring(i * (int)(Math.floor(cipherText.length() / rows)), (i + 1) * (int)(Math.floor(cipherText.length() / rows)));
			if(i == rows - 1) {
				strings[i] += cipherText.substring((i + 1) * (int)(Math.floor(cipherText.length() / rows)), cipherText.length());
			}
		}
		int[] alongRows = new int[rows];
		String plainText = "";
		
		while(plainText.length() != cipherText.length()) {
	 		for(int i = 0; i < rows; ++i) {
	 			if(alongRows[i] >= strings[i].length())
	 				continue;
	 			
				plainText += strings[i].charAt(alongRows[i]);
				alongRows[i] += 1;
			}
	 		for(int i = rows - 4; i > 1; --i) {
	 			if(alongRows[i] >= strings[i].length())
	 				continue;
	 			
				plainText += strings[i].charAt(alongRows[i]);
				alongRows[i] += 1;
			}
		}
		
		return plainText;
	}
}

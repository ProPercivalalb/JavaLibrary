package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter
 */
public class Vigenere {


	public static String encode(String plainText, String key) {
		String cipherText = "";
		
		int index = 0;
		for(char ch : plainText.toCharArray()) {
			char newCh = (char)(MathHelper.wrap((ch - 'A') + (key.charAt(MathHelper.wrap(index, 0, key.length())) - 'A'), 0, 26) + 'A');
			cipherText += newCh;
			index += 1;
		}
		
		System.out.println(cipherText);
		return cipherText;
	}
	
	public static String decode(String cipherText, String key) {
		String plainText = "";
		
		int index = 0;
		for(char ch : cipherText.toCharArray()) {
			char newCh = (char)(MathHelper.wrap((ch - 'A') - (key.charAt(MathHelper.wrap(index, 0, key.length())) - 'A'), 0, 26) + 'A');
			plainText += newCh;
			index += 1;
		}
		
		System.out.println(plainText);
		return plainText;
	}
}

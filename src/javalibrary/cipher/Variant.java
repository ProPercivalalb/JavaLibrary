package javalibrary.cipher;

import javalibrary.math.MathHelper;

public class Variant {

	public static String encode(String plainText, String key) {
		String cipherText = "";
		
		for(int index = 0; index < plainText.length(); index++)
			cipherText += (char)(MathHelper.mod(plainText.charAt(index) - key.charAt(index % key.length()), 26) + 'A');
		
		return cipherText;
	}
	
	public static char[] decode(char[] cipherText, String key) {

		char[] plainText = new char[cipherText.length];
		
		for(int index = 0; index < cipherText.length; index++)
			plainText[index] = (char)((cipherText[index] + key.charAt(index % key.length())) % 26 + 'A');
		
		return plainText;
	}
}

package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter
 * @since 25 Nov 2013
 */
public class Beaufort {
	
	public static String encode(String plainText, String key) {
		String cipherText = "";
		
		for(int index = 0; index < plainText.length(); index++)
			cipherText += (char)(MathHelper.mod(key.charAt(index % key.length()) - plainText.charAt(index), 26) + 'A');
		
		return cipherText;
	}
	
	public static char[] decode(char[] cipherText, String key) {
		char[] plainText = new char[cipherText.length];
		
		for(int index = 0; index < cipherText.length; index++)
			plainText[index ] = (char)((26 + key.charAt(index % key.length()) - cipherText[index]) % 26 + 'A');
		
		return plainText;
	}
}

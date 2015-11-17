package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class VigenereAutokey {
	
	public static String encode(String plainText, String key) {
		String autoKey = key + plainText;
		String cipherText = "";
		
		for(int index = 0; index < plainText.length(); index++)
			cipherText += (char)(MathHelper.mod(plainText.charAt(index) + autoKey.charAt(index), 26) + 'A');
		
		return cipherText;
	}
	
	public static char[] decode(char[] cipherText, String key) {
		String autoKey = key;
		char[] plainText = new char[cipherText.length];
		
		for(int index = 0; index < cipherText.length; index++) {
			char newCh = (char)((26 + cipherText[index] - autoKey.charAt(index)) % 26 + 'A');
			plainText[index] = newCh;
			autoKey += newCh;
		}
		
		return plainText;
	}
}

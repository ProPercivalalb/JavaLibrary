package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class VigenereAutoKey {
	
	public static String encode(String plainText, String key) {
		String autoKey = key + plainText;
		String cipherText = "";
		
		for(int index = 0; index < plainText.length(); index++)
			cipherText += (char)(MathHelper.mod(plainText.charAt(index) + autoKey.charAt(index), 26) + 'A');
		
		return cipherText;
	}
	
	public static String decode(String cipherText, String key) {
		String autoKey = key;
		String plainText = "";
		
		for(int index = 0; index < cipherText.length(); index++) {
			char newCh = (char)(MathHelper.mod(cipherText.charAt(index) - autoKey.charAt(index), 26) + 'A');
			plainText += newCh;
			autoKey += newCh;
		}
		
		return plainText;
	}
}

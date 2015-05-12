package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class VigenereAutoKey {

	public static String encode(String plainText, String key) {
		String autoKey = key + plainText;
		String cipherText = "";
		
		int index = 0;
		for(char ch : plainText.toCharArray()) {
			char newCh = (char)(MathHelper.wrap((ch - 'A') + (autoKey.charAt(index) - 'A'), 0, 26) + 'A');
			cipherText += newCh;
			index += 1;
		}
		
		System.out.println(cipherText);
		return cipherText;
	}
	
	public static String decode(String cipherText, String key) {
		String autoKey = key;
		String plainText = "";
		
		int index = 0;
		for(char ch : cipherText.toCharArray()) {
			char newCh = (char)(MathHelper.wrap((ch - 'A') - (autoKey.charAt(index) - 'A'), 0, 26) + 'A');
			plainText += newCh;
			autoKey += newCh;
			index += 1;
		}
		
		System.out.println(plainText);
		return plainText;
	}
}

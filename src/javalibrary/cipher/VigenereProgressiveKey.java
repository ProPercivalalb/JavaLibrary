package javalibrary.cipher;

import javalibrary.math.MathHelper;

public class VigenereProgressiveKey {

	public static String encode(String plainText, String keyword, int period, int progressiveKey) {
		String cipherText = "";

		for(int index = 0; index < plainText.length(); index++) {
			int progression = (int)Math.floor(index / period) * progressiveKey;
			cipherText += (char)(MathHelper.mod(plainText.charAt(index) + progression + keyword.charAt(index % keyword.length()), 26) + 'A');
		}
		
		return cipherText;
	}
	
	public static String decode(String cipherText, String keyword, int period, int progressiveKey) {
		String plainText = "";

		for(int index = 0; index < cipherText.length(); index++) {
			int progression = (int)Math.floor(index / period) * progressiveKey;
			plainText += (char)(MathHelper.mod(cipherText.charAt(index) - progression - keyword.charAt(index % keyword.length()), 26) + 'A');
		}
		
		return plainText;
	}
}

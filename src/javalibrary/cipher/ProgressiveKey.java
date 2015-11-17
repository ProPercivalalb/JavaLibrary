package javalibrary.cipher;

import javalibrary.math.MathHelper;

public class ProgressiveKey {

	protected static String encodeBase(String encodedText, int period, int progressiveKey) {
		String cipherText = "";

		for(int index = 0; index < encodedText.length(); index++) {
			int progression = (int)Math.floor(index / period) * progressiveKey;
			cipherText += (char)(MathHelper.mod((encodedText.charAt(index) - 'A' + progression), 26) + 'A');
		}
		
		return cipherText;
	}
	
	protected static char[] decodeBase(char[] cipherText, int period, int progressiveKey) {
		char[] decodedText = new char[cipherText.length];

		for(int index = 0; index < cipherText.length; index++) {
			int progression = (int)Math.floor(index / period) * progressiveKey;
			decodedText[index] = (char)(MathHelper.mod((cipherText[index] - 'A' - progression), 26) + 'A');
		}
		
		return decodedText;
	}
}

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
	
	protected static String decodeBase(String cipherText, int period, int progressiveKey) {
		String decodedText = "";

		for(int index = 0; index < cipherText.length(); index++) {
			int progression = (int)Math.floor(index / period) * progressiveKey;
			decodedText += (char)(MathHelper.mod((cipherText.charAt(index) - 'A' - progression), 26) + 'A');
		}
		
		return decodedText;
	}
}

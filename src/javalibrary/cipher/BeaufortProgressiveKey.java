package javalibrary.cipher;

public class BeaufortProgressiveKey {

	public static String encode(String plainText, String keyword, int period, int progressiveKey) {
		String encodedText = Beaufort.encode(plainText, keyword);
		return ProgressiveKey.encodeBase(encodedText, period, progressiveKey);
	}
	
	public static char[] decode(char[] plainText, String keyword, int period, int progressiveKey) {
		char[] decodedText = Beaufort.decode(plainText, keyword);
		return ProgressiveKey.decodeBase(decodedText, period, progressiveKey);
	}
}

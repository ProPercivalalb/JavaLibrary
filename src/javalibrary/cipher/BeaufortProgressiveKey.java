package javalibrary.cipher;

public class BeaufortProgressiveKey {

	public static String encode(String plainText, String keyword, int period, int progressiveKey) {
		String encodedText = Beaufort.encode(plainText, keyword);
		return ProgressiveKey.encodeBase(encodedText, period, progressiveKey);
	}
	
	public static String decode(String plainText, String keyword, int period, int progressiveKey) {
		String decodedText = Beaufort.decode(plainText, keyword);
		return ProgressiveKey.decodeBase(decodedText, period, progressiveKey);
	}
}

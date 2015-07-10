package javalibrary.cipher;

public class PortaProgressiveKey {

	public static String encode(String plainText, String keyword, int period, int progressiveKey) {
		String encodedText = Porta.encode(plainText, keyword);
		return ProgressiveKey.encodeBase(encodedText, period, progressiveKey);
	}
	
	public static String decode(String plainText, String keyword, int period, int progressiveKey) {
		String decodedText = Porta.decode(plainText, keyword);
		return ProgressiveKey.decodeBase(decodedText, period, progressiveKey);
	}
}

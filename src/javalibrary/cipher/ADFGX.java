package javalibrary.cipher;


public class ADFGX {

	public static String encode(String plainText, String keysquare, int[] order) {
		//TODO I-J same
		return ADFGVX.encode(plainText, keysquare, order, "ADFGX");
	}
	
	public static String decode(String cipherText, String keysquare, int[] order) {
		return ADFGVX.decode(cipherText, keysquare, order, "ADFGX");
	}
}

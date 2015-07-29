package javalibrary.cipher;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class Keyword {

	/**
	 * Encodes plain text into cipher text encoded in Keyword Cipher
	 * @param plainText The message you wish to encode
	 * @param keyword The keyword to encode and therefore decode the message
	 * @return The Cipher Text
	 */
	public static String encode(String plainText, String keyword) {
		char[] charArray = plainText.toCharArray();
		
		String cipherText = "";
		
		for(char ch : charArray)
			cipherText += keyword.charAt(ch - 'A');
		
		return cipherText;
	}
	
	/**
	 * Decodes cipher text into plain text which was encoded in Keyword Cipher
	 * @param cipherText The message you wish to decode
	 * @param keyword The keyword to encode and therefore decode the message
	 * @return The Plain Text
	 */
	public static String decode(String cipherText, String keyword) {
		char[] charArray = cipherText.toCharArray();
		
		String plainText = "";
		
		for(char ch : charArray)
			plainText += (char)(keyword.indexOf(ch) + 'A');

		return plainText;
	}
}

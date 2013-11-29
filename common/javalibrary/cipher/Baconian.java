package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class Baconian {

	/**
	 * Encodes plain text into cipher text encoded in Autokeyword Cipher
	 * @param plainText The message you wish to encode
	 * @param keyWord The keyword to encode and therefore decode the message
	 * @return The Cipher Text
	 */
	public static String encode(String plainText, String keyWord) {
		char[] charArray = plainText.toCharArray();
		keyWord = keyWord.toUpperCase();
		
		String cipherText = "";
		
		int iteration = 0;
		int keyCount = 0;
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toUpperCase(ch);
			
		}
		
		return cipherText;
	}
	
	/**
	 * Decodes cipher text into plain text which was encoded in Autokeyword Cipher
	 * @param cipherText The message you wish to decode
	 * @param keyWord The keyword to encode and therefore decode the message
	 * @return The Plain Text
	 */
	public static String decode(String cipherText, String keyWord) {
		char[] charArray = cipherText.toCharArray();
		keyWord = keyWord.toLowerCase();
		
		String plainText = "";

		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toLowerCase(ch);
			
			
		}
		System.out.println(keyWord);
		
		return plainText;
	}
}

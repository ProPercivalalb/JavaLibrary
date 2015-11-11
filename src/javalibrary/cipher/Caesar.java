package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class Caesar {

	/**
	 * Encodes plain text into cipher text encoded in Caesar Cipher
	 * @param plainText The message you wish to encode
	 * @param shift The alphabetic shift used to encode the plain text
	 * @return The Cipher Text
	 */
	public static String encode(String plainText, int shift) {
		char[] charArray = plainText.toCharArray();
		
		String cipherText = "";
		
		//Runs through all the characters from the array
		for(char ch : charArray) {
			
			if(!Character.isAlphabetic(ch))
				cipherText += ch;
			else {
				char newLetter = (char)(MathHelper.wrap(shift + ch - 'A', 0, 26) + 'A');
				cipherText += newLetter;
			}
		}
		
		return cipherText;
	}
	
	/**
	 * Decodes cipher text into plain text which was encoded in Caesar Cipher
	 * @param cipherText The message you wish to decode
	 * @param shift The alphabetic shift used to encode the plain text
	 * @return The Plain Text
	 */
	public static String decode(char[] cipherText, int shift) {
		
		char[] plainText = new char[cipherText.length];
		
		//Runs through all the characters from the array
		for(int i = 0; i < cipherText.length; i++)
			plainText[i] = (char)(MathHelper.wrap(-shift + cipherText[i] - 'A', 0, 26) + 'A');
		
		return new String(plainText);
	}
}

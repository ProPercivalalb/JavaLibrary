package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter
 * @since 25 Nov 2013
 */
public class Vigenere {

	/**
	 * Encodes plain text into cipher text encoded in Vigenere Cipher
	 * @param plainText The message you wish to encode
	 * @param keyWord The keyword to encode and therefore decode the message
	 * @return The Cipher Text
	 */
	public static String encode(String plainText, String keyWord) {
		char[] charArray = plainText.toCharArray();
		keyWord = keyWord.toUpperCase();
		
		String cipherText = "";
		
		int iteration = 0;
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toUpperCase(ch);
			
			if(!Character.isAlphabetic(ch))
				cipherText += ch;
			else {
				int keyIndex = MathHelper.wrap(iteration, 0, keyWord.length());
				int shift = keyWord.charAt(keyIndex) - 'A';
				char newLetter = (char)(MathHelper.wrap(shift + ch - 'A', 0, 26) + 'A');
				cipherText += newLetter;
				
				iteration += 1; 
			}
		}
		
		return cipherText;
	}
	
	/**
	 * Decodes cipher text into plain text which was encoded in Vigenere Cipher
	 * @param cipherText The message you wish to decode
	 * @param keyWord The keyword to encode and therefore decode the message
	 * @return The Plain Text
	 */
	public static String decode(String cipherText, String keyWord) {
		char[] charArray = cipherText.toCharArray();
		keyWord = keyWord.toLowerCase();
		
		String plainText = "";
		
		int iteration = 0;
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toLowerCase(ch);
			
			if(!Character.isAlphabetic(ch))
				plainText += ch;
			else {
				int keyIndex = MathHelper.wrap(iteration, 0, keyWord.length());
				int shift = 'a' - keyWord.charAt(keyIndex);
				char newLetter = (char)(MathHelper.wrap(shift + ch - 'a', 0, 26) + 'a');
				plainText += newLetter;
				
				iteration += 1; 
			}
		}
		
		return plainText;
	}
	
}

package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter
 * @since 25 Nov 2013
 */
public class Beaufort {

	/**
	 * Encodes plain text into cipher text encoded in Beaufort Cipher
	 * @param plainText The message you wish to encode
	 * @param keyword The keyword to encode and therefore decode the message
	 * @return The Cipher Text
	 */
	public static String encode(String plainText, String keyword) {
		char[] charArray = plainText.toCharArray();
		keyword = keyword.toUpperCase();
		
		String cipherText = "";
		
		int iteration = 0;
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toUpperCase(ch);
			
			if(!Character.isAlphabetic(ch))
				cipherText += ch;
			else {
				int keyIndex = MathHelper.wrap(iteration, 0, keyword.length());
				char newLetter = (char)(MathHelper.wrap(keyword.charAt(keyIndex) - 'A' - ch - 'A', 0, 26) + 'A');
				cipherText += newLetter;
				
				iteration += 1; 
			}
		}
		
		return cipherText;
	}
	
	/**
	 * Decodes cipher text into plain text which was encoded in Beaufort Cipher
	 * @param cipherText The message you wish to decode
	 * @param keyword The keyword to encode and therefore decode the message
	 * @return The Plain Text
	 */
	public static String decode(String cipherText, String keyword) {
		char[] charArray = cipherText.toCharArray();
		keyword = keyword.toLowerCase();
		
		String plainText = "";
		
		int iteration = 0;
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toLowerCase(ch);
			
			if(!Character.isAlphabetic(ch))
				plainText += ch;
			else {
				int keyIndex = MathHelper.wrap(iteration, 0, keyword.length());
				
				String tempAlphabet = "";
				for(int i = 'a'; i <= 'z'; ++i) {
					char newLetter = (char)MathHelper.wrap(i + ch - 'a', 'a', 'z' + 1);
					tempAlphabet += newLetter;
				}
				
				char newLetter = (char)(tempAlphabet.indexOf(keyword.charAt(keyIndex)) + 'a');
				plainText += newLetter;
				
				iteration += 1; 
			}
		}
		
		return plainText;
	}
	
}

package javalibrary.cipher;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class Affine {

	/**
	 * Encodes plain text into cipher text encoded in Affine Cipher
	 * @param plainText The message you wish to encode
	 * @param a An integer to replace a in the Affine formula
	 * @param b An integer to replace b in the Affine formula
	 * @return The Cipher Text
	 */
	public static String encode(String plainText, int a, int b) {
		char[] charArray = plainText.toCharArray();
		
		String cipherText = "";
		
		String tempAlphabet = "";
		for(int i = 'a'; i < 'z'; ++i)
			tempAlphabet += (char)('a' + MathHelper.wrap(a * (i - 'a') + b, 0, 26));
		
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toUpperCase(ch);
				
			if(!Character.isLetter(ch))
				cipherText += ch;
			else
				cipherText += tempAlphabet.charAt(ch - 'A');
		}
		
		return cipherText;
	}
	
	/**
	 * Decodes cipher text into plain text which was encoded in Affine Cipher
	 * @param cipherText The message you wish to decode
	 * @param shift The alphabetic shift used to encode the plain text
	 * @param a An integer to replace a in the Affine formula
	 * @param b An integer to replace b in the Affine formula
	 * @return The Plain Text
	 */
	public static String decode(String cipherText, int a, int b) {
		char[] charArray = cipherText.toCharArray();
		
		String plainText = "";
		
		String tempAlphabet = "";
		for(int i = 'a'; i < 'z'; ++i)
			tempAlphabet += (char)('a' + MathHelper.wrap(a * (i - 'a') + b, 0, 26));
		
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toLowerCase(ch);
				
			if(!Character.isLetter(ch))
				plainText += ch;
			else
				plainText += (char)(tempAlphabet.indexOf(ch) + 'a');
		}
		
		return plainText;
	}
}

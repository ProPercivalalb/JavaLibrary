package javalibrary.cipher.wip;

import javalibrary.math.MathHelper;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class Autokeyword {

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
			
			if(!Character.isAlphabetic(ch))
				cipherText += ch;
			else {
				int keyIndex = MathHelper.wrap(iteration, 0, keyWord.length());
				int shift = keyWord.charAt(keyIndex) - 'A';
				char newLetter = (char)(MathHelper.wrap(shift + ch - 'A', 0, 26) + 'A');
				cipherText += newLetter;
				
				//Basically the same as Vigenere this is what makes it different and hard to crack
				//if you do not know the keyword in the first place
				if(keyWord.length() < 26) {
					char letter = plainText.charAt(keyCount);
					while(!Character.isAlphabetic(letter)) {
						keyCount += 1;
						letter = plainText.charAt(keyCount);
					}
					keyWord += ch;
					keyCount += 1;
				}
				
				iteration += 1; 
			}
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
				
				//Basically the same as Vigenere this is what makes it different and hard to crack
				//if you do not know the keyword in the first place
				if(keyWord.length() < 26)
					keyWord += newLetter;
				
				iteration += 1; 
			}
		}
		System.out.println(keyWord);
		
		return plainText;
	}
}

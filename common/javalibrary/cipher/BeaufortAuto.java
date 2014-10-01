package javalibrary.cipher;

import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class BeaufortAuto {

	public static String tryDecode(String cipherText, ILanguage language) {
		char[] charArray = cipherText.toCharArray();

		String plainText = "";
		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toUpperCase(ch);
			
			if(Character.isAlphabetic(ch))
				ch = (char)('Z' - ch + 'A');
			
			plainText += ch;
		}
		
		System.out.println(plainText);
		
		int minKeywordLength = 1;
		int maxKeywordLength = 15;
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText);
		plainText = StringTransformer.removeEverythingButLetters(plainText);
		int keyLength = VigenereAuto.findKeywordLength(plainText, minKeywordLength, maxKeywordLength, language);
		System.out.println(keyLength);
		
		String keyword = "";
        for(int i = 0; i < keyLength; ++i) {
        	String temp = StringTransformer.getEveryNthChar(plainText, i, keyLength);
            int shift = VigenereAuto.findBestCaesarShift(temp, language);
            if(shift == 0)
            	keyword += 'Z';
            else
            	keyword += (char)('A' + (shift - 1));
        }
        System.out.println("Keyword: " + keyword);
		
		return Beaufort.decode(cipherText, keyword);
	}
}

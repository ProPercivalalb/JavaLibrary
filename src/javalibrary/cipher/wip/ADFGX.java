package javalibrary.cipher.wip;

import javalibrary.math.MathHelper;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter
 * @since 26 Nov 2013
 */
public class ADFGX {

	public static String encode(String plainText, String gridAlphabet, String keyword) {
		String chars = "ADFGX";
		gridAlphabet = gridAlphabet.toUpperCase();
		keyword = keyword.toUpperCase();
		String tempKeyword = "";
		for(char ch = 'A'; ch <= 'Z'; ++ch)
			if(keyword.contains("" + ch))
				tempKeyword += ch;
		
		char[] charArray = plainText.toCharArray();
		
		String cipherText = "";

		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toUpperCase(ch);
				
			if(!Character.isLetter(ch))
				cipherText += ch;
			else {
				if(ch == 'J')
					ch = 'I';
				int index = gridAlphabet.indexOf(ch);
				String comb = "" + chars.charAt(MathHelper.roundDownToNearest(index, chars.length()) / chars.length()) + chars.charAt(MathHelper.wrap(index, 0, chars.length()));
				cipherText += comb;
			}
		}
		String finalText = "";
		
		for(int i = 0; i < tempKeyword.length(); ++i) {
			finalText += StringTransformer.getEveryNthChar(cipherText, keyword.indexOf(tempKeyword.charAt(i)), tempKeyword.length());
		}
		
		return finalText;
	}
	
	public static String decode(String cipherText, String gridAlphabet, String keyword) {		
		String chars = "ADFGX";
		gridAlphabet = gridAlphabet.toLowerCase();
		keyword = keyword.toLowerCase();
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		String tempKeyword = "";
		for(char ch = 'A'; ch <= 'Z'; ++ch)
			if(keyword.contains("" + ch))
				tempKeyword += ch;
		
		String defaultOrder = "";
		int length = MathHelper.roundDownToNearest(cipherText.length(), tempKeyword.length()) + tempKeyword.length();
		for(int i = 0; i < tempKeyword.length(); ++i) {
			
		}
		
		char[] charArray = cipherText.toCharArray();
		
		String plainText = "";

		//Runs through all the characters from the array
		for(char ch : charArray) {
			//Converts the character to an upper case version if it has one
			ch = Character.toUpperCase(ch);
				
			if(!Character.isLetter(ch))
				plainText += ch;
			else {
				if(ch == 'j')
					ch = 'i';
				int index = gridAlphabet.indexOf(ch);
				String comb = "" + chars.charAt(MathHelper.roundDownToNearest(index, chars.length()) / chars.length()) + chars.charAt(MathHelper.wrap(index, 0, chars.length()));
				plainText += comb;
			}
		}
		String finalText = "";
		
		for(int i = 0; i < tempKeyword.length(); ++i) {
			finalText += StringTransformer.getEveryNthChar(plainText, keyword.indexOf(tempKeyword.charAt(i)), tempKeyword.length());
		}
		
		return finalText;
	}
}

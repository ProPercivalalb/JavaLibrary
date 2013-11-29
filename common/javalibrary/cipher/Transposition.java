package javalibrary.cipher;

import java.util.Hashtable;

import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class Transposition {

	public static String decode(String cipherText, String keyword) {
		
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		keyword = keyword.toUpperCase();
		String temp = "";
		for(int i = 0; i < keyword.length(); ++i) {
			char ch = keyword.charAt(i);
			if(!temp.contains("" + ch))
				temp += ch;
		}
		keyword = temp;
		System.out.println(cipherText.length() + "");
		
		String tempKeyword = "";
		for(char ch = 'A'; ch <= 'Z'; ++ch)
			if(keyword.contains("" + ch))
				tempKeyword += ch;
		
		String finalText = "";
		Hashtable<Character, String> strings = new Hashtable<Character, String>();
		
		for(int i = 0; i < tempKeyword.length(); ++i) {
			strings.put(keyword.charAt(i), StringTransformer.getEveryNthChar(cipherText, keyword.indexOf(tempKeyword.charAt(i)), tempKeyword.length()));
		}
		
		char[] charArray = new char[cipherText.length()];
		for(int i = 0; i < keyword.length(); ++i) {
			String str = strings.get(keyword.charAt(i));
			if(str == null)
				continue;
			
			for(int j = 0; j < str.length(); ++j) {
				if(i + j * keyword.length() < cipherText.length())
					charArray[i + j * keyword.length()] = str.charAt(j);
			}
		}
		return new String(charArray);
	}
}

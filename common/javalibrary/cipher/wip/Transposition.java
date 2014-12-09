package javalibrary.cipher.wip;

import java.util.Hashtable;

import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class Transposition {

	public static String decodeRow(String cipherText, String keyword) {
		keyword = keyword.toUpperCase();
		int[] order = new int[keyword.length()];
		
		int i = 0;
		for(char ch = 'A'; ch <= 'Z'; ++ch) {
			String str = String.valueOf(ch);
			if(keyword.contains(str)) {
				order[keyword.indexOf(str)] = i;
				i++;
			}
		}
		
		for(int k : order)
			System.out.println("" + k);
			
		return decodeRow(cipherText, order);
	}
	
	public static String decodeRow(String cipherText, Integer[] order) {
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		String plainText = "";
		String[] strings = new String[order.length];
		
		for(int i = 0; i < order.length; ++i)
			strings[i] = StringTransformer.getEveryNthChar(cipherText, order[i], order.length);
		
		int i = 0;
		while(cipherText.length() != plainText.length()) {
			for(String k : strings)
				if(i < k.length())
					plainText += k.charAt(i);
			++i;
		}
		
		return plainText;
	}
	
	public static String decodeRow(String cipherText, int[] order) {
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		String plainText = "";
		String[] strings = new String[order.length];
		
		for(int i = 0; i < order.length; ++i)
			strings[i] = StringTransformer.getEveryNthChar(cipherText, order[i], order.length);
		
		int i = 0;
		while(cipherText.length() != plainText.length()) {
			for(String k : strings)
				if(i < k.length())
					plainText += k.charAt(i);
			++i;
		}
		
		return plainText;
	}
	
	
	
	
	
	public static String decodeColumn(String cipherText, String keyword) {
		keyword = keyword.toUpperCase();
		int[] order = new int[keyword.length()];
		
		int i = 0;
		for(char ch = 'A'; ch <= 'Z'; ++ch) {
			String str = String.valueOf(ch);
			if(keyword.contains(str)) {
				order[keyword.indexOf(str)] = i;
				i++;
			}
		}
		
		for(int k : order)
			System.out.println("" + k);
			
		return decodeColumn(cipherText, order);
	}
	
	public static String decodeColumn(String cipherText, Integer[] order) {
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		String plainText = "";
		
		int part = cipherText.length() / order.length;
		
		for(int i = 0; i < order.length; ++i)
			plainText += cipherText.substring(order[i] * part, (order[i] + 1) * part);
		
		return plainText;
	}
	
	public static String decodeColumn(String cipherText, int[] order) {
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		String plainText = "";
		
		for(int i = 0; i < order.length; ++i)
			plainText += StringTransformer.getEveryNthChar(cipherText, order[i], order.length);
		
		return plainText;
	}
}

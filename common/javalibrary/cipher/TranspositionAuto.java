package javalibrary.cipher;

import java.util.ArrayList;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class TranspositionAuto {

	public static String tryDecode(String cipherText) {
		int minKeywordLength = 8;
		int maxKeywordLength = 8;
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
	
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = minKeywordLength; i <= maxKeywordLength; ++i) {
			String defaultStr = "";
			for(int k = 0; k < i; ++k)
				defaultStr += k;
			
			ArrayList<Integer[]> list = permutation(defaultStr);
			
			for(int j = 0; j < list.size(); ++j) {
				
				lastText = Transposition.decodeRow(cipherText, list.get(j));
				currentScore = QuadgramsStats.scoreFitness(lastText);
				//System.out.println(lastText);
				if(currentScore > bestScore) {
					System.out.println(j + "/" + list.size() + " " + lastText);
					bestScore = currentScore;
					plainText = lastText;
				}
			}
			
			System.out.println("Length: " + i);
		}
		
		return "";
	}
	
	
	public static ArrayList<Integer[]> permutation(String str) {
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
	    permutation("", str, list);
	    return list;
	}

	private static void permutation(String prefix, String str, ArrayList<Integer[]> list) {
	    int n = str.length();
	    if (n == 0) {
	    	Integer[] intList = new Integer[prefix.length()];
	    	for(int i = 0; i < prefix.length(); ++i) 
	    		intList[i] = Integer.parseInt("" + prefix.charAt(i));
	    	list.add(intList);
	    }
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), list);
	    }
	}
	
	public static void runThroughtAllWords(String cipherText, String prefix, int length) {
	     if (prefix.length() < length) {
	        for (char c = 'A'; c <= 'Z'; c++) {
				String text = prefix + c;
				System.out.println(text);
				
				runThroughtAllWords(cipherText, text, length);
	        }
	    }
	}
	
	private static void getPermeratation(String cipherText, int keyLength) {
		int defaultValue = 0;
		String defaultStr = "";
		int[] order = new int[keyLength];
		for(int i = 0; i < order.length; ++i) {
			order[i] = i;
			defaultStr += i;
		}
		
		defaultValue = Integer.valueOf(defaultStr);
		
		//for(int i = 0; i < )
	}
}

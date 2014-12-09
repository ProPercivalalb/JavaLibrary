package javalibrary.cipher;

import java.util.ArrayList;
import java.util.Arrays;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class AMSCOTranspositionAuto {

	public static String tryDecode(String cipherText) {
		int minKeywordLength = 5;
		int maxKeywordLength = 5;
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
			
			ArrayList<int[]> list = permutation(defaultStr);
			
			for(int j = 0; j < list.size(); ++j) {
				
				lastText = AMSCOTransposition.decode(cipherText, list.get(j));
				currentScore = QuadgramsStats.scoreFitness(lastText);
				//System.out.println(lastText);
				if(currentScore > bestScore) {
					System.out.println(j + "/" + list.size() + " "  + Arrays.toString(list.get(j)) + " " + lastText);
					bestScore = currentScore;
					plainText = lastText;
				}
			}
			
			System.out.println("Length: " + i);
		}
		
		return plainText;
	}
	
	public static ArrayList<int[]> permutation(String str) {
		ArrayList<int[]> list = new ArrayList<int[]>();
	    permutation("", str, list);
	    return list;
	}

	private static void permutation(String prefix, String str, ArrayList<int[]> list) {
	    int n = str.length();
	    if (n == 0) {
	    	int[] intList = new int[prefix.length()];
	    	for(int i = 0; i < prefix.length(); ++i) 
	    		intList[i] = Integer.parseInt("" + prefix.charAt(i));
	    	list.add(intList);
	    }
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), list);
	    }
	}
}

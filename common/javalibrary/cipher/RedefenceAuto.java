package javalibrary.cipher;

import java.util.ArrayList;
import java.util.Arrays;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class RedefenceAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		int minRows = 3;
		int maxRows = 3;
		String lastText = "";
		String plainText = cipherText;
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = minRows; i <= maxRows; ++i) {
			lastText = Redefence.decode(cipherText, i);
			currentScore = QuadgramsStats.scoreFitness(lastText);
		
			if(currentScore > bestScore) {
				System.out.println(lastText);
				bestScore = currentScore;
				plainText = lastText;
			}
		}
		
		return plainText;
	}
	
	public static void permutation(int[] str) {
	    permutation(new int[0], str);
	}

	public static ArrayList<int[]> permutations(int[] a) {
	    ArrayList<int[]> ret = new ArrayList<int[]>();
	    permutation(a, 0, ret);
	    return ret;
	}

	public static void permutation(int[] arr, int pos, ArrayList<int[]> list){
	    if(arr.length - pos == 1) {
	    	
	    }
	    else
	        for(int i = pos; i < arr.length; i++){
	            int h = arr[pos];
	            int j = arr[i];
	    	    arr[pos] = h;
	    	    arr[i] = j;
	            
	            permutation(arr, pos + 1, list);
	            arr[pos] = j;
	    	    arr[i] = h;
	        }
	}
}

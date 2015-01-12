package javalibrary.cipher;

import java.util.Arrays;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.math.ArrayHelper;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class AMSCOAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		int size = 5;
		boolean reversed = false;
		
		AMSCOTask amscot = new AMSCOTask(cipherText, reversed);
		permutation(amscot, ArrayHelper.range(0, size), 0);
		
		return amscot.plainText;
	}
	

	public static class AMSCOTask implements PermutationTask {

		public String cipherText;
		public boolean reversed;
		
		public AMSCOTask(String cipherText, boolean reversed) {
			this.cipherText = cipherText;
			this.reversed = reversed;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onPermutation(int[] order) {
			this.lastText = AMSCO.decode(this.cipherText, this.reversed, order);
			
			this.currentScore = QuadgramsStats.scoreFitness(this.lastText);
			
			if(this.currentScore > this.bestScore) {
				System.out.println("BEST: " + Arrays.toString(order) + "  " + this.currentScore + "   " + this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
		}
	}
	
	public interface PermutationTask {
		public void onPermutation(int[] order);
	}
	
	public static void permutation(PermutationTask task, int[] arr, int pos) {
	    if(arr.length - pos == 1)
	    	task.onPermutation(arr);
	    else
	        for(int i = pos; i < arr.length; i++) {
	            int h = arr[pos];
	            int j = arr[i];
	            arr[pos] = j;
	            arr[i] = h;
	            
	            permutation(task, arr, pos + 1);
	            arr[pos] = h;
	    	    arr[i] = j;
	        }
	}
}

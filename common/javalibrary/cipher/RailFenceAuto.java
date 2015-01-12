package javalibrary.cipher;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class RailFenceAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		int minRows = 2;
		int maxRows = 15;
		String lastText = "";
		String plainText = cipherText;
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = minRows; i <= maxRows; ++i) {
			lastText = RailFence.decode(cipherText, i);
			currentScore = QuadgramsStats.scoreFitness(lastText);
		
			if(currentScore > bestScore) {
				System.out.println(lastText);
				bestScore = currentScore;
				plainText = lastText;
			}
		}
		
		return plainText;
	}
	
}

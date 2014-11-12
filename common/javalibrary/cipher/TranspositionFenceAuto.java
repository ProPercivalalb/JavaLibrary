package javalibrary.cipher;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class TranspositionFenceAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		int minRows = 2;
		int maxRows = 15;
		String lastText = "";
		String plainText = cipherText;
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = minRows; i <= maxRows; ++i) {
			lastText = TranspositionFence.decode(cipherText, i);
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

package javalibrary.cipher;

import javalibrary.fitness.Quadgrams;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class CaesarAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = 0; i < 26; ++i) {
			lastText = Caesar.decode(cipherText, i);
			currentScore = Quadgrams.scoreFitness(lastText);
			if(currentScore > bestScore) {
				bestScore = currentScore;
				plainText = lastText;
			}
		}
		
		return plainText;
	}
}

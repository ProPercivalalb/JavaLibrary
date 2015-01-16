package javalibrary.cipher;

import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class CaesarAuto {

	public static String tryDecode(String cipherText, ILanguage language) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = 0; i < 26; ++i) {
			lastText = Caesar.decode(cipherText, i);
			currentScore = QuadgramStats.scoreFitness(lastText, language);
			if(currentScore > bestScore) {
				System.out.println("Shift: " + i);
				bestScore = currentScore;
				plainText = lastText;
			}
		}
		
		return plainText;
	}
}

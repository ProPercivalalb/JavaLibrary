package javalibrary.cipher;

import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class AffineAuto {

	public static String tryDecode(String cipherText, ILanguage language) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int a : new int[] {1,3,5,7,9,11,15,17,19,21,23,25}) {
  			for(int b = 0; b < 26; ++b) {
				lastText = Affine.decode(cipherText, a, b);
				currentScore = QuadgramStats.scoreFitness(lastText, language);
				if(currentScore > bestScore) {
					System.out.println("a: " + a + " b: " + b + " " + lastText);
					bestScore = currentScore;
					plainText = lastText;
				}
  			}
		}
		
		return plainText;
	}
}

package javalibrary.cipher;

import javalibrary.fitness.Quadgrams;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class AffineAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int a = 1; a < 25; a += 2) {
  			for(int b = 0; b < 26; ++b) {
				lastText = Affine.decode(cipherText, a, b);
				currentScore = Quadgrams.scoreFitness(lastText);
				if(currentScore > bestScore) {
					System.out.println("a: " + a + " b: " + b);
					bestScore = currentScore;
					plainText = lastText;
				}
  			}
		}
		
		return plainText;
	}
}

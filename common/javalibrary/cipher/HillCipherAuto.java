package javalibrary.cipher;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class HillCipherAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		String lastText = "";
		String plainText = cipherText;
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = 0; i < 26; ++i) {
			for(int j = 0; j < 26; ++j) {
				for(int k = 0; k < 26; ++k) {
					for(int l = 0; l < 26; ++l) {
						lastText = HillCipher.decode(cipherText, i, j, k, l);
						if(lastText.equals(""))
							continue;
						currentScore = QuadgramsStats.scoreFitness(lastText);
						if(currentScore > bestScore) {
							System.out.println(i + " " + j + " " + k + " " + l +  " " + lastText);
							bestScore = currentScore;
							plainText = lastText;
						}
					}
				}
			}
		}
		
		return plainText;
	}
	
}

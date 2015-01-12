package javalibrary.cipher;

import java.util.Random;

import javalibrary.cipher.wip.KeySquareManipulation;
import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class KeywordAuto {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 50000;
	public static final Random rand = new Random();
	
	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		String bestEverKey = "";
		String plainText = "";
		boolean running = true;
		while(running) {
			String lastText = "";
			String parentKey = KeySquareManipulation.generateRandKey();
		
			
			double bestFitness = QuadgramsStats.scoreFitness(Keyword.decode(cipherText, parentKey));
			double bestEverFitness = bestFitness;
			double lastFitness = 0.0D;
			
			int iteration = 0;
			for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP = TEMP - STEP_VALUE) {
				for (int COUNT = COUNT_VALUE; COUNT > 0; COUNT--) {
					
					String childKey = KeySquareManipulation.exchange2letters(parentKey);
		
					lastText = Keyword.decode(cipherText, childKey);
					lastFitness = QuadgramsStats.scoreFitness(lastText);
					double dF = lastFitness - bestFitness;
					if(lastFitness - bestEverFitness > 0) {
						plainText = lastText;
			        	bestEverKey = childKey;
			        	bestEverFitness = lastFitness;
			        	System.out.println(bestEverKey + " " + lastFitness + " Iteration: " + iteration + " Text: " + lastText);
					}
					
			        if(dF > 0) {
			        	bestFitness = lastFitness;
			        	parentKey = childKey;
			        }
			        else if(dF < 0) { 
			        	double prob = Math.exp(dF / TEMP);
			        	double integer = rand.nextDouble() + 0.0D;
			        	if(prob > integer) {
			        		bestFitness = lastFitness;
				        	parentKey = childKey;
			        	}
					}
			        ++iteration;
				}
			}
		}
		System.out.println("   END " + bestEverKey);
		
		return plainText;
	}
}

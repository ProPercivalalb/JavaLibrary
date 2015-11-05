package javalibrary.cipher.wip;

import javalibrary.fitness.TextFitness;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class PlayfairAuto {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 10.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 50000;
	
	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		boolean running = true;
		String bestEverKey = "";

		String plainText = "";
		String lastText = "";
		String parentKey = KeySquareManipulation.generateRandKeySquare();
		
		double bestFitness = TextFitness.scoreFitnessQuadgrams(Playfair.decode(cipherText, parentKey), null);
		double bestEverFitness = bestFitness;
		double lastFitness = 0.0D;
		int iteration = 0;
		
		for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP = TEMP - STEP_VALUE) {
			for (int COUNT = COUNT_VALUE; COUNT > 0; COUNT--) {
					
				String childKey = KeySquareManipulation.modifyKey(parentKey);
				lastText = Playfair.decode(cipherText, childKey);
				lastFitness = TextFitness.scoreFitnessQuadgrams(lastText, null);
				double dF = lastFitness - bestFitness;
				if(lastFitness - bestEverFitness > 0) {
			        	bestEverKey = childKey;
			        	bestEverFitness = lastFitness;
			        	System.out.println(bestEverKey + " " + lastFitness + " Iteration: " + iteration);
					}
					
			        if(dF > 0) {
			        	bestFitness = lastFitness;
			        	parentKey = childKey;
			        }
			        else if(dF < 0) { 
			        	double prob = Math.exp(dF / TEMP);
			        	if(prob > Math.random()) {
			        		bestFitness = lastFitness;
				        	parentKey = childKey;
			        	}
					}
			        
			        ++iteration;
				}
			}
		return "";
		//return plainText;
	}
}

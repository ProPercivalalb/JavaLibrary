package javalibrary.cipher.auto;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Playfair;
import javalibrary.cipher.wip.KeySquareManipulation;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class PlayfairAuto implements IForceDecrypt {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 50000;
	
	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		boolean running = true;
		String bestEverKey = "";

		String plainText = "";
		String lastText = "";
		String parentKey = KeySquareManipulation.generateRandKeySquare();
		
		double bestFitness = QuadgramStats.scoreFitness(Playfair.decode(cipherText, parentKey), language);
		double bestEverFitness = bestFitness;
		double lastFitness = 0.0D;
		int iteration = 0;
		
		for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP = TEMP - STEP_VALUE) {
			for (int COUNT = COUNT_VALUE; COUNT > 0; COUNT--) {
					
				String childKey = KeySquareManipulation.modifyKey(parentKey);
				lastText = Playfair.decode(cipherText, childKey);
				lastFitness = QuadgramStats.scoreFitness(lastText, language);
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
	
	@Override
	public String getName() {
		return "Playfair";
	}
	
	@Override
	public EncryptionData getEncryptionData() {
		return EncryptionData.createNew();
	}
	
	@Override
	public JPanel getVarsPanel() {
		return new JPanel();
	}
}

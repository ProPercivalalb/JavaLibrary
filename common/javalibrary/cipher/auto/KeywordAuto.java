package javalibrary.cipher.auto;

import java.util.Random;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Keyword;
import javalibrary.cipher.wip.KeySquareManipulation;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class KeywordAuto implements IForceDecrypt {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 10000;
	public static final Random rand = new Random();
	
	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		progressBar.setMaximum(2000000);
		String bestEverKey = "";
		String plainText = "";

			String lastText = "";
			String parentKey = KeySquareManipulation.generateRandKey();
			
			double bestFitness = QuadgramStats.scoreFitness(Keyword.decode(cipherText, parentKey), language);
			
			double bestEverFitness = bestFitness;
			double lastFitness = 0.0D;
			
			int iteration = 0;
			for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP = TEMP - STEP_VALUE) {
				for (int COUNT = COUNT_VALUE; COUNT > 0; COUNT--) {
					
					String childKey = KeySquareManipulation.exchange2letters(parentKey);
		
					lastText = Keyword.decode(cipherText, childKey);
					lastFitness = QuadgramStats.scoreFitness(lastText, language);
					double dF = lastFitness - bestFitness;
					if(lastFitness - bestEverFitness > 0) {
						plainText = lastText;
			        	bestEverKey = childKey;
			        	bestEverFitness = lastFitness;
			        	output.println("Fitness: %f, Key: %s, Plaintext: %s", lastFitness, bestEverKey, lastText);
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
			        progressBar.setValue(progressBar.getValue() + 1);
				}
			}

		return plainText;
	}
	
	@Override
	public String getName() {
		return "Keyword";
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

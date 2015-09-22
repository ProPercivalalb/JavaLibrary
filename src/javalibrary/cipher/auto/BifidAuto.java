package javalibrary.cipher.auto;

import java.util.List;
import java.util.Random;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Bifid;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.wip.KeySquareManipulation;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Alex Barter
 */
public class BifidAuto implements IForceDecrypt {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 500;
	
	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		progressBar.addMaxValue((int)(TEMP_VALUE / STEP_VALUE) * COUNT_VALUE);

		Random rand = new Random(System.currentTimeMillis());
		
		double bestFitnessFinal = -99e99;
		
		while(true) {
			progressBar.setValue(0);
			String bestKey = KeySquareManipulation.generateRandKeySquare();
			String bestText = Bifid.decode(cipherText, bestKey, 0);
			double maxscore = QuadgramStats.scoreFitness(bestText, language);
			
			String bestEverKey = bestKey;
			String bestEverText = bestText;
			double bestscore = maxscore;
			
			int iteration = 0;
			for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP -= STEP_VALUE) {
				for(int count = 0; count < COUNT_VALUE; count++){ 
						
					String lastKey = KeySquareManipulation.exchange2letters(bestKey);
			
					String lastText = Bifid.decode(cipherText, lastKey, 0); //TODO
					double score = QuadgramStats.scoreFitness(lastText, language);
					double dF = score - maxscore;
					
				    if(dF >= 0) {
				        maxscore = score;
				        bestKey = lastKey;
				    }
				    else if(TEMP > 0) { 
				    	double prob = Math.exp(dF / TEMP);
				        if(prob > rand.nextDouble()) {
				        	maxscore = score;
					        bestKey = lastKey;
				        }
					}
				    
				    if(maxscore > bestscore) {
				        bestEverKey = lastKey;
				        bestEverText = lastText;
				        bestscore = maxscore;
				        mostLikely.setText(lastText);
				        //output.println("Fitness: %f, Key: %s, Plaintext: %s", score, bestEverKey, lastText);
					}
				    
				    progressBar.addValue(1);
				}
			}
			
			if(bestscore > bestFitnessFinal) {
				bestFitnessFinal = bestscore;
				System.out.println(String.format("Best Fitness: %f, Key: %s, Plaintext: %s", bestscore, bestEverKey, bestEverText));
				output.println("Best Fitness: %f, Key: %s, Plaintext: %s", bestscore, bestEverKey, bestEverText);
			}
		}
	}
	
	@Override
	public String getName() {
		return "Bifid";
	}
	
	@Override
	public EncryptionData getEncryptionData() {
		return EncryptionData.createNew();
	}
	
	@Override
	public JPanel getVarsPanel() {
		return new JPanel();
	}
	
	@Override
	public List<StatisticRange> getStatistics() {
		return null;
	}
	
	@Override
	public boolean canDictionaryAttack() {
		return false;
	}

	@Override
	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output output, ProgressValue progressBar) {
		
	}
}

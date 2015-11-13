package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Keyword;
import javalibrary.cipher.wip.KeySquareManipulation;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;
import nationalciphernew.cipher.stats.StatisticRange;
import nationalciphernew.cipher.stats.StatisticType;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class KeywordAuto implements IForceDecrypt {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 100;
	
	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		progressBar.addMaxValue((int)(TEMP_VALUE / STEP_VALUE) * COUNT_VALUE);

		Random rand = new Random(System.currentTimeMillis());
		
		double bestFitnessFinal = -99e99;
		
		while(true) {
			progressBar.setValue(0);
			String bestKey = KeySquareManipulation.generateRandKey();
			String bestText = Keyword.decode(cipherText, bestKey);
			double maxscore = TextFitness.scoreFitnessQuadgrams(bestText, language);
			
			String bestEverKey = bestKey;
			String bestEverText = bestText;
			double bestscore = maxscore;
			
			int iteration = 0;
			for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP -= STEP_VALUE) {
				for(int count = 0; count < COUNT_VALUE; count++){ 
						
					String lastKey = KeySquareManipulation.exchange2letters(bestKey);
			
					String lastText = Keyword.decode(cipherText, lastKey);
					double score = TextFitness.scoreFitnessQuadgrams(lastText, language);
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
	
	@Override
	public List<StatisticRange> getStatistics() {
		List<StatisticRange> list = new ArrayList<StatisticRange>();
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 66.0D, 3.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 68.0D, 3.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 81.0D, 9.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 77.0D, 7.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 77.0D, 7.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 24.0D, 2.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 50.0D, 2.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 428.0D, 58.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 109.0D, 22.0D));
		return list;
	}
	
	@Override
	public boolean canDictionaryAttack() {
		return true;
	}

	@Override
	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output output, ProgressValue progressBar) {
		
		
		progressBar.addMaxValue(words.size());
		
		String lastText = "";
		double bestScore = Integer.MIN_VALUE;

		
		for(String word : words) {
			//Normal order alphabet
			/**
			String change = "";
			if("")
			for(char i : word.toCharArray()) {
				if(!change.contains("" + i))
					change += i;
			}
			
			for(char i = 'A'; i <= 'Z'; ++i) {
				if(!change.contains("" + i))
					change += i;
			}**/
			
			//Half alphabet after
			String change = "";
			for(char i : word.toCharArray()) {
				if(!change.contains("" + i))
					change += i;
			}
			
			for(char i : "NOPQRSTUVWXYZABCDEFGHIJKLM".toCharArray()) {
				if(!change.contains("" + i))
					change += i;
			}
			
			//System.out.println(word + " " + change);
			lastText = Keyword.decode(cipherText, change);
			
			progressBar.addValue(1);
			
			double currentScore = TextFitness.scoreFitnessQuadgrams(lastText, language);
			if(currentScore > bestScore) {
				
				output.println("Fitness: %f, Word: %s, Plaintext: %s", currentScore, word + " ---> " + change, lastText);
				bestScore = currentScore;
			}
		}
	}
}

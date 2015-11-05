package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.ADFGVX;
import javalibrary.cipher.auto.RedefenceAuto.PermutationTask;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.cipher.wip.KeySquareManipulation;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

public class ADFGVXAuto implements IForceDecrypt {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 10000;
	
	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		progressBar.addMaxValue((int)(TEMP_VALUE / STEP_VALUE) * COUNT_VALUE);

		Random rand = new Random(System.currentTimeMillis());
		
		double bestFitnessFinal = -99e99;
		
		//int[] order = new int[] {3, 1, 4, 0, 5, 2};
		//String untransformedText = ColumnarRow.decode(cipherText, order);
		
		while(true) {
			progressBar.setValue(0);
			String bestKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			int[] bestArray = new int[] {3, 1, 4, 0, 5, 2};//ArrayHelper.range(0, 6);
			System.out.println(Arrays.toString(bestArray));
			String bestText = ADFGVX.decode(cipherText, bestKey, bestArray);
			double maxscore = TextFitness.scoreFitnessQuadgrams(bestText, language);
			
			String bestEverKey = bestKey;
			String bestEverText = bestText;
			double bestscore = maxscore;
			
			int iteration = 0;
			for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP -= STEP_VALUE) {
				for(int count = 0; count < COUNT_VALUE; count++){ 
	
					String lastKey = bestKey;
					//if(count % 4 == 0)
						lastKey = KeySquareManipulation.exchange2letters(bestKey);
					//else
					//	bestArray = KeySquareManipulation.exchangeOrder(bestArray);
					
					String lastText = ADFGVX.decode(cipherText, lastKey, bestArray);
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
	
	public void permutate(PermutationTask task, int[] arr, int pos) {
	    if(arr.length - pos == 1)
	    	task.onPermutation(arr);
	    else
	        for(int i = pos; i < arr.length; i++) {
	            int h = arr[pos];
	            int j = arr[i];
	            arr[pos] = j;
	            arr[i] = h;
	            
	            permutate(task, arr, pos + 1);
	            arr[pos] = h;
	    	    arr[i] = j;
	        }
	}
	
	@Override
	public String getName() {
		return "ADFGVX";
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
		return false;
	}

	@Override
	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output output, ProgressValue progressBar) {
		
	}
}

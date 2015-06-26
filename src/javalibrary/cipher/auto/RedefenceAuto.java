package javalibrary.cipher.auto;

import java.util.List;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Redefence;
import javalibrary.fitness.QuadgramStats;
import javalibrary.fitness.StatisticRange;
import javalibrary.language.ILanguage;
import javalibrary.math.ArrayHelper;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter (10AS)
 */
public class RedefenceAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		
		int size = 4;
		
		RedefenceTask rt = new RedefenceTask(cipherText, language, output);
		this.permutate(rt, ArrayHelper.range(1, size + 1), 0);
		
		return rt.plainText;
	}
	
	public static class RedefenceTask implements PermutationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		
		public RedefenceTask(String cipherText, ILanguage language, Output output) {
			this.cipherText = cipherText;
			this.language = language;
			this.output = output;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onPermutation(int[] order) {
			this.lastText = Redefence.decode(this.cipherText, order);
				
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, Order: %d, Plaintext: %s", this.currentScore, order, this.lastText);
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
		}
	}
	
	public interface PermutationTask {
		public void onPermutation(int[] order);
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
		return "Redefence";
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
}

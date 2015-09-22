package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Columnar;
import javalibrary.cipher.ColumnarRow;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.math.ArrayHelper;
import javalibrary.math.MathHelper;
import javalibrary.swing.ProgressValue;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Alex Barter (10AS)
 */
public class ColumnarRowAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		int minKeyLength = data.getData("minkeylength", Integer.class);
		int maxKeyLength = data.getData("maxkeylength", Integer.class);
		
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			progressBar.addMaxValue(MathHelper.factorialBig(keyLength));
		
		ColumnarRowTask tt = new ColumnarRowTask(cipherText, language, output, progressBar);
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			this.permutate(tt, ArrayHelper.range(0, keyLength), 0);
		
		return tt.plainText;
	}

	public static class ColumnarRowTask implements PermutationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue progressBar;
		
		public ColumnarRowTask(String cipherText, ILanguage language, Output output, ProgressValue progressBar) {
			this.cipherText = cipherText;
			this.language = language;
			this.output = output;
			this.progressBar = progressBar;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onPermutation(int[] order) {
			this.lastText = ColumnarRow.decode(this.cipherText, order);
			
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, Order: %s, Plaintext: %s", this.currentScore, Arrays.toString(order), this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.progressBar.addValue(1);
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
		return "Columnar Row";
	}
	
	@Override
	public EncryptionData getEncryptionData() {
		EncryptionData data = EncryptionData.createNew();
		String text = rangeBox.getText().replaceAll("[^-0-9]", "");
		int minlength = 0;
		int maxlength = 0;
		if(!text.contains("-")) {
			minlength = Integer.valueOf(text);
			maxlength = Integer.valueOf(text);
		}
		else {
			minlength = Integer.valueOf(text.split("-")[0]);
			maxlength = Integer.valueOf(text.split("-")[1]);
		}
		data.putData("minkeylength", Math.min(minlength, maxlength));
		data.putData("maxkeylength", Math.max(maxlength, maxlength));
		return data;
	}
	
	private JTextField rangeBox = new JTextField("2-8");
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel("Keyword length range:   ");
		panel.add(range);
		panel.add(rangeBox);
		return panel;
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

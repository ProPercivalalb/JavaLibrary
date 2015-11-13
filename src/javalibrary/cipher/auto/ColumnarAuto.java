package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Columnar;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.math.ArrayHelper;
import javalibrary.math.MathHelper;
import javalibrary.swing.ProgressValue;
import nationalciphernew.cipher.stats.StatisticRange;
import nationalciphernew.cipher.stats.StatisticType;

/**
 * @author Alex Barter (10AS)
 */
public class ColumnarAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		int minKeyLength = data.getData("minkeylength", Integer.class);
		int maxKeyLength = data.getData("maxkeylength", Integer.class);
		
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			progressBar.addMaxValue(MathHelper.factorialBig(keyLength));
		
		TranspositionTask tt = new TranspositionTask(cipherText, language, output, progressBar);
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			this.permutate(tt, ArrayHelper.range(0, keyLength), 0);
		
		return tt.plainText;
	}

	public static class TranspositionTask implements PermutationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue progressBar;
		
		public TranspositionTask(String cipherText, ILanguage language, Output output, ProgressValue progressBar) {
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
			this.lastText = Columnar.decode(this.cipherText, order);
			
			this.currentScore = TextFitness.scoreFitnessQuadgrams(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, Order: %s, Plaintext: %s", this.currentScore, Arrays.toString(order), this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.progressBar.addValue(1);
		}
	}
	
	public static interface PermutationTask {
		public void onPermutation(int[] order);
	}
	
	public static void permutate(PermutationTask task, int[] arr, int pos) {
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
		return "Columnar";
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
		List<StatisticRange> list = new ArrayList<StatisticRange>();
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 63.0D, 5.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 73.0D, 11.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 96.0D, 18.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 41.0D, 8.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 41.0D, 12.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 6.0D, 4.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 50.0D, 7.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 653.0D, 16.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 128.0D, 15.0D));
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

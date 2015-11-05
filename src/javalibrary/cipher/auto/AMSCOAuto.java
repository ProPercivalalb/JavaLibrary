package javalibrary.cipher.auto;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.AMSCO;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.math.ArrayHelper;
import javalibrary.math.MathHelper;
import javalibrary.swing.ProgressValue;

/**
 * @author Alex Barter (10AS)
 */
public class AMSCOAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		int minKeyLength = data.getData("minkeylength", Integer.class);
		int maxKeyLength = data.getData("maxkeylength", Integer.class);
		boolean reversed = data.getData("first", Boolean.class);
		
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			progressBar.addMaxValue(MathHelper.factorialBig(keyLength));
		
		AMSCOTask amscot = new AMSCOTask(cipherText, language, output, progressBar, reversed);
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			this.permutate(amscot, ArrayHelper.range(0, keyLength), 0);
		
		return amscot.plainText;
	}

	public static class AMSCOTask implements PermutationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue progressBar;
		public boolean reversed;
		
		public AMSCOTask(String cipherText, ILanguage language, Output output, ProgressValue progressBar, boolean reversed) {
			this.cipherText = cipherText;
			this.language = language;
			this.output = output;
			this.progressBar = progressBar;
			this.reversed = reversed;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onPermutation(int[] order) {
			this.lastText = AMSCO.decode(this.cipherText, this.reversed, order);
			
			this.currentScore = TextFitness.scoreFitnessQuadgrams(this.lastText, this.language);
			
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
		return "AMSCO";
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
		data.putData("first", comboBox.getSelectedIndex() == 0);
		return data;
	}
	
	private JTextField rangeBox = new JTextField("2-8");
	private JComboBox comboBox = new JComboBox(new String[] {"** | *  | ** - First", " * | ** | *  - Second"});
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel("Keyword length range:  ");
		JLabel reverse = new JLabel("  Type?  ");
		range.setMaximumSize(new Dimension(200, 100));
		panel.add(range);
		panel.add(rangeBox);
		panel.add(reverse);
		panel.add(comboBox);
		return panel;
	}
	
	@Override
	public List<StatisticRange> getStatistics() {
		List<StatisticRange> list = new ArrayList<StatisticRange>();
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 63.0D, 5.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 72.0D, 10.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 94.0D, 19.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 44.0D, 10.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 43.0D, 13.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 11.0D, 4.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 50.0D, 8.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 688.0D, 15.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 188.0D, 17.0D));
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

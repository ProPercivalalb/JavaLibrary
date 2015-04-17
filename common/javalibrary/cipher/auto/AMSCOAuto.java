package javalibrary.cipher.auto;

import java.util.Arrays;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.AMSCO;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.math.ArrayHelper;
import javalibrary.math.MathHelper;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 * @author Alex Barter (10AS)
 */
public class AMSCOAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		int minKeyLength = data.getData("minkeylength", Integer.class);
		int maxKeyLength = data.getData("maxkeylength", Integer.class);
		boolean reversed = data.getData("first", Boolean.class);
		
		int total = 0;
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			total += MathHelper.factorial(keyLength);
		progressBar.setMaximum(total);
		
		AMSCOTask amscot = new AMSCOTask(cipherText, language, output, progressBar, reversed);
		for(int keyLength = minKeyLength; keyLength <= maxKeyLength; ++keyLength)
			this.permutate(amscot, ArrayHelper.range(0, keyLength), 0);
		
		return amscot.plainText;
	}

	public static class AMSCOTask implements PermutationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public JProgressBar progressBar;
		public boolean reversed;
		
		public AMSCOTask(String cipherText, ILanguage language, Output output, JProgressBar progressBar, boolean reversed) {
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
			
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, Order: %s, Plaintext: %s", this.currentScore, Arrays.toString(order), this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.progressBar.setValue(this.progressBar.getValue() + 1);
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
		JLabel range = new JLabel("Keyword length range:   ");
		JLabel reverse = new JLabel("    Type?   ");
		panel.add(range);
		panel.add(rangeBox);
		panel.add(reverse);
		panel.add(comboBox);
		return panel;
	}
}

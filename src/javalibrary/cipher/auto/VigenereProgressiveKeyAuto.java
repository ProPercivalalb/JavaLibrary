package javalibrary.cipher.auto;

import java.awt.Dimension;
import java.math.BigInteger;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.VigenereProgressiveKey;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

/**
 * @author Alex Barter (10AS)
 */
public class VigenereProgressiveKeyAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {		
		int minKeywordLength = data.getData("minkeylength", Integer.class);
		int maxKeywordLength = data.getData("maxkeylength", Integer.class);
		int minPeriod = data.getData("minperiod", Integer.class);
		int maxPeriod = data.getData("maxperiod", Integer.class);
		int minProgressiveKey = data.getData("minprogressivekey", Integer.class);
		int maxProgressiveKey = data.getData("maxprogressivekey", Integer.class);

		BigInteger TWENTY_SIX = BigInteger.valueOf(26);
		
		for(int length = minKeywordLength; length <= maxKeywordLength; length++)
			progressBar.addMaxValue(TWENTY_SIX.pow(length).multiply(BigInteger.valueOf(maxPeriod - maxPeriod + 1).multiply(BigInteger.valueOf(maxProgressiveKey - minProgressiveKey + 1))));
		
		output.println("Possible Permutations: " + progressBar.maxValue);
		
		VigenereProgressiveKeyTask vpkt = new VigenereProgressiveKeyTask(cipherText, language, output, progressBar);
		
		for(int length = minKeywordLength; length <= maxKeywordLength; length++)
			this.run(vpkt, length, 0, "", minPeriod, maxPeriod, minProgressiveKey, maxProgressiveKey);
		
		return vpkt.plainText;
	}
	
	public static class VigenereProgressiveKeyTask implements KeyCreation {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue value;
		
		public VigenereProgressiveKeyTask(String cipherText, ILanguage language, Output output, ProgressValue value) {
			this.cipherText = cipherText;
			this.language = language;
			this.output = output;
			this.value = value;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onKeyCreate(String key, int period, int progressiveKey) {
			this.lastText = VigenereProgressiveKey.decode(this.cipherText, key, period, progressiveKey);
				
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore >= this.bestScore) {
				this.output.println("Attempt: %s, Fitness: %f, Key: %s, Period %d, Progessive Key %d, Plaintext: %s", this.value.value, this.currentScore, key, period, progressiveKey, this.lastText);
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.value.addValue(1);
		}

	}
	
	public interface KeyCreation {
		public void onKeyCreate(String key, int period, int progressiveKey);
	}
	
	public void run(KeyCreation task, int no, int time, String key, int minPeriod, int maxPeriod, int minProgKey, int maxProgKey) {
		for(char i = 'A'; i <= 'Z'; ++i) {
			String backup = key;
			
			backup += i;
			
			if(time + 1 >= no) {
				for(int period = minPeriod; period <= maxPeriod; period++)
					for(int progKey = minProgKey; progKey <= maxProgKey; progKey++)
						task.onKeyCreate(backup, period, progKey);
				continue;
			}
			
			run(task, no, time + 1, backup, minPeriod, maxPeriod, minProgKey, maxProgKey);
		}
	}
	
	@Override
	public String getName() {
		return "Vigenere Progkey";
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
		
		text = periodBox.getText().replaceAll("[^-0-9]", "");
		minlength = 0;
		maxlength = 0;
		if(!text.contains("-")) {
			minlength = Integer.valueOf(text);
			maxlength = Integer.valueOf(text);
		}
		else {
			minlength = Integer.valueOf(text.split("-")[0]);
			maxlength = Integer.valueOf(text.split("-")[1]);
		}
		data.putData("minperiod", Math.min(minlength, maxlength));
		data.putData("maxperiod", Math.max(maxlength, maxlength));
		
		text = proKeyBox.getText().replaceAll("[^-0-9]", "");
		minlength = 0;
		maxlength = 0;
		if(!text.contains("-")) {
			minlength = Integer.valueOf(text);
			maxlength = Integer.valueOf(text);
		}
		else {
			minlength = Integer.valueOf(text.split("-")[0]);
			maxlength = Integer.valueOf(text.split("-")[1]);
		}
		data.putData("minprogressivekey", Math.min(minlength, maxlength));
		data.putData("maxprogressivekey", Math.max(maxlength, maxlength));
		
		return data;
	}
	
	private JTextField rangeBox = new JTextField("2-5");
	private JTextField periodBox = new JTextField("2-5");
	private JTextField proKeyBox = new JTextField("1");
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel(" Keyword length range: ");
		range.setMaximumSize(new Dimension(200, 100));
		rangeBox.setMinimumSize(new Dimension(50, 100));
		panel.add(range);
		panel.add(rangeBox);
		range = new JLabel(" Period range: ");
		range.setMaximumSize(new Dimension(200, 100));
		periodBox.setMinimumSize(new Dimension(50, 100));
		panel.add(range);
		panel.add(periodBox);
		range = new JLabel(" Progressive key range: ");
		range.setMaximumSize(new Dimension(200, 100));
		proKeyBox.setMinimumSize(new Dimension(50, 100));
		panel.add(range);
		panel.add(proKeyBox);
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

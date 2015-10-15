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
import javalibrary.cipher.VigenereAutoKey;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

/**
 * @author Alex Barter (10AS)
 */
public class VigenereAutokeyAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {		
		int minKeywordLength = data.getData("minkeylength", Integer.class);
		int maxKeywordLength = data.getData("maxkeylength", Integer.class);

		BigInteger TWENTY_SIX = BigInteger.valueOf(26);
		
		for(int length = minKeywordLength; length <= maxKeywordLength; length++)
			progressBar.addMaxValue(TWENTY_SIX.pow(length));
		
		String startText = "";
		VigenereAutoKeyTask ctt = new VigenereAutoKeyTask(cipherText, language, output, progressBar);
		
		for(int length = minKeywordLength; length <= maxKeywordLength; length++)
			this.run(ctt, length - startText.length(), 0, startText);
		
		return ctt.plainText;
	}
	
	public static class VigenereAutoKeyTask implements KeyCreation {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue value;
		
		public VigenereAutoKeyTask(String cipherText, ILanguage language, Output output, ProgressValue value) {
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
		public void onKeyCreate(String key) {
			this.lastText = VigenereAutoKey.decode(this.cipherText, key);
				
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore >= this.bestScore) {
				this.output.println("Fitness: %f, Key: %s, Plaintext: %s", this.currentScore, key, this.lastText);
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.value.addValue(1);
		}

	}
	
	public interface KeyCreation {
		public void onKeyCreate(String key);
	}
	
	public void run(KeyCreation task, int no, int time, String key) {
		for(char i = 'A'; i <= 'Z'; ++i) {
			String backup = key;
			
			backup += i;
			
			if(time + 1 >= no) {
				task.onKeyCreate(backup);
				continue;
			}
			
			run(task, no, time + 1, backup);
		}
	}
	
	@Override
	public String getName() {
		return "Vigenere Autokey";
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
	
	private JTextField rangeBox = new JTextField("2-5");
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel("Keyword length range:  ");
		range.setMaximumSize(new Dimension(200, 100));
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

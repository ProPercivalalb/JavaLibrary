package javalibrary.cipher.auto;

import java.math.BigInteger;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Vigenere;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.math.MathHelper;
import javalibrary.swing.ProgressValue;
import nationalciphernew.cipher.stats.StatisticRange;

/**
 * @author Alex Barter (10AS)
 */
public class TestAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		int minKeyLength = data.getData("minkeylength", Integer.class);
		int maxKeyLength = data.getData("maxkeylength", Integer.class);
		
		BigInteger TWENTY_SIX = BigInteger.valueOf(26);
		
		for(int length = minKeyLength; length <= maxKeyLength; ++length)
			progressBar.addMaxValue(TWENTY_SIX.pow(length));
		
		System.out.println(progressBar.maxValue.toString());
		
		TestTask mt = new TestTask(cipherText.toCharArray(), language, output, progressBar);
		for(int length = minKeyLength; length <= maxKeyLength; ++length)
			this.run(mt, length, 0, new char[length]);
		
		return new String(mt.plainText);
	}

	public static class TestTask implements KeyCAEW {

		public char[] cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue progressBar;
		
		public TestTask(char[] cipherText, ILanguage language, Output output, ProgressValue progressBar) {
			this.cipherText = cipherText;
			this.language = language;
			this.output = output;
			this.progressBar = progressBar;
			this.lastText = new char[cipherText.length];
		}
		
		public char[] lastText;
		public char[] plainText;
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onKeyCreate(char[] key) {
			
			for(int index = 0; index < cipherText.length; index++)
				lastText[index] = (char)((26 + cipherText[index] - key[index % key.length]) % 26 + 'A');

			
			this.currentScore = TextFitness.scoreFitnessQuadgrams(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, Key: %s, Plaintext: %s", this.currentScore, new String(key), new String(this.lastText));	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.progressBar.addValue(1);
		}
	}
	
	public static interface KeyCAEW {
		public void onKeyCreate(char[] key);
	}
	
	public void run(KeyCAEW task, int no, int time, char[] key) {
		for(char i = 'A'; i <= 'Z'; i++) {
			key[time] = i;
			
			if(time + 1 >= no) {
				task.onKeyCreate(key);
				continue;
			}
			
			run(task, no, time + 1, key);
		}
	}

	@Override
	public String getName() {
		return "Test";
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

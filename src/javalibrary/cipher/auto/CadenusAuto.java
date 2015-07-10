package javalibrary.cipher.auto;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Cadenus;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.util.ProgressValue;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Alex Barter (10AS)
 */
public class CadenusAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar) {		
		
		int minSize = data.getData("minsize", Integer.class);
		int maxSize = data.getData("maxsize", Integer.class);

		String startText = "";
		
		CadenusTranspositionTask ctt = new CadenusTranspositionTask(cipherText, language, output);
		
		for(int size = minSize; size <= maxSize; size++)
			this.run(ctt, new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','X','Y','Z'}, size - startText.length(), 0, startText);
		
		return ctt.plainText;
	}
	
	public static class CadenusTranspositionTask implements KeyCreation {

		public String cipherText;
		public ILanguage language;
		public Output output;
		
		public CadenusTranspositionTask(String cipherText, ILanguage language, Output output) {
			this.cipherText = cipherText;
			this.language = language;
			this.output = output;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onKeyCreate(String key) {
			this.lastText = Cadenus.decode(this.cipherText, key);
				
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore >= this.bestScore) {
				this.output.println("Fitness: %f, Key: %s, Plaintext: %s", this.currentScore, key, this.lastText);
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
		}

	}
	
	public interface KeyCreation {
		public void onKeyCreate(String key);
	}
	
	public void run(KeyCreation task, char[] characters, int no, int time, String key) {
		for(char character : characters) {
			String backup = key;
			if(key.contains("" + character))
				continue;
			
			backup += character;
			
			if(time + 1 >= no) {
				task.onKeyCreate(backup);
				continue;
			}
			
			run(task, characters, no, time + 1, backup);
		}
	}
	
	@Override
	public String getName() {
		return "Cadenus";
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
		data.putData("minsize", Math.min(minlength, maxlength));
		data.putData("maxsize", Math.max(maxlength, maxlength));
		return data;
	}
	
	private JTextField rangeBox = new JTextField("2-8");
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel("Number of columns range:  ");
		range.setMaximumSize(new Dimension(200, 100));
		panel.add(range);
		panel.add(rangeBox);
		return panel;
	}
	
	@Override
	public List<StatisticRange> getStatistics() {
		List<StatisticRange> list = new ArrayList<StatisticRange>();
		list.add(new StatisticRange(StatisticType.INDEX_OF_COINCIDENCE, 63.0D, 5.0D));
		list.add(new StatisticRange(StatisticType.MAX_IOC, 74.0D, 11.0D));
		list.add(new StatisticRange(StatisticType.MAX_KAPPA, 95.0D, 17.0D));
		list.add(new StatisticRange(StatisticType.DIGRAPHIC_IOC, 40.0D, 9.0D));
		list.add(new StatisticRange(StatisticType.EVEN_DIGRAPHIC_IOC, 41.0D, 13.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_3, 10.0D, 4.0D));
		list.add(new StatisticRange(StatisticType.LONG_REPEAT_ODD, 49.0D, 9.0D));
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 657.0D, 17.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 134.0D, 18.0D));
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

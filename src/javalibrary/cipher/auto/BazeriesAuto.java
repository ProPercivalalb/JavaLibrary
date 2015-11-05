package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Bazeries;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

/**
 * @author Alex Barter (10AS)
 */
public class BazeriesAuto implements IForceDecrypt {
	
	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		progressBar.addMaxValue(1000000);
		BazeriesTask bt = new BazeriesTask(cipherText, language, output, progressBar);
		this.iterate(bt);
		
		return bt.plainText;
	}
	
	public static class BazeriesTask implements IterationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue progressBar;
		
		public BazeriesTask(String cipherText, ILanguage language, Output output, ProgressValue progressBar) {
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
		public void onIteration(int numberKey) {
			this.lastText = Bazeries.decode(this.cipherText, numberKey);
			
			this.currentScore = TextFitness.scoreFitnessQuadgrams(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, Number Key: %d, Plaintext: %s", this.currentScore, numberKey, this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.progressBar.addValue(1);
		}
	}
	
	public interface IterationTask {
		public void onIteration(int numberKey);
	}

	public void iterate(IterationTask task) {
  		for(int numberKey = 1; numberKey < 1000000; ++numberKey)
  			task.onIteration(numberKey);
	}
	
	@Override
	public String getName() {
		return "Bazeries";
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
		list.add(new StatisticRange(StatisticType.LOG_DIGRAPH, 432.0D, 57.0D));
		list.add(new StatisticRange(StatisticType.SINGLE_LETTER_DIGRAPH, 112.0D, 24.0D));
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

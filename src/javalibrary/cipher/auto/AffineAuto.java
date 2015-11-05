package javalibrary.cipher.auto;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Affine;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.cipher.stats.StatisticType;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;
import javalibrary.swing.ProgressValue;

/**
 * @author Alex Barter (10AS)
 */
public class AffineAuto implements IForceDecrypt {
	
	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar, JTextField mostLikely) {
		progressBar.addMaxValue(12 * 26);
		AffineTask at = new AffineTask(cipherText, language, output, progressBar);
		this.iterate(at);
		
		return at.plainText;
	}
	
	public static class AffineTask implements IterationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue progressBar;
		
		public AffineTask(String cipherText, ILanguage language, Output output, ProgressValue progressBar) {
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
		public void onIteration(int a, int b) {
			this.lastText = Affine.decode(this.cipherText, a, b);
			
			this.currentScore = TextFitness.scoreFitnessQuadgrams(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, A: %d, B: %d, Plaintext: %s", this.currentScore, a, b, this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.progressBar.addValue(1);
		}
	}
	
	public interface IterationTask {
		public void onIteration(int a, int b);
	}

	public void iterate(IterationTask task) {
		for(int a : new int[] {1,3,5,7,9,11,15,17,19,21,23,25})
  			for(int b = 0; b < 26; ++b)
  				task.onIteration(a, b);
	}
	
	@Override
	public String getName() {
		return "Affine";
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

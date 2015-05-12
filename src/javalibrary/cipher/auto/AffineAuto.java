package javalibrary.cipher.auto;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Affine;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter (10AS)
 */
public class AffineAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		progressBar.setMaximum(12 * 26);
		AffineTask at = new AffineTask(cipherText, language, output, progressBar);
		this.iterate(at);
		
		return at.plainText;
	}
	
	public static class AffineTask implements IterationTask {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public JProgressBar progressBar;
		
		public AffineTask(String cipherText, ILanguage language, Output output, JProgressBar progressBar) {
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
			
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore > this.bestScore) {
				this.output.println("Fitness: %f, A: %d, B: %d, Plaintext: %s", this.currentScore, a, b, this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
			this.progressBar.setValue(this.progressBar.getValue() + 1);
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
}

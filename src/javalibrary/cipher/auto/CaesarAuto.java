package javalibrary.cipher.auto;

import java.util.List;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Caesar;
import javalibrary.fitness.QuadgramStats;
import javalibrary.fitness.StatisticRange;
import javalibrary.language.ILanguage;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter (10AS)
 */
public class CaesarAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		progressBar.setMaximum(26);
		for(int i = 0; i < 26; ++i) {
			lastText = Caesar.decode(cipherText, i);
			currentScore = QuadgramStats.scoreFitness(lastText, language);
			if(currentScore > bestScore) {
				output.println("Fitness: %f, Shift: %d, Plaintext: %s", currentScore, i, lastText);
				bestScore = currentScore;
				plainText = lastText;
			}
			progressBar.setValue(progressBar.getValue() + 1);
		}
		
		return plainText;
	}

	@Override
	public String getName() {
		return "Caesar Shift";
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
		return null;
	}
}

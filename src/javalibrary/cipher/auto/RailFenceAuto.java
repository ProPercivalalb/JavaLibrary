package javalibrary.cipher.auto;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.RailFence;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.string.StringTransformer;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter (10AS)
 */
public class RailFenceAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		int minRows = 2;
		int maxRows = 15;
		String lastText = "";
		String plainText = cipherText;
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		
		for(int i = minRows; i <= maxRows; ++i) {
			lastText = RailFence.decode(cipherText, i);
			currentScore = QuadgramStats.scoreFitness(lastText, language);
		
			if(currentScore > bestScore) {
				output.println("Fitness: %f, Number of Rows: %d, Plaintext: %s", currentScore, minRows, lastText);
				bestScore = currentScore;
				plainText = lastText;
			}
		}
		
		return plainText;
	}
	
	@Override
	public String getName() {
		return "Rail Fence";
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

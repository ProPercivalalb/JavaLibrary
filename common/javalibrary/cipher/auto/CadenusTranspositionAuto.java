package javalibrary.cipher.auto;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.CadenusTransposition;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter (10AS)
 */
public class CadenusTranspositionAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {		
		int size = 4;
		String startText = "";
		
		CadenusTranspositionTask ctt = new CadenusTranspositionTask(cipherText, language, output);
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
			this.lastText = CadenusTransposition.decode(this.cipherText, key);
				
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			
			if(this.currentScore >= this.bestScore) {
				output.println("Fitness: %f, Key: %s, Plaintext: %s", this.currentScore, key, this.lastText);
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
			backup += character;
			
			if(time + 1 >= no) {
				boolean end = false;
				
				for(char ch : backup.toCharArray())
					if(backup.indexOf(ch) != backup.lastIndexOf(ch))
						end = true;
				
				if(!end)
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
		return EncryptionData.createNew();
	}
	
	@Override
	public JPanel getVarsPanel() {
		return new JPanel();
	}
}

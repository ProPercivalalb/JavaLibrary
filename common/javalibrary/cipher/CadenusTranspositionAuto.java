package javalibrary.cipher;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class CadenusTranspositionAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		int size = 4;
		String startText = "";
		
		CadenusTranspositionTask ctt = new CadenusTranspositionTask(cipherText);
		run(ctt, new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','X','Y','Z'}, size - startText.length(), 0, startText);
		
		return ctt.plainText;
	}
	
	public static class CadenusTranspositionTask implements KeyCreation {

		public String cipherText;
		
		public CadenusTranspositionTask(String cipherText) {
			this.cipherText = cipherText;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onKeyCreate(String key) {
			this.lastText = CadenusTransposition.decode(this.cipherText, key);
				
			this.currentScore = QuadgramsStats.scoreFitness(this.lastText);
			
			
			if(this.currentScore >= this.bestScore) {
				System.out.println("BEST: " + key + "  " + this.currentScore + "   " + this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			

			
		}

	}
	
	public interface KeyCreation {
		public void onKeyCreate(String key);
	}
	
	public static void run(KeyCreation task, char[] characters, int no, int time, String key) {
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
}

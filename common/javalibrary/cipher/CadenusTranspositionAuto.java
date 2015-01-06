package javalibrary.cipher;

import java.util.ArrayList;

import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
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
		
		CadenusTranspositionTask ctt = new CadenusTranspositionTask(cipherText);
		run(ctt, 'A', 'Z', size, 0, "");
		
		return "";
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
				
			this.currentScore = QuadgramsStats.scoreFitness(lastText);
	
			if(this.currentScore > this.bestScore) {
				System.out.println(key + "  " + this.currentScore + "   " + this.lastText);	
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
		}

	}
	
	public interface KeyCreation {
		public void onKeyCreate(String key);
	}
	
	public static void run(KeyCreation task, char range_low, char range_high, int no, int time, String key) {
		for(int i = range_low; i <= range_high; i++) {
			String backup = key;
			backup += (char)i;
			
			if(time + 1 >= no) {
				boolean end = false;
				
				for(char ch : backup.toCharArray())
					if(backup.indexOf(ch) != backup.lastIndexOf(ch))
						end = true;
				
				if(!end)
					task.onKeyCreate(backup);
				
				continue;
			}
			
			run(task, range_low, range_high, no, time + 1, backup);
		}
	}
}

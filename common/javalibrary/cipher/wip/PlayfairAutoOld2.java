package javalibrary.cipher.wip;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.string.StringTransformer;
import javalibrary.swing.chart.ChartData;
import javalibrary.swing.chart.ChartList;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class PlayfairAutoOld2 {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		boolean running = true;
		while(running) {
		String lastText = "";
		String plainText = "";
		double bestScore = Integer.MIN_VALUE;
		//double lastScore = Integer.MIN_VALUE;
		double currentScore = 0.0D;
		
		String lastAlphabet = "AFCDEBGHLKIMNOQPRSTUVWXYZ";
		String bestAlphabet = "AFCDEBGHLKIMNOQPRSTUVWXYZ";
		
		for(int i = 0; i < 100000; ++i) {
			lastAlphabet = KeySquareManipulation.modifyKey(bestAlphabet);//, "A****B****I****P****VWXYZ");
			
			lastText = Playfair.decode(cipherText, lastAlphabet);
			currentScore = QuadgramsStats.scoreFitness(lastText);
			if(currentScore > bestScore) {
				bestAlphabet = lastAlphabet;
				bestScore = currentScore;
				plainText = lastText;
			}
			else {
				
			}
			//else if(currentScore > bestScore) {
			//	bestScore = currentScore;
			//	plainText = lastText;
			//}
		}
		System.out.println(bestAlphabet + ": ");
		System.out.println("   " + lastText);
		}
		return "";
		//return plainText;
	}
}

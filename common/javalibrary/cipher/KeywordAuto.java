package javalibrary.cipher;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javalibrary.fitness.Quadgrams;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.string.StringTransformer;
import javalibrary.swing.chart.ChartData;
import javalibrary.swing.chart.ChartList;

/**
 * @author Alex Barter
 * @since 27 Nov 2013
 */
public class KeywordAuto {

	//20 for 200-300 words 10 for lower
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 50000;
	public static final Random rand = new Random();
	
	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		boolean running = true;
		while(running) {
			String plainText = "";
			String lastText = "";
			String parentKey = "MONTALBCDEFGHIJKPQRSUVWXYZ";
			
			double bestFitness = Quadgrams.scoreFitness(Keyword.decode(cipherText, parentKey));
			double lastFitness = 0.0D;
			
			for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP = TEMP - STEP_VALUE) {
				for (int COUNT = COUNT_VALUE; COUNT > 0; COUNT--) {
					
					String childKey = KeySquareManipulation.exchange2letters(parentKey);
					for(int i = 0; TEMP < 19; ++i) {
						System.out.println("dawe");
						childKey = KeySquareManipulation.exchange2letters(childKey);
					}
					
					lastText = Keyword.decode(cipherText, childKey);
					lastFitness = Quadgrams.scoreFitness(lastText);
					double dF = lastFitness - bestFitness;
			        if(dF > 0) {
			        	bestFitness = lastFitness;
			        	parentKey = childKey;
		           		System.out.println(" " + parentKey + " " + lastFitness + " " + lastText);
			        }
			        else if(dF < 0) { 
			        	double prob = Math.exp(dF / TEMP);
			        	double integer = rand.nextDouble() + 0.2D;
			        	if(prob > integer) {
			        		bestFitness = lastFitness;
				        	parentKey = childKey;
			           		System.out.println(" " + parentKey + " " + lastFitness + " " + lastText);
			        	}
					}
			        
			        //if(COUNT % 5000 == 0)
				}
			}
		}
		System.out.println("   END");
		
		return "";
	}
}

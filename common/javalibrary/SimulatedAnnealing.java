package javalibrary;

import javalibrary.cipher.wip.KeySquareManipulation;
import javalibrary.cipher.wip.Playfair;
import javalibrary.fitness.QuadgramsStats;

/**
 * @author Alex Barter (10AS)
 */
public class SimulatedAnnealing {

	public static void run(String cipher) {
		
        double temperature = 1000000D;
        double coolingRate = 0.0001D;
        
        String parentKey = KeySquareManipulation.generateRandKeySquare();
        
        String bestKey = parentKey;
        String bestText = Playfair.decode(cipher, parentKey);
        double bestFitness = QuadgramsStats.scoreFitness(bestText);
        
        String currentKey = parentKey;
        String currentText = bestText;
        double currentFitness = bestFitness;
        
		while(temperature > 1) {
			String newKey = KeySquareManipulation.modifyKey(currentKey);
			
			String newText = Playfair.decode(cipher, newKey);
	        double newFitness = QuadgramsStats.scoreFitness(newText);
	        
	        if(newFitness < currentFitness || Math.exp((currentFitness - newFitness) / temperature) > Math.random()) {
	        	currentKey = newKey;
	        	currentText = newText;
	        	currentFitness = newFitness;
	        }
	        
	        if(currentFitness > bestFitness) {
	        	bestKey = currentKey;
	        	bestText = currentText;
	        	bestFitness = currentFitness;
	        	System.out.println(bestKey + " " + bestText + " " + bestFitness);
	        }
	        	
	        	
	        temperature *= 1 - coolingRate;
		}
	}
}
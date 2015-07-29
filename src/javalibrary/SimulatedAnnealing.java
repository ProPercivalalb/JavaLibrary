package javalibrary;

import java.util.Random;

import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;

public abstract class SimulatedAnnealing {

	public Random rand = new Random(System.currentTimeMillis());
	public static double TEMP_VALUE = 20.0D;
	public static final double STEP_VALUE = 0.1D;
	public static final int COUNT_VALUE = 500;
	
	public String cipherText;
	
	public SimulatedAnnealing(String cipherText) {
		this.cipherText = cipherText;
	}
	
	public void runAlgorithm(ILanguage language, Output output) {
		
		double bestFitnessFinal = -99e99;
		
		while(true) {
			String bestText = this.getStartText();
			double maxscore = QuadgramStats.scoreFitness(bestText, language);
			
			String bestEverText = bestText;
			double bestscore = maxscore;
			
			int iteration = 0;
			for(double TEMP = TEMP_VALUE; TEMP >= 0; TEMP -= STEP_VALUE) {
				for(int count = 0; count < COUNT_VALUE; count++){ 
					String lastText = this.getModifiedText(count);
					double score = QuadgramStats.scoreFitness(lastText, language);
					double dF = score - maxscore;
					
				    if(dF >= 0) {
				        maxscore = score;
				    }
				    else if(TEMP > 0) { 
				    	double prob = Math.exp(dF / TEMP);
				        if(prob > rand.nextDouble()) {
				        	maxscore = score;
				        }
					}
				    
				    if(maxscore > bestscore) {
				        bestEverText = lastText;
				        bestscore = maxscore;
				        this.foundNewMaxima();
				        output.println("Best Fitness: %f, Plaintext: %s", bestscore, lastText);
					}

				}
			}
			
			if(bestscore > bestFitnessFinal) {
				bestFitnessFinal = bestscore;
				System.out.println(String.format("Best Fitness: %f, Plaintext: %s", bestscore, bestEverText));
				output.println("Best Fitness: %f, Plaintext: %s", bestscore, bestEverText);
			}
		}
	}
	
	public abstract String getStartText();
	
	public abstract String getModifiedText(int count);

	public abstract void foundNewMaxima();
}

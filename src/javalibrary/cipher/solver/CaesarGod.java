package javalibrary.cipher.solver;

import javalibrary.Output;
import javalibrary.cipher.Caesar;
import javalibrary.fitness.TextFitness;
import javalibrary.language.ILanguage;

public class CaesarGod implements IGodSolver {

	@Override
	public Result getBestResult(String cipherText, ILanguage language, Output output) {
		String bestText = "";
		double bestScore = Double.NEGATIVE_INFINITY;
		
		for(int i = 0; i < 26; ++i) {
			String lastText = Caesar.decode(cipherText, i);
			double currentScore = TextFitness.scoreFitnessQuadgrams(lastText, language);
			
			if(currentScore > bestScore) {
				bestScore = currentScore;
				bestText = lastText;
			}
		}
		
		return new Result(bestText, bestScore);
	}

}

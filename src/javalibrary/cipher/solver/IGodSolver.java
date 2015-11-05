package javalibrary.cipher.solver;

import javalibrary.Output;
import javalibrary.language.ILanguage;

public interface IGodSolver {

	public Result getBestResult(String cipherText, ILanguage language, Output output);
	
	public static class Result {
		public String text;
		public double score;
		
		public Result(String text, double score) {
			this.text = text;
			this.score = score;
		}
	}
}

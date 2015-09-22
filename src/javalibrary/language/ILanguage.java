package javalibrary.language;

import javalibrary.fitness.NGramData;

/**
 * @author Alex Barter
 */
public interface ILanguage {
	
	public double getFrequencyOfLetter(char character);

	public double getMaxFrequency();
	
	public void loadNGramData();
	
	public NGramData getQuadgramData();
	
	public double getNormalCoincidence();
	
	public String getName();
	
	public String getImagePath();
}
package javalibrary.fitness;

import java.util.HashMap;

/**
 * @author Alex Barter (10AS)
 */
public class NGramData {

	public HashMap<String, Double> mapping;
	public double floor;
	public double fitnessPerChar;

	public NGramData(HashMap<String, Double> mapping, double floor, double fitnessPerChar) {
		this.mapping = mapping;
		this.floor = floor;
		this.fitnessPerChar = fitnessPerChar;
	}
}

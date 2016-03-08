package javalibrary.fitness;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alex Barter (10AS)
 */
public class NGramData {

	public HashMap<String, Double> mapping;
	public ArrayList<Double> valueMapping;
	public double floor;
	public double fitnessPerChar;

	public NGramData(HashMap<String, Double> mapping, double floor, double fitnessPerChar) {
		this.mapping = mapping;
		this.floor = floor;
		this.fitnessPerChar = fitnessPerChar;
		this.valueMapping = new ArrayList<Double>();
	}
	
	public double getValue(String gram) {
		return valueMapping.get((gram.charAt(0) - 65) * 17576 + (gram.charAt(1) - 65) * 626 + (gram.charAt(2) - 65) * 26 + gram.charAt(3) - 65);
	}
}

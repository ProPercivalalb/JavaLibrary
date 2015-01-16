package javalibrary.fitness;

import java.util.HashMap;

/**
 * @author Alex Barter (10AS)
 */
public class NGramData {

	public HashMap<String, Double> mapping;
	public double floor;

	public NGramData(HashMap<String, Double> mapping, double floor) {
		this.mapping = mapping;
		this.floor = floor;
	}
}

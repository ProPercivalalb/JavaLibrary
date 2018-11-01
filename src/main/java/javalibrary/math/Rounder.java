package javalibrary.math;

public class Rounder {

	public static double round(double number, int decimals) {
		return (double)Math.round(number * Math.pow(10, decimals)) / Math.pow(10, decimals);
	}
	
}

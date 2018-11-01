package javalibrary.math;

public class ArrayOperations {

	public static int[] multiply(int[] array, int multipler) {
		for(int i = 0; i < array.length; i++)
			array[i] *= multipler;
		return array;
	}
	
	public static int[] divide(int[] array, int multipler) {
		for(int i = 0; i < array.length; i++)
			array[i] /= multipler;
		return array;
	}
	
	public static int[] mod(int[] array, int mod) {
		for(int i = 0; i < array.length; i++)
			array[i] = (array[i] % mod + mod) % mod;
		return array;
	}
	
	/**
	 * Completes the subtract operation on each index of
	 * array1 - array2
	 */
	public static int[] subtract(int[] array1, int[] array2) {
		int[] answer = new int[array1.length];
		for(int i = 0; i < array1.length; i++)
			answer[i] = array1[i] - array2[i];
		return answer;
	}
	
	public static int[] add(int[] array1, int[] array2) {
		int[] answer = new int[array1.length];
		for(int i = 0; i < array1.length; i++)
			answer[i] = array1[i] + array2[i];
		return answer;
	}
	
	//Double Version
	public static double[] multiply(double[] array, double multipler) {
		for(int i = 0; i < array.length; i++)
			array[i] *= multipler;
		return array;
	}
	
	public static double[] divide(double[] array, double multipler) {
		for(int i = 0; i < array.length; i++)
			array[i] /= multipler;
		return array;
	}
	
	public static double[] mod(double[] array, int mod) {
		for(int i = 0; i < array.length; i++)
			array[i] = (array[i] % mod + mod) % 26;
		return array;
	}
	
	/**
	 * Completes the subtract operation on each index of
	 * array1 - array2
	 */
	public static double[] subtract(double[] array1, double[] array2) {
		double[] answer = new double[array1.length];
		for(int i = 0; i < array1.length; i++)
			answer[i] = array1[i] - array2[i];
		return answer;
	}
	
	public static double[] add(double[] array1, double[] array2) {
		double[] answer = new double[array1.length];
		for(int i = 0; i < array1.length; i++)
			answer[i] = array1[i] + array2[i];
		return answer;
	}
	
	
}

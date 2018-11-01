package javalibrary.util;

import java.util.Arrays;

/**
 * @author Alex Barter (10AS)
 */
public class ArrayUtil {
	
	public static int[] createRange(int end) {
		return createRange(0, end);
	}
	
	public static int[] createRange(int start, int end) {
	    int[] arr = new int[end - start];
	    for(int i = 0; i < arr.length; i++) arr[i] = i + start;
	    return arr;
	}
	
	public static Integer[] createRangeInteger(int end) {
		return createRangeInteger(0, end);
	}
	
	public static Integer[] createRangeInteger(int start, int end) {
		Integer[] arr = new Integer[end - start];
	    for(int i = 0; i < arr.length; i++) arr[i] = i + start;
	    return arr;
	}
	
	public static Object[] concatMultiple(Object[]... arrs) {
		int totalLength = 0;
		for(Object[] arr : arrs) totalLength += arr.length;
		Object[] mergedArr = new Object[totalLength];
		int destPos = 0;
		for(Object[] arr : arrs) {
			System.arraycopy(arr, 0, mergedArr, destPos, arr.length);
			destPos += arr.length;
		}
		return mergedArr;
	}
	
	public static Object[] concat(Object[] arrA, Object[] arrB) {
		Object[] mergedArr = new Object[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}

	public static int[] concat(int[] arrA, int[] arrB) {
		int[] mergedArr = new int[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}
	
	public static char[] concat(char[] arrA, char[] arrB) {
		char[] mergedArr = new char[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}
	
	public static double[] concat(double[] arrA, double[] arrB) {
		double[] mergedArr = new double[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}
	
	public static float[] concat(float[] arrA, float[] arrB) {
		float[] mergedArr = new float[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}
	
	public static byte[] concat(byte[] arrA, byte[] arrB) {
		byte[] mergedArr = new byte[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}
	
	public static long[] concat(long[] arrA, long[] arrB) {
		long[] mergedArr = new long[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}
	
	public static boolean[] concat(boolean[] arrA, boolean[] arrB) {
		boolean[] mergedArr = new boolean[arrA.length + arrB.length];
		System.arraycopy(arrA, 0, mergedArr, 0, arrA.length);
		System.arraycopy(arrB, 0, mergedArr, arrA.length, arrB.length);
		return mergedArr;
	}
	
	public static <T> T[] copyRange(T[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static int[] copyRange(int[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static char[] copyRange(char[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static double[] copyRange(double[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static float[] copyRange(float[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static byte[] copyRange(byte[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static long[] copyRange(long[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static boolean[] copyRange(boolean[] arr, int a, int b) {
		return Arrays.copyOfRange(arr, a, b);
	}
	
	public static <T> T[] copy(T[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	public static int[] copy(int[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	public static char[] copy(char[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	public static double[] copy(double[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	public static float[] copy(float[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	public static byte[] copy(byte[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	public static long[] copy(long[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	public static boolean[] copy(boolean[] arr) {
		return Arrays.copyOfRange(arr, 0, arr.length);
	}
	
	/**
	 * Checks if the target elements is contained within the given range.
	 * Looks between the indexes a (inclusive) and b (exclusive)
	 * a should be a minimum of 0 and b a maximum of the length of the array
	 * The number of indexes check is equal b - a
	 * Parameters are not checked to be valid they are assumed to be
	 * @return If the target is found within the given range it returns it index if it is not found -1 is returned
	 */
	public static int indexOf(Object[] arr, int a, int b, Object t) {
		for(int i = a; i < b; i++) if(t == null ? arr[i] == null : t.equals(arr[i])) return i;
	    return -1;
	}
	
	public static int indexOf(int[] arr, int a, int b, int t) {
		for(int i = a; i < b; i++) if(arr[i] == t) return i;
	    return -1;
	}
	
	public static int indexOf(char[] arr, int a, int b, char t) {
		for(int i = a; i < b; i++) if(arr[i] == t) return i;
	    return -1;
	}
	
	public static int indexOf(double[] arr, int a, int b, double t) {
		for(int i = a; i < b; i++) if(arr[i] == t) return i;
	    return -1;
	}
	
	public static int indexOf(float[] arr, int a, int b, float t) {
		for(int i = a; i < b; i++) if(arr[i] == t) return i;
	    return -1;
	}
	
	public static int indexOf(byte[] arr, int a, int b, byte t) {
		for(int i = a; i < b; i++) if(arr[i] == t) return i;
	    return -1;
	}
	
	public static int indexOf(long[] arr, int a, int b, long t) {
		for(int i = a; i < b; i++) if(arr[i] == t) return i;
	    return -1;
	}
	
	public static int indexOf(boolean[] arr, int a, int b, boolean t) {
		for(int i = a; i < b; i++) if(arr[i] == t) return i;
	    return -1;
	}

	//Primitive type index of
	public static int indexOf(Object[] arr, Object t) {
		return indexOf(arr, 0, arr.length, t);
	}
	
	public static int indexOf(int[] arr, int t) {
		return indexOf(arr, 0, arr.length, t);
	}
	
	public static int indexOf(char[] arr, char t) {
		return indexOf(arr, 0, arr.length, t);
	}

	public static int indexOf(double[] arr, double t) {
		return indexOf(arr, 0, arr.length, t);
	}
	
	public static int indexOf(float[] arr, float t) {
		return indexOf(arr, 0, arr.length, t);
	}
	
	public static int indexOf(byte[] arr, byte t) {
		return indexOf(arr, 0, arr.length, t);
	}
	
	public static int indexOf(long[] arr, long t) {
		return indexOf(arr, 0, arr.length, t);
	}
	
	public static int indexOf(boolean[] arr, boolean t) {
		return indexOf(arr, 0, arr.length, t);
	}
	
	//Primitive type contains with range parameters
	public static boolean contains(Object[] arr, int a, int b, Object t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	public static boolean contains(int[] arr, int a, int b, int t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	public static boolean contains(char[] arr, int a, int b, char t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	public static boolean contains(double[] arr, int a, int b, double t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	public static boolean contains(float[] arr, int a, int b, float t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	public static boolean contains(byte[] arr, int a, int b, byte t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	public static boolean contains(long[] arr, int a, int b, long t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	public static boolean contains(boolean[] arr, int a, int b, boolean t) {
		return indexOf(arr, a, b, t) != -1;
	}
	
	//Primitive type contains
	public static boolean contains(Object[] arr, Object t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	
	public static boolean contains(int[] arr, int t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	
	public static boolean contains(char[] arr, char t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	
	public static boolean contains(double[] arr, double t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	
	public static boolean contains(float[] arr, float t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	
	public static boolean contains(byte[] arr, byte t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	
	public static boolean contains(long[] arr, long t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	
	public static boolean contains(boolean[] arr, boolean t) {
		return indexOf(arr, 0, arr.length, t) != -1;
	}
	

	/**
	 * Shuffles the given array and returns the same object
	 */
	public static Object[] shuffle(Object[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
		    Object randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static int[] shuffle(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
			int randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static char[] shuffle(char[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
		    char randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static double[] shuffle(double[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
			double randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static float[] shuffle(float[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
			float randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static byte[] shuffle(byte[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
			byte randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static long[] shuffle(long[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
			long randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static boolean[] shuffle(boolean[] arr) {
		for(int i = 0; i < arr.length; i++) {
			int randIndex = i + RandomUtil.pickRandomInt(arr.length - i);
			boolean randElement = arr[randIndex];
		    arr[randIndex] = arr[i];
		    arr[i] = randElement;	
		}
		return arr;
	}
	
	public static int[] toIndexedArray(int[] arr) {
		int[] inArr = new int[arr.length];
		for(int i = 0; i < arr.length; i++)
			inArr[arr[i]] = i;
		return inArr;
	
	}

	/**
	 * Converts an integer array to an identical array but in double type
	 */
	public static double[] convertNumType(int[] input) {
		double[] output = new double[input.length];
		for(int i = 0; i < input.length; i++)
			output[i] = input[i];
		return output;
	}
	
	public static int[] convertNumType(double[] input) {
		int[] output = new int[input.length];
		for(int i = 0; i < input.length; i++)
			output[i] = (int)Math.floor(input[i]);
		return output;
	}

	public static byte[] convertCharType(char[] input) {
		byte[] output = new byte[input.length];
		for(int i = 0; i < input.length; i++)
			output[i] = (byte)input[i];
		return output;
	}
	
	public static char[] convertCharType(byte[] input) {
		char[] output = new char[input.length];
		for(int i = 0; i < input.length; i++)
			output[i] = (char)input[i];
		return output;
	}
}

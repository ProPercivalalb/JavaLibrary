package javalibrary.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Alex Barter (10AS)
 */
public class ArrayUtil {

	//Primitive array initializer
	public static double[] createDouble(int length) {
		return new double[length]; 
	}
	
	public static int[] createInteger(int length) {
		return new int[length];
	}
	
	public static char[] createCharacter(int length) {
		return new char[length];
	}
	
	public static int[] range(int start, int end){
	    int[] arr = new int[end - start];
	    for(int i = 0; i < end - start; i++)
	        arr[i] = start + i;
	    return arr;
	}
	
	public static Integer[] rangeInt(int start, int end){
		Integer[] arr = new Integer[end - start];
	    for(int i = 0; i < end - start; i++)
	        arr[i] = start + i;
	    return arr;
	}
	
	public static char[] charRange(char start, char end){
		char[] arr = new char[end - start];
	    for(int i = 0; i < end - start; i++)
	        arr[i] = (char)(start + i);
	    return arr;
	}
	
	public static int[] trim(int[] array, int startIndex, int length) {
		int[] newArray = new int[length];
		for(int i = 0; i < length; i++)
			newArray[i] = array[startIndex + i];
		return newArray;
	}
	
	public static char[] trim(char[] charArray, int startIndex, int length) {
		char[] newArray = new char[length];
		for(int i = 0; i < length; i++)
			newArray[i] = charArray[startIndex + i];
		return newArray;
	}
	
	public static double[] trim(double[] array, int startIndex, int length) {
		double[] newArray = new double[length];
		for(int i = 0; i < length; i++)
			newArray[i] = array[startIndex + i];
		return newArray;
	}
	
	public static <T> T[] fill(T filler, int length){
	    T[] arr = (T[])Array.newInstance(filler.getClass(), length);
	    for(int i = 0; i < length; i++)
	        arr[i] = filler;
	    return arr;
	}
	
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	public static char[] concat(char[] a, char[] b) {
		int aLen = a.length;
		int bLen = b.length;
		char[] c = new char[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	public static int[] concat(int[] a, int[] b) {
		int aLen = a.length;
		int bLen = b.length;
		int[] c = new int[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	public static byte[] concat(byte[] a, byte[] b) {
		int aLen = a.length;
		int bLen = b.length;
		byte[] c = new byte[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	public static double[] concat(double[] a, double[] b) {
		int aLen = a.length;
		int bLen = b.length;
		double[] c = new double[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	public static int[] concat(int[] a, int b) {
		int aLen = a.length;
		int[] c = new int[aLen + 1];
		System.arraycopy(a, 0, c, 0, aLen);
		c[aLen] = b;
		return c;
	}
	
	public static char[] concat(char[] a, char b) {
		int aLen = a.length;
		char[] c = new char[aLen + 1];
		System.arraycopy(a, 0, c, 0, aLen);
		c[aLen] = b;
		return c;
	}
	
	public static byte[] concat(byte[] a, byte b) {
		int aLen = a.length;
		byte[] c = new byte[aLen + 1];
		System.arraycopy(a, 0, c, 0, aLen);
		c[aLen] = b;
		return c;
	}
	
	//TODO public static int[] concat(int[]... abc) {
		//for(int[])
	//}
	
	public static char[] copyOfRange(char[] array, int startIndex, int length) {
		return Arrays.copyOfRange(array, startIndex, startIndex + length);
	}
	
	public static byte[] copyOfRange(byte[] array, int startIndex, int length) {
		return Arrays.copyOfRange(array, startIndex, startIndex + length);
	}
	
	public static int[] copyOfRange(int[] array, int startIndex, int endIndex) {
		return Arrays.copyOfRange(array, startIndex, endIndex);
	}



	public static void shuffle(char[] key) {
		int n = key.length;
		for(int i = 0; i < key.length; i++) {
		    int random = i + (int) (Math.random() * (n - i));
		    
		    char randomElement = key[random];
		    key[random] = key[i];
		    key[i] = randomElement;	
		
		}
	}
	
	public static void shuffle(int[] key) {
		int n = key.length;
		for(int i = 0; i < key.length; i++) {
		    int random = i + (int) (Math.random() * (n - i));
		    
		    int randomElement = key[random];
		    key[random] = key[i];
		    key[i] = randomElement;	
		
		}
	}
	
	public static <T> int indexOf(T[] arr, int a, int b, T t) {
		for(int i = a; i < b; i++) if(t == null ? arr[i] == null : arr[i].equals(t)) return i;
	    return -1;
	}
	
	/**
	 * Checks if the target elements is contained within the given range.
	 * Looks between the indexes a (inclusive) and b (exclusive)
	 * a should be a minimum of 0 and b a maximum of the length of the array
	 * parameters are not checked to be valid they are assumed to be
	 * @return If the target is found within the given range it returns it index if it is not found -1 is returned
	 */
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

	//Primitive type index of
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
	
	//Primitive type contains with range parameters
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
	
	//Primitive type contains
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

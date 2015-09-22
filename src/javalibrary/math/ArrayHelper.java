package javalibrary.math;


/**
 * @author Alex Barter (10AS)
 */
public class ArrayHelper {

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
	
	//public static int[] random(int start, int end){
	//    int[] arr = new int[end - start];
	//    for(int i = 0; i < end - start; i++)
	///        arr[i] = start + i;
	//    return arr;
	//}
	
	public static <T> T[] fill(T filler, int length){
	    T[] arr = (T[])new Object[length];
	    for(int i = 0; i < length; i++)
	        arr[i] = filler;
	    return arr;
	}
}

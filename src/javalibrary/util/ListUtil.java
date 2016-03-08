package javalibrary.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javalibrary.math.ArrayUtil;
import javalibrary.string.StringTransformer;

public class ListUtil {

	public static List<Integer> range(int start, int end){
		List<Integer> range = new ArrayList<Integer>();
	    for(int i = 0; i < end - start + 1; i++)
	    	range.add(start + i);
	    return range;
	}
	
	public static List<Integer> randomRange(int start, int end) {
		List<Integer> range = range(start, end);
		Collections.shuffle(range);
		return range;
	}
	
	public static void removeAll(List<Integer> list, int[] toRemove) {
		for(int o : toRemove) list.remove((Integer)o);
	}
	
	public static int[] toArray(List<Integer> list) {
		int[] array = new int[list.size()];
		
		for(int i = 0; i < list.size(); i++) 
			array[i] = list.get(i);
		
		return array;
	}
	
	public static ArrayList<Integer> toList(int[] array) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i = 0; i < array.length; i++) 
			list.add(array[i]);
		
		return list;
	}
	
	public static ArrayList<Character> toList(char[] array) {
		ArrayList<Character> list = new ArrayList<Character>();
		
		for(int i = 0; i < array.length; i++) 
			list.add(array[i]);
		
		return list;
	}
	
	public static <T> ArrayList<T> toList(T[] array) {
		ArrayList<T> list = new ArrayList<T>();
		
		for(int i = 0; i < array.length; i++) 
			list.add(array[i]);
		
		return list;
	}

	public static String toString(int[] order) {
		return toString(order, 0);
	}
	
	public static String toString(int[] order, int add) {
		String total = "[";
		for(int i = 0; i < order.length; ++i) {
			String bit = "" + (order[i] + add);
			if(bit.length() < 2) bit = "0" + bit;
			total += bit + (i == order.length - 1 ? "" : ",");
		}
		return total + "]";
	}
	
	public static String toCardString2(int[] order, int add) {
		String total = "[";
		for(int i = 0; i < order.length; ++i) {
			String bit = "" + (order[i] + add);
		
			total += bit + (i == order.length - 1 ? "" : ",");
		}
		return total + "]";
	}
	
	public static String toCardString(int[] order, int add) {
		String total = "[";
		for(int i = 0; i < order.length; ++i) {
			String bit = "" + (order[i] + add);
			if(order[i] < 0)
				bit = "x";
			total += bit + (i == order.length - 1 ? "" : ",");
		}
		return total + "]";
	}

	public static ArrayList<Integer> removeFromCopy(ArrayList<Integer> list, int... toRemove) {
		ArrayList<Integer> newUnknowns = (ArrayList<Integer>)list.clone();
		for(int remove : toRemove)
			newUnknowns.remove((Integer)remove);
		return newUnknowns;
	}

	public static int[] removeFromCopy(int[] array, int target) {

		int len = array.length - 1;
		int[] c = new int[len];
		
		int i = 0;
		for(int j : array) {
			if(j != target)
				c[i++] = j;
		}

		return c;
	}
	
	public static int[] removeFromCopy(int[] array, int target, int target2) {
	
		int len = array.length - 2;
		int[] c = new int[len];
		
		int i = 0;
		for(int j : array) {
			if(j != target && j != target2)
				c[i++] = j;
		}

		return c;
	}
}

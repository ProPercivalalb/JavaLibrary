package javalibrary.math.sequence;

import java.util.ArrayList;

/**
 * @author Alex Barter
 * @since 29 Oct 2013
 */
public class FibonacciSequence {

	/**
	 * Gets the number at the given position in the sequence
	 * @param position The position in the sequence you want the number from
	 * @return The number at the position
	 */
	public static int getNthNumberInSequence(int position) {
		int value = 1;
		int previousValue = 0;
		for(int i = 0; i < position - 1; ++i) {
			int lastValue = value;
			value = value + previousValue;
			previousValue = lastValue;
		}
		return value;
	}

	/**
	 * Gets the sequence up to a given iteration
	 * @param iterations The size of the List to be returned
	 * @return A list containing some of the sequence
	 */
	public static ArrayList<Integer> getSequenceUpTo(int iterations) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int value = 1;
		int previousValue = 0;
		list.add(value);
		
		for(int i = 0; i < iterations - 1; ++i) {
			int lastValue = value;
			value = value + previousValue;
			list.add(value);
			previousValue = lastValue;
		}
		
		return list;
	}
}

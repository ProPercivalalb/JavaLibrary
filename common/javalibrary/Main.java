package javalibrary;

import java.util.Hashtable;

import javalibrary.math.Bound;
import javalibrary.reflection.ClassFinder;
import javalibrary.string.StringAnalyzer;

/**
 * @author Alex Barter
 * @since 29 Oct 2013
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Timer timer = new Timer();
		//System.out.println("" + FibonacciSequence.getSequenceUpTo(12).get(3));
		final long startTime = System.currentTimeMillis();
		ClassFinder.getClasses("/");
		final long endTime = System.currentTimeMillis();

		System.out.println("Total execution time: " + (endTime - startTime) );
		
		Hashtable<String, Integer> t = StringAnalyzer.analyzeLetterCombination("d", 1, 3);
		for(String str : t.keySet()) {
			System.out.println(str);
		}
		
		Bound.bound(12, 4, 2);
		//System.out.printf("Timer Running for %s milliseconds", timer.getTimeRunning(Units.Time.MILLISECOND));
	}

}

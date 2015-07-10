package javalibrary.math;

/**
 * @author Alex Barter
 * @since 29 Oct 2013
 */
public class Bound {

	/**
	 * Bounds the given integer between the lower and upper bound
	 * @param target The integer to be tested
	 * @param lowerBound The minimum value the target number can be
	 * @param upperBound The maximum value the target number can be
	 * @return The final integer bounded between the 2 numbers
	 */
	public static int bound(int target, int lowerBound, int upperBound) {
		//If the target is smaller that the lower bound set it to that value
		if(target < lowerBound)
			target = lowerBound;
		//If the target is bigger that the upper bound set it to that vaue
		if(target > upperBound)
			target = upperBound;
		return target;
	}
	
	public static int upper(int target, int upperBound) {
		//If the target is bigger that the upper bound set it to that vaue
		if(target > upperBound)
			target = upperBound;
		return target;
	}
}

package javalibrary.lib;

import javalibrary.math.Units;
import javalibrary.math.Units.Time;

/**
 * @author Alex Barter
 * @since 30 Oct 2013
 */
public class Timer {

	private long startTime = 0;
	
	public Timer() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void pause() {
		
	}
	
	public void runLoop() {
		
	}
	
	
	public double getTimeRunning(Units.Time unit) {
		double timeInMilliseconds = System.currentTimeMillis() - this.startTime;
		return Units.Time.convert(timeInMilliseconds, Time.MILLISECOND, unit);
	}
}

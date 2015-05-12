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
	
	public void restart() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void pause() {
		
	}
	
	public void runLoop() {
		
	}
	
	public void displayTime() {
		System.out.println("Milliseconds: " + this.getTimeRunning(Time.MILLISECOND));
	}
	
	public double getTimeRunning(Units.Time unit) {
		double timeInMilliseconds = System.currentTimeMillis() - this.startTime;
		return Units.Time.convert(timeInMilliseconds, Time.MILLISECOND, unit);
	}
}

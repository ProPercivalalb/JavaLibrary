package javalibrary.lib;

import java.util.ArrayList;
import java.util.List;

import javalibrary.math.Statistics;
import javalibrary.math.Units;
import javalibrary.math.Units.Time;

/**
 * @author Alex Barter
 */
public class Timer {

	private long startTime = 0;
	public List<Long> recordedTimes;
	
	public Timer() {
		this.startTime = System.nanoTime();
		this.recordedTimes = new ArrayList<Long>();
	}
	
	public void restart() {
		this.startTime = System.nanoTime();
	}
	
	public void pause() {
		
	}
	
	public void runLoop() {
		
	}
	
	/**
	 * Adds the time since last reset t
	 */
	public void recordTime() {
		this.recordedTimes.add((long)this.getTimeRunning(Time.MILLISECOND));
		this.restart();
	}
	
	public Statistics getRecordedTimesStats() {
		return new Statistics(this.recordedTimes);
		
	}
	
	public void displayTime() {
		System.out.println("Milliseconds: " + this.getTimeRunning(Time.MILLISECOND));
	}
	
	/**
	 * @return The time in milliseconds since the object was created or startTime reset 
	 */
	public long getTimeRunning() {
		return System.nanoTime() - this.startTime;
	}
	
	public double getTimeRunning(Time unit) {
		double timeInMilliseconds = System.nanoTime() - this.startTime;
		return Units.Time.convert(timeInMilliseconds, Time.NANOSECOND, unit);
	}
}

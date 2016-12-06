package javalibrary.list;

public abstract class Result implements Comparable<Result> {

	public final double score;
	
	public Result(double score) {
		this.score = score;
	}
	
	//If other is null it is better
	
	public boolean isResultBetter(Result other) { return false; }
	public boolean isResultWorse(Result other)  { return false; }
	
	public boolean isResultEqual(Result other)  { return false; }
	
	public boolean isResultBetterOrEqual(Result other)  { return false; }
	public boolean isResultWorseOrEqual(Result other)  { return false; }

	@Override
	public int compareTo(Result other)  { return 0; }
	
	@Override
	public String toString() {
		return String.format("Result = [%f]", this.score);
	}
}

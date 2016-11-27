package javalibrary.list;

public abstract class Result implements Comparable<Result> {

	public final double score;
	public final static Result UNIVERSAL_BEST = new Result(0.0D) {};
	
	public Result(double score) {
		this.score = score;
	}
	
	public boolean isResultBetter(Result other) { return false; }
	public boolean isResultWorse(Result other)  { return false; }
	
	public boolean isResultEqual(Result other)  { return false; }
	
	public boolean isResultBetterOrEqual(Result other)  { return false; }
	public boolean isResultWorseOrEqual(Result other)  { return false; }

	@Override
	public int compareTo(Result other)  { return 0; }
}

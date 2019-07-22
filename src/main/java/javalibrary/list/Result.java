package javalibrary.list;

public abstract class Result implements Comparable<Result> {

	public final double score;
	
	public Result(double score) {
		this.score = score;
	}
	
	public abstract boolean isBetterThan(Result other);
	public abstract boolean isWorseThan(Result other);
	
	public abstract boolean isEqualTo(Result other);
	
	public abstract boolean isBetterOrEqual(Result other);
	public abstract boolean isWorseOrEqual(Result other);
	
	@Override
	public String toString() {
		return String.format("Result = [%f]", this.score);
	}
}

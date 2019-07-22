package javalibrary.list;

/**
 * Convenient Result class
 * For this class +Infinity is 'better' than -Infinity (aka bigger values 'better')
 */
public class ResultNegative extends Result {

	public ResultNegative(double score) {
		super(score);
	}
	
	@Override
	public boolean isBetterThan(Result other) {
		return this.score > other.score;
	}
	
	@Override
	public boolean isWorseThan(Result other) {
		return this.score < other.score;
	}
	
	@Override
	public boolean isEqualTo(Result other) {
		return this.score == other.score;
	}
	
	@Override
	public boolean isBetterOrEqual(Result other) {
		return this.score >= other.score;
	}
	
	@Override
	public boolean isWorseOrEqual(Result other) {
		return this.score <= other.score;
	}

	@Override
	public int compareTo(Result other) {
		return Double.compare(other.score, this.score);
	}
}

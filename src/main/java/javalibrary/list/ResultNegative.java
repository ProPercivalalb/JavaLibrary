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
	public boolean isResultBetter(Result other) {
		return this.score > other.score;
	}
	
	@Override
	public boolean isResultWorse(Result other) {
		return this.score < other.score;
	}
	
	@Override
	public boolean isResultEqual(Result other) {
		return this.score == other.score;
	}
	
	@Override
	public boolean isResultBetterOrEqual(Result other) {
		return this.score >= other.score;
	}
	
	@Override
	public boolean isResultWorseOrEqual(Result other) {
		return this.score <= other.score;
	}

	@Override
	public int compareTo(Result other) {
		return Double.compare(other.score, this.score);
	}
}

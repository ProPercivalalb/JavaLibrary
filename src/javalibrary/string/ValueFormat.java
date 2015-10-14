package javalibrary.string;

public class ValueFormat {

	public static String getNumber(double value) {
		Double d = value;
		long l = d.longValue();
		return l == d ? "" + l : "" + d; 
	}
}

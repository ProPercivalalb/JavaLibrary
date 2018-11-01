package javalibrary.exception;

/**
 * @author Alex Barter (10AS)
 */
public class MatrixNoInverse extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MatrixNoInverse(String string) {
		super(string);
	}
}

package javalibrary.exception;

/**
 * @author Alex Barter (10AS)
 */
public class MatrixNotSquareException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MatrixNotSquareException(String string) {
		super(string);
	}

}

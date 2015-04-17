package javalibrary;

/**
 * @author Alex Barter (10AS)
 */
public interface Output {

	public void print(String text, Object... format);
	
	public void println(String text, Object... format);
}

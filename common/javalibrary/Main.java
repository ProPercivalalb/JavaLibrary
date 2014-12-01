package javalibrary;

import javalibrary.cipher.HillCipherAuto;
import javalibrary.math.matrics.Matrix;

/**
 * @author Alex Barter
 * @since 29 Oct 2013
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HillCipherAuto.run(26, 2 * 2, 0, new int[4]);
	}

}

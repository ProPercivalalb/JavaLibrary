package javalibrary;

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
		Matrix matrix = new Matrix("15");
		Matrix letters = new Matrix("3");
		Matrix a = matrix.multiply(letters).modular(26);
		a.print();
		Matrix inverseMod = matrix.inverseMod(26);
		Matrix back = inverseMod.multiply(a).modular(26);
		inverseMod.print();
		back.print();
	}

}

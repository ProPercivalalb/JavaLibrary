package javalibrary.cipher;

import java.util.ArrayList;

import javalibrary.exception.MatrixDeterminateException;
import javalibrary.exception.MatrixNoInverse;
import javalibrary.math.MathHelper;
import javalibrary.math.matrics.Matrix;

/**
 * @author Alex Barter (10AS)
 */
public class HillCipher {

	public static String decode(String cipherText, Matrix keyMatrix) { 
	    Matrix inverseMatrix;
		
		try {
			inverseMatrix = keyMatrix.inverseMod(26);
			
		}
		catch(MatrixNoInverse e) {
			return "";
		}
		catch(MatrixDeterminateException e) {
			return "";
		}
		
		int size = inverseMatrix.size();
	    String plainText = ""; 
	    for(int i = 0; i < cipherText.length(); i += size){
	    	String[] let = new String[size];
	    	for(int j = 0; j < size; j++)
	    		let[j] = ((int)cipherText.charAt(i + j) - 65);
	    	Matrix letters = new Matrix(let);
	    } 
	    return plainText;
	}
}

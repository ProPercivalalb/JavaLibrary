package javalibrary.cipher;

import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
import javalibrary.math.matrics.Matrix;

/**
 * @author Alex Barter (10AS)
 */
public class HillCipher {

	public static String decode(String cipherText, Matrix keyMatrix) throws MatrixNotSquareException, MatrixNoInverse { 
	    Matrix inverseMatrix;
		inverseMatrix = keyMatrix.inverseMod(26);
		
		int size = inverseMatrix.size();
	    String plainText = ""; 
	    for(int i = 0; i < cipherText.length(); i += size){
	    	
	    	int[][] let = new int[size][1];
	    	for(int j = 0; j < size; j++)
	    		let[j][0] = ((int)cipherText.charAt(i + j) - 65);
	    	
	    	Matrix cipherMatrix = new Matrix(let);
	    	Matrix plainMatrix = inverseMatrix.multiply(cipherMatrix).modular(26);
	    	
	    	for(int j = 0; j < size; j++)
	    		plainText += (char)(plainMatrix.matrix[j][0] + 65);
	    		
	    }
	    
	    return plainText;
	}
}

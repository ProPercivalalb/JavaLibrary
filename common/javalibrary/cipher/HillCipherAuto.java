package javalibrary.cipher;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.math.matrics.Matrix;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class HillCipherAuto {

	public static String tryDecode(String cipherText) {
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toUpperCase();
		
		String lastText = "";
		String plainText = cipherText;
		double bestScore = Integer.MIN_VALUE;
		double currentScore = 0;
		int matrixSize = 2;
		
		
		for(int x = 0; x < matrixSize; ++x) {
			for(int y = 0; y < matrixSize; ++y) {
				for(int k = 0; k < 26; ++k) {
					for(int l = 0; l < 26; ++l) {
						
						
						lastText = HillCipher.decode(cipherText, i, j, k, l);
						if(lastText.equals(""))
							continue;
						currentScore = QuadgramsStats.scoreFitness(lastText);
						if(currentScore > bestScore) {
							System.out.println(i + " " + j + " " + k + " " + l +  " " + lastText);
							bestScore = currentScore;
							plainText = lastText;
						}
					}
				}
			}
		}
		
		return plainText;
	}
	
	public static everyMatrixXxY(int max, int orderY, int orderX) {
		
	}
	
	public static void run(int max, int no, int time, int[] array) {
		
		for(int i = 0; i < max; i++) {
			array[time] = i;
			if(time + 1 >= no) {
				for(int j : array) 
					System.out.print(j + ", ");
				System.out.println("");
				continue;
			}
			
			run(max, no, time + 1, array);
		}
	}
	
	
	public static void everyMatrix(int matrixSize, Matrix matrix, int maxValue, int currentCoordX, int currentCoordY) {
		if(matrix.matrix[currentCoordY][currentCoordX] == maxValue) {
			
			
		}
		else {
			Matrix newMatrix = matrix.copy();
			newMatrix.matrix[currentCoordY][currentCoordX] += 1;
			newMatrix.print();
			for(int x = 0; x < matrix.order[1]; x++) {
				for(int y = 0; y < matrix.order[0]; y++) {
					if(currentCoordX != x && currentCoordY != y)
						everyMatrix(matrixSize, matrix, maxValue, x, y);
				}
			}
			
			everyMatrix(matrixSize, newMatrix, maxValue, currentCoordX, currentCoordY);
		}
	}
}

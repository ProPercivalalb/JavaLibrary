package javalibrary.cipher;

import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
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
		
		int matrixSize = 2;
		
		HillCipherTask hct = new HillCipherTask(cipherText);
		run(hct, 0, 25, (int)Math.pow(matrixSize, 2), matrixSize, matrixSize, 0, new int[(int) Math.pow(matrixSize, 2)]);
		
		return hct.plainText;
	}
	
	public static class HillCipherTask implements MatrixCreation {

		public String cipherText;
		
		public HillCipherTask(String cipherText) {
			this.cipherText = cipherText;
		}
		
		public String lastText = "";
		public String plainText = "";
		public double bestScore = Integer.MIN_VALUE;
		public double currentScore = 0;
			
		@Override
		public void onMatrixCreate(Matrix matrix) {
			try {
				this.lastText = HillCipher.decode(this.cipherText, matrix);
					
			}
			catch(MatrixNoInverse e) {
				return;
			}
			catch(MatrixNotSquareException e) {
				return;
			}
				
			this.currentScore = QuadgramsStats.scoreFitness(lastText);
			if(this.currentScore > this.bestScore) {
				
				System.out.println(this.lastText);
				matrix.print();
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
				
		}

	}
	
	public interface MatrixCreation {
		public void onMatrixCreate(Matrix matrix);
	}

	public static void run(MatrixCreation task, int range_low, int range_high, int no, int rows, int columns, int time, int[] array) {
		for(int i = range_low; i <= range_high; i++) {
			array[time] = i;
			
			if(time + 1 >= no) {
				Matrix matrix = new Matrix(array, rows, columns);
				task.onMatrixCreate(matrix);
				continue;
			}
			
			run(task, range_low, range_high, no, rows, columns, time + 1, array);
		}
	}
}

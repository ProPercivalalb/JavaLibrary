package javalibrary.cipher.auto;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.HillCipher;
import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.math.matrics.Matrix;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Alex Barter (10AS)
 */
public class HillCipherAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, JProgressBar progressBar) {
		int matrixSize = 2;
		
		progressBar.setMaximum((int)Math.pow(26, Math.pow(matrixSize, 2)));
		output.println("" + (int)Math.pow(26, Math.pow(matrixSize, 2)));
		HillCipherTask hct = new HillCipherTask(cipherText, language, output, progressBar);
		this.run(hct, 0, 25, (int)Math.pow(matrixSize, 2), matrixSize, matrixSize, 0, new int[(int) Math.pow(matrixSize, 2)]);
		
		return hct.plainText;
	}
	
	public static class HillCipherTask implements MatrixCreation {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public JProgressBar progressBar;
		
		public HillCipherTask(String cipherText, ILanguage language, Output output, JProgressBar progressBar) {
			this.cipherText = cipherText;
			this.language = language;
			this.output = output;
			this.progressBar = progressBar;
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
			finally {
				this.progressBar.setValue(this.progressBar.getValue() + 1);
			}
			
			this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
			if(this.currentScore > this.bestScore) {
				
				this.output.println("Fitness: %f, Matrix: %s, Plaintext: %s", this.currentScore, matrix, this.lastText);
				this.bestScore = this.currentScore;
				this.plainText = this.lastText;
			}
			
		}

	}
	
	public interface MatrixCreation {
		public void onMatrixCreate(Matrix matrix);
	}

	public void run(MatrixCreation task, int range_low, int range_high, int no, int rows, int columns, int time, int[] array) {
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
	
	@Override
	public String getName() {
		return "Hill";
	}
	
	@Override
	public EncryptionData getEncryptionData() {
		return EncryptionData.createNew();
	}
	
	@Override
	public JPanel getVarsPanel() {
		return new JPanel();
	}
}

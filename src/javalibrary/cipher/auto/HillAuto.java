package javalibrary.cipher.auto;

import java.awt.Dimension;
import java.math.BigInteger;
import java.util.List;

import javalibrary.EncryptionData;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.cipher.Hill;
import javalibrary.cipher.stats.StatisticRange;
import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.math.matrics.Matrix;
import javalibrary.util.ProgressValue;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Alex Barter (10AS)
 */
public class HillAuto implements IForceDecrypt {

	@Override
	public String tryDecode(String cipherText, EncryptionData data, ILanguage language, Output output, ProgressValue progressBar) {
		int minMatrixSize = data.getData("minmatrixsize", Integer.class);
		int maxMatrixSize = data.getData("maxmatrixsize", Integer.class);

		BigInteger TWENTY_SIX = BigInteger.valueOf(26);
		
		for(int matrixSize = minMatrixSize; matrixSize <= maxMatrixSize; ++matrixSize)
			progressBar.addMaxValue(TWENTY_SIX.pow((int)Math.pow(matrixSize, 2)));

		HillCipherTask hct = new HillCipherTask(cipherText, language, output, progressBar);
		
		for(int matrixSize = minMatrixSize; matrixSize <= maxMatrixSize; ++matrixSize)
			this.run(hct, 0, 25, (int)Math.pow(matrixSize, 2), matrixSize, matrixSize, 0, new int[(int)Math.pow(matrixSize, 2)]);
		
		return hct.plainText;
	}
	
	public static class HillCipherTask implements MatrixCreation {

		public String cipherText;
		public ILanguage language;
		public Output output;
		public ProgressValue progressBar;
		
		public HillCipherTask(String cipherText, ILanguage language, Output output, ProgressValue progressBar) {
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
				this.lastText = Hill.decode(this.cipherText, matrix);
				
				this.currentScore = QuadgramStats.scoreFitness(this.lastText, this.language);
				if(this.currentScore > this.bestScore) {
					
					this.output.println("Fitness: %f, Matrix: %s, Plaintext: %s", this.currentScore, matrix, this.lastText);
					this.bestScore = this.currentScore;
					this.plainText = this.lastText;
				}
			}
			catch(MatrixNoInverse e) {
				return;
			}
			catch(MatrixNotSquareException e) {
				return;
			}
			finally {
				this.progressBar.addValue(1);
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
		EncryptionData data = EncryptionData.createNew();
		String text = rangeBox.getText().replaceAll("[^-0-9]", "");
		int minlength = 0;
		int maxlength = 0;
		if(!text.contains("-")) {
			minlength = Integer.valueOf(text);
			maxlength = Integer.valueOf(text);
		}
		else {
			minlength = Integer.valueOf(text.split("-")[0]);
			maxlength = Integer.valueOf(text.split("-")[1]);
		}
		data.putData("minmatrixsize", Math.min(minlength, maxlength));
		data.putData("maxmatrixsize", Math.max(maxlength, maxlength));
		return data;
	}
	
	private JTextField rangeBox = new JTextField("2-3");
	
	@Override
	public JPanel getVarsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel range = new JLabel("Matrix Size range:  ");
		range.setMaximumSize(new Dimension(200, 100));
		panel.add(range);
		panel.add(rangeBox);
		return panel;
	}
	
	@Override
	public List<StatisticRange> getStatistics() {
		return null;
	}
	
	@Override
	public boolean canDictionaryAttack() {
		return false;
	}

	@Override
	public void tryDictionaryAttack(String cipherText, List<String> words, ILanguage language, Output output, ProgressValue progressBar) {
		
	}
}

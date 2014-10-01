package javalibrary.math.matrics;

import javalibrary.exception.MatrixMutiplyException;
import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class Matrix {

	public final int[] order = new int[2];
	public final int[][] matrix;
	
	public Matrix(String... rows) {
		this.matrix = new int[rows.length][rows[0].contains(",") ? rows[0].split(",").length : 1];
		
		for(int i = 0; i < rows.length; ++i) {
			
			String[] values;
			if(!rows[i].contains(","))
				values = new String[] {rows[i]};
			else
				values = rows[i].split(",");
			
			for(int k = 0; k < values.length; ++k)
				this.matrix[i][k] = Integer.parseInt(values[k]);
		}
	
		this.order[0] = matrix.length; //Number of rows (y)
		this.order[1] = matrix[0].length; //Number of columns (x)
		this.print();
	}
	
	public Matrix(int[][] rows) {
		this.matrix = rows;
		this.order[0] = matrix.length; //Number of rows (y)
		this.order[1] = matrix[0].length; //Number of columns (x)
		this.print();
	}
	
	public void print() {
		System.out.println("Matrix " + this);
		for(int i = 0; i < this.order[0]; ++i) {
			String str = " ";
			for(int k = 0; k < this.matrix[i].length; ++k) {
				str += this.matrix[i][k];
				if(k != this.matrix[i].length - 1)
					str += ",";
			}
			System.out.println(str);
		}
		System.out.println(" Y Order: " + this.order[0]);
		System.out.println(" X Order: " + this.order[1]);
		System.out.println("");
	}
	
	/**
	 * 
	 * @param multiMatrix The matrix you wish to test if multiplication is possible
	 * @return If the 
	 */
	public boolean canMultiplyMatrixBy(Matrix multiMatrix) {
		return this.order[1] == multiMatrix.order[0];
	}
	
	public Matrix setModular(int mod) {
		int[][] newMatrix = new int[this.order[0]][this.order[1]];
		
		for(int y = 0; y < this.order[0]; ++y)
			for(int x = 0; x < this.order[1]; ++x)
				newMatrix[y][x] = MathHelper.wrap(this.matrix[y][x], 0, mod);
		
		return new Matrix(newMatrix);
	}
	
	public Matrix multiply(int times) {
		int[][] newMatrix = new int[this.order[0]][this.order[1]];
		
		for(int y = 0; y < order[0]; ++y)
			for(int x = 0; x < order[1]; ++x)
				newMatrix[y][x] = matrix[y][x] * times;
		
		return new Matrix(newMatrix);
	}
	
	public Matrix multiply(Matrix multiMatrix) {
		if(!canMultiplyMatrixBy(multiMatrix))
			throw new MatrixMutiplyException();
		
		int[][] newMatrix = new int[this.order[0]][multiMatrix.order[1]];
		
			for(int y = 0; y < multiMatrix.order[1]; ++y) {
				for(int x = 0; x < this.order[1]; ++x) {
					for(int pos = 0; pos < multiMatrix.order[1]; ++pos) {
						int first = matrix[y][x];
						int second = multiMatrix.matrix[x][pos];
						newMatrix[y][pos] += first * second;
				}
			}
		}
		
		return new Matrix(newMatrix);
		
	}
}

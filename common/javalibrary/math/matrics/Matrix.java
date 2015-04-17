package javalibrary.math.matrics;

import java.math.BigInteger;

import javalibrary.exception.MatrixMutiplyException;
import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
import javalibrary.math.MathHelper;

/**
 * @author Alex Barter (10AS)
 */
public class Matrix {

	public final int[] order = new int[2];
	public final double[][] matrix;
	
	public Matrix(String... data) {
		this.matrix = new double[data.length][data[0].contains(",") ? data[0].split(",").length : 1];
		
		for(int i = 0; i < data.length; ++i) {
			
			String[] values;
			if(!data[i].contains(","))
				values = new String[] {data[i]};
			else
				values = data[i].split(",");
			
			for(int k = 0; k < values.length; ++k)
				this.matrix[i][k] = Double.parseDouble(values[k]);
		}
	
		this.order[0] = this.matrix.length; //Number of rows (y)
		this.order[1] = this.matrix[0].length; //Number of columns (x)
	}
	
	public Matrix(double[][] data) {
		this.matrix = data;
		this.order[0] = this.matrix.length; //Number of rows (y)
		this.order[1] = this.matrix[0].length; //Number of columns (x)
	}
	
	public Matrix(int[][] data) {
		this.matrix = new double[data.length][data[0].length];
		
		for(int y = 0; y < data.length; ++y) {
	
			for(int x = 0; x < data[y].length; ++x)
				this.matrix[y][x] = new Double(data[y][x]);
		}
		this.order[0] = this.matrix.length; //Number of rows (y)
		this.order[1] = this.matrix[0].length; //Number of columns (x)
	}
	
	public Matrix(int[] data, int rows, int columns) {
		this.matrix = new double[rows][columns];
		
		for(int y = 0; y < rows; ++y) {
	
			for(int x = 0; x < columns; ++x)
				this.matrix[y][x] = new Double(data[y * rows + x]);
		}
		this.order[0] = rows; //Number of rows (y)
		this.order[1] = columns; //Number of columns (x)
	}
	
	public void print() {
		System.out.println("Matrix ");
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
	
	public Matrix modular(int mod) {
		double[][] newMatrix = new double[this.order[0]][this.order[1]];
		
		for(int y = 0; y < this.order[0]; ++y)
			for(int x = 0; x < this.order[1]; ++x)
				newMatrix[y][x] = MathHelper.wrap(this.matrix[y][x], 0, mod);
		
		return new Matrix(newMatrix);
	}
	
	public Matrix multiply(double times) {
		double[][] newMatrix = new double[this.order[0]][this.order[1]];
		
		for(int y = 0; y < this.order[0]; ++y)
			for(int x = 0; x < this.order[1]; ++x)
				newMatrix[y][x] = this.matrix[y][x] * times;
		
		return new Matrix(newMatrix);
	}
	
	public Matrix multiply(Matrix multiMatrix) {
		if(!this.canMultiplyMatrixBy(multiMatrix))
			throw new MatrixMutiplyException();
		
		double[][] newMatrix = new double[this.order[0]][multiMatrix.order[1]];
		
		for(int y = 0; y < multiMatrix.order[0]; ++y) {
			for(int x = 0; x < this.order[1]; ++x) {
				for(int pos = 0; pos < multiMatrix.order[1]; ++pos) {
					double first = this.matrix[y][x];
					double second = multiMatrix.matrix[x][pos];
					newMatrix[y][pos] += first * second;
				}
			}
		}
		
		return new Matrix(newMatrix);
	}
	
	public int size() {
	    if(!this.isSquare())
	        throw new MatrixNotSquareException();
	    
	    return this.order[0];
	}
	
	public boolean isSquare() {
		return this.order[0] == this.order[1];
	}
	
	public double determinant() {
	    if(!this.isSquare())
	        throw new MatrixNotSquareException();
	    
	    if (this.order[0] == 1 && this.order[1] == 1)
	    	return this.matrix[0][0];

	    if (this.order[0] == 2 && this.order[1] == 2)
	        return this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0];
	    
	    double sum = 0.0F;
	    for (int i = 0; i < this.order[1]; i++)
	        sum += this.changeSign(i) * this.matrix[0][i] * createSubMatrix(0, i).determinant();
	    
	    return sum;
	}
	
	public int changeSign(int column) {
		return column % 2 == 0 ? 1 : -1;
	}
	
	public Matrix createSubMatrix(int excluding_row, int excluding_col) {
		double[][] newMatrix = new double[this.order[0] - 1][this.order[1] - 1];
		
	    int newRow = 0;
	    for(int i = 0; i < this.order[0]; i++) {
	        if(i == excluding_row)
	            continue;
	        
	        int newColumn = 0;
	        for(int j = 0; j < this.order[1];j++) {
	            if (j == excluding_col)
	                continue;
	            newMatrix[newRow][newColumn] = this.matrix[i][j];
	            newRow += 1;
	            newColumn += 1;
	        }
	    }
	    return new Matrix(newMatrix);
	} 
	
	public Matrix adjugate() {
		double[][] newMatrix = new double[this.order[1]][this.order[0]];
		
	    for (int i = 0; i < this.order[0]; i++)
	        for (int j = 0; j < this.order[1]; j++)
	        	newMatrix[j][i] = this.matrix[i][j];
	    
	    return new Matrix(newMatrix);
	}
	
	public Matrix minors() {
		if(this.order[0] == 1 && this.order[1] == 1) {
			double[][] newMatrix = new double[][] {new double[] {this.matrix[0][0]}};
			return new Matrix(newMatrix);
		}
		
		if(this.order[0] == 2 && this.order[1] == 2) {
			double[][] newMatrix = new double[][] {new double[] {this.matrix[1][1], this.matrix[1][0]}, new double[] {this.matrix[0][1], this.matrix[0][0]}};
			return new Matrix(newMatrix);
		}
		
		double[][] newMatrix = new double[this.order[0]][this.order[1]];
		
		for (int i = 0; i < this.order[0]; i++)
	        for (int j = 0; j < this.order[1]; j++)
	        	newMatrix[i][j] = createSubMatrix(i, j).determinant();
		
		 return new Matrix(newMatrix);
	}
	
	public Matrix cofactor() {
		double[][] newMatrix = new double[this.order[0]][this.order[1]];
		
	    for (int i = 0; i < this.order[0]; i++)
	        for (int j = 0; j < this.order[1]; j++)
	        	newMatrix[i][j] = changeSign(i) * changeSign(j) * this.matrix[i][j];
	    
	    return new Matrix(newMatrix);
	}
	
	public Matrix inverse() {
		if(!this.isSquare())
	        throw new MatrixNotSquareException();
		
		double determinant = this.determinant();
		
		if(determinant == 0)
			throw new MatrixNoInverse();
		
	    return this.minors().cofactor().adjugate().multiply(1.0F / determinant);
	}
	
	public Matrix copy() {
		double[][] newMatrix = new double[this.order[0]][this.order[1]];
		
	    for (int y = 0; y < this.order[0]; y++)
	        for (int x = 0; x < this.order[1]; x++)
	        	newMatrix[y][x] = this.matrix[y][x];
	    
	    return new Matrix(newMatrix);
	}
	
	public Matrix inverseMod(int mod) throws MatrixNotSquareException, MatrixNoInverse {
		if(!this.isSquare())
	        throw new MatrixNotSquareException();
		
		double determinant = this.determinant();
		int multiplicativeInverse = 0;
		try {
			multiplicativeInverse = BigInteger.valueOf((int)determinant).modInverse(BigInteger.valueOf(mod)).intValue();
		}
		catch(ArithmeticException e) {
			throw new MatrixNoInverse();
		}
		
	    return this.minors().cofactor().adjugate().modular(mod).multiply(multiplicativeInverse).modular(mod);
	}
	
	@Override
	public String toString() {
		return String.format("Order %d, %d", this.order[0], this.order[1]);
	}
 }

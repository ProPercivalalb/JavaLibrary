package javalibrary.math.matrics;

import java.math.BigInteger;

import javalibrary.exception.MatrixMutiplyException;
import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
import javalibrary.lib.Timer;
import javalibrary.math.MathHelper;
import javalibrary.string.StringTransformer;
import javalibrary.string.ValueFormat;
import javalibrary.util.ArrayUtil;

public class MatrixNew {
	
	//Matrix data held in row to row order
	public double[] data;
	//Order of matrix rows * column
	public int rows, columns;
	
	public MatrixNew(int[] data, int rows, int columns) {
		this(ArrayUtil.convertNumType(data), rows, columns);
	}
	
	public MatrixNew(double[] data, int rows, int columns) {
		this.data = data;
		this.rows = rows;
		this.columns = columns;
		
		//Some error checking
		if(rows * columns != data.length)
			throw new IllegalArgumentException("Matrix data array len doesn't match specified args.");
	}
	
	/**
	 * Creates a blank matrix -- MUST BE POPULATED --
	 */
	public MatrixNew(int rows, int columns) {
		this(ArrayUtil.createDouble(rows * columns), rows, columns);
	}

	public void print() {
		System.out.println("Matrix ");
		for(int i = 0; i < this.rows; ++i) {
			String str = " ";
			for(int k = 0; k <  this.columns; ++k) {
				str += ValueFormat.getNumber(this.data[i * this.columns + k]);
				if(k != this.columns - 1)
					str += ",";
			}
			System.out.println(str);
		}
		System.out.println(" Y Order: " + this.rows);
		System.out.println(" X Order: " + this.columns);
		System.out.println("");
	}
	
	public MatrixNew multiply(double scalar) {
		MatrixNew product = new MatrixNew(this.rows, this.columns);
		
		for(int r = 0; r < this.rows; ++r)
			for(int c = 0; c < this.columns; ++c)
				product.data[r * product.columns + c] = this.data[r * this.columns + c] * scalar;
		
		return product;
	}
	
	public MatrixNew divide(double scalar) {
		MatrixNew product = new MatrixNew(this.rows, this.columns);
		
		for(int r = 0; r < this.rows; ++r)
			for(int c = 0; c < this.columns; ++c)
				product.data[r * product.columns + c] = this.data[r * this.columns + c] / scalar;
		
		return product;
	}

	public MatrixNew multiply(MatrixNew parent) {
		if(this.columns != parent.rows)
			throw new MatrixMutiplyException("Matrices are invlaid size for multiplication");
		
		MatrixNew product = new MatrixNew(this.rows, parent.columns);
		
		//Dot product each row from this matrix with each column from parent matrix
		for(int rT = 0; rT < this.rows; ++rT)
			for(int cP = 0; cP < parent.columns; ++cP)
				for(int pos = 0; pos < this.columns; ++pos)
					product.data[rT * product.columns + cP] += 
							this.data[rT * this.columns + pos] *
							parent.data[pos * parent.columns + cP];

		return product;
	}
	
	/**
	 * A crude modular function adds or subtracts the given mod till each cell is within 0 and mod.
	 */
	public MatrixNew modular(int mod) {
		MatrixNew result = new MatrixNew(this.rows, this.columns);
		
	    for(int r = 0; r < this.rows; r++)
	        for(int c = 0; c < this.columns; c++)
	        	result.data[r * result.columns + c] = MathHelper.wrap(this.data[r * result.columns + c], 0, mod);
	    
	    return result;
	}
	
	/**
	 * Returns the matrix which is the equivalent of dividing by said matrix
	 */
	public MatrixNew inverse() throws MatrixNoInverse {
		double determinant = this.determinant();
		
		if(determinant == 0)
			throw new MatrixNoInverse("Matrix has no inverse, determinant is 0.");
		
	    return this.minors().cofactor().adjugate().divide(determinant);
	}
	
	/**
	 * Returns the matrix which is the equivalent of multiplying by the multiplicative inverse matrix
	 */
	public MatrixNew inverseMod(int mod) throws MatrixNoInverse {
		double determinant = this.determinant();
		
		int multiplicativeInverse = 0;
		try {
			multiplicativeInverse = BigInteger.valueOf((int)determinant).modInverse(BigInteger.valueOf(mod)).intValue();
		}
		catch(ArithmeticException e) {
			throw new MatrixNoInverse("Matrix no inverse in mod " + mod + ", De: " + determinant  + ", MuIn of De: " + multiplicativeInverse);
		}
		
	    return this.minors().cofactor().adjugate().modular(mod).multiply(multiplicativeInverse).modular(mod);
	}
	
	/**
	 * Reflects the matrix in the main diagonal (which runs from top-left to bottom-right)
	 */
	public MatrixNew adjugate() {
		MatrixNew result = new MatrixNew(this.columns, this.rows);
		
		for(int r = 0; r < this.rows; ++r)
			for(int c = 0; c < this.columns; ++c)
				result.data[c * result.columns + r] = this.data[r * this.columns + c];
	    
	    return result;
	}
	
	/**
	 * 1x1 and 2x2 are special otherwise the in minor matrix each term is the determinant
	 * of the matrix excluding the row and column of which the term is in
	 * Minor matrix is only defined for square matrices
	 */
	public MatrixNew minors() {
		if(this.rows != this.columns)
			throw new MatrixNotSquareException("The determinant can't be found for non-square matrices");
		
		MatrixNew result = new MatrixNew(this.rows, this.columns);
		
		switch(this.rows) {
		case 1: //Simply the only number in the matrix
			result.data[0] = this.data[0];
	    	break;
	    case 2: //2x2 matrices
	    	result.data[0] = this.data[3];
	    	result.data[1] = this.data[2];
	    	result.data[2] = this.data[1];
	    	result.data[3] = this.data[0];
	    	break;
	    default: //Matrices greater than 2x2  
	    	for(int r = 0; r < this.rows; r++)
		        for(int c = 0; c < this.columns; c++)
		        	result.data[r * result.columns + c] = this.createMatrixExcluding(r, c).determinant();
		}
		
		return result;
	}
	
	/**
	 * Then the cofactor matrix C has each element of M multiplied by its sign 
	 * -1^{r+c} where r,c are the row and column number of each position.
	 */
	public MatrixNew cofactor() {
		MatrixNew result = new MatrixNew(this.rows, this.columns);
		
	    for(int r = 0; r < this.rows; r++)
	        for(int c = 0; c < this.columns; c++)
	        	result.data[r * result.columns + c] = this.changeSign(r) * this.changeSign(c) * this.data[r * this.columns + c];
	    
	    return result;
	}
	
	public double determinant() {
	    if(this.rows != this.columns)
	    	throw new MatrixNotSquareException("The determinant can't be found for non-square matrices");
	    
	    
	    switch(this.rows) {
	    case 1: //Simply the only number in the matrix
	    	return this.data[0];
	    case 2: //2x2 matrix ad - bc
	    	return this.data[0] * this.data[3] - this.data[1] * this.data[2];
	    default: //Matrices greater than 2x2  
	    	double sum = 0.0D;
	    	for(int i = 0; i < this.columns; i++)
	    		sum += this.changeSign(i) * this.data[i] * this.createMatrixExcluding(0, i).determinant();   
	    	return sum;		
	    }
	}
	
	public MatrixNew createMatrixExcluding(int rowT, int columnT) {
		MatrixNew minorCopy = new MatrixNew(this.columns - 1, this.rows - 1);
		
	    int rowN = 0;
	    for(int rowO = 0; rowO < this.rows; rowO++) {
	        if(rowO == rowT) continue;
	        
	        int columnN = 0;
	        for(int columnO = 0; columnO < this.columns; columnO++) {
	            if(columnO == columnT) continue;
	            minorCopy.data[rowN * minorCopy.columns + columnN] = this.data[rowO * this.columns + columnO];
	            columnN += 1;
	        }
            rowN += 1;
	    }
	    
	    return minorCopy;
	}
	
	//TODO create a unquine index for each square matrix
	//public int indexifier() {
	//	
	//}
	
	//Small internally used function that returns 1 when i is even and -1 when odd
	private int changeSign(int i) {
		return i % 2 == 0 ? 1 : -1;
	}
	
	@Override
	public String toString() {
		return "[" + StringTransformer.joinWithClean(this.data, ", ") + "] " + rows + "x" + columns;
	}
}

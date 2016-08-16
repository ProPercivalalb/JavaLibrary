package javalibrary.math.matrics;

import javalibrary.exception.MatrixMutiplyException;
import javalibrary.exception.MatrixNoInverse;
import javalibrary.exception.MatrixNotSquareException;
import javalibrary.lib.Timer;
import javalibrary.string.ValueFormat;
import javalibrary.util.ArrayUtil;

public class MatrixNew {

	public static void main(String[] args) {
		MatrixNew m1 = new MatrixNew(new int[] {3, 4, 2}, 1, 3);
	
		MatrixNew m2 = new MatrixNew(new int[] {13,9,7,15,8,7,4,6,6,4,0,3}, 3, 4);
		
		MatrixNew product = new MatrixNew(new int[] {3, 0, 2, 2, 0, -2, 0, 1, 1}, 3, 3);//m1.multiply(m2);
	
		
		System.out.println("" + new MatrixNew(new int[] {6,1,1,4,-2,5,2,8,7}, 3, 3).determinant());
	}
	
	//Matrix data held in row to row order
	public double[] data;
	//Order of matrix rows x column
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
	
	public MatrixNew inverse() {
		double determinant = this.determinant();
		
		if(determinant == 0)
			throw new MatrixNoInverse();
		
	    return this.minors().cofactor().adjugate().divide(determinant);
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
	
	//Small internally used function that returns 1 when i is even and -1 when odd
	private int changeSign(int i) {
		return i % 2 == 0 ? 1 : -1;
	}
}

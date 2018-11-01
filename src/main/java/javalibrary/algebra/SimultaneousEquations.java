package javalibrary.algebra;

import javalibrary.math.ArrayOperations;
import javalibrary.math.GCD;
import javalibrary.math.matrics.Matrix;
import javalibrary.util.ArrayUtil;

public class SimultaneousEquations {

	public static int[] solveSimEquationsMod(int[][] simEquations, int mod) {
		int UNKNOWNS = simEquations.length;
		
		int[] matrixData = new int[0];
		for(int i = 0; i < UNKNOWNS; i++) 
			matrixData = ArrayUtil.concat(matrixData, ArrayUtil.copyRange(simEquations[i], 0, UNKNOWNS));
		
		Matrix matrix = new Matrix(matrixData, UNKNOWNS, UNKNOWNS);
		Matrix invMatrix = matrix.inverseMod(mod);
			
		Matrix equalsMatrix = new Matrix(UNKNOWNS, 1);
		for(int i = 0; i < UNKNOWNS; i++)
			equalsMatrix.data[i] = simEquations[i][UNKNOWNS];

		
		return ArrayUtil.convertNumType(invMatrix.multiply(equalsMatrix).modular(mod).data);
	}
	
	public static double[] solveSimEquations(double[][] simEquations) {
		int UNKNOWNS = simEquations.length;
		
		double[] matrixData = new double[0];
		for(int i = 0; i < UNKNOWNS; i++)
			matrixData = ArrayUtil.concat(matrixData, ArrayUtil.copyRange(simEquations[i], 0, UNKNOWNS));
		
		Matrix matrix = new Matrix(matrixData, UNKNOWNS, UNKNOWNS);
		
		Matrix equalsMatrix = new Matrix(UNKNOWNS, 1);
		for(int i = 0; i < UNKNOWNS; i++)
			equalsMatrix.data[i] = simEquations[i][UNKNOWNS];

		return matrix.inverse().multiply(equalsMatrix).data;
	}
	

	
	
	
	
	
	
	
	
	
	
	public static double[] solveSimEquationsNODecimal(double[][] simEquations) {
		int UNKNOWNS = simEquations.length;
		
		int equatIndex = 0;
		int constIndex = 0;
		
		
		for(int i = 0; i < UNKNOWNS; i++)
			for(int j = 0 ; j < UNKNOWNS; j++)
				if(i != j)
					simEquations[i] = ArrayOperations.add(simEquations[i], simEquations[j]);
		out:
		for(int j = 0 ; j < UNKNOWNS; j++) {
			boolean zero = false;
			for(int i = 0; i < UNKNOWNS; i++) {
			
				double value = simEquations[i][j];
				if(value == 0.0D) {
					zero = true;
					break;
				}
			}
	
			if(!zero) {
				equatIndex = 0;
				constIndex = j;
				break;
			}
	
		}
		
		double lcm = 1;
		for(int i = 0; i < UNKNOWNS; i++) 
			lcm *= simEquations[i][constIndex];
		
		for(int i = 0; i < UNKNOWNS; i++) 
			ArrayOperations.multiply(simEquations[i], lcm / simEquations[i][constIndex]);
		
		double[][] productEquations = new double[UNKNOWNS - 1][UNKNOWNS];
		for(int i = 0, realI = 0; i < UNKNOWNS; i++) {
			if(i == equatIndex) continue;
			double[] newEquation = new double[UNKNOWNS];
			double[] target1 = simEquations[equatIndex];
			double[] target2 = simEquations[i];
			
			//number of 'a's in second equation
			for(int j = 0, realJ = 0; j < UNKNOWNS + 1; j++) {
				if(j == constIndex) continue;
				newEquation[realJ++] = target1[j] - target2[j];
			}
			
			boolean allInteger = true;
			for(int j = 0; j < UNKNOWNS; j++)
				if(newEquation[j] != Math.floor(newEquation[j])) 
					allInteger = false;
			
			
			if(allInteger)
				ArrayOperations.divide(newEquation, GCD.gcd(ArrayUtil.convertNumType(newEquation)));
			productEquations[realI++] = newEquation;
		}
		
		double[] solution = null;
		
		if(UNKNOWNS - 1 == 1)
			solution = new double[] {(productEquations[0][1] / productEquations[0][0])};
		else
			solution = solveSimEquations(productEquations);
	
		double[] finalSolution = new double[UNKNOWNS];
		int indexGivenSolution = 0;
		for(int i = 0; i < UNKNOWNS; i++) {
			if(i == constIndex)
				finalSolution[i] = -1;
			else
				finalSolution[i] = solution[indexGivenSolution++];
		}
		
		for(int subBackIndex = 0; subBackIndex < UNKNOWNS; subBackIndex++) {
			if(simEquations[subBackIndex][constIndex] == 0) continue;
			double answer = simEquations[subBackIndex][UNKNOWNS];
			
			for(int i = 0; i < UNKNOWNS; i++) {
				if(i == constIndex) continue;
				answer -= finalSolution[i] * simEquations[subBackIndex][i];
			}
			finalSolution[constIndex] = answer / simEquations[subBackIndex][constIndex];
			break;
		}
		
		return finalSolution;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

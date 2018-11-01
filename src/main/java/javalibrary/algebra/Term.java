package javalibrary.algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javalibrary.string.ValueFormat;

public class Term implements Comparable<Term> {

	public double coefficent;
	public List<Variable> variables;
	
	public Term() {
		this(1.0D);
	}
	
	public Term(double coefficent) {
		this.coefficent = coefficent;
		this.variables = new ArrayList<Variable>();
	}
	
	public static Term createTerm(String str) {
		Term term = new Term();
		
		int comultipler = 1;
		if(str.startsWith("-"))
			comultipler = -1;
		if(str.startsWith("-") || str.startsWith("+"))
			str = str.substring(1, str.length());
		
		String numberString = "";
		for(int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if(Arrays.asList('0','1','2','3','4','5','6','7','8','9','.').contains(ch))
				numberString += ch;
			else {

				break;
			}
		}
		
		
		str = str.substring(numberString.length(), str.length());
		
		if(numberString.length() == 0)
			numberString = "1";
		
		term.coefficent = comultipler * Double.valueOf(numberString);
		
		if(str.length() > 0) {
			for(int i = 0; i < str.length(); i++) {
				char id = str.charAt(i);
				Variable var = new Variable(id);
				if(i + 1 < str.length()) {
					char ch = str.charAt(i + 1);
					if(ch == '^') {
						String numberString2 = "";
						for(int j = i + 2; j < str.length(); j++) {
							char char2 = str.charAt(j);
							if(Arrays.asList('0','1','2','3','4','5','6','7','8','9','.','-','+').contains(char2))
								numberString2 += char2;
							else {

								break;
							}
						}
						
						i += numberString2.length() + 1;
						var.power = Double.valueOf(numberString2);
					}
				}
				
				
				term.variables.add(var);
			}
		}
		
		
		return term;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Term) {
			Term otherTerm = (Term)obj;
			List<Variable> baseVarCopy = new ArrayList<Variable>(otherTerm.variables);
			List<Variable> tempVarCopy = new ArrayList<Variable>(this.variables);
			
			return baseVarCopy.equals(tempVarCopy) && otherTerm.coefficent == this.coefficent;
		}
		
		return false;
	}

	public String toStringNoSign() {
		String termString = Math.abs(this.coefficent) == 1.0D && this.variables.size() > 0 ? "" : ValueFormat.getNumber(Math.abs(this.coefficent)) + "";
		for(Variable variable : this.variables)
			termString += variable.toString() + "";
		return termString;
	}
	
	@Override
	public String toString() {
		String termString = this.coefficent == 1.0D ? "" : ValueFormat.getNumber(this.coefficent) + "";
		for(Variable variable : this.variables)
			termString += variable.toString() + "";
		return termString;
	}

	public Term addVariable(Variable variable) {
		this.variables.add(variable);
		Collections.sort(this.variables);
		return this;
	}

	@Override
	public int compareTo(Term arg) {
		return (int)(arg.coefficent - this.coefficent);
	}
	
}

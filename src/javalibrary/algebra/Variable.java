package javalibrary.algebra;

import javalibrary.string.ValueFormat;

public class Variable implements Comparable<Variable> {

	public char identifier;
	public double power;
	
	public Variable(char identifier, double power) {
		this.identifier = identifier;
		this.power = power;
	}
	
	public Variable(char identifier) {
		this(identifier, 1.0D);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Variable) {
			Variable otherVar = (Variable)obj;
			return otherVar.identifier == this.identifier && otherVar.power == this.power;
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		return "(" + this.identifier + (this.power == 1.0D ? "" : "^" + ValueFormat.getNumber(this.power)) + ")";
	}

	@Override
	public int compareTo(Variable arg) {
		return Character.compare(this.identifier, arg.identifier);
	}
}

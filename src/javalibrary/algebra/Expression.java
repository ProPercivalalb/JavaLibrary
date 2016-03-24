package javalibrary.algebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Expression {

	public List<Term> terms;
	
	public Expression() {
		this.terms = new ArrayList<Term>();
	}
	
	/**
	 * 
	 * @return 
	 */
	public static Expression createExpression(String str) {
		Expression expression = new Expression();
		
		str = str.replace(" ", "");
		
		char lastChar = ' ';
		String currentTerm = "";
		
		for(int i = 0; i < str.length(); i++) {
			char cChar = str.charAt(i);
			
			if(lastChar != '^' && (cChar == '+' || cChar == '-') || i == str.length() - 1) {
				if(i == str.length() - 1)
					currentTerm += cChar;
				
				if(currentTerm.length() > 0) {
					expression.terms.add(Term.createTerm(currentTerm));
					currentTerm = "";
				}
			}
			
			lastChar = cChar;
			currentTerm += cChar;
		}
		
		return expression;
	}
	
	public void addTerm(Term term, boolean keptOrder) {
		this.terms.add(term);
		if(!keptOrder)
			Collections.sort(this.terms);
	}
	
	public void addTerm(Term term) {
		this.addTerm(term, true);
	}
	
	public void subValueIn(char identifier, double value) {
		System.out.println("Subbing in value " + value + " to var " + identifier);
		for(Term term : this.terms) {
			
			List<Integer> indexsToRemove = new ArrayList<Integer>();
			int i = 0;
			for(Variable var : term.variables) {
				if(var.identifier == identifier) {
					term.coefficent *= Math.pow(value, var.power);
					indexsToRemove.add(i);
				}
				i++;
			}
			
			for(int index : indexsToRemove)
				term.variables.remove(index);
		}
	}
	
	public void combineLikeTerms() {
		List<Term> combined = new ArrayList<Term>();
		
		while(this.terms.size() > 0) {
			Term baseTerm = this.terms.get(0);
			
			List<Term> likeTerms = new ArrayList<Term>();
			
			int i = 0;
			for(Term term : this.terms) {
				if(i++ == 0) continue;
				List<Variable> baseVarCopy = new ArrayList<Variable>(baseTerm.variables);
				List<Variable> tempVarCopy = new ArrayList<Variable>(term.variables);
				if(baseVarCopy.size() == tempVarCopy.size())
					if(baseVarCopy.size() == 0)
						likeTerms.add(term);
					else {
						Collections.sort(baseVarCopy);
						Collections.sort(tempVarCopy);
						
						if(baseVarCopy.equals(tempVarCopy))
							likeTerms.add(term);
					}
		
			}
			for(Term likeTerm : likeTerms) {
				baseTerm.coefficent += likeTerm.coefficent;
			}
			
			combined.add(baseTerm);
			
			this.terms.remove(0);
			this.terms.removeAll(likeTerms);
		}
		
		this.terms = combined;
	}
	
	@Override
	public String toString() {
		String expressionString = "";
		
		List<Term> temp = new ArrayList<Term>(this.terms);
		
		for(int i = 0; i < temp.size(); i++) {
			Term term = temp.get(i);
			
			if(term.coefficent < 0.0D)
				expressionString += "- ";
			else if(i > 0)
				expressionString += "+ ";
			
			expressionString += term.toStringNoSign();
			
			if(i < temp.size() - 1)
				expressionString += " ";
		}
		
		if(temp.size() == 0)
			expressionString = "0";
		
		return expressionString;
	}
	
	public boolean simplifyFully() {
		List<Term> combined = new ArrayList<Term>();
		
		for(Term term : this.terms) {
			for(Term term2 : this.terms) {
				if(term == term2) continue;
				
				boolean hasSameVars = true;
				
				for(Variable var1 : (term.variables.size() > term2.variables.size() ?  term.variables : term2.variables)) {
					if(!(term.variables.size() > term2.variables.size() ?  term2.variables : term.variables).contains(var1))
						hasSameVars = false;
				}
				
				if(hasSameVars) {
					combined.add(new Term(term.coefficent + term2.coefficent));
				}
				else
					combined.add(term);
			}
		}
		
		this.terms = combined;
		
		return true;
	}
}

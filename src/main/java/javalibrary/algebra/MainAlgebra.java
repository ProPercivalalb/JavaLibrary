package javalibrary.algebra;

public class MainAlgebra {

	public static void main(String[] args) {
		Expression expression = Expression.createExpression("2a^2.62 - 4xy^2 + z + 4x^2 + 8x");
		
		//expression.addTerm(new Term(2).addVariable(new Variable('x')));
		//expression.addTerm(new Term(-4).addVariable(new Variable('y', 2)).addVariable(new Variable('x')));
		//expression.addTerm(new Term(1).addVariable(new Variable('z')));
		//expression.addTerm(new Term(4).addVariable(new Variable('x', 2)));
		//expression.addTerm(new Term(8).addVariable(new Variable('x')));
		
		System.out.println(expression.toString());
		expression.combineLikeTerms();
		System.out.println(expression.toString());
		expression.subValueIn('x', 5);
		expression.subValueIn('y', 2);
		expression.subValueIn('z', 105);
		System.out.println(expression.toString());
		expression.combineLikeTerms();
		System.out.println(expression.toString());
	}

}

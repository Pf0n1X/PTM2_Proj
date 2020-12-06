package booleanExpressions;

public class NotExpression implements BooleanExpression {
	
	// Data Members
	private BooleanExpression expression;
	
	// Constructors
	public NotExpression(BooleanExpression expression) {
		this.expression = expression;
	}
	
	// Getters & Setters
	/**
	 * @return the expression
	 */
	public BooleanExpression getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(BooleanExpression expression) {
		this.expression = expression;
	}

	@Override
	public boolean calculate() {
		
		return !this.expression.calculate();
	}

}

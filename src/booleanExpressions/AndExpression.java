package booleanExpressions;

public class AndExpression extends BinaryBooleanExpression {

	@Override
	public boolean calculate() {
		
		return this.getRight().calculate() && this.getLeft().calculate();
	}
}
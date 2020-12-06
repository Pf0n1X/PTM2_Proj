package booleanExpressions;

public class OrExpression extends BinaryBooleanExpression {

	@Override
	public boolean calculate() {
		
		return this.getLeft().calculate() || this.getRight().calculate();
	}
}

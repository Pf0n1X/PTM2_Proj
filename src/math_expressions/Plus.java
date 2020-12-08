package math_expressions;

public class Plus extends BinaryExpression {

	public Plus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		return this.getLeft().calculate() + this.getRight().calculate();
	}
}

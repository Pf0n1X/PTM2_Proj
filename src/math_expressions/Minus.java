package math_expressions;

public class Minus extends BinaryExpression {

	public Minus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		return this.getLeft().calculate() - this.getRight().calculate();
	}

}

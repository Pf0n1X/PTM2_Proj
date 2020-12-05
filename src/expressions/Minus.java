package expressions;

public class Minus extends BinaryExpression {

	public Minus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		return this.getRight().calculate() - this.getLeft().calculate();
	}

}

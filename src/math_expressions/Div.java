package math_expressions;

public class Div extends BinaryExpression {

	public Div(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		return this.getLeft().calculate() / this.getRight().calculate();
	}

}

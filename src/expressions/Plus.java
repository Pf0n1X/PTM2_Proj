package expressions;

public class Plus extends BinaryExpression {

	public Plus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return this.getRight().calculate() + this.getLeft().calculate();
	}
}

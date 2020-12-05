package expressions;

public abstract class BinaryExpression implements Expression {
	
	// Data members
	protected Expression left;
	protected Expression right;
	
	// Constructors
	public BinaryExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	// Getters & Setters
	public void setRight(Expression right) {
		this.right = right;
	}
	
	public Expression getRight() {
		return this.right;
	}
	
	public void setLeft(Expression left) {
		this.left = left;
	}
	
	public Expression getLeft() {
		return this.left;
	}
}

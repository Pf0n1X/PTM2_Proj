package booleanExpressions;

public abstract class BinaryBooleanExpression implements BooleanExpression {
	
	// Data Members
	protected BooleanExpression right;
	protected BooleanExpression left;
	
	// Getters & Setters
	/**
	 * @return the right
	 */
	public BooleanExpression getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(BooleanExpression right) {
		this.right = right;
	}
	/**
	 * @return the left
	 */
	public BooleanExpression getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(BooleanExpression left) {
		this.left = left;
	}
	
	
}

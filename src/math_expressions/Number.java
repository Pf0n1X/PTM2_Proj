package math_expressions;

public class Number implements Expression {
	
	// Data Members
	private double value;
	
	// Constructors
	public Number(double value) {
		this.setValue(value);
	}
	
	// Getters && Setters
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	// Methods
	@Override
	public double calculate() {
		return this.getValue();
	}
}

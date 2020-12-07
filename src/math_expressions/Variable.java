package math_expressions;

import java.util.Observable;
import java.util.Observer;

public class Variable extends Observable implements Expression, Observer {
	
	// Data Members
	private String key;
	private double val;
	
	// Constructors
	public Variable(String key, double val) {
		this.setKey(key);
		this.setVal(val);
	}

	// Methods
	@Override
	public void update(Observable arg0, Object arg1) {
		Variable var = (Variable) arg0;
		
		// Compare the two variable values
		if (this.getVal() != var.getVal())
			setVal(var.getVal());
	}

	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// Getters & Setters 
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the val
	 */
	public double getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(double val) {
		this.val = val;
		setChanged();
		notifyObservers(this.val);
	}
}

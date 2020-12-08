package math_expressions;

import commands.ConnectCommand;

public class SimulatorSymbolVariable extends Variable {
	
	// Constructors
	public SimulatorSymbolVariable(String key, double val) {
		super(key, val);
	}
	
	// Getters & Setters
	@Override
	public void setVal(double val) {
		super.setVal(val);
		setChanged();
		notifyObservers(val);
		ConnectCommand.send("set " + this.getKey() + " " + this.getVal());
	}
}

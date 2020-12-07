package math_expressions;

import commands.ConnectCommand;

public class SimulatorVariable extends Variable {
	
	// Constructors
	public SimulatorVariable(String key, double val) {
		super(key, val);
	}
	
	// Getters & Setters
	@Override
	public void setVal(double val) {
		super.setVal(val);
		setChanged();
		notifyObservers(val);

		ConnectCommand.executeServerCommand("set " + this.getKey() + " " + this.getVal());
	}

}

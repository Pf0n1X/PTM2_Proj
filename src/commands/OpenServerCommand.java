package commands;

import math_expressions.Expression;

public class OpenServerCommand implements Command {
	
	// Data Members
	private Expression port;
	private Expression rate;
	
	// Constructors
	public OpenServerCommand(Expression port, Expression rate) {
		this.setPort(port);
		this.setRate(rate);
	}
	
	// Getters & Setters
	/**
	 * @return the port
	 */
	public Expression getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Expression port) {
		this.port = port;
	}

	/**
	 * @return the rate
	 */
	public Expression getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Expression rate) {
		this.rate = rate;
	}
	
	// Methods
	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}
}

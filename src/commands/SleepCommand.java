package commands;

import java.util.concurrent.TimeUnit;

import math_expressions.Expression;

public class SleepCommand implements Command {
	
	// Data Members
	private Expression timeExpression;
	
	// Constructors
	public SleepCommand(Expression timeExpression) {
		this.setTimeExpression(timeExpression);
	}
	
	// Getters & Setters
	/**
	 * @return the timeExpression
	 */
	public Expression getTimeExpression() {
		return timeExpression;
	}

	/**
	 * @param timeExpression the timeExpression to set
	 */
	public void setTimeExpression(Expression timeExpression) {
		this.timeExpression = timeExpression;
	}

	// Methods
	@Override
	public int execute() {
		try {
			TimeUnit.MILLISECONDS.sleep((long) this.getTimeExpression().calculate());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0; // TODO: Figure out what to return here.
	}

}

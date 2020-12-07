package commands;

import java.util.ArrayList;

import booleanExpressions.BooleanExpression;

public abstract class ConditionParser extends Command {
	
	// Data Members
	protected BooleanExpression condition;
	protected ArrayList<Command> commands;
	
	// Constructors
	public ConditionParser(BooleanExpression condition) {
		setCondition(condition);
		setCommands(new ArrayList<Command>());
	}
	
	// Getters & Setters
	/**
	 * @return the condition
	 */
	public BooleanExpression getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(BooleanExpression condition) {
		this.condition = condition;
	}
	/**
	 * @return the commands
	 */
	public ArrayList<Command> getCommands() {
		return this.commands;
	}
	/**
	 * @param commands the Commands to set
	 */
	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}
}
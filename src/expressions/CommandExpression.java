package expressions;

import commands.Command;

public class CommandExpression implements Expression {
	
	// Data Members
	Command c;
	
	// Constructors
	public CommandExpression(Command c) {
		this.c = c;
	}
	
	// Methods
	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return c.execute();
	}
}
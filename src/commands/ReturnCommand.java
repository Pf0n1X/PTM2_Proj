package commands;

import java.util.ArrayList;
import math_expressions.Expression;

public class ReturnCommand implements Command {
	
	// Data Members
	private Expression exp;
	
	// Constructors
	private ReturnCommand() {
		
	}
	
	// Methods
	@Override
	public int execute() {
		return 0;
	}
	
	// SubClasses
	public class Builder extends CommandBuilder {

		@Override
		public Command build(ArrayList<String> args) {
			return new ReturnCommand();
		}	
	}
}
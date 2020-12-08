package commands;

import java.util.ArrayList;

import interpreter.Interpreter;
import interpreter.ShuntingYard;
import math_expressions.Expression;

public class ReturnCommand extends Command {
	
	// Data Members
	private Expression exp;
	
	// Constructors
	public ReturnCommand(Interpreter interpreter) {
		super(interpreter);
	}
	
	// Methods
	@Override
	public int execute() {
		int indexToken = this.interpreter.getTokenIndex();
		String[] block = this.interpreter.getTokens().get(this.interpreter.getTokenBlockIndex());
		ArrayList<String> expression = new ArrayList<String>();

		for (int i = (indexToken + 1); i < block.length; i++) {
			expression.add(block[i]);
		}

		this.interpreter
				.setReturnedValue(ShuntingYard.run(expression, this.interpreter.getServerSymbolTable()));

		this.interpreter.setTokenIndex(expression.size() + this.interpreter.getTokenIndex());

		return 0;
	}
}
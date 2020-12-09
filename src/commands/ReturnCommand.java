package commands;

import java.util.ArrayList;

import interpreter.Interpreter;
import interpreter.ShuntingYard;

public class ReturnCommand extends Command {
	
	// Constructors
	public ReturnCommand(Interpreter interpreter) {
		super(interpreter);
	}
	
	// Methods
	@Override
	public int execute() {
		int tokenIndex = this.interpreter.getTokenIndex();
		String[] block = this.interpreter.getTokens().get(this.interpreter.getTokenBlockIndex());
		ArrayList<String> expression = new ArrayList<String>();

		for (int i = (tokenIndex + 1); i < block.length; i++) {
			expression.add(block[i]);
		}

		this.interpreter.setReturnedValue(
				ShuntingYard.run(expression, this.interpreter.getServerSymbolTable()));

		this.interpreter.setTokenIndex(expression.size() + this.interpreter.getTokenIndex());

		return 0;
	}
}
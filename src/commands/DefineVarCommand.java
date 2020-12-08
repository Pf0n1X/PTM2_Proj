package commands;

import java.util.ArrayList;

import interpreter.Interpreter;
import math_expressions.Variable;

public class DefineVarCommand extends Command {

	public DefineVarCommand(Interpreter interpreter) {
		super(interpreter);
	}

	@Override
	public int execute() {
		ArrayList<String[]> tokens = this.interpreter.getTokens();
		int indexBlockOfTokens = this.interpreter.getTokenBlockIndex();
		int indexToken = this.interpreter.getTokenIndex();
		String variableServerName = tokens.get(indexBlockOfTokens)[indexToken + 1];
		this.interpreter.getServerSymbolTable().put(variableServerName, new Variable(variableServerName, 0.0));
		this.interpreter.setTokenIndex(indexToken + 1);

		return 0;
	}
}
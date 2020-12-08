package commands;

import java.util.ArrayList;

import interpreter.Interpreter;
import interpreter.ShuntingYard;
import math_expressions.Variable;

public class PutVarCommand extends Command {
	
	
	public PutVarCommand(Interpreter interpreter) {
		super(interpreter);
	}
	
	@Override
	public int execute() {
		double res;
		Interpreter inter = this.getInterpreter();
		ArrayList<String[]> tokens = inter.getTokens();
		int tokenBlockIndex = inter.getTokenBlockIndex();
		int tokenIndex = inter.getTokenIndex();
		String serverVarKey = tokens.get(tokenBlockIndex)[tokenIndex - 1];
		ArrayList<String> expr = new ArrayList<String>();
		String[] block;
		
		// Check if the current command is bind. and if so, return.
		if (tokens.get(tokenBlockIndex)[tokenIndex + 1].equals("bind")) {
			return 0;
		}

		block = this.interpreter.getTokens().get(inter.getTokenBlockIndex());
		
		// Add the tokens to the expression.
		for (int i = (tokenIndex + 1); i < block.length; i++) {
			expr.add(block[i]);
		}

		res = ShuntingYard.run(expr, inter.getServerSymbolTable());
		
		// If a variable with that name exist, assign a new value to it.
		if (inter.getServerSymbolTable().containsKey(serverVarKey)) {
			Variable serverVariable = inter.getServerSymbolTable().get(serverVarKey);
			serverVariable.setVal(res);
			inter.getServerSymbolTable().put(serverVarKey, serverVariable);
		} else {
			
			// Otherwise, show an error.
			System.out.println("ERROR: A variable with the name " + serverVarKey + " does not exist.");
		}
		
		// Advance the token index cursor.
		inter.setTokenIndex(expr.size() + inter.getTokenIndex());

		return 0;
	}
}

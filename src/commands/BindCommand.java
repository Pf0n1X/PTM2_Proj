package commands;

import java.util.ArrayList;

import interpreter.Interpreter;
import math_expressions.SimulatorSymbolVariable;
import math_expressions.Variable;

public class BindCommand extends Command {

	public BindCommand(Interpreter interpreter) {
		super(interpreter);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int execute() {
		ArrayList<String[]> tokens = this.interpreter.getTokens();
		int indexBlockOfTokens = this.interpreter.getTokenBlockIndex();
		int indexToken = this.interpreter.getTokenIndex();
		String variableSimulatorName = tokens.get(indexBlockOfTokens)[indexToken + 1];
		String variableServerName = tokens.get(indexBlockOfTokens)[indexToken - 2];

		SimulatorSymbolVariable simulatorVariable = this.interpreter.getSimulatorSymbolTable().get(variableSimulatorName);
		Variable serverVariable = null;

		if (this.interpreter.getServerSymbolTable().containsKey(variableServerName) == true) {
			serverVariable = this.interpreter.getServerSymbolTable().get(variableServerName);
		} else {
			System.out.println("Variable with the name: " + variableServerName + "does not exists...");
			return 0;
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		simulatorVariable.addObserver(serverVariable);
		serverVariable.addObserver(simulatorVariable);

		serverVariable.setVal(simulatorVariable.getVal());

		this.interpreter.setTokenIndex(indexToken + 1);
		return 0;
	}

}

package commands;

import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import interpreter.Interpreter;
import interpreter.ShuntingYard;
import math_expressions.Expression;

public class SleepCommand extends Command {
	
	// Data Members
	private Expression timeExpression;
	
	// Constructors
	public SleepCommand(Interpreter interpeter) {
		super(interpeter);
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

		int timeToSleep = (int) ShuntingYard.execute(expression, this.interpreter.getServerSymbolTable());

		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.interpreter.setTokenIndex(expression.size() + this.interpreter.getTokenIndex());

		return 0;
	}

}

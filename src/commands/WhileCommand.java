package commands;

import interpreter.Interpreter;

public class WhileCommand extends ConditionParser {

	public WhileCommand(Interpreter interpreter) {
		super(interpreter);
	}

	@Override
	public int execute() {
		super.execute();
		
		Interpreter inter = this.getInterpreter();
		
		// Validate the condition and run through the commands if it is
		// equal to true.
		while (this.calculateCondition())
			this.runCommands();
		
		// Move the index cursors forward.
		inter.setTokenBlockIndex(this.getTokenBlockIndexBeginning() + this.getCommands().size());
		inter.setTokenIndex(0);
		
		return 0;
	}
}
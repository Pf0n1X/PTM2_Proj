package commands;

import interpreter.Interpreter;

public class IfCommand extends ConditionParser {
	
	
	public IfCommand(Interpreter interpreter) {
		super(interpreter);
	}

	@Override
	public int execute() {
		super.execute();
		
		Interpreter inter = this.getInterpreter();
		
		// Get the condition result.
		boolean conditionRes = this.calculateCondition();
		
		// Validate the condition.
		if (conditionRes)
			this.runCommands();
		
		// Move the index cursors forward.
		inter.setTokenBlockIndex(this.getTokenBlockIndexBeginning() + this.getCommands().size());
		inter.setTokenIndex(0);
		
		return 0;
	}
}
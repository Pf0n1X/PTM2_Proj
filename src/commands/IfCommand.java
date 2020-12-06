package commands;

import booleanExpressions.BooleanExpression;

public class IfCommand extends ConditionParser {
	
	
	public IfCommand(BooleanExpression condition) {
		super(condition);
	}

	@Override
	public int execute() {
		if (this.getCondition().calculate()) {
			for (Command command : commands) {
				command.execute();
			}
		};
	
		return 0; // TODO: What am I supposed to return here.
	}
}
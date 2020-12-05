package commands;

import booleanExpressions.BooleanExpression;

public class WhileCommand extends ConditionParser {

	public WhileCommand(BooleanExpression condition) {
		super(condition);
	}

	@Override
	public int execute() {
		while (this.getCondition().calculate()) {
			for (Command command : commands) {
				command.execute();
			}
		}
		
		return 0; // TODO: What am I supposed to return here ?
	}
}
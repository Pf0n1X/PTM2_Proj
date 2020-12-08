package commands;

import interpreter.Interpreter;

public class DisconnectCommand extends Command {

	public DisconnectCommand(Interpreter interpreter) {
		super(interpreter);
	}

	@Override
	public int execute() {
		ConnectCommand.close();

		OpenServerCommand.stop();

		return 1;
	}

}

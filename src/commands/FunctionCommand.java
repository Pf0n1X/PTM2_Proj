package commands;

import java.util.ArrayList;

public class FunctionCommand extends Command {
	
	// Data Members
	private ArrayList<Command> commands;
	
	// Constructors
	public FunctionCommand(ArrayList<Command> commands) {
		this.setCommands(commands);
	}
	
	// Getters & Setters
	/**
	 * @return the commands
	 */
	public ArrayList<Command> getCommands() {
		return commands;
	}


	/**
	 * @param commands the commands to set
	 */
	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}

	// Methods
	@Override
	public int execute() {
		for (Command command : commands) {
			command.execute();
		}
		
		return 0;
	}
}
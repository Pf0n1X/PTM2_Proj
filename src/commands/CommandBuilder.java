package commands;

import java.util.HashMap;
import interpreter.Interpreter;

public class CommandBuilder {
	
	// Data Members
	private HashMap<String, Command> commandMap;
	
	// Constructors
	public CommandBuilder(Interpreter interpreter) {
		this.setCommandMap(new HashMap<String, Command>());
		this.getCommandMap().put("return", new ReturnCommand(interpreter));
		this.getCommandMap().put("var", new DefineVarCommand(interpreter));
		this.getCommandMap().put("openDataServer", new OpenServerCommand(interpreter)); // TODO: Add port and rate settings
		this.getCommandMap().put("while", new WhileCommand(interpreter));
		this.getCommandMap().put("if", new IfCommand(interpreter));
		this.getCommandMap().put("bind", new BindCommand(interpreter));
		this.getCommandMap().put("connect", new ConnectCommand(interpreter));
		this.getCommandMap().put("disconnect", new DisconnectCommand(interpreter));
		this.getCommandMap().put("sleep", new SleepCommand(interpreter));
		this.getCommandMap().put("=", new PutVarCommand(interpreter));
	}
	
	// Methods
	public Command getCommand (String commandName) {
		return this.getCommandMap().get(commandName);
	};
	
	// Getters & Setters
	/**
	 * @return the commandMap
	 */
	public HashMap<String, Command> getCommandMap() {
		return commandMap;
	}

	/**
	 * @param commandMap the commandMap to set
	 */
	public void setCommandMap(HashMap<String, Command> commandMap) {
		this.commandMap = commandMap;
	}
}

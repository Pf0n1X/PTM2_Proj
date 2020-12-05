package interpreter;

import java.util.HashMap;

import commands.Command;
import commands.DefineVarCommand;
import commands.IfCommand;
import commands.OpenServerCommand;
import commands.SleepCommand;
import commands.WhileCommand;

public abstract class Interpreter {
	
	// Data Members
	private HashMap<String, Command> commandMap;
	
	
	public Interpreter() {
		this.setCommandMap(new HashMap<String, Command>());
		
		this.getCommandMap().put("var", new DefineVarCommand());
		this.getCommandMap().put("openDataServer", new OpenServerCommand(null, null)); // TODO: Add port and rate settings
		this.getCommandMap().put("while", new WhileCommand(null));
		this.getCommandMap().put("if", new IfCommand(null));
		this.getCommandMap().put("sleep", new SleepCommand(null));
	}

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

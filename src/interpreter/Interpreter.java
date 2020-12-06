package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.Line;

import commands.Command;
import commands.DefineVarCommand;
import commands.IfCommand;
import commands.OpenServerCommand;
import commands.SleepCommand;
import commands.WhileCommand;

public abstract class Interpreter {
	
	// Data Members
	private HashMap<String, Command> commandMap;
	
	// Constant Members
	private static final String lexerMatch = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|-?\\d+\\.?\\d*|-?\\d*\\.?\\d+|\".*\"|==|!=|<=|>=|<|>|\\+|-|\\*|\\/|&&|\\|\\||!|=|\\(|\\)|\\{|\\}|\\w+)";
	
	// Constructors
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
	
	// Methods
	private ArrayList<String> lexer(String input) {
		Matcher matcher = Pattern.compile(lexerMatch).matcher(input);
		ArrayList<String> output = new ArrayList<String>();
		
		while (matcher.find())
			output.add(input.substring(matcher.start(), matcher.end()));
		
		return output;
	}
}

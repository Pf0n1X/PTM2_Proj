package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commands.Command;
import commands.DefineVarCommand;
import commands.IfCommand;
import commands.OpenServerCommand;
import commands.ReturnCommand;
import commands.SleepCommand;
import commands.WhileCommand;
import math_expressions.CommandExpression;
import math_expressions.Plus;
import symbols.SymbolTable;

public class Interpreter {
	
	// Data Members
	private HashMap<String, Command> commandMap;
	private SymbolTable symbolTable;
	
	
	// Constant Members
	private static final String lexerMatch = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|-?\\d+\\.?\\d*|-?\\d*\\.?\\d+|\".*\"|==|!=|<=|>=|<|>|\\+|-|\\*|\\/|&&|\\|\\||!|=|\\(|\\)|\\{|\\}|\\w+)";
	
	// Constructors
	public Interpreter() {
		
		// Initialize the command map.
		this.setCommandMap(new HashMap<String, Command>());
//		this.getCommandMap().put("+", new Plus(null, null));
//		this.getCommandMap().put("-", new Minus(null, null));
//		this.getCommandMap().put("*", new Mul(null, null));
//		this.getCommandMap().put("/", new Div(null, null));
		this.getCommandMap().put("return", new ReturnCommand());
		this.getCommandMap().put("var", new DefineVarCommand());
		this.getCommandMap().put("openDataServer", new OpenServerCommand()); // TODO: Add port and rate settings
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

	/**
	 * @return the symbolTable
	 */
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	/**
	 * @param symbolTable the symbolTable to set
	 */
	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}
	
	// Methods
	private ArrayList<String[]> useLexer(String[] input) {
		ArrayList<String[]> tokens = new ArrayList<String[]>();
		
		for (String line : input) {

			line = line.replaceAll("\\{", "\\ { ").replaceAll("\\}", "\\ } ").replaceAll("\\>", "\\ > ")
					.replaceAll("\\<", "\\ < ").replaceAll("\\+", "\\ + ").replaceAll("\\-", "\\ - ")
					.replaceAll("\\*", "\\ * ").replaceAll("\\/", "\\ / ").replaceAll("\\(", "\\ ( ")
					.replaceAll("\\)", "\\ ) ").replaceAll("\\=", "\\ = ").trim();

			tokens.add(line.split("\\s+"));
		}
		
		return tokens;
	}
	
	public double interpret(String[] code) {
		ArrayList<String[]> tokens = useLexer(code);
		parse(tokens);
		
		return 0;
	}
	
	public void parse(ArrayList<String[]> tokens) {
		
		// Go through every token block(AKA line).
		for (int tokenBlockIndex = 0; tokenBlockIndex < tokens.size(); tokenBlockIndex++) {
			
			// Go through the key words.
			for (int tokenIndex = 0; tokenIndex < tokens.get(tokenBlockIndex).length; tokenIndex++) {
				System.out.println("T1");
				System.out.println(tokens.get(tokenBlockIndex)[tokenIndex]);
				Command command = this.getCommandMap().get(tokens.get(tokenBlockIndex)[tokenIndex]);
				
				if (command != null) {
					
					System.out.println(command.getClass());
					CommandExpression cmdExp = new CommandExpression(command);
					cmdExp.calculate();
				}
			}
			
		}
	}
}

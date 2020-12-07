package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.Line;

import commands.Command;
import commands.CommandBuilder;
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
	private int tokenIndex;
	private int tokenBlockIndex;
	private double returnedValue;
	private ArrayList<String[]> tokens;
	private CommandBuilder commandBuilder;
	
	// Constant Members
	private static final String lexerMatch = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|-?\\d+\\.?\\d*|-?\\d*\\.?\\d+|\".*\"|==|!=|<=|>=|<|>|\\+|-|\\*|\\/|&&|\\|\\||!|=|\\(|\\)|\\{|\\}|\\w+)";
	
	// Constructors
	public Interpreter() {
		
		// Initialize the command map.
		this.setCommandBuilder(new CommandBuilder(this));
	}

	// Getters & Setters
	/**
	 * @return the commandBuilder
	 */
	public CommandBuilder getCommandBuilder() {
		return commandBuilder;
	}

	/**
	 * @param commandBuilder the commandBuilder to set
	 */
	public void setCommandBuilder(CommandBuilder commandBuilder) {
		this.commandBuilder = commandBuilder;
	}
	
	/**
	 * @return the tokenIndex
	 */
	public int getTokenIndex() {
		return tokenIndex;
	}

	/**
	 * @param tokenIndex the tokenIndex to set
	 */
	public void setTokenIndex(int tokenIndex) {
		this.tokenIndex = tokenIndex;
	}

	/**
	 * @return the tokenBlockIndex
	 */
	public int getTokenBlockIndex() {
		return tokenBlockIndex;
	}

	/**
	 * @param tokenBlockIndex the tokenBlockIndex to set
	 */
	public void setTokenBlockIndex(int tokenBlockIndex) {
		this.tokenBlockIndex = tokenBlockIndex;
	}

	/**
	 * @return the returnedValue
	 */
	public double getReturnedValue() {
		return returnedValue;
	}

	/**
	 * @param returnedValue the returnedValue to set
	 */
	public void setReturnedValue(double returnedValue) {
		this.returnedValue = returnedValue;
	}

	/**
	 * @return the tokens
	 */
	public ArrayList<String[]> getTokens() {
		return tokens;
	}

	/**
	 * @param tokens the tokens to set
	 */
	public void setTokens(ArrayList<String[]> tokens) {
		this.tokens = tokens;
	}

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
		for (tokenBlockIndex = 0; tokenBlockIndex < tokens.size(); tokenBlockIndex++) {
			
			// Go through the key words.
			for (tokenIndex = 0; tokenIndex < tokens.get(tokenBlockIndex).length; tokenIndex++) {
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

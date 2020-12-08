package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import commands.Command;
import commands.CommandBuilder;
import math_expressions.CommandExpression;
import math_expressions.SimulatorSymbolVariable;
import math_expressions.Variable;

public class Interpreter {
	
	// Data Members
	private CommandBuilder commandBuilder;
	private ArrayList<String[]> tokens;
	private double returnedValue;
	private int tokenIndex;
	private int tokenBlockIndex;
	private HashMap<String, Variable> serverSymbolTable;
	private HashMap<String, SimulatorSymbolVariable> simulatorSymbolTable;

	// Constructors
	public Interpreter() {
		this.setServerSymbolTable(new HashMap<String, Variable>());
		this.setSimulatorSymbolTable(new HashMap<String, SimulatorSymbolVariable>());
		this.tokens = new ArrayList<String[]>();
		this.commandBuilder = new CommandBuilder(this);
		this.returnedValue = 0;
		this.tokenIndex = 0;
		this.tokenBlockIndex = 0;
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
	 * @return the serverSymbolTable
	 */
	public HashMap<String, Variable> getServerSymbolTable() {
		return serverSymbolTable;
	}

	/**
	 * @param serverSymbolTable the simulatorSymbolTable to set
	 */
	public void setServerSymbolTable(HashMap<String, Variable> serverSymbolTable) {
		this.serverSymbolTable = serverSymbolTable;
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
	
	// Resets variables
	private void reset() {
		
		// Reset the tokens.
		this.getTokens().clear();
		this.setTokens(null);
		this.setTokens(new ArrayList<String[]>());
		this.setTokenIndex(0);
		this.setTokenBlockIndex(0);
		this.setReturnedValue(0);
		
		// Initialize the symbol tables.
		this.getServerSymbolTable().clear();
		this.getSimulatorSymbolTable().clear();
		
		// Initialize the simulator variables
		this.getSimulatorSymbolTable().put("simX", new SimulatorSymbolVariable("simX", 0.0));
		this.getSimulatorSymbolTable().put("simY", new SimulatorSymbolVariable("simY", 0.0));
		this.getSimulatorSymbolTable().put("simZ", new SimulatorSymbolVariable("simZ", 0.0));
	}
	
	public double interpret(String[] code) {
		reset();
		ArrayList<String[]> tokens = useLexer(code);
		parse(tokens);
		
		return this.getReturnedValue();
	}
	
	public void parse(ArrayList<String[]> tokens) {
		this.setTokens(tokens);
		
		// Go through every token block(AKA line).
		for (tokenBlockIndex = 0; tokenBlockIndex < tokens.size(); tokenBlockIndex++) {
			
			// Go through the key words.
			for (tokenIndex = 0; tokenIndex < tokens.get(tokenBlockIndex).length; tokenIndex++) {
				Command command = this.getCommandBuilder().getCommand(tokens.get(tokenBlockIndex)[tokenIndex]);
				
				if (command != null) {
					CommandExpression cmdExp = new CommandExpression(command);
					cmdExp.calculate();
				}
			}
		}
	}
	public void printInterpeter() {
		System.out.println("\nResults:");
		System.out.println(this.serverSymbolTable.keySet());
		System.out.println(this.simulatorSymbolTable.keySet());

		for (Variable var : this.serverSymbolTable.values()) {
			System.out.print(var.getKey() + " = " + var.getVal() + ", ");
		}
		System.out.println("");
		for (Variable var : this.simulatorSymbolTable.values()) {
			System.out.print(var.getKey() + " = " + var.getVal() + ", ");
		}
		System.out.println("");
	}

	public HashMap<String, SimulatorSymbolVariable> getSimulatorSymbolTable() {
		return simulatorSymbolTable;
	}

	public void setSimulatorSymbolTable(HashMap<String, SimulatorSymbolVariable> simulatorSymbolTable) {
		this.simulatorSymbolTable = simulatorSymbolTable;
	}
}
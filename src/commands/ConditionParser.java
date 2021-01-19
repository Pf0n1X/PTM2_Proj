package commands;

import java.util.ArrayList;
import java.util.HashMap;

import interpreter.Interpreter;
import interpreter.ShuntingYard;
import math_expressions.CommandExpression;
import math_expressions.SimulatorSymbolVariable;

public abstract class ConditionParser extends Command {
	


	// Data Members
	protected String operator;
	protected int tokenBlockIndexBeginning;
	protected ArrayList<String[]> commands;
	protected ArrayList<String> left;
	protected ArrayList<String> right;
	
	// Constructors
	public ConditionParser(Interpreter interpreter) {
		super(interpreter);

		this.setCommands(new ArrayList<String[]>());
		this.setLeft(new ArrayList<String>());
		this.setRight(new ArrayList<String>());
		this.setTokenBlockIndexBeginning(0);
	}
	
	// Methods
	@Override
	public int execute() {
		
		// Get the interpreter.
		Interpreter inter = this.getInterpreter();
		
		// Reset the variables from the server
		HashMap<String, SimulatorSymbolVariable> simVars = inter.getSimulatorSymbolTable();
		
		for (String key: simVars.keySet()) {
			SimulatorSymbolVariable var = simVars.get(key);
			var.setVal(ConnectCommand.get("get " + key));
		}
		
		
		// Set the block index from which to start.
		this.setTokenBlockIndexBeginning(inter.getTokenBlockIndex());
		createCondition();
		
		// Set the indexes according to the new token block index.
		inter.setTokenBlockIndex(inter.getTokenBlockIndex() + 1);
		inter.setTokenIndex(0);
		
		// Create the commands according to the current indexes.
		createCommands();
		
		return 0;
	}
	
	protected void runCommands() {
		int blockLength;
		String commandName;
		Command currCommand;
		CommandExpression currCalculatableExpression;
		CommandBuilder builder = this.getInterpreter().getCommandBuilder();
		
		// Go through the command block.
		for (int tokenBlockIndex = 0; tokenBlockIndex < this.getCommands().size(); tokenBlockIndex++) {
			
			// Get the command block's length.
			blockLength = this.getCommands().get(tokenBlockIndex).length;
			
			// Go through every token in the command block.
			for (int tokenIndex = 0; tokenIndex < blockLength; tokenIndex++) {
				
				// Get the current command according to
				// the token(which represent a command name).
				commandName = this.getCommands().get(tokenBlockIndex)[tokenIndex];
				currCommand = builder.getCommand(commandName);
				
				if (currCommand != null) {
					
					// Set the new token block and token positions
					this.getInterpreter().setTokenBlockIndex(tokenBlockIndex + this.getTokenBlockIndexBeginning() + 1);
					this.getInterpreter().setTokenIndex(tokenIndex);
					
					// TODO: Might need to remove this.
//					currCommand.setInterpreter(this.interpreter);
					
					// Run the command using the new token positions
					currCalculatableExpression = new CommandExpression(currCommand);
					currCalculatableExpression.calculate();
				}
				
			}
		}
	}
	
	private void createCommands() {
		ArrayList<String[]> tokens = this.getInterpreter().getTokens();
		int curTokenBlockIndex = this.getInterpreter().getTokenBlockIndex();

		while (true) {
			if (tokens.get(curTokenBlockIndex)[0].equals("}")) {
				break;
			} else
				this.getCommands().add(tokens.get(curTokenBlockIndex));

			curTokenBlockIndex++;
		}
	}
 
	private void createCondition() {
		ArrayList<String[]> tokens = this.getInterpreter().getTokens();
		int curBlockIndex = this.getInterpreter().getTokenBlockIndex();
		int curTokenIndex = this.getInterpreter().getTokenIndex() + 1;
		boolean flag = true;
		String curToken;
		
		while(flag) {
			curToken = tokens.get(curBlockIndex)[curTokenIndex];
			
			if (curToken.equals("<") ||
					curToken.equals(">") ||
					curToken.equals("=" )||
					curToken.equals("!")) {
				flag = false;
			} else {
				this.getLeft().add(tokens.get(curBlockIndex)[curTokenIndex]);
				curTokenIndex++;
			}
		}

		this.setOperator(tokens.get(curBlockIndex)[curTokenIndex]);

		if (tokens.get(curBlockIndex)[curTokenIndex + 1].equals("=")) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(this.operator).append("=");
			this.setOperator(stringBuilder.toString());
			curTokenIndex++;
		}

		curTokenIndex++;
		
		curToken = tokens.get(curBlockIndex)[curTokenIndex];
		while (!curToken.equals("{")) {
			this.getRight().add(tokens.get(curBlockIndex)[curTokenIndex]);
			curTokenIndex++;
			curToken = tokens.get(curBlockIndex)[curTokenIndex];
		}
	}
	
	// Calculate the condition according to the
	protected boolean calculateCondition() {
		double leftVal = ShuntingYard.run(this.getLeft(), this.interpreter.getServerSymbolTable());
		double rightVal = ShuntingYard.run(this.getRight(), this.interpreter.getServerSymbolTable());

		switch (this.getOperator()) {
		case ">":
			return (leftVal > rightVal);
		case "<":
			return (leftVal < rightVal);
		case "==":
			return (leftVal == rightVal);
		case "!=":
			return (leftVal != rightVal);
		case "<=":
			return (leftVal <= rightVal);
		case ">=":
			return (leftVal >= rightVal);
		}

		return false;
	}

	
	// Getters & Setters
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the tokenBlockIndexBeginning
	 */
	public int getTokenBlockIndexBeginning() {
		return tokenBlockIndexBeginning;
	}

	/**
	 * @param tokenBlockIndexBeginning the tokenBlockIndexBeginning to set
	 */
	public void setTokenBlockIndexBeginning(int tokenBlockIndexBeginning) {
		this.tokenBlockIndexBeginning = tokenBlockIndexBeginning;
	}

	/**
	 * @return the commands
	 */
	public ArrayList<String[]> getCommands() {
		return commands;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(ArrayList<String[]> commands) {
		this.commands = commands;
	}

	/**
	 * @return the left
	 */
	public ArrayList<String> getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(ArrayList<String> left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public ArrayList<String> getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(ArrayList<String> right) {
		this.right = right;
	}
}
package commands;

import interpreter.Interpreter;

public abstract class Command {
	
	// Data Members
	private Interpreter interpreter;
	
	// Constructors
	public Command(Interpreter interpreter) {
		this.setInterpreter(interpreter);
	}
	
	// Methods
	// TODO: Might need to add parameters.
	public abstract int execute(String[] args);

	// Getters & Setters
	/**
	 * @return the interpreter
	 */
	public Interpreter getInterpreter() {
		return interpreter;
	}

	/**
	 * @param interpreter the interpreter to set
	 */
	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}
}

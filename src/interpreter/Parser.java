package interpreter;

import commands.CommandBuilder;

public class Parser {
	
	// Data Members
	private static Parser parserInstance = null;
	public static CommandBuilder builder = new CommandBuilder();
	
	// Constructors
	private Parser() {
		builder.add("")
	}
	
	// Methods
	public static Parser getInstance() {
		if (parserInstance == null) {
			parserInstance = new Parser();
		} 
		
		return parserInstance;
	}
	
	public void parse(String[] code) {
		int res;
		String codeLine;
		
		for (int lineIndex = 0; lineIndex < code.length; lineIndex++) {
			codeLine = 
		}
	}
	
	// SubClasses
	private class Lexer {
		
		// Data Members
		private static Lexer lexerInstance = null;
		
		// Constructors
		private Lexer() {
			
		}
		
		// Methods
		public static Lexer getInstance() {
			if (lexerInstance == null) {
				lexerInstance = new Lexer();
			}
			
			return lexerInstance;
		}
		
		public String[] run(String line) {
			
		}
	}
}

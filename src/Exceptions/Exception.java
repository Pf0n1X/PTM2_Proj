package Exceptions;

import commands.ExceptionCommand;
public class Exception {
	  // Defines a basic Symbol Exception.
	  public static class SymbolException extends ExceptionCommand {
	    public final String symbolName;
	    
	    public SymbolException(String symbolName) {
	      this.symbolName = symbolName;
	    }
	  }
	  
	  // Defines a Symbol Not Exists Exception.
	  public static class SymbolNotExistsException extends SymbolException {
	    public SymbolNotExistsException(String symbolName) {
	      super(symbolName);
	    }
	  }
	  
	  // Defines a Symbol UnInitialized Exception.
	  public static class SymbolUnInitializedException extends SymbolException {
	    public SymbolUnInitializedException(String symbolName) {
	      super(symbolName);
	    }
	  }
	  
	  // Defines a Symbol Already Exists Exception.
	  public static class SymbolAlreadyExistsException extends SymbolException {
	    public SymbolAlreadyExistsException(String symbolName) {
	      super(symbolName);
	    }
	  }
	  
}

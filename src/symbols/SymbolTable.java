package symbols;

import java.util.HashMap;
import Exceptions.Exception;
import Exceptions.Exception.SymbolAlreadyExistsException;
import Exceptions.Exception.SymbolUnInitializedException;
public class SymbolTable {
	private double returnValue = 0.0D;
	private HashMap<String, Symbol> symTable;

	public SymbolTable() {
		this.symTable = new HashMap<>();
	}

	// Returns a specific symbol from the table by it's name.
	public Symbol getSymbol(String s) {
		if (!this.symTable.containsKey(s))
			;
		return this.symTable.get(s);
	}
	
	  
	  // Adds a new symbol to the table.
	  public void addSymbol(final String s, Symbol sym) throws SymbolAlreadyExistsException  {
	
	    if (this.symTable.containsKey(s) && ((Symbol)this.symTable.get(s)).isInitialized()) 
	    	throw new Exception.SymbolAlreadyExistsException(s); 
	    
	    if (this.symTable.containsKey(s) && !((Symbol)this.symTable.get(s)).isInitialized())
	    	removeSymbol(s); 
	    if (sym == null) {
	    	
	      // Creates a new generic symbol to be added.
	      sym = new Symbol() {
	    	  
	          public void setValue(double val) {
	            RegularSymbol newSymbol = new RegularSymbol(s);
	            newSymbol.setValue(val);
	            SymbolTable.this.removeSymbol(s);
	            try {
	              SymbolTable.this.addSymbol(s, newSymbol);
	            } catch (SymbolAlreadyExistsException symbolAlreadyExistsException) {}
	          }
	          
	          public double getValue() throws SymbolUnInitializedException {
	            throw new SymbolUnInitializedException(s);
	          }
	          
	          // Only when the specific symbol is created, it would be defined as initialized
	          public boolean isInitialized() {
	            return false;
	          }
	        };
	    }
	    this.symTable.put(s, sym);
	  }

	private void removeSymbol(String s) {
		this.symTable.remove(s);
	}

	public boolean hasSymbol(String s) {
		return this.symTable.containsKey(s);
	}

	public double getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(double returnValue) {
		this.returnValue = returnValue;
	}
}

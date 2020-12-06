package symbols;

import Exceptions.Exception.SymbolUnInitializedException;

public interface Symbol {
	  void setValue(double paramDouble);
	  double getValue() throws SymbolUnInitializedException;
	  boolean isInitialized();
}

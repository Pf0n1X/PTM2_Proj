package symbols;
import Exceptions.Exception.SymbolUnInitializedException;

// Defines a Regular Symbol
public class RegularSymbol implements Symbol {
  private String symName;
  private Double value;
  
  public RegularSymbol(String symName) {
    this.value = null;
    this.symName = symName;
  }
  
  @Override
  public void setValue(double val) {
    this.value = Double.valueOf(val);
  }

  @Override
  public double getValue() throws SymbolUnInitializedException {
    if (this.value == null)
      throw new SymbolUnInitializedException(this.symName); 
    return this.value.doubleValue();
  }

  @Override
  public boolean isInitialized() {
    return true;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null)
      return false; 
    if (!(obj instanceof RegularSymbol))
      return false; 
    RegularSymbol other = (RegularSymbol)obj;
    return (Double.doubleToLongBits(this.value.doubleValue()) == Double.doubleToLongBits(other.value.doubleValue()));
  }
}
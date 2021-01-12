package commands;

import java.util.List;

public interface CommandFactory {
	  public Command create(List<String> paramList);
}

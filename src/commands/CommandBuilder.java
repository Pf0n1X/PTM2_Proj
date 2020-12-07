package commands;

import java.util.ArrayList;

public abstract class CommandBuilder {
	public abstract Command build(ArrayList<String> args);
}

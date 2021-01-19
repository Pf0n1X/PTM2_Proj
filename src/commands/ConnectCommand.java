package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import interpreter.Interpreter;
import interpreter.ShuntingYard;

public class ConnectCommand extends Command {
	
	// Data Members
	private static Socket server;
	private static PrintWriter serverPrinter;
	public static volatile boolean isConnect = false; // TODO: Change to private

	// Constructors
	public ConnectCommand(Interpreter interpreter) {
		super(interpreter);
	}

	// Methods
	@Override
	public int execute() {
		int tokenBlockIndex = this.getInterpreter().getTokenBlockIndex();
		int tokenIndex = this.getInterpreter().getTokenIndex();
		ArrayList<String[]> tokens = this.getInterpreter().getTokens();
		ArrayList<String> connectionExpression = new ArrayList<String>();
		String[] block = this.interpreter.getTokens().get(tokenBlockIndex);
		String ip;
		double port;
		
//		Get the ip and port
		ip = tokens.get(tokenBlockIndex)[tokenIndex + 1];
		for (int connectionBlockIndex = (tokenIndex + 2); connectionBlockIndex < block.length; connectionBlockIndex++) {
			connectionExpression.add(block[connectionBlockIndex]);
		}
		
		port = ShuntingYard.run(connectionExpression, this.interpreter.getServerSymbolTable());
		
		// Attempt a connection
		while (!isConnect) {
			try {
				server = new Socket(ip, (int)port);
				serverPrinter = new PrintWriter(server.getOutputStream());
				isConnect = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.interpreter.setTokenIndex(connectionExpression.size() + 1);

		return 0;
	}
	
	public static void close() {
		if (isConnect == true) {
			
			send("bye");
			serverPrinter.close();

			while (true) {
				try {
					server.close();
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			isConnect = false;
		}
	}
	
	// This method sends a command string to the simulation server.
	public static void send(String line) {
		if (isConnect == true) {
			serverPrinter.println(line);
			serverPrinter.flush();
		}
	}
	
	public static Double get(String line) {
		
		// Send the command to the server.
		// The command template is "get x" with x being the simulator variable name.
		send(line);
		
		// Read the answer/
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
			String serverLine;
			
			while (!(serverLine = reader.readLine()).contains(line)) {
				String retVal = serverLine.substring(serverLine.indexOf("'") + 1, serverLine.lastIndexOf("'"));
				return Double.parseDouble(!retVal.isEmpty() ? retVal : "0");
			}
		} catch (IOException error) {
			error.printStackTrace();
		}
		
		return null;
	}
}

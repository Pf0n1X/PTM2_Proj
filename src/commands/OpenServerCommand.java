package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import interpreter.Interpreter;
import interpreter.ShuntingYard;
import math_expressions.SimulatorSymbolVariable;

public class OpenServerCommand extends Command {
	
	// Data Members
	public static volatile boolean isConnect = false; // TODO: Change to private.
	private int port;
	private int rate;
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static BufferedReader input = null;
	
	// Constructors
	public OpenServerCommand(Interpreter interpeter) {
		super(interpeter);
		this.setPort(0);
		this.setRate(0);
	}
	
	
	// Methods
	@Override
	public int execute() {
		calcExpression();
		new Thread(() -> run()).start();
		
//		try {
//			while(!isConnect)
//				Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		return 0;
	}


	private void run() {
		try {
			while (isConnect == false) {
				serverSocket = new ServerSocket(port);
				clientSocket = serverSocket.accept();
				input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				isConnect = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		readInput();
		
		try {
			input.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (true) {
			try {
				if (clientSocket.isClosed() == false) {
					clientSocket.close();
				}
				serverSocket.close();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private void readInput() {

		String[] variblesKeys = { "simX", "simY", "simZ" };
		String[] variblesValues;
		String lineToRead;

		try {
			while (isConnect == true) {

				if ((lineToRead = input.readLine()) == null) {
					continue;
				} else {
					variblesValues = lineToRead.split(",");

					for (int i = 0; i < variblesKeys.length; i++) {
						if (isConnect == false) {
							break;
						}
						String simulatorVariableName = variblesKeys[i];
						double simulatorVariableValue = Double.parseDouble(variblesValues[i]);

						SimulatorSymbolVariable simulatorVariable = this.interpreter.getSimulatorSymbolTable()
								.get(simulatorVariableName);

						if (simulatorVariable != null) {
							simulatorVariable.setVal(simulatorVariableValue);
						} else {
							simulatorVariable = new SimulatorSymbolVariable(simulatorVariableName, simulatorVariableValue);
							simulatorVariable.setVal(simulatorVariableValue);
							this.interpreter.getSimulatorSymbolTable().put(simulatorVariableName, simulatorVariable);
						}
					}

					Thread.sleep(1000 / this.rate);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void calcExpression() {
		ArrayList<String[]> tokens = this.interpreter.getTokens();
		int tokenBlockIndex = this.interpreter.getTokenBlockIndex();
		int tokenIndex = this.interpreter.getTokenIndex() + 1;
		int lengthOfBlock = tokens.get(tokenBlockIndex).length;
		String[] string = tokens.get(tokenBlockIndex);
		ArrayList<String> list = new ArrayList<String>();

		// Creates the server's port
		for (; tokenIndex < (lengthOfBlock - 1); tokenIndex++) {
			if ((ShuntingYard.isParsableToDouble(string[tokenIndex]) &&
				 ShuntingYard.isParsableToDouble(string[tokenIndex + 1]) 
				 || (string[tokenIndex].equals(")") && 
			         ShuntingYard.isParsableToDouble(string[tokenIndex + 1]))
			     || (string[tokenIndex].equals(")") && string[tokenIndex + 1].equals("("))
				 || (ShuntingYard.isParsableToDouble(string[tokenIndex]) && string[tokenIndex + 1].equals("(")))) {
				list.add(string[tokenIndex]);
				break;
			}
			list.add(string[tokenIndex]);
		}

		this.port = (int) ShuntingYard.run(list, new Interpreter().getServerSymbolTable());

		list.clear();

		for (; tokenIndex < lengthOfBlock; tokenIndex++) {
			list.add(string[tokenIndex]);
		}

		this.rate = (int) ShuntingYard.run(list, this.interpreter.getServerSymbolTable());

		this.interpreter.setTokenIndex(string.length);		
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public static ServerSocket getServerSocket() {
		return serverSocket;
	}


	public static void setServerSocket(ServerSocket serverSocket) {
		OpenServerCommand.serverSocket = serverSocket;
	}


	public int getRate() {
		return rate;
	}


	public void setRate(int rate) {
		this.rate = rate;
	}


	public static boolean isConnect() {
		return isConnect;
	}


	public static void setConnect(boolean isConnect) {
		OpenServerCommand.isConnect = isConnect;
	}


	public static Socket getClientSocket() {
		return clientSocket;
	}


	public static void setClientSocket(Socket clientSocket) {
		OpenServerCommand.clientSocket = clientSocket;
	}


	public static BufferedReader getInput() {
		return input;
	}


	public static void setInput(BufferedReader input) {
		OpenServerCommand.input = input;
	}
	public static void stop() {
		setConnect(false);
	}
	
}

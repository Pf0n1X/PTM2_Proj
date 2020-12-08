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
	// Data Members
	private static volatile boolean isConnect = false;
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

		new Thread(() -> runServer()).start();
		return 0;
	}


	private void runServer() {
		try {
			while (isConnect == false) {
				serverSocket = new ServerSocket(port);
				serverSocket.setSoTimeout(1000);
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

		String[] variblesNames = { "simX", "simY", "simZ" };

		String[] variblesValues;
		String line;

		try {
			while (isConnect == true) {

				if ((line = input.readLine()) == null) {
					continue;
				} else {
					variblesValues = line.split(",");

					for (int i = 0; i < variblesNames.length; i++) {
						if (isConnect == false) {
							break;
						}
						String simulatorVariableName = variblesNames[i];
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
		int indexBlockOfTokens = this.interpreter.getTokenBlockIndex();
		int indexToken = this.interpreter.getTokenIndex() + 1;
		int lengthOfBlock = tokens.get(indexBlockOfTokens).length;
		String[] str = tokens.get(indexBlockOfTokens);
		ArrayList<String> list = new ArrayList<String>();

		// Creates the server's port
		for (; indexToken < (lengthOfBlock - 1); indexToken++) {
			// Checks for:
			// 1. [..."number", "number"...]
			// 2. [...")", "number"...]
			// 3. [...")", "("...]
			// 4. [..."number", "("...]
			if ((ShuntingYard.isParsableToDouble(str[indexToken]) && ShuntingYard.isParsableToDouble(str[indexToken + 1])
					|| (str[indexToken].equals(")") && ShuntingYard.isParsableToDouble(str[indexToken + 1]))
					|| (str[indexToken].equals(")") && str[indexToken + 1].equals("("))
					|| (ShuntingYard.isParsableToDouble(str[indexToken]) && str[indexToken + 1].equals("(")))) {
				list.add(str[indexToken]);
				break;
			}
			list.add(str[indexToken]);
		}

		this.port = (int) ShuntingYard.run(list, new Interpreter().getServerSymbolTable());

		list.clear();

		for (; indexToken < lengthOfBlock; indexToken++) {
			list.add(str[indexToken]);
		}

		this.rate = (int) ShuntingYard.run(list, this.interpreter.getServerSymbolTable());

		this.interpreter.setTokenIndex(str.length);		
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
	
	
}

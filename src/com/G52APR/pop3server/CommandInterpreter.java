package com.G52APR.pop3server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.lang.NullPointerException;

public class CommandInterpreter implements Runnable {
	protected POP3 state;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String result; 

	String command;

	public CommandInterpreter(Socket clntSocket, POP3 pop3) {
		this.socket = clntSocket;
		this.state = pop3;
	}

	public void run() {
		try {
			try {
				in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()
								));
				out = new PrintWriter(socket.getOutputStream(), true);
				doService();
			} finally {
				socket.close();
				state.close();
				System.out.println("Client offline.");
			}
		} catch (IOException exception) {
		} catch (NullPointerException exception) {}
	}

	public void doService() throws IOException {
		while (true) {		
			command = in.readLine();
			result = handleInput(command);
			out.println(result);
		}
	}

	protected String handleInput(String input) {
		String[] command_line = input.split(" ");
		String command = command_line[0].toUpperCase();
		String result = null;

		String[] parameters = null;
		Command cmd = null;

		try {
			parameters = new String[command_line.length - 1];
			System.arraycopy(command_line, 1, parameters, 0,
					command_line.length - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		switch (command) {
		case "USER":
			cmd = new USERcommand(parameters, this.state);
			break;

		case "PASS":
			cmd = new PASScommand(parameters, this.state);
			break;

		case "QUIT":
			cmd = new QUITcommand(parameters, this.state);
			break;

		default:
			if (this.state.logined()) {
				switch (command) {
				case "STAT":
					cmd = new STATcommand(parameters, this.state);
					break;

				case "LIST":
					cmd = new LISTcommand(parameters, this.state);
					break;

				case "RETR":
					cmd = new RETRcommand(parameters, this.state);
					break;

				case "DELE":
					cmd = new DELEcommand(parameters, this.state);
					break;

				case "NOOP":
					cmd = new NOOPcommand(parameters, this.state);
					break;

				case "RSET":
					cmd = new RSETcommand(parameters, this.state);
					break;

				case "TOP":
					cmd = new TOPcommand(parameters, this.state);
					break;

				case "UIDL":
					cmd = new UIDLcommand(parameters, this.state);
					break;

				default:
					result = "-Fail Unkown Command";
					break;
				}

			} else {
				result = "-ERR Haven't logined. Use USER and PASS login.";
			}
		}
		if (cmd != null)
			result = cmd.execute();
		return result;
	}

}

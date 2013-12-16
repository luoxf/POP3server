package com.G52APR.pop3server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class POP3server {
	public static void main(String[] args) throws IOException { 
		System.out.println("Starting Server");
		final int SBAP_PORT = 8888;
		ServerSocket server_socket = null;
		
		Database test = new Database("jdbc:mysql://localhost:3306/pop3", "pop3", "BWvDSeQnqWTxqr4Z");
		
		try {
			server_socket = new ServerSocket(SBAP_PORT);
			test.connect();
		} catch (BindException e) {
			System.out.println("Port is taken");
			System.exit(1);
		} catch (InstantiationException e) {
			System.out.println("Instantiation error");
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.out.println("Illegal access");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.out.println("Dependency error");
			System.exit(1);
		}catch(SQLException se){   
		    System.out.println("Cannot connect to the database");   
		    System.exit(1);
		}
		
		
	    System.out.println("Waiting for clients to connect...");
		
		while (true) {
			POP3 pop3 = new POP3(test); 
			Socket clntSocket = server_socket.accept();
			System.out.println("Client connected.");
			CommandInterpreter server = new CommandInterpreter(clntSocket, pop3);
			Thread t = new Thread(server);
			t.start();
		}
	}

}

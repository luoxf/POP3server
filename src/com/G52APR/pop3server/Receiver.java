package com.G52APR.pop3server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Receiver implements Runnable {
	private String result;
	private Socket socket;
	private BufferedReader in;
	private UserInterface ui;
	
	public Receiver(UserInterface ui, Socket socket) {
		this.setSocket(socket);
		try {
			this.in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()
							));
		} catch (IOException e) {}
		this.ui = ui;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				this.result = this.in.readLine();
				
				// server if off
				if (this.result == null) {
					JOptionPane.showMessageDialog(ui.frame,
							"Cannot connected to server.", "Connection Interrupt",
							JOptionPane.ERROR_MESSAGE);
					ui.setSocket(null);
					ui.exit();
				}
				
				// run quick while not login
				if (this.result.equals("+OK terminating connection.")) {
					ui.setSocket(null);
					ui.exit();
				}
				
				this.ui.printResult(this.result);
			} catch (IOException e) {}
		}
		
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}

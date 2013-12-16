package com.G52APR.pop3server;

public class DELEcommand implements Command {
	String[] parameters;
	POP3 state;
	int index;
	
	public DELEcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}

	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: RETR <number>";
		if (this.state.msgDeleted(index)) return "-ERR no such message";
		
		if ( !this.state.msgDeleted(index) ) {
			this.state.setAsDelete(index);
			return "+OK message " + String.valueOf(index) + " deleted";
		} else {
			return "-ERR message " + String.valueOf(index) + " already deleted";
		}
	}

	@Override
	public boolean checkParameters() {
		try {
			this.index = Integer.parseInt(parameters[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (NumberFormatException e) {
		  	return false;
		}
		
		return this.parameters != null && this.parameters.length == 1 && index >= 0;
	}

}

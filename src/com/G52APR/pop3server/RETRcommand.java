package com.G52APR.pop3server;

public class RETRcommand implements Command {
	String[] parameters;
	POP3 state;
	int index;
	
	public RETRcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}

	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: RETR <number>";
		return (!this.state.msgDeleted(this.index)) ? "+OK " + this.state.getMsgSize(index) + " octets\n"  + this.state.getContent(index) + "\n." 
				: "-ERR no such message";
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
		
		return this.parameters != null && this.parameters.length == 1;
	}

}

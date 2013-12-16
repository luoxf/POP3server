package com.G52APR.pop3server;

public class LISTcommand implements Command {
	String[] parameters;
	POP3 state;
	int index;
	
	public LISTcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}
	
	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: LIST <number>";
		
		if (this.parameters.length == 1) {
			return (!this.state.msgDeleted(index)) ? "+OK " + String.valueOf(this.parameters[0]) + " " + String.valueOf(this.state.getMsgSize(index)) : "-ERR no such message";
		} else {
			String result = "+OK " + this.state.getMsgNum() + " messages (" + this.state.getMaildropSize() + " octets)";
			result += this.listMsg();
			return result;
		}
	}

	@Override
	public boolean checkParameters() {
		if (this.parameters != null && this.parameters.length == 1) {
			try {
				this.index = Integer.parseInt(parameters[0]);
			} catch (NumberFormatException e) {
			  	return false;
			}
			return index >= 0 ? true : false;
		}
		
		return this.parameters == null || this.parameters.length == 0;
	}
	
	private String listMsg() {
		String result = "";
		for (int index=0; index <= this.state.getMsgMaxnum(); index++) {
			if ( this.state.msgDeleted(index) )	continue;
			result += "\n" + String.valueOf(index) + " " + String.valueOf(this.state.getMsgSize(index));
		}
		
		return result;
	}
}

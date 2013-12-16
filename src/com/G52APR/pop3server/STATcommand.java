package com.G52APR.pop3server;

public class STATcommand implements Command {
	String[] parameters;
	POP3 state;
	
	public STATcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}
	
	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: STAT";
		return "+OK " + state.getMsgNum() + " "  + state.getMaildropSize();
	}

	@Override
	public boolean checkParameters() {
		return this.parameters == null || this.parameters.length == 0;
	}

}

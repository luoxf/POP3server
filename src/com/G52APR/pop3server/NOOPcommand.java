package com.G52APR.pop3server;

public class NOOPcommand implements Command {
	String[] parameters;
	POP3 state;
	
	public NOOPcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}
	
	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: NOOP";
		return "+OK";
	}

	@Override
	public boolean checkParameters() {
		return this.parameters == null || this.parameters.length == 0;
	}

}

package com.G52APR.pop3server;

public class RSETcommand implements Command {
	String[] parameters;
	POP3 state;
	
	public RSETcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}
	
	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: RSET";
		this.state.restoreMsg();
		return "+OK maildrop has " + state.getMsgNum() + " messages (" + state.getMaildropSize() + " octets)";
	}

	@Override
	public boolean checkParameters() {
		return this.parameters == null || this.parameters.length == 0;
	}

}

package com.G52APR.pop3server;

public class USERcommand implements Command {
	String[] parameters;
	POP3 state;
	
	public USERcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}

	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: USER user_name";
		String user_name = this.parameters[0];
		
		if ( !this.state.logined() ) {
			this.state.setUser(user_name);
			return "+OK Please enter your password";
		}
		return "-ERR You have already logined as " + this.state.getUserName();
	}

	@Override
	public boolean checkParameters() {
		return this.parameters != null && this.parameters.length == 1;
	}

}

package com.G52APR.pop3server;

public class QUITcommand implements Command {
	String[] parameters;
	POP3 state;
	
	public QUITcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}

	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: QUIT";
		if ( !this.state.logined() ) {
			return "+OK terminating connection.";
		} else {
			String user_name = this.state.getUserName();
			this.state.close();
			return "+OK " + user_name + " POP3 server signing off(" + this.checkMailbox() + ")";
		}
	}

	@Override
	public boolean checkParameters() {
		return this.parameters == null || this.parameters.length == 0;
	}
	
	private String checkMailbox() {
		for (int index=0; index < this.state.getMsgMaxnum(); index++) {
			if ( this.state.msgDeleted(index) )	return "removing deleted messages";
		}
		
		return "no messages need to remove";
	}

}

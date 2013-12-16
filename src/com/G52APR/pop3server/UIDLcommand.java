package com.G52APR.pop3server;

public class UIDLcommand implements Command {
	String[] parameters;
	POP3 state;
	int index;
	
	public UIDLcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}
	
	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: UIDL <number>";
		
		if (this.parameters.length == 1) {
			return (!this.state.msgDeleted(index)) ? "+OK " + String.valueOf(this.parameters[0]) + " " + this.state.getUIDL(index) : "-ERR no such message";
		} else {
			String result = "+OK " + this.state.getMsgNum() + " messages (" + this.state.getMaildropSize() + " octets)";
			result += this.listMD5();
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
	
	private String listMD5() {
		String result = "";
		for (int index=0; index <= this.state.getMsgMaxnum(); index++) {
			if ( this.state.msgDeleted(index) )	continue;
			result += "\n" + String.valueOf(index) + " " + this.state.getUIDL(index);
		}
		
		return result;
	}
}

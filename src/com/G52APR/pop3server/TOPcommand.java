package com.G52APR.pop3server;

public class TOPcommand implements Command {
	String[] parameters;
	POP3 state;
	int index;
	int line;
	
	String header;
	String content;
	String[] body; 
	String msg_content;
	
	public TOPcommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}
	
	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: USER <message> <line>";
		
		if (index <= this.state.getMsgMaxnum() && !this.state.msgDeleted(this.index) ) {
			content = this.state.getContent(index);
			// check from database, from header to body will have a empty line, so split by \n\n
			header = content.split("\n\n")[0];
			// subtract the head, the rest is body 
			body = content.replace(header, "").split("\n");
			msg_content = "+OK\n" + header + "\n";
			// at beginning will have 2 line breaks
			for (int u=2; u < this.line + 2; u++)
				try {
				msg_content += "\n" + body[u];
				} catch (ArrayIndexOutOfBoundsException exception) {} // if input line is larger than the msg line
			
			return msg_content;
		} else {
			return "-ERR no such message";
		}
	}

	@Override
	public boolean checkParameters() {
		try {
			this.index = Integer.parseInt(parameters[0]);
			this.line = Integer.parseInt(parameters[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (NumberFormatException e) {
		  	return false;
		}
		
		return this.parameters != null && this.parameters.length == 2 && index >= 0;
	}

}

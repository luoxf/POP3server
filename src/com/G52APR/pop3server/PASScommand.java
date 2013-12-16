package com.G52APR.pop3server;

public class PASScommand implements Command {
	String[] parameters;
	POP3 state;
	
	public PASScommand(String[] parameters, POP3 state) {
		this.parameters = parameters;
		this.state = state;
	}
	
	@Override
	public String execute() {
		if ( !checkParameters() )	return "-ERR Usage: PASS password";
		String password = join(this.parameters," ");
		
		if (this.state.userNameUsed()) {
			return this.state.getUserName() + " is already logined another client" ;
		}
				
		if ( !this.state.logined() ) {
			if (this.state.getUserName().equals(null))	return "-ERR Please enter your username first.";
			this.state.tryLogin(this.state.getUserName(), password);
			return this.state.logined() ?  "+OK " + this.state.getUserName() + "'s maildrop has "  + this.state.getMsgNum() + " messages (" + this.state.getMaildropSize() + " octets)" : "-ERR Sorry, wrong password.";
		}
		return "-ERR You have already logined as " + this.state.getUserName();
	}

	@Override
	public boolean checkParameters() {
		return this.parameters != null;
	}
	
	public static String join(String r[],String d)
	{
	    if (r.length == 0) return "";
	    StringBuilder sb = new StringBuilder();
        int i;
        for(i=0;i<r.length-1;i++)
            sb.append(r[i]+d);
        return sb.toString()+r[i];
	}

}

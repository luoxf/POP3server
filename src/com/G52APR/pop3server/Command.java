package com.G52APR.pop3server;

public interface Command {
	public String execute();
	public boolean checkParameters();
}

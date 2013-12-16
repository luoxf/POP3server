package com.G52APR.pop3server;

import java.util.Arrays;

public class POP3 {
	private Database db;	
	private boolean TRANSACTION = false;
	private String user_name;
	private int user_id;
	private int[] tmp_delete_msg;
	private int delete_msg_index;
	
	public POP3(Database db) {
		this.db = db;
		this.tmp_delete_msg = new int[100];
	}
	
	protected void setUser(String user) {
		this.user_name = user;
	}
	
	protected String getUserName() {
		return this.user_name;
	}
	
	protected boolean userNameUsed() {
		return this.db.userNameUsed(this.user_name);
	}
	
	protected void tryLogin(String user, String password) {
		if (this.db.login(user, password)) {
			this.TRANSACTION = true;
			this.user_id = this.db.getUserId(this.user_name);
			System.out.println("Client logined");
		} else {
			this.TRANSACTION = false;
		}
	}
	
	protected void close() {
		this.db.deleteMsg(this.delete_msg_index, this.tmp_delete_msg);
		this.db.logout(this.user_id);
		this.TRANSACTION =false;
		this.user_name = null;
		this.user_id = 0;
		if (this.TRANSACTION) {
			System.out.println("Client logout");
		}
	}
	
	protected boolean logined() {
		return this.TRANSACTION;
	}
	
	protected int getMsgNum() {
		return this.db.getMsgNum(this.user_id) - this.delete_msg_index;
	}
	
	protected int getMsgMaxnum() {
		return this.db.getMsgMaxnum(this.user_id);
	}
	
	protected int getMsgSize(int index) {
		return this.db.getMsgSize(this.user_id, index);
	}
	
	protected int getMaildropSize() {
		return this.db.getMaildropSize(this.user_id, this.delete_msg_index, this.tmp_delete_msg);
	}
	
	protected void restoreMsg() {
		Arrays.fill(this.tmp_delete_msg, -1);
		this.delete_msg_index = 0;
	}
	
	protected boolean msgDeleted(int index) {
		return this.db.getMsgSize(this.user_id, index) == -1 || inTmpDeleteList(index);
	}
	
	protected void setAsDelete(int index) {
		this.tmp_delete_msg[delete_msg_index] = index;
		this.delete_msg_index++;
	}

	protected String getContent(int index) {
		return this.db.getContent(this.user_id, index);
	}

	protected String getUIDL(int index) {
		return this.db.getUIDL(this.user_id, index);
	}
	
	private boolean inTmpDeleteList(int index) {
		for (int u=0; u < delete_msg_index; u++) {
			if (index == tmp_delete_msg[u])	return true;
		}
		return false;
	}

}

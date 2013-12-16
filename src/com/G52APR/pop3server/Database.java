package com.G52APR.pop3server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Database {
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private Connection dbConnection;
	private java.sql.Statement stat;
	private String command;
	private java.sql.ResultSet result;
	
	public Database(String url, String username, String password) {
		this.dbUrl = url;
		this.dbUsername = username;
		this.dbPassword = password;
		this.dbConnection = null;
		this.stat = null;
		this.command = null;
		this.result = null;
	}
	
	public void connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		this.dbConnection = DriverManager.getConnection(
		    		this.dbUrl, this.dbUsername , this.dbPassword 
		    		);
		this.stat = dbConnection.createStatement();
	}
	
	public boolean userNameUsed(String username) {
		try {
			this.command = "SELECT `tiLocked` FROM `m_Maildrop` WHERE `vchUsername`='" + username + "'";
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return (this.result.getInt("tiLocked") == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		// user name not registered in database
	}
	
	public boolean login(String username, String password) {
		try {
			this.command = "SELECT `vchPassword` FROM `m_Maildrop` WHERE `vchUsername`='" + username + "'";
			this.result = stat.executeQuery(this.command);
			this.result.next();
			if (password.equals(this.result.getString("vchPassword"))) {
				this.command = "UPDATE `m_Maildrop` SET `tiLocked`=1 WHERE `vchUsername`='" + username + "'";
				stat.execute(this.command);
				return true;
			}
		} catch (SQLException e) {}

		return false;
	}
	
	public void logout(int user_id) {
		try {
			this.command = "UPDATE `m_Maildrop` SET `tiLocked`= 0 WHERE `iMaildropID`='" + user_id + "'";
			stat.executeUpdate(this.command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getUserId(String username) {
		try {
			this.command = "SELECT `iMaildropID` FROM `m_Maildrop` WHERE `vchUsername`='" + username + "'";
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return this.result.getInt("iMaildropID");
		} catch (SQLException e) {}
		
		return -1;
	}

	public int getMsgNum(int user_id) {
		try {
			this.command = "SELECT COUNT(`iMailID`) FROM `m_Mail` WHERE `iMaildropID`=" + user_id;
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return this.result.getInt("COUNT(`iMailID`)");
		} catch (SQLException e) {}
		
		return 0;
	}
	
	public int getMsgMaxnum(int user_id) {
		try {
			this.command = "SELECT MAX(`iMailID`) FROM `m_Mail` WHERE `iMaildropID`=" + user_id;
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return this.result.getInt("MAX(`iMailID`)");
		} catch (SQLException e) {}
		
		return 0;
	}

	public int getMaildropSize(int user_id, int delete_msg_index, int[] tmp_delete_msg) {
		try {
			this.command = "SELECT sum(length(`txMailContent`)) as `size` FROM `m_Mail` WHERE `iMaildropID`=" + user_id;
			for (int u=0; u < delete_msg_index; u++) {
				 this.command += " and `iMailID`<>" + tmp_delete_msg[u];
			}
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return this.result.getInt("size");
		} catch (SQLException e) {}
		
		return 0;
	}
	
	public int getMsgSize(int user_id, int msg_id) {
		try {
			this.command = "SELECT length(`txMailContent`) as `len` FROM `m_Mail` WHERE `iMaildropID`=" + user_id + " and `iMailID`=" + msg_id;			
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return this.result.getInt("len");
		} catch (SQLException e) {}
		
		return -1;
	}
	
	public String getContent(int user_id, int msg_id) {
		try {
			this.command = "SELECT `txMailContent` as `msg` FROM `m_Mail` WHERE `iMaildropID`=" + user_id + " and `iMailID`=" + msg_id;
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return this.result.getString("msg");
		} catch (SQLException e) {}
		
		return "Empty";
	}
		
	public boolean inDeleteList(int[] list) {
		return false;
	}

	public String getUIDL(int user_id, int msg_id) {
		try {
			this.command = "SELECT `vchUIDL` FROM `m_Mail` WHERE `iMaildropID`=" + user_id + " and `iMailID`=" + msg_id;
			this.result = stat.executeQuery(this.command);
			this.result.next();
			return this.result.getString("vchUIDL");
		} catch (SQLException e) {}
		
		return "Empty";
	}
	
	public void deleteMsg(int delete_msg_index, int[] tmp_delete_msg) {
		try {
			for (int u=0; u < delete_msg_index; u++) {
				this.command = "DELETE FROM `m_Mail` WHERE `iMailID`=" + tmp_delete_msg[u];
				stat.execute(this.command);
			}
		} catch (SQLException e) {}
	}
}


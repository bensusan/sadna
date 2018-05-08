package TS_ServiceLayer;

import java.io.Serializable;

import TS_SharedClasses.*;

public class HelloMessage implements Serializable {

	private String guest;
	private String[] ls;
	private int g;
    private String userName;
    private String password;
    
    public HelloMessage() {
    }

    public HelloMessage(String guest, String[] ls, int g, String userName, String password) {
    	this.guest = guest;
    	this.ls = ls;
    	this.g = g;
        this.userName = userName;
        this.password = password;
    }
    
    public String getGuest() {
		return guest;
	}

	public void setString(String guest) {
		this.guest = guest;
	}

	public String[] getLs() {
		return ls;
	}

	public void setLs(String[] ls) {
		this.ls = ls;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}

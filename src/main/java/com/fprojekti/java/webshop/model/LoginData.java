package com.fprojekti.java.webshop.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class LoginData {

	private static final String DATE_FORMAT_ZA_ISPIS = "yyyy-MM-dd hh:mm:ss";
	private long id;
	private String username;
	private String ipAddr;
	private Date loginDate;

	public LoginData(long id, String username, Date loginDate, String ipAddr) {
		super();
		this.id = id;
		this.username = username;
		this.loginDate = loginDate;
		this.ipAddr = ipAddr;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getDateAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ZA_ISPIS);
		return sdf.format(getLoginDate());
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ZA_ISPIS);
		return String.format("%s logged in at %s from %s", username, sdf.format(loginDate), ipAddr);
	}
}

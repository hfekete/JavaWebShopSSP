package com.fprojekti.java.webshop.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 773890433949729221L;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User(String username) {
		super();
		this.username = username;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return ((User) obj).getUsername().equals(this.getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(username).toHashCode();
	}
}

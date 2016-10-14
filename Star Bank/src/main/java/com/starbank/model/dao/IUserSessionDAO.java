package com.starbank.model.dao;

import java.util.Date;

import org.springframework.stereotype.Component;


public interface IUserSessionDAO {

	String INSERT_SESSION_SQL = "INSERT INTO Sessions VALUES (null, ?, ?, ?, ?)";

	public void insertSessionInfo(int userId, Date date,String description, String ipAddress);
	
}

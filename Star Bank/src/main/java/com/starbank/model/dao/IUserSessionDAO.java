package com.starbank.model.dao;

import java.util.Date;
import java.util.List;

import com.starbank.exceptions.UserSessionException;
import com.starbank.model.entity.UserSession;


public interface IUserSessionDAO {

	static final String INSERT_SESSION_SQL = "INSERT INTO Sessions VALUES (null, ?, ?, ?, ?)";
	static final String SELECT_SESSIONS_SQL = "SELECT * FROM Sessions WHERE user_id = ?;";
	public void insertSessionInfo(int userId, Date date,String description, String ipAddress);

	List<UserSession> getAllSessions(int userId) throws UserSessionException;
	
}

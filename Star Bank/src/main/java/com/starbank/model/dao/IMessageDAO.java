package com.starbank.model.dao;

import com.starbank.exceptions.MessageException;
import com.starbank.model.entity.Message;

public interface IMessageDAO {

	static final String INSERT_MESSAGE_SQL = "INSERT INTO Messages VALUES (null, ?, ?, ?, ?)";
	
	public boolean sendMessageToUser(Message message, int userId) throws MessageException;
	
}

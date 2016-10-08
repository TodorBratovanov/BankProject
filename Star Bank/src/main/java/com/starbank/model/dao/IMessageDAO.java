package com.starbank.model.dao;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.MessageException;
import com.starbank.model.entity.Message;

@Component
public interface IMessageDAO {

	static final String INSERT_MESSAGE_SQL = "INSERT INTO Messages VALUES (null, ?, ?, ?, ?)";
	
	public boolean sendMessageToUser(Message message, int userId) throws MessageException;
	
}

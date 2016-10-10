package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.MessageException;
import com.starbank.model.entity.Message;

@Component
public interface IMessageDAO {

	static final String INSERT_MESSAGE_SQL = "INSERT INTO Messages VALUES (null, ?, ?, ?, ?)";
	static final String SELECT_MESSAGES_SQL = "SELECT * FROM Messages WHERE user_id = ?;";
	public boolean sendMessageToUser(Message message, int userId) throws MessageException;
	public List<Message> getAllMessages(int userId) throws MessageException;
}

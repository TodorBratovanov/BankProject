package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.starbank.exceptions.MessageException;
import com.starbank.model.entity.Message;

public class SendMessageDAO {
	
	private static final String INSERT_MESSAGE_SQL = "INSERT INTO Messages VALUES (null, ?, ?, ?, ?)";

	public boolean sendMessageToUser(Message message, int userId) throws MessageException {
		

		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_SQL);
			ps.setString(1, message.getTitle());
			ps.setString(2, message.getText());
			ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(4, userId);

			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new MessageException("Sending message failed");
		}
	}


}

package com.starbank.model.dao.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.starbank.exceptions.MessageException;
import com.starbank.model.dao.DBConnection;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.entity.Message;

public class MessageRepository implements IMessageDAO {

	private JdbcTemplate jdbcTemplate;
	
	public MessageRepository() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public MessageRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean sendMessageToUser(Message message, int userId) throws MessageException {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(IMessageDAO.INSERT_MESSAGE_SQL);
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

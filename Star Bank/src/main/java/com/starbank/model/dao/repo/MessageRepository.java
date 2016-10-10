package com.starbank.model.dao.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.starbank.exceptions.MessageException;
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
		try {
			return jdbcTemplate.update(IMessageDAO.INSERT_MESSAGE_SQL, message.getTitle(),message.getText(),
					Timestamp.valueOf(LocalDateTime.now()),userId) > 0;
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Message> getAllMessages(int userId) throws MessageException {
		List<Message> messages = new ArrayList<>();
		try {
			messages =  this.jdbcTemplate.query(IMessageDAO.SELECT_MESSAGES_SQL,
					new Object[] { userId }, new RowMapper<Message>() {
			            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	Message message = new Message();
			                message.setMessageId(rs.getInt("message_id"));
			                message.setTitle(rs.getString("title"));
			                message.setText(rs.getString("text"));
			                message.setDate(rs.getTimestamp("date"));
			                
			                return message;
			            }
			        });
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return messages;
	}

}

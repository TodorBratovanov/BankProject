package com.starbank.model.dao.repo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.starbank.exceptions.MessageException;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.entity.Message;

public class MessageRepository {

	private JdbcTemplate jdbcTemplate;
	
	public MessageRepository() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public MessageRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public boolean sendMessageToUser(Message message, int userId) throws MessageException {
		try {
			return jdbcTemplate.update(IMessageDAO.INSERT_MESSAGE_SQL, message.getTitle(),message.getText(),
					Timestamp.valueOf(LocalDateTime.now()),userId) > 0;
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

}

package com.starbank.model.dao.repo;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.starbank.model.dao.IUserSessionDAO;
@Component
public class UserSessionRepository implements IUserSessionDAO{

	private JdbcTemplate jdbcTemplate;
	
	public UserSessionRepository() {
		
	}
	@Autowired
	public UserSessionRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void insertSessionInfo(int userId, Date date, String ipAddress) {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA"+ipAddress);
		jdbcTemplate.update(IUserSessionDAO.INSERT_SESSION_SQL, new Object[] { date,"WTF",ipAddress,userId });
		
	}

}

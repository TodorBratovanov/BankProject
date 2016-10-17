package com.starbank.model.dao.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserSessionException;
import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.entity.UserSession;

@Component
public class UserSessionRepository implements IUserSessionDAO {

	private JdbcTemplate jdbcTemplate;

	public UserSessionRepository() {
	}

	@Autowired
	public UserSessionRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insertSessionInfo(int userId, Date date, String description, String ipAddress) {
		jdbcTemplate.update(IUserSessionDAO.INSERT_SESSION_SQL, new Object[] { date, description, ipAddress, userId });
	}

	@Override
	public List<UserSession> getAllSessions(int userId) throws UserSessionException{
		List<UserSession> sessions = new ArrayList<>();
		try {
			sessions = this.jdbcTemplate.query(IUserSessionDAO.SELECT_SESSIONS_SQL, new Object[] { userId },
					new RowMapper<UserSession>() {
						public UserSession mapRow(ResultSet rs, int rowNum) throws SQLException {
							UserSession session = null;
							try {
								session = new UserSession(rs.getInt("session_id"),
										rs.getTimestamp("date_time"), rs.getString("description"),
										rs.getString("ip_address"), rs.getInt("user_id"));
							} catch (DateTimeException | InvalidStringException | IdException e) {
								e.printStackTrace();
							}
							return session;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new UserSessionException("");
		}
		return sessions;
	}

}

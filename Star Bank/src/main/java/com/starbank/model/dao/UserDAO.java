package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.User;

public class UserDAO {

	private static final String SELECT_USER_SQL = "SELECT user_id FROM Users WHERE email = ? AND password = md5(?);";
	private static final String SELECT_USERID_SQL = "SELECT user_id FROM users WHERE user_id = ?;";
	@Autowired
	public int loginUser(User user) throws UserException {
		
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			return rs.getInt(1);

		} catch (SQLException e) {
			throw new UserException("User login failed!");
		}
	}

}

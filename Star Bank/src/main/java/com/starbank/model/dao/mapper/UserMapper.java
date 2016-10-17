package com.starbank.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.starbank.model.entity.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		User user = null;
		try {
			user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), 
					rs.getString("phone_number"), rs.getString("email"), rs.getString("password"), rs.getString("address"), 
					rs.getString("egn"), rs.getBoolean("is_admin"), rs.getBoolean("registered"), rs.getBoolean("likes")); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
}

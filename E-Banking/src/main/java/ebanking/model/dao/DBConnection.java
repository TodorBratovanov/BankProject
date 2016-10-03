package ebanking.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
	private static DBConnection instance;

	private Connection connection;
	
	private static final String DB_SCHEMA;
	private static final String SSL_DISABLE;
	private static final String DB_PORT;
	private static final String DB_HOST;
	private static final String DB_PASSWORD;
	private static final String DB_USERNAME;
	
	static {
		Properties prop = new Properties();
		InputStream input = null;
		String schema = "";
		String ssl = "";
		String port = "";
		String host = "";
		String password = "";
		String username = "";

		try {
			input = new FileInputStream("resources" + File.separator + "config" + File.separator + "config.properties");

			// load a properties file
			prop.load(input);

			schema = prop.getProperty("dbSchema");
			ssl = prop.getProperty("sslDisable");
			port = prop.getProperty("dbPort");
			host = prop.getProperty("dbHost");
			password = prop.getProperty("dbPassword");
			username = prop.getProperty("dbUsername");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DB_SCHEMA = schema;
			SSL_DISABLE = ssl;
			DB_PORT = port;
			DB_HOST = host;
			DB_PASSWORD = password;
			DB_USERNAME = username;
		}
		
	}


	private DBConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");

		this.connection = DriverManager.getConnection("jdbc:mysql://" + 
			DB_HOST + ":" + DB_PORT + "/" + DB_SCHEMA + SSL_DISABLE, DB_USERNAME, DB_PASSWORD);
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			try {
				instance = new DBConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

}

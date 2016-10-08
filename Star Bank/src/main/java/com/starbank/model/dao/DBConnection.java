package com.starbank.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.starbank.model.dao.repo.TransactionFinalizerRepository;


public class DBConnection {

	private static final int FINALIZE_TRANSACTION_TIME = 60000;

	private static DBConnection instance;

	private Connection connection;

	private static final String DB_SCHEMA = "e_banking";
	private static final String SSL_DISABLE = "?autoReconnect=true&useSSL=false";
	private static final String DB_PORT = "3306";
	private static final String DB_HOST = "127.0.0.1";
	private static final String DB_PASSWORD = "06011028";
	private static final String DB_USERNAME = "root";

	private DBConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");

		this.connection = DriverManager.getConnection(
				"jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_SCHEMA + SSL_DISABLE, DB_USERNAME, DB_PASSWORD);


		Thread transactionFinalizer = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(FINALIZE_TRANSACTION_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					new TransactionFinalizerRepository().finalizeAllUserTransactions();
				}
			}
		});

		transactionFinalizer.setDaemon(true);
		transactionFinalizer.start();

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

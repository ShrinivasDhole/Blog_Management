package com.db.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

	static String URL = "jdbc:mysql://localhost:3306/blog";
	static String USER = "root";
	static String PASS = "manager";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

}

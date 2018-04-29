package com.github.mouse0w0.database.util;

import java.sql.*;

public class DatabaseUtils {

	public static void requireDriver(String name) throws SQLException {
		try {
			Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Not found driver.", e);
		}
	}

	public static void close(Statement statement) throws SQLException {
		if (statement != null) {
			statement.close();
		}
	}

	public static void close(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
	}

	public static void close(Connection connection) throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

}

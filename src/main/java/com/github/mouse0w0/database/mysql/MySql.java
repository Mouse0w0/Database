package com.github.mouse0w0.database.mysql;

import java.sql.*;
import com.github.mouse0w0.database.Column;
import com.github.mouse0w0.database.internal.DatabaseBase;
import com.github.mouse0w0.database.util.DatabaseUtils;

public class MySql extends DatabaseBase {
	
	public static MySql create(String url, String user, String password) throws SQLException {
		DatabaseUtils.requireDriver("com.mysql.jdbc.Driver");
		return new MySql(url, user, password);
	}
	
	private final String url;
	private final String user;
	private final String password;
	
	public MySql(String url, String user, String password) {
		this(url, user, password, -1);
	}
	
	public MySql(String url, String user, String password, int maxPoolSize) {
		super(maxPoolSize);
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	@Override
	public String getDatabaseType() {
		return "MySQL";
	}
	
	@Override
	protected Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	@Override
	public boolean createSchema(String schema) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("CREATE DATABASE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean hasSchema(String schema) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME='?'")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean deleteSchema(String schema) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("DROP DATABASE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean createTable(String table, Column... columns) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean createTable(String schema, String table, Column... columns) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ? (?)")) {
			statement.setString(0, schema + "." + table);
			statement.setString(1, ""); // TODO
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean hasTable(String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasTable(String schema, String table) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("SELECT TABLE_SCHEMA, TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA='?' AND TABLE_NAME='?'")) {
			statement.setString(0, schema);
			statement.setString(1, table);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean deleteTable(String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteTable(String schema, String table) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("DROP TABLE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}
}

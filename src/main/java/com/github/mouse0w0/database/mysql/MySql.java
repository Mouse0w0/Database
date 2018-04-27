package com.github.mouse0w0.database.mysql;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.mouse0w0.database.Column;
import com.github.mouse0w0.database.Database;
import com.github.mouse0w0.database.Table;
import com.github.mouse0w0.database.internal.SimpleTable;
import com.github.mouse0w0.database.util.SQLUtils;

public class MySql implements Database {
	
	public static MySql create(String url, String user, String password) throws SQLException {
		SQLUtils.requireDriver("com.mysql.jdbc.Driver");
		
		Connection connection = DriverManager.getConnection(url, user, password);
		return new MySql(connection);
	}
	
	private final Connection connection;
	private final Map<String, Table> tables = Collections.synchronizedMap(new HashMap<>());
	
	private MySql(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public String getDatabaseType() {
		return "MySQL";
	}

	@Override
	public boolean isConnected() throws SQLException {
		return !connection.isClosed();
	}

	@Override
	public void disconnect() throws SQLException {
		if(isConnected()) {
			connection.close();
		}
	}

	@Override
	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException{
		return connection.prepareCall(sql);
	}

	@Override
	public boolean createSchema(String schema) throws SQLException {
		try (PreparedStatement statement = prepareStatement("CREATE DATABASE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		}
	}

	@Override
	public boolean hasSchema(String schema) throws SQLException {
		try (PreparedStatement statement = prepareStatement("SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME='?'")) {
			statement.setString(0, schema);
			return statement.execute();
		}
	}

	@Override
	public boolean deleteSchema(String schema) throws SQLException {
		try (PreparedStatement statement = prepareStatement("DROP DATABASE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		}
	}

	@Override
	public Table getTable(String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Table getTable(String schema, String table) throws SQLException {
		return tables.computeIfAbsent(schema + "." + table, (key) -> new SimpleTable(this, key));
	}

	@Override
	public Table createTable(String table, Column... columns) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Table createTable(String schema, String table, Column... columns) throws SQLException {
		return getTable(schema, table);
	}

	@Override
	public boolean hasTable(String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasTable(String schema, String table) throws SQLException {
		try (PreparedStatement statement = prepareStatement("SELECT TABLE_SCHEMA, TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA='?' AND TABLE_NAME='?'")) {
			statement.setString(0, schema);
			statement.setString(1, table);
			return statement.execute();
		}
	}

	@Override
	public boolean deleteTable(String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteTable(String schema, String table) throws SQLException {
		try (PreparedStatement statement = prepareStatement("DROP TABLE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		}
	}
}

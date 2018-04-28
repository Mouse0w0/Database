package com.github.mouse0w0.database.mysql;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.mouse0w0.database.Column;
import com.github.mouse0w0.database.Table;
import com.github.mouse0w0.database.internal.DatabaseBase;
import com.github.mouse0w0.database.internal.SimpleTable;
import com.github.mouse0w0.database.util.SQLUtils;

public class MySql extends DatabaseBase {
	
	public static MySql create(String url, String user, String password) throws SQLException {
		SQLUtils.requireDriver("com.mysql.jdbc.Driver");
		return new MySql(url, user, password);
	}
	
	private final String url;
	private final String user;
	private final String password;
	
	private final Map<String, Table> tables = Collections.synchronizedMap(new HashMap<>());
	
	private MySql(String url, String user, String password) {
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
	public boolean createSchema(String schema) throws SQLException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("CREATE DATABASE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean hasSchema(String schema) throws SQLException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME='?'")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean deleteSchema(String schema) throws SQLException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("DROP DATABASE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
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
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ? (?)")) {
			statement.setString(0, schema + "." + table);
			statement.execute();
		} finally {
			freeConnection(connection);
		}
		return getTable(schema, table);
	}

	@Override
	public boolean hasTable(String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasTable(String schema, String table) throws SQLException {
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
	public boolean deleteTable(String schema, String table) throws SQLException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("DROP TABLE ?")) {
			statement.setString(0, schema);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}
}

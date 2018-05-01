package com.github.mouse0w0.database.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.github.mouse0w0.database.Column;
import com.github.mouse0w0.database.internal.DatabaseBase;

public class SQLite extends DatabaseBase {
	
	private final String url;
	
	public SQLite(String url) {
		this(url, -1);
	}
	
	public SQLite(String url, int maxPoolSize) {
		super(maxPoolSize);
		this.url = url;
	}
	
	@Override
	public String getDatabaseType() {
		return "SQLite";
	}
	
	@Override
	protected Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}

	@Override
	public boolean createSchema(String schema) throws SQLException, InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasSchema(String schema) throws SQLException, InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteSchema(String schema) throws SQLException, InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean createTable(String table, Column... columns) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ? (?)")) {
			statement.setString(0, table);
			StringBuilder sb = new StringBuilder(" ");
			for (int i = 0; i < columns.length; i++) {
				if(i != 0)
					sb.append(",");
				sb.append(columns[i]);
			}
			statement.setString(1, sb.toString());
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean createTable(String schema, String table, Column... columns) throws SQLException, InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasTable(String table) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("SELECT name FROM sqlite_master WHERE name='?'")) {
			statement.setString(0, table);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
	}

	@Override
	public boolean hasTable(String schema, String table) throws SQLException, InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deleteTable(String table) throws SQLException, InterruptedException {
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement("DROP TABLE ?")) {
			statement.setString(0, table);
			return statement.execute();
		} finally {
			freeConnection(connection);
		}
		
	}

	@Override
	public boolean deleteTable(String schema, String table) throws SQLException, InterruptedException {
		throw new UnsupportedOperationException();
	}
}

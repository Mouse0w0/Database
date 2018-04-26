package com.github.mouse0w0.database.mysql;

import java.sql.*;

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
	public Table getOrCreateTable(String name, Column... columns) {
		// TODO 自动生成的方法存根
		return new SimpleTable(this, name, columns);
	}

}

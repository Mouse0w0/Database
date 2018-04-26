package com.github.mouse0w0.database;

import java.sql.SQLException;

public interface Database {
	
	String getDatabaseType();
	
	boolean isConnected() throws SQLException;
	
	void disconnect() throws SQLException;
	
	Table getOrCreateTable(String name, Column... columns);
}

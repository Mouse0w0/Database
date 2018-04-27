package com.github.mouse0w0.database;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface Database {
	
	String getDatabaseType();
	
	boolean isConnected() throws SQLException;
	
	void disconnect() throws SQLException;
	
	boolean createSchema(String schema) throws SQLException;
	
	boolean hasSchema(String schema) throws SQLException;
	
	boolean deleteSchema(String schema) throws SQLException;
	
	Table getTable(String table) throws SQLException;
	
	Table getTable(String schema, String table) throws SQLException;
	
	Table createTable(String table, Column... columns) throws SQLException;
	
	Table createTable(String schema, String table, Column... columns) throws SQLException;
	
	boolean hasTable(String table) throws SQLException;
	
	boolean hasTable(String schema, String table) throws SQLException;
	
	boolean deleteTable(String table) throws SQLException;
	
	boolean deleteTable(String schema, String table) throws SQLException;
	
	Statement createStatement() throws SQLException;
	
	PreparedStatement prepareStatement(String sql) throws SQLException;
	
	CallableStatement prepareCall(String sql) throws SQLException;
}

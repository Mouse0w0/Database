package com.github.mouse0w0.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public interface Database {
	
	String getDatabaseType();
	
	boolean isConnected();
	
	void disconnect() throws SQLException;
	
	void disconnectOnComplete() throws SQLException;
	
	/**
	 * @throws InterruptedException 
	 * @throws  
	 * @see Database#freeConnection(Connection)
	 */
	Connection getConnection() throws SQLException;
	
	/**
	 * @see Database#getConnection()
	 */
	void freeConnection(Connection connection);
	
	void sync(Consumer<Connection> consumer) throws SQLException;
	
	void async(Consumer<Connection> consumer) throws SQLException;
	
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
}

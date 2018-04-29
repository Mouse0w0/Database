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
	 * @see Database#freeConnection(Connection)
	 */
	Connection getConnection() throws SQLException, InterruptedException;
	
	/**
	 * @see Database#getConnection()
	 */
	void freeConnection(Connection connection);
	
	void sync(Consumer<Connection> consumer) throws SQLException, InterruptedException;
	
	void async(Consumer<Connection> consumer) throws SQLException;
	
	boolean createSchema(String schema) throws SQLException, InterruptedException;
	
	boolean hasSchema(String schema) throws SQLException, InterruptedException;
	
	boolean deleteSchema(String schema) throws SQLException, InterruptedException;
	
	boolean createTable(String table, Column... columns) throws SQLException, InterruptedException;
	
	boolean createTable(String schema, String table, Column... columns) throws SQLException, InterruptedException;
	
	boolean hasTable(String table) throws SQLException, InterruptedException;
	
	boolean hasTable(String schema, String table) throws SQLException, InterruptedException;
	
	boolean deleteTable(String table) throws SQLException, InterruptedException;
	
	boolean deleteTable(String schema, String table) throws SQLException, InterruptedException;
}

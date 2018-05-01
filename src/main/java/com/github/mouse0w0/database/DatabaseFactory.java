package com.github.mouse0w0.database;

import java.sql.SQLException;

import com.github.mouse0w0.database.mysql.MySql;
import com.github.mouse0w0.database.sqlite.SQLite;
import com.github.mouse0w0.database.util.DatabaseUtils;

public class DatabaseFactory {

	public static MySql createMySql(String host, int port, String user, String password) throws SQLException {
		DatabaseUtils.requireDriver("com.mysql.jdbc.Driver");
		return new MySql("jdbc:mysql://" + host + ":" + port + "/", user, password);
	}
	
	public static MySql createMySql(String host, int port, String user, String password, int maxPoolSize) throws SQLException {
		DatabaseUtils.requireDriver("com.mysql.jdbc.Driver");
		return new MySql("jdbc:mysql://" + host + ":" + port + "/", user, password, maxPoolSize);
	}
	
	public static SQLite createSQLite(String path) throws SQLException {
		DatabaseUtils.requireDriver("org.sqlite.JDBC");
		return new SQLite("jdbc:sqlite:" + path);
	}
	
	public static SQLite createSQLite(String path, int maxPoolSize) throws SQLException {
		DatabaseUtils.requireDriver("org.sqlite.JDBC");
		return new SQLite("jdbc:sqlite:" + path, maxPoolSize);
	}
}

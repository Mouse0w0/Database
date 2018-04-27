package com.github.mouse0w0.database.internal;

import com.github.mouse0w0.database.Database;
import com.github.mouse0w0.database.Table;

public class SimpleTable implements Table {
	
	private final Database database;
	private final String name;
	
	public SimpleTable(Database database, String name) {
		this.database = database;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Database getDatabase() {
		return database;
	}

}

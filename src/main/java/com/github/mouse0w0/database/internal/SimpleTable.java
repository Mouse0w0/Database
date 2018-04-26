package com.github.mouse0w0.database.internal;

import com.github.mouse0w0.database.Column;
import com.github.mouse0w0.database.Database;
import com.github.mouse0w0.database.Table;

public class SimpleTable implements Table {
	
	private final Database database;
	private final String name;
	private final Column[] columns;
	
	public SimpleTable(Database database, String name, Column[] columns) {
		this.database = database;
		this.name = name;
		this.columns = columns;
	}

	@Override
	public String getName() {
		// TODO 自动生成的方法存根
		return null;
	}

}

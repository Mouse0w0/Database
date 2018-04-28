package com.github.mouse0w0.database;

public class Column {

	private final String name;
	private final DataType dataType;
	private final ColumnRule[] rules;
	
	public Column(String name, DataType dataType, ColumnRule... rules) {
		this.name = name;
		this.dataType = dataType;
		this.rules = rules;
	}

	public String getName() {
		return name;
	}
	
	public DataType getDataType() {
		return dataType;
	}

	public ColumnRule[] getRules() {
		return rules;
	}
	
	public String toSqlCommand() {
		return null;
	}
}

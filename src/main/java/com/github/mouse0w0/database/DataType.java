package com.github.mouse0w0.database;

public class DataType {

	private final String name;
	private final String option;

	public DataType(String name) {
		this(name, null);
	}

	public DataType(String name, String option) {
		this.name = name;
		this.option = option;
	}

	public String getName() {
		return name;
	}

	public String getOption() {
		return option;
	}
}

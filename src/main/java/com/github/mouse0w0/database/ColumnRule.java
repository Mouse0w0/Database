package com.github.mouse0w0.database;

public class ColumnRule {
	
	public static final ColumnRule NOT_NULL = new ColumnRule("NOT NULL");
	public static final ColumnRule UNIQUE = new ColumnRule("UNIQUE");
	public static final ColumnRule PRIMARY_KEY = new ColumnRule("PRIMARY KEY");
	public static final ColumnRule BINARY = new ColumnRule("BINARY");
	public static final ColumnRule UNSIGNED = new ColumnRule("UNSIGNED");
	public static final ColumnRule ZERO_FILL = new ColumnRule("ZERO FILL");
	public static final ColumnRule AUTO_INCREMENT = new ColumnRule("AUTO_INCREMENT");
	
	public static final ColumnRule FOREIGN_KEY = new ColumnRule("FOREIGN KEY");
	public static final ColumnRule CHECK = new ColumnRule("CHECK");
	public static final ColumnRule DEFAULT = new ColumnRule("DEFAULT");
	
	private final String name;
	private final String option;

	private ColumnRule(String name) {
		this(name, null);
	}

	private ColumnRule(String name, String option) {
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

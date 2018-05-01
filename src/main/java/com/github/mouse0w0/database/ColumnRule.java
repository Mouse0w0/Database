package com.github.mouse0w0.database;

public class ColumnRule {
	
	public static final ColumnRule NOT_NULL = new ColumnRule("NOT NULL");
	public static final ColumnRule UNIQUE = new ColumnRule("UNIQUE");
	public static final ColumnRule PRIMARY_KEY = new ColumnRule("PRIMARY KEY");
	public static final ColumnRule BINARY = new ColumnRule("BINARY");
	public static final ColumnRule UNSIGNED = new ColumnRule("UNSIGNED");
	public static final ColumnRule ZERO_FILL = new ColumnRule("ZERO FILL");
	public static final ColumnRule AUTO_INCREMENT = new ColumnRule("AUTO_INCREMENT");
	
//	public static final ColumnRule FOREIGN_KEY = new ColumnRule("FOREIGN KEY");
	public static final CheckColumnRule CHECK = new CheckColumnRule("CHECK");
	public static final DefaultColumnRule DEFAULT = new DefaultColumnRule("DEFAULT");
	
	private final String name;

	private ColumnRule(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toSQLCommand() {
		return name;
	}
	
	public static class CheckColumnRule extends ColumnRule {
		
		private final String check;
		
		public CheckColumnRule(String name) {
			this(name, null);
		}

		public CheckColumnRule(String name, String check) {
			super(name);
			this.check = check;
		}

		public String getCheck() {
			return check;
		}
		
		public CheckColumnRule check(String check) {
			return new CheckColumnRule(getName(), check);
		}
		
		@Override
		public String toSQLCommand() {
			return getName() + "(" + check + ")";
		}
	}
	
	public static class DefaultColumnRule extends ColumnRule {
		
		private final String defaultValue;
		
		public DefaultColumnRule(String name) {
			this(name, null);
		}

		public DefaultColumnRule(String name, String value) {
			super(name);
			this.defaultValue = value;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
		
		public DefaultColumnRule defaultValue(String check) {
			return new DefaultColumnRule(getName(), check);
		}
		
		@Override
		public String toSQLCommand() {
			return getName() + " " + defaultValue;
		}
	}

}

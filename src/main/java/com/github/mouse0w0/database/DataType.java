package com.github.mouse0w0.database;

public class DataType {
	
	public static final DataType BIT = new DataType("BIT");
	public static final DataType TINYINT = new DataType("TINYINT");
	public static final DataType SMALLINT = new DataType("SMALLINT");
	public static final DataType MEDIUMINT = new DataType("MEDIUMINT");
	public static final DataType INT = new DataType("INT");
	public static final DataType BIGINT = new DataType("BIGINT");
	
	public static final DataType FLOAT = new DataType("FLOAT");
	public static final DataType DOUBLE = new DataType("DOUBLE");
	public static final DataType DECIMAL = new DataType("DECIMAL");
	
	public static final DataType DATE = new DataType("DATE");
	public static final DataType TIME = new DataType("TIME");
	public static final DataType YEAR = new DataType("YEAR");
	public static final DataType DATETIME = new DataType("DATETIME");
	public static final DataType TIMESTAMP = new DataType("TIMESTAMP");
	
	public static final DataType CHAR = new DataType("CHAR");
	public static final DataType VARCHAR = new DataType("VARCHAR");
	
	public static final DataType TINYTEXT = new DataType("TINYTEXT");
	public static final DataType TEXT = new DataType("TEXT");
	public static final DataType MEDIUMTEXT = new DataType("MEDIUMTEXT");
	public static final DataType LONGTEXT = new DataType("LONGTEXT");
	
	public static final DataType TINYBLOB = new DataType("TINYBLOB");
	public static final DataType BLOB = new DataType("BLOB");
	public static final DataType MEDIUMBLOB = new DataType("MEDIUMBLOB");
	public static final DataType LONGBLOB = new DataType("LONGBLOB");
	
	public static final DataType ENUM = new DataType("ENUM");
	public static final DataType SET = new DataType("SET");
	
	public static final DataType BOOLEAN = new DataType("BOOLEAN");
	public static final DataType BOOL = new DataType("BOOL");
	
	public static final DataType JSON = new DataType("JSON");
	
	public static final DataType GEOMETRY = new DataType("GEOMETRY");
	public static final DataType POINT = new DataType("POINT");
	public static final DataType LINESTRING = new DataType("LINESTRING");
	public static final DataType POLYGON = new DataType("POLYGON");
	public static final DataType GEOMETRYCOLLECTION = new DataType("GEOMETRYCOLLECTION");
	public static final DataType MULTILINESTRING = new DataType("MULTILINESTRING");
	public static final DataType MULTIPOINT = new DataType("MULTIPOINT");
	public static final DataType MULTIPOLYGON = new DataType("MULTIPOLYGON");

	private final String name;

	private DataType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

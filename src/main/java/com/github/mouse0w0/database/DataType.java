package com.github.mouse0w0.database;

public class DataType {
	
	public static final LimitedDataType BIT = new LimitedDataType("BIT");
	public static final LimitedDataType TINYINT = new LimitedDataType("TINYINT");
	public static final LimitedDataType SMALLINT = new LimitedDataType("SMALLINT");
	public static final LimitedDataType MEDIUMINT = new LimitedDataType("MEDIUMINT");
	public static final LimitedDataType INT = new LimitedDataType("INT");
	public static final LimitedDataType INTEGER = new LimitedDataType("INTEGER");
	public static final LimitedDataType BIGINT = new LimitedDataType("BIGINT");
	
	public static final FloatDataType FLOAT = new FloatDataType("FLOAT");
	public static final FloatDataType DOUBLE = new FloatDataType("DOUBLE");
	public static final FloatDataType DECIMAL = new FloatDataType("DECIMAL");
	public static final FloatDataType REAL = new FloatDataType("REAL");
	public static final FloatDataType NUMERIC = new FloatDataType("NUMERIC");
	
	public static final DataType DATE = new DataType("DATE");
	public static final DataType TIME = new DataType("TIME");
	public static final DataType YEAR = new DataType("YEAR");
	public static final DataType DATETIME = new DataType("DATETIME");
	public static final DataType TIMESTAMP = new DataType("TIMESTAMP");
	
	public static final LimitedDataType CHAR = new LimitedDataType("CHAR");
	public static final LimitedDataType VARCHAR = new LimitedDataType("VARCHAR");
	
	public static final DataType TINYTEXT = new DataType("TINYTEXT");
	public static final DataType TEXT = new DataType("TEXT");
	public static final DataType MEDIUMTEXT = new DataType("MEDIUMTEXT");
	public static final DataType LONGTEXT = new DataType("LONGTEXT");
	public static final DataType CLOB = new DataType("CLOB");
	
	public static final DataType TINYBLOB = new DataType("TINYBLOB");
	public static final DataType BLOB = new DataType("BLOB");
	public static final DataType MEDIUMBLOB = new DataType("MEDIUMBLOB");
	public static final DataType LONGBLOB = new DataType("LONGBLOB");
	
	public static final EnumDataType ENUM = new EnumDataType("ENUM");
	public static final EnumDataType SET = new EnumDataType("SET");
	
	public static final DataType BOOLEAN = new DataType("BOOLEAN");
	public static final DataType BOOL = new DataType("BOOL");
	
	public static final DataType JSON = new DataType("JSON");
	
//	public static final DataType GEOMETRY = new DataType("GEOMETRY");
//	public static final DataType POINT = new DataType("POINT");
//	public static final DataType LINESTRING = new DataType("LINESTRING");
//	public static final DataType POLYGON = new DataType("POLYGON");
//	public static final DataType GEOMETRYCOLLECTION = new DataType("GEOMETRYCOLLECTION");
//	public static final DataType MULTILINESTRING = new DataType("MULTILINESTRING");
//	public static final DataType MULTIPOINT = new DataType("MULTIPOINT");
//	public static final DataType MULTIPOLYGON = new DataType("MULTIPOLYGON");
//	
//	public static final DataType NULL = new DataType("NULL");

	private final String name;

	private DataType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toSQLCommand() {
		return name;
	}
	
	public static class LimitedDataType extends DataType {
		
		private final int length;
		
		public LimitedDataType(String name) {
			this(name, -1);
		}
		
		public LimitedDataType(String name, int length) {
			super(name);
			this.length = length;
		}
		
		public int getLength() {
			return length;
		}
		
		public LimitedDataType limit(int length) {
			return new LimitedDataType(getName(), length);
		}
		
		@Override
		public String toSQLCommand() {
			return length > 0 ? getName() + "(" + length + ")" : getName();
		}
	}
	
	public static class FloatDataType extends DataType {
		
		private final int length;
		private final int floatLength;
		
		public FloatDataType(String name) {
			this(name, -1, -1);
		}
		
		public FloatDataType(String name, int length, int floatLength) {
			super(name);
			this.length = length;
			this.floatLength = floatLength;
		}
		
		public int getLength() {
			return length;
		}
		
		public int getFloatLength() {
			return floatLength;
		}
		
		public FloatDataType limit(int length, int floatLength) {
			return new FloatDataType(getName(), length, floatLength);
		}
		
		@Override
		public String toSQLCommand() {
			return length > 0 ? getName() + "(" + length + "," + floatLength + ")" : getName();
		}
	}
	
	public static class EnumDataType extends DataType {
		
		private final String[] enums;
		
		public EnumDataType(String name) {
			this(name, null);
		}
		
		public EnumDataType(String name, String[] enums) {
			super(name);
			this.enums = enums;
		}

		public String[] getEnums() {
			return enums;
		}
		
		public EnumDataType limit(String... enums) {
			return new EnumDataType(getName(), getEnums());
		}
		
		@Override
		public String toSQLCommand() {
			StringBuilder sb = new StringBuilder(getName());
			if (enums != null) {
				for (int i = 0; i < enums.length; i++) {

					if (i != 0)
						sb.append(',');

					sb.append('\'').append(enums[i]).append('\'');
				}
			}
			return sb.toString();
		}
	}
}

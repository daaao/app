package io.zhijian.base.sql;

public class SqlConfig {
	public final static String PATH = "/sql";
	public final static String SUFFIX = ".sql";

	public static String getSqlPath(String fileName) {
		return fileName + SUFFIX;
	}
}

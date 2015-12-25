package com.maanadev.mongo;

public interface DBConstants {
	public static final String CONNECTION_URL = "localhost";
	public static final int CONNETION_PORT = 27017;

	// QUERY CONDITIONS
	public static final String EQUAL = "$eq";
	public static final String GREATER_THAN = "$gt";
	public static final String GREATER_THAN_EQUAL = "$gte";
	public static final String LESS_THAN = "$lt";
	public static final String LESS_THAN_EQUAL = "$lte";
	public static final String NOT_EQUAL = "$nt";

	// SORTING
	public static final String ASCENDING = "1";
	public static final String DESCENDING = "-1";
}

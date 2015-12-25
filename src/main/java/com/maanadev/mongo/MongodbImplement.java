package com.maanadev.mongo;

import java.util.NoSuchElementException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import com.maanadev.example.Person;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongodbImplement<T> {

	protected MongoCollection collection;
	protected DB db;
	protected String collectionName;
	protected Class<T> t;
	private MongoClient mongoClient;

	public MongodbImplement(String dbName, String collectionName, Class<T> t) {
		this.t = t;
		this.collectionName = collectionName;
		MongoClient mongoClient = new MongoClient(DBConstants.CONNECTION_URL, DBConstants.CONNETION_PORT);
		db = mongoClient.getDB(dbName);
		Jongo jongo = new Jongo(db);
		collection = jongo.getCollection(collectionName);
	}

	public void save(T t) {

		collection.save(t);

	}

	public void close() {
		mongoClient.close();
	}

	public long getCount() {
		return collection.count();
	}

	/**
	 * This method can be called when delete is required according to a
	 * condition. This conditions can be get from DBConstants class
	 * 
	 * @param parameter
	 * @param condition
	 * @param value
	 * 
	 */
	public void delete(String parameter, String condition, String value) {
		String find = makeFindCondition(parameter, condition, value);

		collection.remove(find);
	}

	/**
	 * This method can be called when delete is required and condition is Equal.
	 * 
	 * @param parameter
	 * @param value
	 * 
	 * 
	 */
	public void delete(String parameter, String value) {
		String find = makeFindEqual(parameter, value);

		collection.remove(find);
	}

	// **********************FIND
	// METHODS***************************************************
	/**
	 * This method can be call when sorting is not needed
	 * 
	 * @param parameter
	 * @param condition
	 * @param value
	 * @return MongoCursor<T>
	 * 
	 * 
	 */
	public MongoCursor<T> find(String parameter, String condition, String value) {
		String find = makeFindCondition(parameter, condition, value);
		MongoCursor<T> cursor = collection.find(find).as(t);
		return cursor;

	}

	/**
	 * This method is called when sorting is required."sortParam"= parameter of
	 * the query,"sort" =sorting condition.This conditions can be get from
	 * DBConstants class
	 * 
	 * @param parameter
	 * @param condition
	 * @param value
	 * @param sortParam
	 * @param sort
	 * @return MongoCursor<T>
	 * 
	 * 
	 */
	public MongoCursor<T> find(String parameter, String condition, String value, String sortParam, String sort) {
		String find = makeFindCondition(parameter, condition, value);
		String sorting = "{" + sortParam + ":" + sort + "}";
		MongoCursor<T> cursor = collection.find(find).sort(sorting).as(t);
		return cursor;

	}

	// ------------------------------------------------------------------------------------
	/**
	 * This method is called when search parameter is an object and sorting
	 * needed. "sortParam"= parameter of the query,"sort" =sorting
	 * condition.This conditions can be get from DBConstants class
	 * 
	 * @param parameter
	 * @param obj
	 * @param sortParam
	 * @param sort
	 * @return MongoCursor<T>
	 * 
	 */
	public MongoCursor<T> find(String parameter, Object obj, String sortParam, String sort) {
		String find = "{" + parameter + ": #}";
		String sorting = "{" + sortParam + ":" + sort + "}";
		MongoCursor<T> cursor = collection.find(find, obj).sort(sorting).as(t);
		return cursor;

	}

	/**
	 * This method is called when search parameter is an object and no sorting
	 * is required.
	 * 
	 * @param parameter
	 * @param obj
	 * @return MongoCursor<T>
	 * 
	 */
	public MongoCursor<T> find(String parameter, Object obj) {
		String find = "{" + parameter + ": #}";
		MongoCursor<T> cursor = collection.find(find, obj).as(t);
		return cursor;

	}

	// -------------------------------------------------------------------------------------
	/**
	 * This method is called when condition is Equal and sorting is required.
	 * "sortParam"= parameter of the query,"sort" =sorting condition.This
	 * conditions can be get from DBConstants class
	 * 
	 * @param parameter
	 * @param value
	 * @param sortParam
	 * @param sort
	 * @return MongoCursor<T>
	 * 
	 */
	public MongoCursor<T> find(String parameter, String value, String sortParam, String sort) {
		String find = makeFindEqual(parameter, value);
		String sorting = "{" + sortParam + ":" + sort + "}";
		MongoCursor<T> cursor = collection.find(find).sort(sorting).as(t);
		return cursor;

	}

	/**
	 * This method is called when condition is Equal
	 * 
	 * @param parameter
	 * @param value
	 * @return MongoCursor<T>
	 */
	public MongoCursor<T> find(String parameter, String value) {
		String find = makeFindEqual(parameter, value);
		MongoCursor<T> cursor = collection.find(find).as(t);
		return cursor;

	}

	// ****************************************************************************************
	/**
	 * This method can be used to get objects in MongoCursor instance.Also it
	 * can be used to handle NoSuchElementException in program.
	 * 
	 * @param cursor
	 * @return Object
	 * @throws NoSuchElementException
	 *
	 */
	public T next(MongoCursor<Person> cursor) throws NoSuchElementException {
		return (T) cursor.next();
	}

	// ****************************************************************************************
	private boolean isNumeric(String value) {

		try {
			Double d = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private String makeFindEqual(String parameter, String value) {
		String find;
		if (isNumeric(value)) {
			find = "{" + parameter + ": {" + DBConstants.EQUAL + ":" + value + "}}";
			return find;
		} else {
			find = "{" + parameter + ": {" + DBConstants.EQUAL + ":" + value + "}}";
			return find;
		}
	}

	private String makeFindCondition(String parameter, String condition, String value) {
		String find;
		if (isNumeric(value))
			find = "{" + parameter + ": {" + condition + ":" + value + "}}";
		else
			find = "{" + parameter + ": {" + condition + ":'" + value + "'}}";
		return find;
	}

}

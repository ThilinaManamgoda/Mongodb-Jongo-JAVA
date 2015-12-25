package com.maanadev.mongo;

import java.util.NoSuchElementException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.marshall.jackson.oid.MongoId;

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

	public void update(UpdateQuery updateQuery) {

		String find = null;
		if (updateQuery.getFindCondition() == null) {
			if (updateQuery.getFindObj() == null) {
				find = makeFindEqual(updateQuery.getFindParameter(), updateQuery.getFindValue());
				executeUpdate(updateQuery, find);
			} else {
				find = "{" + updateQuery.getFindParameter() + ": #}";
				executeUpdate(updateQuery, find, updateQuery.getFindObj());

			}
		} else {
			find = makeFindCondition(updateQuery.getFindParameter(), updateQuery.getFindCondition(),
					updateQuery.getFindValue());
			executeUpdate(updateQuery, find);
		}

	}

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
		if (isMongoId(parameter)) {
			if (isNumeric(value)) {
				find = "{ _id: {" + DBConstants.EQUAL + ":" + value + "}}";
				return find;
			} else {
				find = "{ _id: {" + DBConstants.EQUAL + ":'" + value + "'}}";
				return find;
			}
		} else {
			if (isNumeric(value)) {
				find = "{" + parameter + ": {" + DBConstants.EQUAL + ":" + value + "}}";
				return find;
			} else {
				find = "{" + parameter + ": {" + DBConstants.EQUAL + ":'" + value + "'}}";
				return find;
			}
		}

	}

	private String makeFindCondition(String parameter, String condition, String value) {
		String find;

		if (isMongoId(parameter)) {
			if (isNumeric(value)) {
				find = "{ _id: {" + condition + ":" + value + "}}";
			} else
				find = "{ _id: {" + condition + ":'" + value + "'}}";

		} else {
			if (isNumeric(value)) {
				find = "{" + parameter + ": {" + condition + ":" + value + "}}";
			} else
				find = "{" + parameter + ": {" + condition + ":'" + value + "'}}";
		}

		return find;
	}

	public boolean isMongoId(String parameter) {
		try {
			if (t.getDeclaredField(parameter).getDeclaredAnnotation(MongoId.class) == null)
				return false;

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void executeUpdate(UpdateQuery updateQuery, String find) {
		String with = null;
		if (updateQuery.getOpereator().equals(DBConstants.INCREMENT)) {

			with = "{$inc: {" + updateQuery.getParameter() + ": " + updateQuery.getObj().toString() + "}}";
			if (updateQuery.isCreateNewDocuments()) {
				if (updateQuery.isMultipleDocuments())
					collection.update(find).upsert().multi().with(with);
				else
					collection.update(find).upsert().with(with);
			} else {
				if (updateQuery.isMultipleDocuments())
					collection.update(find).multi().with(with);

				else
					collection.update(find).with(with);
			}

		} else if (updateQuery.getOpereator().equals(DBConstants.SET)) {
			with = "{$set: {" + updateQuery.getParameter() + ": #}}";
			if (updateQuery.isCreateNewDocuments()) {
				if (updateQuery.isMultipleDocuments())
					collection.update(find).upsert().multi().with(with, updateQuery.getObj());
				else
					collection.update(find).upsert().with(with, updateQuery.getObj());
			} else {
				if (updateQuery.isMultipleDocuments())
					collection.update(find).multi().with(with, updateQuery.getObj());

				else
					collection.update(find).with(with, updateQuery.getObj());
			}

		}

	}

	private void executeUpdate(UpdateQuery updateQuery, String find, Object obj) {
		String with = null;
		if (updateQuery.getOpereator().equals(DBConstants.INCREMENT)) {

			with = "{$inc: {" + updateQuery.getParameter() + ": " + updateQuery.getObj().toString() + "}}";
			if (updateQuery.isCreateNewDocuments()) {
				if (updateQuery.isMultipleDocuments())
					collection.update(find, obj).upsert().multi().with(with);
				else
					collection.update(find, obj).upsert().with(with);
			} else {
				if (updateQuery.isMultipleDocuments())
					collection.update(find, obj).multi().with(with);

				else
					collection.update(find, obj).with(with);
			}

		} else if (updateQuery.getOpereator().equals(DBConstants.SET)) {
			with = "{$set: {" + updateQuery.getParameter() + ": #}}";
			if (updateQuery.isCreateNewDocuments()) {
				if (updateQuery.isMultipleDocuments())
					collection.update(find, obj).upsert().multi().with(with, updateQuery.getObj());
				else
					collection.update(find, obj).upsert().with(with, updateQuery.getObj());
			} else {
				if (updateQuery.isMultipleDocuments())
					collection.update(find, obj).multi().with(with, updateQuery.getObj());

				else
					collection.update(find, obj).with(with, updateQuery.getObj());
			}

		}

	}

}

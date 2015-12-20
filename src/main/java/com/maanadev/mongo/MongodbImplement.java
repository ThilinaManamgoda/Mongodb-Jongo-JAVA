package com.maanadev.mongo;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongodbImplement<T> {

	protected MongoCollection collection;
	protected DB db;
	protected String collectionName;
	protected Class<T> t;

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

	public T get(String id) {

		T return_t = (T) collection.findOne("{ _id:" + id + "}").as(t);
		return return_t;
	}

	public long getCount() {
		return collection.count();
	}

	public void delete(String id) {
		collection.remove("{ _id:" + id + "}");
	}
}

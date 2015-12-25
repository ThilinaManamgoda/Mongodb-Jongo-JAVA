package com.maanadev.example;

import java.util.NoSuchElementException;

import org.jongo.MongoCursor;

import com.maanadev.mongo.DBConstants;
import com.maanadev.mongo.MongodbImplement;
import com.maanadev.mongo.UpdateQuery;
import com.mongodb.DB;

public class Example {

	public static void main(String[] args) {

		MongodbImplement<Person> m = new MongodbImplement<Person>("test3", "persons2", Person.class);

		// *****CREATING PERSON OBJECT*****

		// Person p1 = new Person();
		// p1.set_id(1);
		// p1.setFirstName("a");
		// p1.setLastName("c");
		// p1.setAge(18);
		 Address address = new Address();
		 address.setLane1("try1lane1");
		 address.setLane2("lane2");
		// p1.setAddress(address);
		//
		// Person p2 = new Person();
		// p2.set_id(2);
		// p2.setFirstName("a");
		// p2.setLastName("d");
		// p2.setAge(19);
		Address address2 = new Address();
		address2.setLane1("new lane1");
		address2.setLane2("lane2");
		// p2.setAddress(address2);

		// *****SAVING IN "TEST3" DATABASE AND COLLECTION "PERSONS2"*****
		// m.save(p1);
		// m.save(p2);
		// System.out.println("saved");

		// *****DELETE*****
		// m.delete("key", DBConstants.EQUAL, "1234");

		// *****FIND PERSON WITH CONDITIONS*****

		// MongoCursor<Person> cursor = m.find("age", DBConstants.LESS_THAN,
		// "19");

		// *****FIND PERSON WITH EQUALITY**********

		// MongoCursor<Person> cursor = m.find("age","18");

		// *****FIND PERSON WITH EQUALITY AND OBJECT*****

		// MongoCursor<Person> cursor = m.find("address", address);
		//CREATING A UPDATE QUERY
		UpdateQuery updateQuery = new UpdateQuery();
		updateQuery.setCreateNewDocuments(false);
		updateQuery.setMultipleDocuments(true);
		updateQuery.setFind("age", DBConstants.GREATER_THAN,"40");
		updateQuery.setParameter("age");
		updateQuery.setEntity(10);
		updateQuery.setOpereator(DBConstants.INCREMENT);
		m.update(updateQuery);
		MongoCursor<Person> cursor = m.find("firstName", "a", "lastName", DBConstants.ASCENDING);
		Person p_return = null;
		try {
			while (cursor.hasNext()) {
				p_return = m.next(cursor);
				System.out.println(p_return.getAge());
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

}

package com.maanadev.example;

import java.util.NoSuchElementException;

import org.jongo.MongoCursor;

import com.maanadev.mongo.DBConstants;
import com.maanadev.mongo.MongodbImplement;

public class Example {

	public static void main(String[] args) {

		MongodbImplement<Person> m = new MongodbImplement<Person>("test3", "persons2", Person.class);

		// CREATING PERSON OBJECT

		Person p = new Person();
		p.set_id(1234);
		p.setFirstName("ssss");
		p.setLastName("lastName");
		p.setAge(18);

		// SAVING IN "TEST3" DATABASE AND COLLECTION "PERSONS2"
		m.save(p);
		System.out.println("saved");


		// DELETE
		// m.delete("1234");

		// FIND PERSON
		//MongoCursor<Person> cursor = m.find("age", DBConstants.LESS_THAN, "19");
		MongoCursor<Person> cursor = m.find("age","18");
		Person p_return = null;
		try {
			p_return = m.next(cursor);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

		System.out.println(p_return.getAge());
	}

}

package com.maanadev.example;

import com.maanadev.mongo.MongodbImplement;

public class Example {

	public static void main(String[] args) {

		MongodbImplement<Person> m= new MongodbImplement<Person>("test3", "persons2",Person.class);
		
		//CREATING PERSON OBJECT
		
		Person p = new Person();
		p.set_id(123);
		p.setFirstName("firstName");
		p.setLastName("lastName");
		
		//SAVING IN "TEST3" DATABASE AND COLLECTION "PERSONS2"
		
		m.save(p);
		System.out.println("saved");
		//GET THE PERSON OBJECT
		
		Person p_return =m.get("123");
		
		
		System.out.println(p.getFirstName());
	}

}

package com.maanadev.example;

import com.maanadev.mongo.MongodbImplement;

public class Example {

	public static void main(String[] args) {

		MongodbImplement<Person> m= new MongodbImplement<Person>("test3", "persons2",Person.class);
		
		//CREATING PERSON OBJECT
		
		Person p = new Person();
		p.set_id(1234);
		p.setFirstName("ssss");
		p.setLastName("lastName");
		
		//SAVING IN "TEST3" DATABASE AND COLLECTION "PERSONS2"
		m.save(p);
		System.out.println("saved");
		
		//GET THE PERSON OBJECT
		Person p_return =m.get("1234");
		
		//DELETE 
//		m.delete("1234");
		
		System.out.println(p_return.getFirstName());
	}

}

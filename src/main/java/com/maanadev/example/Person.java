package com.maanadev.example;

import org.jongo.marshall.jackson.oid.MongoId;

public class Person {
	@MongoId 
	private long key;
    
    private String firstName;
    private String lastName;
    private int age;
   public long getKey() {
		return key;
	}
	public void setKey(long key) {
		this.key = key;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	//getters & setters
	public long get_id() {
		return key;
	}
	public void set_id(long _id) {
		this.key = _id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
    
}
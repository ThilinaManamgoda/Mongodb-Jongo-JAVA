package com.maanadev.mongo;

import org.jongo.marshall.jackson.oid.MongoId;

public class UpdateQuery {

	private boolean createNewDocuments = false;
	private boolean multipleDocuments = false;
	private Object obj;
	private String opereator;
	private String parameter;
	private String findParameter;
	private String findCondition = null;
	private String findValue;
	private Object findObj = null;

	public Object getFindObj() {
		return findObj;
	}

	/**
	 * set find Object criteria.Default value is null.
	 * 
	 * @param findObj
	 */

	public void setFindObj(Object findObj) {
		this.findObj = findObj;
	}

	/**
	 * Set update Operator [Ex:Increment Or set a value ]. These values can be
	 * obtain from DBConstants
	 * 
	 * @param opereator
	 */
	public void setOpereator(String opereator) {
		this.opereator = opereator;
	}

	public String getFindParameter() {
		return findParameter;
	}

	/**
	 * Set find criteria for searching.
	 * 
	 * @param findParameter
	 *            = which parameter of the object searching
	 * @param findCondition
	 *            = Under which condition [ Ex: Equal,LagerThan].if it's Equal, value should be set to null.(DBConstants have Values)
	 * @param value
	 *            =if searching is happening through custom object then this should be set to null.
	 */
	public void setFind(String findParameter, String findCondition, String value) {
		this.findParameter = findParameter;
		this.findCondition = findCondition;
		this.findValue = value;
	}

	public String getFindCondition() {
		return findCondition;
	}

	public String getFindValue() {
		return findValue;
	}

	public String getOpereator() {
		return opereator;
	}

	public String getParameter() {
		return parameter;
	}

	/**
	 * Which parameter should be updated
	 * 
	 * @param parameter
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Object getObj() {
		return obj;
	}

	/**
	 * Set the value for update
	 * 
	 * @param obj
	 */

	public void setEntity(Object obj) {
		this.obj = obj;
	}

	public UpdateQuery() {
	}

	public boolean isCreateNewDocuments() {
		return createNewDocuments;
	}

	/**
	 * If the searching document is not in the database ,by setting this "true"
	 * it will create new one.Default value is false.
	 * 
	 * @param createNewDocuments
	 */
	public void setCreateNewDocuments(boolean createNewDocuments) {
		this.createNewDocuments = createNewDocuments;
	}

	public boolean isMultipleDocuments() {
		return multipleDocuments;
	}

	/**
	 * By setting this "true " ,if there is multiple documents that match the
	 * searching criteria ,all of them will be updated.Default value is false.
	 * 
	 * @param multipleDocuments
	 */
	public void setMultipleDocuments(boolean multipleDocuments) {
		this.multipleDocuments = multipleDocuments;
	}

}
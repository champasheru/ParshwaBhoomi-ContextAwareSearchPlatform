/**
 * parshwabhoomi-server	11-Nov-2017:7:56:06 PM
 */
package org.cs.parshwabhoomi.server.dto.adapter;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class JsonEventMetadata {
	public enum JsonValueType{
		//string, numeric, boolean, null
		JSON_VALUE_TYPE_PRIMITIVE,
		//a simple object identified by a key
		JSON_VALUE_TYPE_OBJECT,
		//an array
		JSON_VALUE_TYPE_ARRAY,
		//an object that is not identified by a key but is a part of an array instead.
		JSON_VALUE_TYPE_ARRAY_OBJECT
	}
	
	private String key;
	private JsonValueType jsonValueType;
	
	public JsonEventMetadata() {
		
	}
	
	/**
	 * @param key
	 * @param jsonValueType
	 */
	public JsonEventMetadata(String key, JsonValueType jsonValueType) {
		this.key = key;
		this.jsonValueType = jsonValueType;
	}


	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}


	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}


	/**
	 * @return the jsonValueType
	 */
	public JsonValueType getJsonValueType() {
		return jsonValueType;
	}


	/**
	 * @param jsonValueType the jsonValueType to set
	 */
	public void setJsonValueType(JsonValueType jsonValueType) {
		this.jsonValueType = jsonValueType;
	}
}

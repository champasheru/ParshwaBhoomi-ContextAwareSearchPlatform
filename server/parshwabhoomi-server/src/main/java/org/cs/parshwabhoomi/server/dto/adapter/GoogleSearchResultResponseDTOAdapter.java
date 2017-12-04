/**
 * parshwabhoomi-server	10-Nov-2017:6:18:37 PM
 */
package org.cs.parshwabhoomi.server.dto.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dto.search.JsonEventMetadata;
import org.cs.parshwabhoomi.server.dto.search.JsonEventMetadata.JsonValueType;
import org.cs.parshwabhoomi.server.model.SearchResult;
import org.cs.parshwabhoomi.server.model.SearchResult.Provider;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class GoogleSearchResultResponseDTOAdapter {
	private Stack<JsonEventMetadata> stack = new Stack<>();
	private boolean itemsConsumed = false;
	
	public List<SearchResult> buildResponse(InputStream inputStream){
		List<SearchResult> results = new ArrayList<>();
		
		JsonParser parser = Json.createParser(inputStream);
		while(parser.hasNext()){
			Event event = parser.next();
			JsonEventMetadata metadata = null;
			switch(event){
				case KEY_NAME:
					String key = parser.getString();
					metadata = new JsonEventMetadata();
					metadata.setKey(key);
					stack.push(metadata);
					LogManager.getLogger().info("Processing key: "+key);
					break;
					
				case VALUE_FALSE:
				case VALUE_TRUE:
				case VALUE_NULL:
				case VALUE_NUMBER:
				case VALUE_STRING:
					stack.pop();
					break;
					
				case START_ARRAY:
					if(stack.isEmpty()){
						//Json started with an array.
						LogManager.getLogger().info("Empty stack; parser initialized with an Array!");
					}else{
						metadata = stack.peek();
						metadata.setJsonValueType(JsonValueType.JSON_VALUE_TYPE_ARRAY);
						if(metadata.getKey().equalsIgnoreCase("items")){
							while(parser.hasNext() && !itemsConsumed){
								SearchResult searchResult = parseSingleJsonObject(parser);
								if(searchResult != null){
									results.add(searchResult);
								}
								LogManager.getLogger().info("Search results parsed so far: "+results.size());
							}
						}
					}
					break;
				
				case END_ARRAY:
					if(!stack.isEmpty()){//stack will be empty when end of parsing is reached.
						stack.pop();
					}
					break;
					
				case START_OBJECT:
					if(stack.isEmpty()){
						//Json started with an object.
						LogManager.getLogger().info("Empty stack; parser initialized with Object!");
					}else{
						metadata = stack.peek();
						//Remember by the time, following is to be decided, if the object was part of an array the start_array event has been processed by now.
						if(metadata.getJsonValueType() == JsonValueType.JSON_VALUE_TYPE_ARRAY){
							LogManager.getLogger().info("Object found inside an array; so it's an anonymous object i.e. one w/o associated key!");
							LogManager.getLogger().info("Not pushing an entry on the stack");
						}else{
							metadata.setJsonValueType(JsonValueType.JSON_VALUE_TYPE_OBJECT);
						}
					}
					break;
					
				case END_OBJECT:
					if(!stack.isEmpty()){//stack will be empty when end of parsing is reached.
						metadata = stack.peek();
						//if the value type was array (array_object), then don't pop meta off the stack.
						if(metadata.getJsonValueType() == JsonValueType.JSON_VALUE_TYPE_OBJECT){
							stack.pop();
						}
					}
					break;
					
				default:
					LogManager.getLogger().info("Finished processing an event");
					break;
			}
		}
		
		LogManager.getLogger().info("Total search results parsed: "+results.size());
		
		return results;
	}
	
	
	private SearchResult parseSingleJsonObject(JsonParser parser){
		LogManager.getLogger().info("** Parsing single search result...");
		
		SearchResult searchResult = null;
		
		boolean keepParsing = true;
		
		while(parser.hasNext() && keepParsing){
			Event event = parser.next();
			JsonEventMetadata metadata = null;
			String key = null;
			
			switch(event){
				case KEY_NAME:
					key = parser.getString();
					metadata = new JsonEventMetadata();
					metadata.setKey(key);
					stack.push(metadata);
					LogManager.getLogger().info("Processing key with name: "+key);
					break;
					
				case VALUE_FALSE:
				case VALUE_TRUE:
				case VALUE_NULL:
				case VALUE_NUMBER:
				case VALUE_STRING:
					//The previously pushed key which is associated with this value event and consume if found amongst the desired set of keys.
					key = stack.peek().getKey();
					stack.pop();
					String top = stack.peek().getKey();
					//Just to make sure that the keys from only the given enclosing objects are being parsed.
					if(top.equalsIgnoreCase("items") || top.equalsIgnoreCase("cse_thumbnail") || top.equalsIgnoreCase("metatags") || top.equalsIgnoreCase("cse_image")){
						populate(searchResult, parser, key);
					}
					break;
					
				case START_ARRAY:
					stack.peek().setJsonValueType(JsonValueType.JSON_VALUE_TYPE_ARRAY);
					break;
					
				case END_ARRAY:
					LogManager.getLogger().info("Finished processing an array...");
					if(stack.peek().getKey().equalsIgnoreCase("items")){
						//Hack: problem is - items array end event is encountered here; so need to detect and exit cleanly ;(
						LogManager.getLogger().info("Finished consuming all items...");
						itemsConsumed = true;
						stack.pop();
						return searchResult;
					}
					
					stack.pop();
					LogManager.getLogger().info("Stack snapshot @ array end: "+stack);
					break;
					
				case START_OBJECT:
					LogManager.getLogger().info("Starting to process new object...");
					LogManager.getLogger().info("Stack snapshot @ obj start: "+stack);
					
					metadata = stack.peek();
					//Remember by the time, following is to be decided, if the object was part of an array the start_array event has been processed by now.
					if(metadata.getJsonValueType() == JsonValueType.JSON_VALUE_TYPE_ARRAY){
						LogManager.getLogger().info("Object found inside an array; so it's an anonymous object i.e. one w/o associated key!");
						LogManager.getLogger().info("Not pushing an entry on the stack");
						
						if(metadata.getKey().equalsIgnoreCase("items")){
							LogManager.getLogger().info("Starting to process new Search Result Item object...");
							searchResult = new SearchResult();
							searchResult.setProvider(Provider.SEARCH_PROVIDER_GOOGLE);
						}
					}else{
						metadata.setJsonValueType(JsonValueType.JSON_VALUE_TYPE_OBJECT);
					}
					
					break;
					
				case END_OBJECT:
					LogManager.getLogger().info("Finished processing an object...");
					LogManager.getLogger().info("Stack snapshot @ obj end: "+stack);
					LogManager.getLogger().info("Decide object value type: pop only if value type is object else dont because it's an array_object!");
					
					metadata = stack.peek();
					if(metadata.getJsonValueType() == JsonValueType.JSON_VALUE_TYPE_ARRAY){
						if(metadata.getKey().equalsIgnoreCase("items")){
							LogManager.getLogger().info("Finished processing new Search Result Item object...");
							keepParsing = false;
						}
					}else{
						//means it's a normal object with associated key
						stack.pop();
					}
					break;
					
				default:
					LogManager.getLogger().info("Finished processing an event");
					break;
			}
		}
		
		LogManager.getLogger().info("Finished parsing single search result **");
		return searchResult;
	}
	
	
	private void populate(SearchResult searchResult, JsonParser parser, String key){
		if(key.equalsIgnoreCase("title")){
			searchResult.setTitle(parser.getString().trim());
		}else if(key.equalsIgnoreCase("htmlTitle")){
			searchResult.setHtmlTitle(parser.getString().trim());
		}else if(key.equalsIgnoreCase("link")){
			searchResult.setLink(parser.getString().trim());
		}else if(key.equalsIgnoreCase("displayLink")){
			searchResult.setDisplayLink(parser.getString().trim());
		}else if(key.equalsIgnoreCase("snippet")){
			searchResult.setSnippet(parser.getString().trim());
		}else if(key.equalsIgnoreCase("htmlSnippet")){
			searchResult.setHtmlSnippet(parser.getString().trim());
		}else if(key.equalsIgnoreCase("formattedUrl")){
			searchResult.setFormattedUrl(parser.getString().trim());
		}else if(key.equalsIgnoreCase("htmlFormattedUrl")){
			searchResult.setHtmlFormattedUrl(parser.getString().trim());
		}else if(key.equalsIgnoreCase("src") || key.equalsIgnoreCase("image") || key.equalsIgnoreCase("og:image")){
			//these keys are found under objects: cse_thumbnail, metatags, cse_image respectively.
			//if any of these has valid value then that's sufficient.
			searchResult.setImageUrl(parser.getString().trim());
		}
	}
}

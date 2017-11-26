/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.dataparser;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.domainobjects.SearchResult;

/**
 *
 * @author Saurabh
 * This class serializes the collection of SearchResult objects to the XML.
 */
public class XMLSerializer {
    private static String xmlHeader="<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    //Later make this method generic.Take Object[] & name of the class to
    //which to serialize each object of the array.There will be a contract-
    //interface like say- XMLSerializable which will be implmented by each element
    //in the array.
    public static String serialize(Collection<SearchResult> results){
        System.out.println("Serializing XML...");
        StringBuilder serializedXML=new StringBuilder();
        serializedXML.append(xmlHeader+"\n");
        serializedXML.append("<resultset>\n");
        for(SearchResult sr:results){
            serializedXML.append(getSingleSearchResultXML(sr));
        }
        serializedXML.append("</resultset>");
        LogManager.getLogger().info("Done! XML = "+serializedXML.toString());
        return serializedXML.toString();
    }
    
    private static String getSingleSearchResultXML(SearchResult result){
    	LogManager.getLogger().info("Serialising single result...");
    	StringBuilder xml= new StringBuilder();
    	xml.append("<result>\n");
    	xml.append("<type>" + escapeXML(result.getType().name()) + "</type>\n");
    	xml.append("<title>" + escapeXML(result.getTitle()) + "</title>\n");
    	xml.append("<summary>" + escapeXML(result.getSnippet()) + "</summary>\n");
    	//xml.append("<url>" + escapeXML(result.getLink()) + "</url>\n");
    	xml.append("</result>\n");
    	return xml.toString();
    }
    
    
    public static String escapeXML(String xml){
    	String temp=xml.replaceAll("&", "&amp;");
    	temp=temp.replaceAll("<", "&lt;");
    	temp=temp.replaceAll(">", "&gt;");
    	temp=temp.replaceAll("\"", "&quot;");
    	return temp;
    }
      
}

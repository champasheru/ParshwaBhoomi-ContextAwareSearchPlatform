/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.dataparser;

import java.util.Collection;

import org.cs.parshwabhoomi.server.datastore.SearchResult;

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
            serializedXML.append(sr.getSearchResultXML());
        }
        serializedXML.append("</resultset>");
        System.out.println("Done!!!");
        return serializedXML.toString();
    }
      
}

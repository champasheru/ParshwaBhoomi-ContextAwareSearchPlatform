/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.datastore;

/**
 *
 * @author saurabh
 */
public abstract class SearchResult {
 
    //The type of the result
    public static final String RESULT_VENDOR="RESULT_VENDOR";
    public static final String RESULT_USERPREF_LOCATION="RESULT_USERPREF_LOCATION";
    public static final String RESULT_LOCATION="RESULT_LOCATION";
    public static final String RESULT_GENERAL="RESULT_GENERAL";
    
    //The result type would be one of the 4 types above.
    protected String type;
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type=type;
    }
    
    abstract public String getSearchResultXML();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.dataparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.cs.parshwabhoomi.server.datastore.SearchServiceResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author saurabh
 */
public class SearchResponseParser extends DefaultHandler {

    private ArrayList<SearchServiceResult> resultList=null;
    private SearchServiceResult tempResult;
    private int index=0;
    private String temp;
    
    public SearchResponseParser(){
        
    }

    public ArrayList<SearchServiceResult> parseResponse(InputStream is){
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            System.out.println("Is namespace aware= "+parser.isNamespaceAware()+"\n");
            //Now parse the result
            //The SearchResultHandler will parse the XML,create the relevant
            //domnain objects from it & add them to the array passed to it.
            parser.parse(is,this);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            if(resultList!=null){
                return resultList;
            }else{
                return null;
            }
        }
    }
    
    public void startDocument() throws SAXException{
        System.out.println("The SAXParser has STARTED parsing the document");
        resultList=new ArrayList<SearchServiceResult>();
    }

    public void endDocument()throws SAXException{
        System.out.println("The SAXParser has ENDED parsing the document");
        System.out.println("Found: "+resultList.size()+" results...");
    }

    public void startElement(String uri,String localName,String qName, Attributes atts) throws SAXException{
        if(qName.equalsIgnoreCase("result")){
            //if the xml node with the name "Result" is found,create the
            //new SearchResult domain object
            tempResult=new SearchServiceResult();
        }
    }

    public void endElement(String uri,String localName,String qName) throws SAXException{
        if(qName.equalsIgnoreCase("result")){
            //after the <Result> element is finished parsing,add the corresponding
            //SearchResult object created in the startElement() to the collection.
            resultList.add(tempResult);
            index++;
        }else if(qName.equalsIgnoreCase("title")){
            tempResult.setTitle(temp);
        }else if(qName.toLowerCase().contains("clickurl")){
            tempResult.setUrl(temp);
        }else if(qName.equalsIgnoreCase("abstract")){
            tempResult.setSummary(temp);
        }//Yahoo V1 search service used to have summary element in it's response,replaced in V2 with abstract.
        /*else if(qName.equalsIgnoreCase("summary")){ 
         tempResult.setSummary(temp);
        }*/
        /*else if(qName.equalsIgnoreCase("clickurl")){
            tempResult.setUrl(temp);
        }*/
    }
    
    public void characters(char[] ch,int start,int length) throws SAXException{
        temp=new String(ch, start, length);
    }
    
    
    
    /*** V2 ***/
    public ArrayList<SearchServiceResult> parseJsonV2(String jsonString){
    	System.out.println("[SearchResponseParser] Parsing JSON response from Google Custome Search Engine API...");
        resultList=null;
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONArray results= jsonObject.getJSONArray("items");
            
            if(results.length()>0){
                resultList=new ArrayList<SearchServiceResult>();
            }
            
            for(int i=0;i<results.length();i++){
                JSONObject searchResult= results.getJSONObject(0);
                
                tempResult=new SearchServiceResult();
                tempResult.setTitle(searchResult.getString("title"));
                tempResult.setSummary(searchResult.getString("snippet"));
                tempResult.setUrl(searchResult.getString("link"));
                
                resultList.add(tempResult);
            }
        } catch (JSONException ex) {
            Logger.getLogger(SearchResponseParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultList;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.datastore;

/**
 *
 * @author saurabh
 */
public class CustomSearchResult extends SearchResult{
    private String name; //name of the vendor/business registered for the local context based search
    private String address;
    private String category;//the category into which the business falls
    private int contact;
    //the offerings text or advertisement that gives info about what product/s the vendor sells
    //plus any other info like discounts,service etc.
    private String offerings;
    
    public CustomSearchResult(){
        
    }
    
    public CustomSearchResult(String name,String address,String category,int contact,String offerings) {
        this.name=name;
        this.address=address;
        this.category=category;
        this.contact=contact;
        this.offerings=offerings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferings() {
        return offerings;
    }

    public void setOfferings(String offerings) {
        this.offerings = offerings;
    }
    
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getSearchResultXML(){
        StringBuilder xml= new StringBuilder();
        xml.append("<customresult>\n");
        xml.append("<type>" + Utils.escapeXML(type) + "</type>\n");
        xml.append("<name>" + Utils.escapeXML(name) + "</name>\n");
        xml.append("<address>" + Utils.escapeXML(address) + "</address>\n");
        xml.append("<contact>" + contact + "</contact>\n");
        xml.append("<category>" + Utils.escapeXML(category) + "</category>\n");
        xml.append("<offerings>" + Utils.escapeXML(offerings) + "</offerings>\n");
        xml.append("</customresult>\n");
        return xml.toString();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.domainobjects;

/**
 *
 * @author saurabh
 */
public class BusinessCategory extends DBEntity{
    private String name;
    private String description;
    
    public BusinessCategory(){
        
    }

    public BusinessCategory(int id, String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

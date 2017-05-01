/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datastore;

/**
 *
 * @author saurabh
 */
public class BusinessCategory {
    private int id;
    private String name;
    private String uiDescription;
    
    public BusinessCategory(){
        
    }

    public BusinessCategory(int id, String name, String uiDescription) {
        this.id = id;
        this.name = name;
        this.uiDescription = uiDescription;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUiDescription() {
        return uiDescription;
    }

    public void setUiDescription(String uiDescription) {
        this.uiDescription = uiDescription;
    }
    
}

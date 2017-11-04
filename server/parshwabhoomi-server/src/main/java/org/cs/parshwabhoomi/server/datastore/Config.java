/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.datastore;

import java.util.Collection;
import java.util.HashMap;

import org.cs.parshwabhoomi.server.dao.DBManager;

/**
 *
 * @author saurabh
 */
public class Config {
    
    //Business categories list
    public static String CATEGORY_COMPUTERS="Computers,Electronics,Gadgets";
    public static String CATEGORY_AUTOMOBILES="Automobiles,Cars";
    public static String CATEGORY_FOOD="Food";
    public static String CATEGORY_LIFESTYLE="Lifestyle";
    public static String CATEGORY_TRAVEL="Travel";
    public static String CATEGORY_TRAINING="Training,Education";
    
    /** A HashMap that holds the key-value pairs of the form-
     *  (categoryName,BusinessCategory domain object) that are retrieved from the database.
     */
    private static HashMap<String,BusinessCategory> categories;
    
    static{
        System.out.println("The 1st time Config class is being loaded...");
        DBManager dbManager= DBManager.getDBManager();
        categories=dbManager.getCategories();
    }
    
    public static BusinessCategory getValue(String key){
        return categories.get(key);
    }
    
    public static Collection<BusinessCategory> getCategories(){
        return categories.values();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smartsearch;

import comm.SearchService;
import database.DBManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saurabh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SearchService service;
        try {
            DBManager dbManager=DBManager.getDBManager();
            dbManager.getSearchResultsFor("apple","saurabh",null,null,"Pune");
            service=new SearchService("Yahoo");
            System.out.println("\n********************************\n");
            String resultsXML=service.getSearchResultsXMLFor("apple","saurabh","18.51657820","73.84310780");
            System.out.println("Main: Results ===\n"+resultsXML+"\n====");      
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

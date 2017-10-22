/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Saurabh
 */
public class Utils {
    
    public static String parseJsonForAddress(String jsonString){
        String address="";
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            if(!jsonObject.getString("status").equalsIgnoreCase("OK")){
                return null;
            }
            
            JSONArray results=jsonObject.getJSONArray("results");
            if(results.length()==0){
                return null;
            }
            
            JSONObject address1=results.getJSONObject(1);
            JSONArray addressComponents=address1.getJSONArray("address_components");
            for(int i=0;i<addressComponents.length();i++){
                JSONArray addressTypes=addressComponents.getJSONObject(i).getJSONArray("types");
                for(int j=0;j<addressTypes.length();j++){
                    if(addressTypes.getString(j).equalsIgnoreCase("neighborhood")){
                        System.out.println("[Utils] parseJsonForAddress: neighborhood= "+addressComponents.getJSONObject(i).getString("long_name"));
                        address+=addressComponents.getJSONObject(i).getString("long_name")+",";
                    }else if(addressTypes.getString(j).equalsIgnoreCase("sublocality")){
                        System.out.println("[Utils] parseJsonForAddress: sublocality= "+addressComponents.getJSONObject(i).getString("long_name"));
                        address=address+addressComponents.getJSONObject(i).getString("long_name")+",";
                    }else if(addressTypes.getString(j).equalsIgnoreCase("locality")){
                        address=address+addressComponents.getJSONObject(i).getString("long_name");
                        System.out.println("[Utils] parseJsonForAddress: locality= "+addressComponents.getJSONObject(i).getString("long_name"));
                    }
                }
            }
            System.out.println("[Utils] parseJsonForAddress: address= "+address);
        } catch (JSONException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return address;
    }
    
    public static String escapeXML(String xml){
        String temp=xml.replaceAll("&", "&amp;");
        temp=temp.replaceAll("<", "&lt;");
        temp=temp.replaceAll(">", "&gt;");
        temp=temp.replaceAll("\"", "&quot;");
        return temp;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cs.parshwabhoomi.server.dto.adapter;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.model.Address;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gayatri
 */
public class GoogleGeocodedAddressResponseDTOAdapter {
    
    public static Address parseJsonForAddress(String jsonString){
    	Address address = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if(!jsonObject.getString("status").equalsIgnoreCase("OK")){
                return address;
            }
            
            JSONArray results = jsonObject.getJSONArray("results");
            if(results.length()==0){
                return address;
            }
            
            JSONObject address1 = results.getJSONObject(1);
            address = new Address();
            JSONArray addressComponents = address1.getJSONArray("address_components");
            for(int i= 0; i < addressComponents.length(); i++){
                JSONArray addressTypes = addressComponents.getJSONObject(i).getJSONArray("types");
                for(int j = 0; j < addressTypes.length(); j++){
                    if(addressTypes.getString(j).equalsIgnoreCase("premise")){
                        LogManager.getLogger().info("premise = "+addressComponents.getJSONObject(i).getString("long_name"));
                        address.setPremise(addressComponents.getJSONObject(i).getString("long_name"));
                    }else if(addressTypes.getString(j).equalsIgnoreCase("route")){
                        LogManager.getLogger().info("route = "+addressComponents.getJSONObject(i).getString("long_name"));
                        address.setRouteOrLane(addressComponents.getJSONObject(i).getString("long_name"));
                    }else if(addressTypes.getString(j).equalsIgnoreCase("sublocality")){
                    	LogManager.getLogger().info("sublocality = "+addressComponents.getJSONObject(i).getString("long_name"));
                    	if(address.getSublocality() != null){
                    		address.setSublocality(address.getSublocality()+","+addressComponents.getJSONObject(i).getString("long_name"));
                    	}else{
                    		address.setSublocality(addressComponents.getJSONObject(i).getString("long_name"));
                    	}
                    }else if(addressTypes.getString(j).equalsIgnoreCase("locality")){
                    	LogManager.getLogger().info("locality = "+addressComponents.getJSONObject(i).getString("long_name"));
                        address.setLocality(addressComponents.getJSONObject(i).getString("long_name"));
                    }
                }
            }
            LogManager.getLogger().info("Address built from JSON = "+address);
        } catch (JSONException ex) {
        	LogManager.getLogger().error("Couldn't parse address!", ex);
        }
                
        return address;
    }
    
}

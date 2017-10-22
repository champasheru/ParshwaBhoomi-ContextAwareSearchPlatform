/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datastore;

/**
 *
 * @author saurabh
 */
public class Vendor {
    private String vendorName;
    private String businessCategory;
    private String rootOrLane;
    private String sublocality;
    private String locality;
    private int contactNo;
    private String offerings;

    public Vendor(){
        
    }
    
    public Vendor(String vendorName, String businessCategory, String address, int contactNo, String offerings) {
        this.vendorName = vendorName;
        this.businessCategory = businessCategory;
        this.rootOrLane = rootOrLane;
        this.sublocality=sublocality;
        this.locality=locality;
        this.contactNo = contactNo;
        this.offerings = offerings;
    }

    public String getRootOrLane() {
        return rootOrLane;
    }

    
    public void setRootOrLane(String rootOrLane) {
        this.rootOrLane = rootOrLane;
    }
    
    
    public String getSublocality() {
        return sublocality;
    }
    
    
    public void setSublocality(String sublocality) {
        this.sublocality = sublocality;
    }
    
    
    public String getLocality() {
        return locality;
    }
    
    
    public void setLocality(String locality) {
        this.locality = locality;
    }
    
    
    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getOfferings() {
        return offerings;
    }

    public void setOfferings(String offerings) {
        this.offerings = offerings;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

}

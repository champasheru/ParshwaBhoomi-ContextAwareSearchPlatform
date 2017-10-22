/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datastore;

import java.util.HashMap;

/**
 *
 * @author saurabh
 */
public class User {
    private String name;
    private String password;
    private int age;
    private String address;
    private int contactNo;
    private String educationInfo;
    private String workInfo;
    private HashMap<String,String> userPrefs;

    public User(){
        
    }
    
    public User(String name, int age, String address, int contactNo, String educationInfo, String workInfo, HashMap<String, String> userPrefs) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.contactNo = contactNo;
        this.educationInfo = educationInfo;
        this.workInfo = workInfo;
        this.userPrefs = userPrefs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getEducationInfo() {
        return educationInfo;
    }

    public void setEducationInfo(String educationInfo) {
        this.educationInfo = educationInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public HashMap<String, String> getUserPrefs() {
        return userPrefs;
    }

    public void setUserPrefs(HashMap<String, String> userPrefs) {
        this.userPrefs = userPrefs;
    }

    public String getWorkInfo() {
        return workInfo;
    }

    public void setWork(String workInfo) {
        this.workInfo = workInfo;
    }   
}

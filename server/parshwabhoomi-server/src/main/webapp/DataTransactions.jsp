<%-- 
    Document   : DataTransactions.jsp
    Created on : May 14, 2011, 2:37:44 AM
    Author     : saurabh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@page import="database.DBManager,datastore.Vendor,datastore.User,java.util.HashMap" %>
        <%
            String registrationFor=request.getParameter("registrationFor");

            DBManager dbManager=DBManager.getDBManager();
            if(registrationFor.equalsIgnoreCase("user")){
               User user =new User();
               user.setName(request.getParameter("name"));
               user.setAddress(request.getParameter("address"));
               user.setContactNo(Integer.parseInt(request.getParameter("contact")));
               user.setWork(request.getParameter("work"));
               user.setEducationInfo(request.getParameter("education"));
               
               HashMap<String,String> userPrefs=new HashMap();
               //Get the user preferences
               if(request.getParameter("Travel")!=null && !request.getParameter("Travel").equals("")){
                    userPrefs.put("Travel",request.getParameter("Travel").trim());
               }
               if(request.getParameter("Training")!=null && !request.getParameter("Training").trim().equals("")){
                    userPrefs.put("Training",request.getParameter("Training").trim());
               }
               if(request.getParameter("Food")!=null && !request.getParameter("Food").trim().equals("")){
                    userPrefs.put("Food",request.getParameter("Food").trim());
               }
               if(request.getParameter("Computers")!=null && !request.getParameter("Computers").trim().equals("")){
                    userPrefs.put("Computers",request.getParameter("Computers").trim());
               }
               if(request.getParameter("Automobiles")!=null && !request.getParameter("Automobiles").trim().equals("")){
                    userPrefs.put("Automobiles",request.getParameter("Automobiles").trim());
               }
               if(request.getParameter("Lifestyle")!=null && !request.getParameter("Lifestyle").trim().equals("")){
                    userPrefs.put("Lifestyle",request.getParameter("Lifestyle").trim());
               }
               
               user.setUserPrefs(userPrefs);
               System.out.println("_USER PROFILE UPDATE: Before sending to DBMAnager,prefs= "+userPrefs);

               //Either user is updating the existing profile or registering the new one.
               if(request.getParameter("mode")!=null && request.getParameter("mode").equals("edit")){
                    dbManager.updateUserProfile(user);
                    out.println("<h2 align='center'>Your profile updated successfully.</h2>");
               }else{
                   user.setPassword(request.getParameter("password").trim());
                   dbManager.addUser(user);
                   out.println("<h2 align='center'>Thank You! Your user profile has been registered now.</h2>");
               }
        %>
        <% }else if(registrationFor.equalsIgnoreCase("vendor")){ 
               Vendor vendor=new Vendor();
               vendor.setVendorName(request.getParameter("name"));
               vendor.setRootOrLane(request.getParameter("rootOrLane"));
               vendor.setSublocality(request.getParameter("sublocality"));
               vendor.setLocality(request.getParameter("locality"));
               vendor.setContactNo(Integer.parseInt(request.getParameter("contact")));
               vendor.setBusinessCategory(request.getParameter("category"));
               vendor.setOfferings(request.getParameter("offerings"));
               dbManager.addVendor(vendor);
               out.println("<h2 align='center'>Thank You! Your vendor profile has been registered now.</h2>");
           }
        %>
        <p align="center"><a href="index.jsp">Click here to go Home</a></p> 
    </body>
</html>

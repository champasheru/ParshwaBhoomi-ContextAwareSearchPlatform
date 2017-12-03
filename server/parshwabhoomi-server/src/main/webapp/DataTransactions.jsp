<%-- 
    Document   : DataTransactions.jsp
    Created on : May 14, 2011, 2:37:44 AM
    Author     : saurabh
--%>

<%@page import="org.cs.parshwabhoomi.server.dao.raw.impl.BusinessVendorDaoImpl"%>
<%@page import="org.cs.parshwabhoomi.server.model.UserCredential"%>
<%@page import="org.cs.parshwabhoomi.server.model.BusinessCategory"%>
<%@page import="java.util.EnumMap"%>
<%@page import="org.cs.parshwabhoomi.server.model.ContactInfo"%>
<%@page import="org.cs.parshwabhoomi.server.model.Address"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@page import="org.cs.parshwabhoomi.server.model.BusinessVendor, 
        				org.cs.parshwabhoomi.server.model.EndUser,
        				org.cs.parshwabhoomi.server.AppContext,
        				org.cs.parshwabhoomi.server.dao.raw.impl.UserCredentialDaoImpl,
        				org.cs.parshwabhoomi.server.dao.raw.impl.BusinessVendorDaoImpl,
        				org.cs.parshwabhoomi.server.dao.raw.impl.EndUserDaoImpl,
        				java.util.HashMap" %>
        <%
        	String registrationFor = request.getParameter("registrationFor");

			        	
        	if (registrationFor.equalsIgnoreCase("user")) {
        		EndUser endUser = new EndUser();
        		endUser.setName(request.getParameter("name"));
        		Address address = new Address();
        		address.setLocality(request.getParameter("address"));
        		endUser.setAddress(address);
        		ContactInfo contactInfo = new ContactInfo();
        		contactInfo.setPrimaryMobile(request.getParameter("contact"));
        		endUser.setContactInfo(contactInfo);
        		endUser.setWorkInfo(request.getParameter("work"));
        		endUser.setEducationInfo(request.getParameter("education"));

        		EnumMap<BusinessCategory, String> userPrefs = new EnumMap<>(BusinessCategory.class);
        		//Get the user preferences
        		if (request.getParameter("Travel") != null && !request.getParameter("Travel").equals("")) {
        			userPrefs.put(BusinessCategory.TRAVEL, request.getParameter("Travel").trim());
        		}
        		if (request.getParameter("Training") != null && !request.getParameter("Training").trim().equals("")) {
        			userPrefs.put(BusinessCategory.TRAINING,
        					request.getParameter("Training").trim());
        		}
        		if (request.getParameter("Food") != null && !request.getParameter("Food").trim().equals("")) {
        			userPrefs.put(BusinessCategory.FOOD, request.getParameter("Food").trim());
        		}
        		if (request.getParameter("Computers") != null && !request.getParameter("Computers").trim().equals("")) {
        			userPrefs.put(BusinessCategory.COMPUTERS,
        					request.getParameter("Computers").trim());
        		}
        		if (request.getParameter("Automobiles") != null
        				&& !request.getParameter("Automobiles").trim().equals("")) {
        			userPrefs.put(BusinessCategory.AUTOMOBILES, request.getParameter("Automobiles").trim());
        		}
        		if (request.getParameter("Lifestyle") != null && !request.getParameter("Lifestyle").trim().equals("")) {
        			userPrefs.put(BusinessCategory.LIFESTYLE, request.getParameter("Lifestyle").trim());
        		}

        		endUser.setUserPrefs(userPrefs);
        		System.out.println("_USER PROFILE UPDATE: Before sending to DBMAnager,prefs= " + userPrefs);
				
        		EndUserDaoImpl endUserDaoImpl = (EndUserDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("EndUserDaoImpl");
        		//Either user is updating the existing profile or registering the new one.
        		if (request.getParameter("mode") != null && request.getParameter("mode").equals("edit")) {
        			endUserDaoImpl.updateUserProfile(endUser);
        			out.println("<h2 align='center'>Your profile updated successfully.</h2>");
        		} else {
        			UserCredential userCredential = new UserCredential();
        			endUser.setUserCredential(userCredential);
        			userCredential.setPassword(request.getParameter("password").trim());
        			
        			UserCredentialDaoImpl userCredentialDaoImpl = (UserCredentialDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("UserCredentialDaoImpl", endUserDaoImpl);
        			endUserDaoImpl.beginTransaction();
        			userCredentialDaoImpl.addUserCredential(userCredential);
        			endUserDaoImpl.addUserProfile(endUser);
        			endUserDaoImpl.commitTransaction();
        			userCredentialDaoImpl.close();
        			out.println("<h2 align='center'>Thank You! Your user profile has been registered now.</h2>");
        		}
        		endUserDaoImpl.close();
        %>
        <% }else if(registrationFor.equalsIgnoreCase("vendor")){ 
               BusinessVendor vendor=new BusinessVendor();
               vendor.setName(request.getParameter("name"));
               Address address = new Address();
               address.setRouteOrLane(request.getParameter("rootOrLane"));
               address.setSublocality(request.getParameter("sublocality"));
               address.setLocality(request.getParameter("locality"));
               vendor.setAddress(address);
               ContactInfo contactInfo = new ContactInfo();
               contactInfo.setPrimaryMobile(request.getParameter("contact"));
               vendor.setContactInfo(contactInfo);
               vendor.setBusinessCategory(BusinessCategory.valueOf(request.getParameter("category")));
               vendor.setOfferings(request.getParameter("offerings"));
               
               BusinessVendorDaoImpl businessVendorDaoImpl = (BusinessVendorDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("BusinessVendorDaoImpl");
               businessVendorDaoImpl.add(vendor);
               businessVendorDaoImpl.close();
               out.println("<h2 align='center'>Thank You! Your vendor profile has been registered now.</h2>");
           }
        %>
        <p align="center"><a href="index.jsp">Click here to go Home</a></p> 
    </body>
</html>

<%-- 
    Document   : UserRegistration
    Created on : May 14, 2011, 2:12:24 AM
    Author     : saurabh
--%>

<%@page import="org.cs.parshwabhoomi.server.model.UserCredential"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="java.util.EnumMap"%>
<%@page import="org.cs.parshwabhoomi.server.model.ContactInfo"%>
<%@page import="org.cs.parshwabhoomi.server.model.Address"%>
<%@page import="org.cs.parshwabhoomi.server.AppContext"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Parshwabhoomi: Context Based Search- <%=session.getAttribute("username")%></title>
    </head>
    <body>
        <h2 align="center">Welcome back <%=session.getAttribute("username")%>!</h2>
    </head>
    <body>
        
    <%@ page import="org.cs.parshwabhoomi.server.model.BusinessCategory,
    				java.util.Collection,
    				org.cs.parshwabhoomi.server.model.EndUser,
    				org.cs.parshwabhoomi.server.dao.raw.impl.EndUserDaoImpl,
    				org.cs.parshwabhoomi.server.dao.raw.impl.BusinessCategoryDaoImpl" %>
    
        <div align="center">
        	<!-- post it to self -->
            <form method="post" action="UserProfile.jsp">
                <%
                	BusinessCategoryDaoImpl businessCategoryDaoImpl = (BusinessCategoryDaoImpl) AppContext.getDefaultContext()
                			.getDaoProvider().getDAO("BusinessCategoryDaoImpl");
                	Map<String, BusinessCategory> categories = businessCategoryDaoImpl.getCategories();
                	businessCategoryDaoImpl.close();
					
                	EndUserDaoImpl endUserDaoImpl = (EndUserDaoImpl) AppContext.getDefaultContext().getDaoProvider()
                			.getDAO("EndUserDaoImpl");
                	
                	String username = (String)session.getAttribute("username");
                	String mode = (String)session.getAttribute("mode");
                	//mode is set from Profile.jsp
                	if (mode.equalsIgnoreCase("view")) {
                		LogManager.getLogger().info("Rendering user profile...");
                		session.setAttribute("mode", "edit");
                	} else {
                		//save the updated profile to db.
                		LogManager.getLogger().info("Updating user profile...");
                		
                		EndUser endUser = new EndUser();
                		UserCredential credential = (UserCredential)session.getAttribute("userCred");
                		endUser.setUserCredential(credential);
                		
                		endUser.setName(request.getParameter("name"));
                		
                		Address address = new Address();
                		address.setRouteOrLane(request.getParameter("addressLine1"));
                		address.setSublocality(request.getParameter("addressLine2"));
                		address.setLocality(request.getParameter("city"));
                		address.setState(request.getParameter("state"));
                		address.setPincode(request.getParameter("pincode"));
                		endUser.setAddress(address);
                		
                		ContactInfo contactInfo = new ContactInfo();
                		contactInfo.setPrimaryMobile(request.getParameter("primaryMobile"));
                		contactInfo.setSecondaryMobile(request.getParameter("secondaryMobile"));
                		contactInfo.setEmail(request.getParameter("email"));
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
                		
                		endUserDaoImpl.updateUserProfile(endUser);
                	}
                	
                	EndUser user = endUserDaoImpl.getEndUserDetailedProfile(username);
            		session.setAttribute("userCred", user.getUserCredential());

                	Address address = user.getAddress();
                	String addressLine1 = address.getRouteOrLane() != null ? address.getRouteOrLane() : "";
                	String addressLine2 = address.getSublocality() != null ? address.getSublocality() : "";
                	String state = address.getState() != null ? address.getState() : "";
                	String city = address.getLocality() != null ? address.getLocality() : "";
                	String pincode = address.getPincode() != null ? address.getPincode() : "";
                	out.println("Address : <br/>");
                	out.println("Address Line 1 : <input name='addressLine1' type='text' value='" + addressLine1 + "' >"
                			+ "<br/><br/>");
                	out.println("Address Line 2 : <input name='addressLine2' type='text' value='" + addressLine2 + "' >"
                			+ "<br/><br/>");
                	out.println("City : <input name='city' type='text' value='" + city + "' >" + "<br/><br/>");
                	out.println("State : <input name='state' type='text' value='" + state + "' >" + "<br/><br/>");
                	out.println("Pincode : <input name='pincode' type='text' value='" + pincode + "' >" + "<br/><br/>");

                	ContactInfo contactInfo = user.getContactInfo();
                	String email = contactInfo.getEmail() != null ? contactInfo.getEmail() : "";
                	String primaryMobile = contactInfo.getPrimaryMobile() != null ? contactInfo.getPrimaryMobile() : "";
                	String secondaryMobile = contactInfo.getSecondaryMobile() != null ? contactInfo.getSecondaryMobile() : "";
                	out.println("Contact : <br/>");
                	out.println("Email : <input name='email' type='text' value='" + email + "' >" + "<br/><br/>");
                	out.println("Primary Mobile : <input name='primaryMobile' type='text' value='" + primaryMobile + "' >"
                			+ "<br/><br/>");
                	out.println("Alternate Mobile/Whats App : <input name='secondaryMobile' type='text' value='"
                			+ secondaryMobile + "' >" + "<br/><br/>");

                	String eduInfo = user.getEducationInfo() != null ? user.getEducationInfo() : "";
                	out.println("Education : <input name='education' type='text' value='" + eduInfo + "' >" + "<br/><br/>");
                	String workInfo = user.getWorkInfo() != null ? user.getWorkInfo() : "";
                	out.println("Work/Profession : <input name='work' type='text' value='" + workInfo + "' >" + "<br/><br/>");

                	out.println("Please enter your preferences for the following (use comma as a separator):<br/><br/>");
                	for (Iterator<String> iterator = categories.keySet().iterator(); iterator.hasNext();) {
                		String categoryName = iterator.next();
                		out.print("<b>" + categories.get(categoryName).getDescription() + "</b><br/>");
                		String prefValue = user.getUserPrefs().get(BusinessCategory.valueOf(categoryName));
                		if (prefValue == null) {
                			prefValue = "";
                		}
                		out.println("<textarea name='" + categoryName + "' rows='3' cols='50'>" + prefValue
                				+ "</textarea><br/><br/>");
                	}
                	out.println("<br/><br/>");
                	out.println("<input type='submit' value='Save'>");
                	
                	endUserDaoImpl.close();
                %>
                <br/><br/>
            </form>
        </div>   
    </body>
</html>

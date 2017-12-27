<%@page import="java.util.Date"%>
<%@page import="org.cs.parshwabhoomi.server.utils.DateTimeUtils"%>
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
    <%@ page import="org.cs.parshwabhoomi.server.model.BusinessCategory,
    				java.util.Collection,
    				org.cs.parshwabhoomi.server.model.EndUser,
    				org.cs.parshwabhoomi.server.dao.raw.impl.EndUserDaoImpl,
    				org.cs.parshwabhoomi.server.dao.raw.impl.BusinessCategoryDaoImpl" %>
    				
    	<div style="position: sticky; top:0; width: 100%; background-color: #EFEFEF">
    		<div style="width:50%; margin-left: 25%; margin-right:25%; text-align:center">Parshwabhoomi - Context Based Search</div>
    		<div style="margin-left:70%; right: 4px; margin-right: 0; width:30%; text-align: right"><%=session.getAttribute("username")%> | <a href="Logout.jsp">Logout</a></div>
    	</div>
    	
        <div align="center" style="width: 100%; margin-top: 8px">
        	<!-- post it to self -->
            <form method="post" action="UserProfile.jsp">
                <%
                	String username = (String)session.getAttribute("username");
        			String from = (String)session.getAttribute("from");
        			//mode is set from Profile.jsp
        			String mode = (String)session.getAttribute("mode");
        			LogManager.getLogger().info("mode ="+mode);
            		LogManager.getLogger().info("from ="+from);
            		
                	BusinessCategoryDaoImpl businessCategoryDaoImpl = (BusinessCategoryDaoImpl) AppContext.getDefaultContext()
                			.getDaoProvider().getDAO("BusinessCategoryDaoImpl");
                	Map<String, BusinessCategory> categories = businessCategoryDaoImpl.getCategories();
                	businessCategoryDaoImpl.close();
					
                	EndUserDaoImpl endUserDaoImpl = (EndUserDaoImpl) AppContext.getDefaultContext().getDaoProvider()
                			.getDAO("EndUserDaoImpl");
                	
                	if (mode.equalsIgnoreCase("view")) {
                		LogManager.getLogger().info("Rendering user profile...");
                		session.setAttribute("mode", "edit");
                	} else {
                		//save the updated profile to db.
                		LogManager.getLogger().info("Updating end user profile...");
                		
                		EndUser endUser = (EndUser)session.getAttribute("endUser");
                		
                		endUser.setName(request.getParameter("name"));
                		String dob = request.getParameter("dob");
                		Date date = DateTimeUtils.getDateFromString(dob);
                		endUser.setDateOfBirth(date);
                		
                		Address address = new Address();
                		address.setRouteOrLane(request.getParameter("addressLine1").trim());
                		address.setSublocality(request.getParameter("addressLine2").trim());
                		address.setLocality(request.getParameter("city").trim());
                		address.setState(request.getParameter("state").trim());
                		address.setPincode(request.getParameter("pincode").trim());
                		endUser.setAddress(address);
                		
                		ContactInfo contactInfo = new ContactInfo();
                		contactInfo.setPrimaryMobile(request.getParameter("primaryMobile").trim());
                		contactInfo.setSecondaryMobile(request.getParameter("secondaryMobile").trim());
                		contactInfo.setEmail(request.getParameter("email").trim());
                		endUser.setContactInfo(contactInfo);
                		
                		endUser.setWorkInfo(request.getParameter("work").trim());
                		endUser.setEducationInfo(request.getParameter("education").trim());

                		//EnumMap<BusinessCategory, String> userPrefs = new EnumMap<>(BusinessCategory.class);
                		EnumMap<BusinessCategory, String> userPrefs = endUser.getUserPrefs();
                		//Get the user preferences
                		if (request.getParameter(BusinessCategory.TRAVEL.name()) != null && !request.getParameter(BusinessCategory.TRAVEL.name()).equals("")) {
                			userPrefs.put(BusinessCategory.TRAVEL, request.getParameter(BusinessCategory.TRAVEL.name()).trim());
                		}
                		if (request.getParameter(BusinessCategory.TRAINING.name()) != null && !request.getParameter(BusinessCategory.TRAINING.name()).trim().equals("")) {
                			userPrefs.put(BusinessCategory.TRAINING,request.getParameter(BusinessCategory.TRAINING.name()).trim());
                		}
                		if (request.getParameter(BusinessCategory.FOOD.name()) != null && !request.getParameter(BusinessCategory.FOOD.name()).trim().equals("")) {
                			userPrefs.put(BusinessCategory.FOOD, request.getParameter(BusinessCategory.FOOD.name()).trim());
                		}
                		if (request.getParameter(BusinessCategory.COMPUTERS.name()) != null && !request.getParameter(BusinessCategory.COMPUTERS.name()).trim().equals("")) {
                			userPrefs.put(BusinessCategory.COMPUTERS, request.getParameter(BusinessCategory.COMPUTERS.name()).trim());
                		}
                		if (request.getParameter(BusinessCategory.AUTOMOBILES.name()) != null && !request.getParameter(BusinessCategory.AUTOMOBILES.name()).trim().equals("")) {
                			userPrefs.put(BusinessCategory.AUTOMOBILES, request.getParameter(BusinessCategory.AUTOMOBILES.name()).trim());
                		}
                		if (request.getParameter(BusinessCategory.LIFESTYLE.name()) != null && !request.getParameter(BusinessCategory.LIFESTYLE.name()).trim().equals("")) {
                			userPrefs.put(BusinessCategory.LIFESTYLE, request.getParameter(BusinessCategory.LIFESTYLE.name()).trim());
                		}
						
                		//endUser.setUserPrefs(userPrefs);
                		LogManager.getLogger().info("Prefs set in user profile page: "+endUser.getUserPrefs());
						if(from.equalsIgnoreCase("signup")){
							//toggle
            				from = "login";
            				session.setAttribute("from", from);
            				endUserDaoImpl.addUserProfile(endUser);
                		}else{
                			endUserDaoImpl.updateUserProfile(endUser);
                		}
                	}
                	
                	EndUser user = null;
                	if(from.equalsIgnoreCase("signup")){
                		LogManager.getLogger().info("Signup rendering user profile...");
                		user = new EndUser();
                		UserCredential credential = new UserCredential();
                		credential.setId((Long) session.getAttribute("userId"));
                		credential.setUsername(username);
                		user.setUserCredential(credential);
                		user.setAddress(new Address());
                		user.setContactInfo(new ContactInfo());
                		user.setUserPrefs(new EnumMap<BusinessCategory, String>(BusinessCategory.class));
                	}else{
                		LogManager.getLogger().info("Login rendering user profile retrieved from DB...");
                		user = endUserDaoImpl.getEndUserDetailedProfile(username);	
                	}
                	endUserDaoImpl.close();
                	
            		session.setAttribute("endUser", user);
					
            		String name = user.getName() != null ? user.getName() : "";
            		String dob = user.getDateOfBirth() != null ? DateTimeUtils.getFormattedDate(user.getDateOfBirth()) : "";
            		out.println("Name : <input name='name' type='text' value='" + name + "' >"
                			+ "<br/><br/>");
            		out.println("Birth date (dd/mm/yyyy) : <input name='dob' type='text' value='" + dob + "' >"
                			+ "<br/><br/>");
            		
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
                		out.println("<textarea name='" + categoryName + "' rows='4' cols='50'>" + prefValue
                				+ "</textarea><br/><br/>");
                	}
                	
                	out.println("<input type='submit' value='Save'>");
                %>
                <br/><br/>
            </form>
        </div>   
    </body>
</html>

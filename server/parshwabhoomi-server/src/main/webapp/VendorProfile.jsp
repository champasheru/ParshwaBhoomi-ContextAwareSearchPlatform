<%-- 
    Document   : VendorRegistration
    Created on : May 14, 2011, 2:09:34 AM
    Author     : saurabh
--%>

<%@page import="java.util.List"%>
<%@page import="org.cs.parshwabhoomi.server.core.GoogleMapsService"%>
<%@page import="org.cs.parshwabhoomi.server.model.UserCredential"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.cs.parshwabhoomi.server.model.ContactInfo"%>
<%@page import="org.cs.parshwabhoomi.server.model.Address"%>
<%@page import="org.cs.parshwabhoomi.server.model.BusinessVendor"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@ page import="org.cs.parshwabhoomi.server.model.BusinessCategory,
                				java.util.Collection,
                				org.cs.parshwabhoomi.server.AppContext,
                				org.cs.parshwabhoomi.server.dao.raw.impl.BusinessCategoryDaoImpl" %>
<%@page import="org.cs.parshwabhoomi.server.dao.raw.impl.*"%>
                				
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Parshwabhoomi: Context Based Search- <%=session.getAttribute("username")%></title>
    </head>
    
    <body>
        <div style="position: sticky; top:0; width: 100%; background-color: #EFEFEF">
    		<div style="width:50%; margin-left: 25%; margin-right:25%; text-align:center">Parshwabhoomi - Context Based Search</div>
    		<div style="margin-left:70%; right: 4px; margin-right: 0; width:30%; text-align: right"><%=session.getAttribute("username")%> | <a href="Logout.jsp">Logout</a></div>
    	</div>
    	
        <div align="center" style="width: 100%; margin-top: 8px">
        	<!-- post it to self -->
            <form method="post" action="VendorProfile.jsp">
            	<%
            		BusinessCategoryDaoImpl businessCategoryDaoImpl = (BusinessCategoryDaoImpl) AppContext.getDefaultContext()
            				.getDaoProvider().getDAO("BusinessCategoryDaoImpl");
            		Map<String, BusinessCategory> categories = businessCategoryDaoImpl.getCategories();
            		String bizCategory = request.getParameter("category");
            		BusinessCategory category = businessCategoryDaoImpl.getByName(bizCategory);
            		businessCategoryDaoImpl.close();

            		String username = (String) session.getAttribute("username");
            		String from = (String) session.getAttribute("from");

            		BusinessVendorDaoImpl businessVendorDaoImpl = (BusinessVendorDaoImpl) AppContext.getDefaultContext()
            				.getDaoProvider().getDAO("BusinessVendorDaoImpl");

            		String mode = (String) session.getAttribute("mode");
            		LogManager.getLogger().info("mode ="+mode);
            		LogManager.getLogger().info("from ="+from);
            		//mode is set from Profile.jsp
            		if (mode.equalsIgnoreCase("view")) {
            			LogManager.getLogger().info("Rendering vendor profile...");
            			session.setAttribute("mode", "edit");
            		} else {
            			//save the updated profile to db.
            			LogManager.getLogger().info("Updating vendor profile...");

            			BusinessVendor vendor = (BusinessVendor) session.getAttribute("vendor");
            			
            			vendor.setName(request.getParameter("name").trim());

            			vendor.setBusinessCategory(category);
            			vendor.setOfferings(request.getParameter("offerings").trim());
            			vendor.setTagLine(request.getParameter("tagline").trim());

            			Address address = new Address();
            			address.setRouteOrLane(request.getParameter("routeOrLane").trim());
            			address.setSublocality(request.getParameter("sublocality").trim());
            			address.setLocality(request.getParameter("locality").trim());
            			address.setState(request.getParameter("state").trim());
            			address.setPincode(request.getParameter("pincode").trim());
            			vendor.setAddress(address);

            			ContactInfo contactInfo = new ContactInfo();
            			contactInfo.setPrimaryMobile(request.getParameter("primaryMobile").trim());
            			contactInfo.setSecondaryMobile(request.getParameter("secondaryMobile").trim());
            			contactInfo.setEmail(request.getParameter("email").trim());
            			contactInfo.setLandline(request.getParameter("landline").trim());
            			vendor.setContactInfo(contactInfo);
						
            			if(address != null){
            				LogManager.getLogger().info("Valid address; looking up Geocoded address...");
            				GoogleMapsService mapsService = new GoogleMapsService();
            				List<Float> latLong = mapsService.getGeocodedAddress(address.getFormattedAddress());
            				if(!latLong.isEmpty()){
            					address.setLatitude(String.valueOf(latLong.get(0)));
            					address.setLongitude(String.valueOf(latLong.get(1)));
            				}
            			}
            			
            			if (from.equalsIgnoreCase("signup")) {
            				businessVendorDaoImpl.add(vendor);
            				//toggle
            				from = "login";
            				session.setAttribute("from", from);
            			} else {
            				businessVendorDaoImpl.update(vendor);
            			}
            		}
					
            		BusinessVendor vendor = null;
            		if (from.equalsIgnoreCase("signup")) {
            			LogManager.getLogger().info("Signup rendering vendor profile...");
            			vendor = new BusinessVendor();
            			UserCredential credential = new UserCredential();
            			credential.setId((Long) session.getAttribute("userId"));
            			credential.setUsername(username);
            			vendor.setUserCredential(credential);
            			vendor.setAddress(new Address());
            			vendor.setContactInfo(new ContactInfo());
            		} else {
            			LogManager.getLogger().info("Login rendering vendor profile retrieved from DB...");
            			vendor = businessVendorDaoImpl.getByUsername(username.trim());
            		}
            		businessVendorDaoImpl.close();

            		session.setAttribute("vendor", vendor);

            		String name = vendor.getName() != null ? vendor.getName() : "";
            		String tagline = vendor.getTagLine() != null ? vendor.getTagLine() : "";
            		String offerings = vendor.getOfferings() != null ? vendor.getOfferings() : "";
            		String bc = vendor.getBusinessCategory() != null ? vendor.getBusinessCategory().name() : null;

            		Address address = vendor.getAddress();
            		String addressLine1 = address.getRouteOrLane() != null ? address.getRouteOrLane() : "";
            		String addressLine2 = address.getSublocality() != null ? address.getSublocality() : "";
            		String state = address.getState() != null ? address.getState() : "";
            		String city = address.getLocality() != null ? address.getLocality() : "";
            		String pincode = address.getPincode() != null ? address.getPincode() : "";

            		ContactInfo contactInfo = vendor.getContactInfo();
            		String email = contactInfo.getEmail() != null ? contactInfo.getEmail() : "";
            		String primaryMobile = contactInfo.getPrimaryMobile() != null ? contactInfo.getPrimaryMobile() : "";
            		String secondaryMobile = contactInfo.getSecondaryMobile() != null ? contactInfo.getSecondaryMobile() : "";
            		String landline = contactInfo.getLandline() != null ? contactInfo.getLandline() : "";
            	%>
            	
                Name: <input name="name" type="text" value="<%=name%>"><br/><br/>
                
                Business category:<br/>
                <select name="category" >
                <%
                	for (Iterator<String> iterator = categories.keySet().iterator(); iterator.hasNext();) {
                		String categoryName = iterator.next();
                		if(bc != null && bc.equalsIgnoreCase(categoryName)){
                %>
                    <option id="<%=categories.get(categoryName).getId()%>" value="<%=categoryName%>" selected> <%=categories.get(categoryName).getDescription()%> </option>
                <% 
                		}else{
                %>
                	<option id="<%=categoryName%>" value="<%=categoryName%>"> <%=categories.get(categoryName).getDescription()%> </option>
                <%
                		}
                    }
                %>
                </select><br/><br/>
                
                Tag-line: <br/>
                <textarea name="tagline" rows="2" cols="50"><%=tagline%></textarea><br/><br/>
                Offerings:<br/>
                <textarea name="offerings" rows="4" cols="50"><%=offerings%></textarea><br/><br/>
                
                Address:<br/> 
                Address Line 1: <input name="routeOrLane" type="text" value="<%=addressLine1%>"><br/><br/>
                Address Line 2: <input name="sublocality" type="text" value="<%=addressLine2%>"><br/><br/>
                City: <input name="locality" type="text" value="<%=city%>"><br/><br/>
                State: <input name="state" type="text" value="<%=state%>"><br/><br/>
                Pincode: <input name="pincode" type="text" value="<%=pincode%>"><br/><br/>
                
                Contact information :<br/> 
                Email: <input name="email" type="text" value="<%=email%>"><br/><br/>
                Primary Mobile: <input name="primaryMobile" type="text" value="<%=primaryMobile%>"><br/><br/>
                Alternate Mobile/Whats App: <input name="secondaryMobile" type="text" value="<%=secondaryMobile%>"><br/><br/>
                Landline: <input name="landline" type="text" value="<%=landline%>"><br/><br/>
                
                <input type="submit" value="Save">
            </form>
        </div>   
    </body>
</html>

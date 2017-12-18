<%-- 
    Document   : VendorRegistration
    Created on : May 14, 2011, 2:09:34 AM
    Author     : saurabh
--%>

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
        <h2 align="center">Welcome back <%=session.getAttribute("username")%>!</h2>
        <div align="center">
            <form method="post" action="DataTransactions.jsp">
            	<input id="hmode" type="hidden" name="mode" value="view">
            	<%
            		String username = (String) session.getAttribute("username");
            		BusinessVendorDaoImpl businessVendorDaoImpl = (BusinessVendorDaoImpl) AppContext.getDefaultContext()
            				.getDaoProvider().getDAO("BusinessVendorDaoImpl");
            		BusinessVendor vendor = businessVendorDaoImpl.getByUsername(username.trim());
            		businessVendorDaoImpl.close();
            		
            		String name = vendor.getName() != null ? vendor.getName() : "";
            		String tagline = vendor.getTagLine() != null ? vendor.getTagLine() : "";
            		String offerings = vendor.getOfferings() != null ? vendor.getOfferings() : "";
            		
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
            		
            		String bc = vendor.getBusinessCategory() != null ? vendor.getBusinessCategory().name() : null;
            	%>
                Name: <input name="name" type="text" value="<%=name%>"><br/><br/>
                
                Business category:<br/>
                <select name="category" >
                <%
                	BusinessCategoryDaoImpl businessCategoryDaoImpl = (BusinessCategoryDaoImpl) AppContext.getDefaultContext().getDaoProvider().getDAO("BusinessCategoryDaoImpl");
                	Map<String, BusinessCategory> categories = businessCategoryDaoImpl.getCategories();
                	businessCategoryDaoImpl.close();
                	for (Iterator<String> iterator = categories.keySet().iterator(); iterator.hasNext();) {
                		String categoryName = iterator.next();
                		if(bc.equalsIgnoreCase(categoryName)){
                %>
                    <option id="<%=categoryName%>" value="<%=categoryName%>" selected> <%=categories.get(categoryName).getDescription()%> </option>
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
                <textarea name="tagline" rows="1" cols="50"><%=tagline%></textarea><br/><br/>
                Offerings:<br/>
                <textarea name="offerings" rows="3" cols="50"><%=offerings%></textarea><br/><br/>
                
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

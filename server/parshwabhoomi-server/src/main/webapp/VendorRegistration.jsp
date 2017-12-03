<%-- 
    Document   : VendorRegistration
    Created on : May 14, 2011, 2:09:34 AM
    Author     : saurabh
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Context Based Search- Vendor Registration Form</title>
    </head>
    <body>
        <h2 align="center">Register your vendor profile</h2>
        <div align="center">
            <form method="post" action="DataTransactions.jsp">
                Name : <input name="name" type="text"><br/><br/>
                
                Address<br/> 
                Root/Lane: <input name="rootOrLane" type="text"><br/><br/>
                Sublocality/Suburb: <input name="sublocality" type="text"><br/><br/>
                City: <input name="locality" type="text"><br/><br/>
                
                Contact : <input name="contact" type="text"><br/><br/>
                
                Please select business category:<br/>
                <select name="category" >
                <%@ page import="org.cs.parshwabhoomi.server.model.BusinessCategory,
                				java.util.Collection,
                				org.cs.parshwabhoomi.server.AppContext,
                				org.cs.parshwabhoomi.server.dao.raw.impl.BusinessCategoryDaoImpl" %>
                <%
                	BusinessCategoryDaoImpl businessCategoryDaoImpl = (BusinessCategoryDaoImpl) AppContext.getDefaultContext().getDaoProvider().getDAO("BusinessCategoryDaoImpl");
                	Map<String, BusinessCategory> categories = businessCategoryDaoImpl.getCategories();
                	businessCategoryDaoImpl.close();
                	for (Iterator<String> iterator = categories.keySet().iterator(); iterator.hasNext();) {
                		String categoryName = iterator.next();
                %>
                    <option id="<%=categoryName%>" value="<%=categoryName%>"><%=categories.get(categoryName).getDescription()%></option>
                <% 
                    }
                %>
                </select><br/><br/>
                
                Advertisement or your business offerings :<br/>
                <textarea name="offerings" rows="3" cols="50"></textarea><br/><br/>
                <input type="hidden" name="registrationFor" value="vendor">
                <input type="submit" value="Register">
            </form>
        </div>   
    </body>
</html>

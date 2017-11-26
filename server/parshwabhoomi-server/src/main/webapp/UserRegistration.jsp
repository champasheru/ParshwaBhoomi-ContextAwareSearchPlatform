<%-- 
    Document   : UserRegistration
    Created on : May 14, 2011, 2:12:24 AM
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
        <%
            if(session.getAttribute("username")!=null && session.getAttribute("from")!=null){
        %>
        <title>Context Based Search- Update your profile</title>
    </head>
    <body>
        <h2 align="center">Update your profile</h2>
        <%
            }else{
        %>
        <title>Context Based Search- User Registration Form</title>
    </head>
    <body>
        <h2 align="center">Register your user profile</h2>
        <%
            }
        %>
    <%@ page import="org.cs.parshwabhoomi.server.domainobjects.BusinessCategory,
    				java.util.Collection,
    				org.cs.parshwabhoomi.server.domainobjects.EndUser,
    				org.cs.parshwabhoomi.server.dao.DBManager" %>
    
        <div align="center">
            <form method="post" action="DataTransactions.jsp">
                <input type="hidden" name="registrationFor" value="user">
                <%
                	DBManager dbManager=DBManager.getDBManager();
                
                    if(session.getAttribute("username")!=null && session.getAttribute("from")!=null){
                        String username=(String)session.getAttribute("username");
                        if(((String)session.getAttribute("from")).equals("portal")){
                            session.invalidate();
                        }
                        
                        EndUser user=dbManager.getEndUserDetailedProfile(username);

                        System.out.println("userPrefs= "+user.getUserPrefs());
                        out.println("Name : "+username+"<br/><br/>");
                        out.println("<input name='name' type='hidden' value='"+username+"'>");
                        out.println("<input name='mode' type='hidden' value='edit'>");
                        out.println("Address : <input name='address' type='text' value='"+user.getAddress()+"' >"+"<br/><br/>");
                        out.println("Contact : <input name='contact' type='text' value='"+user.getContactInfo().getPrimaryMobile()+"' >"+"<br/><br/>");
                        out.println("Education : <input name='education' type='text' value='"+user.getEducationInfo()+"' >"+"<br/><br/>");
                        out.println("Work/Profession : <input name='work' type='text' value='"+user.getWorkInfo()+"' >"+"<br/><br/>");

                        out.println("Please enter your preferences for the following:<br/>");
                        out.println("Please seperate your preferenceces with comma)<br/><br/>");

                        Map<String, BusinessCategory> categories = dbManager.getCategories();
                        for(Iterator<String> iterator = categories.keySet().iterator(); iterator.hasNext();){
                        	String categoryName = iterator.next();
                            out.print("<b>"+categories.get(categoryName).getDescription()+"</b><br/>");
                            out.println("<textarea name='"+categoryName+"' rows='3' cols='50'>"+user.getUserPrefs().get(categoryName)+"</textarea><br/><br/>");
                        }
                        out.println("<br/><br/>");
                        out.println("<input type='submit' value='Update Profile'>");
                    }else{
                        out.println("Name : <input name='name' type='text'><br/><br/>");
                        out.println("Password : <input name='password' type='password'><br/><br/>");
                        out.println("Address : <input name='address' type='text'><br/><br/>");
                        out.println("Contact : <input name='contact' type='text'>"+"<br/><br/>");
                        out.println("Education : <input name='education' type='text'>"+"<br/><br/>");
                        out.println("Work/Profession : <input name='work' type='text'>"+"<br/><br/>");

                        out.println("Please enter your preferences for the following:<br/>");
                        out.println("Please seperate your preferenceces with comma)<br/><br/>");

                        Map<String, BusinessCategory> categories = dbManager.getCategories();
                        for(Iterator<String> iterator = categories.keySet().iterator(); iterator.hasNext();){
                        	String categoryName = iterator.next();
                        	out.print("<b>"+categories.get(categoryName).getDescription()+"</b><br/>");
                            out.println("<textarea name='"+categoryName+"' rows='3' cols='50'></textarea><br/><br/>");
                        }
                        out.println("<br/><br/>");
                        out.println("<input type='submit' value='Register'>");
                    }
                %>
                <br/><br/>
            </form>
        </div>   
    </body>
</html>

<%--
    Document   : Login
    Created on : May 14, 2011, 2:12:24 AM
    Author     : saurabh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Context Based Search- User Login</title>
    </head>
    <body>
        <h2 align="center">Context Based Search- User Login</h2>
        <%
            String username="";
            String password="";
            if(session.getAttribute("isUserAuthenticated")!=null){
                username=(String)session.getAttribute("username");
                password=(String)session.getAttribute("password");
                //May be we need to invalidate the session here.
        %>
        <h3 align="center">Invalid username and/or password. Please try again</h3>
        <%
            }
        %>
        <div align="center">
            <form method="post" action="Login">
                Username : <input name="username" type="text" value="<%=username%>"><br/><br/>
                Password : <input name="password" type="password" value="<%=password%>"><br/><br/>
                <input type="hidden" name="from" value="portal">
                <input type="submit" value="Sign In">
            </form>
        </div>
    </body>
</html>

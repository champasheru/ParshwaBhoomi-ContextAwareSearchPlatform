<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="org.cs.parshwabhoomi.server.model.BusinessVendor"%>
<%@page import="org.cs.parshwabhoomi.server.AppContext"%>
<%@page import="org.cs.parshwabhoomi.server.dao.raw.impl.BusinessVendorDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	long vendorID = Long.parseLong(request.getParameter("vendorId"));
	BusinessVendorDaoImpl businessVendorDaoImpl = (BusinessVendorDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("BusinessVendorDaoImpl");
	BusinessVendor vendor = businessVendorDaoImpl.getById(vendorID);
	businessVendorDaoImpl.close();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=vendor.getName()+"-"+vendor.getTagLine() %></title>
</head>
<body>
	<div style="width: 100%;  text-align: center;">
		<h4><%=vendor.getName()%></h4>
		<h4><%=vendor.getTagLine()%></h4>
		<p><%=vendor.getOfferings()%></p>
		<p>
			<b>Address:</b><br/>
			<%=vendor.getAddress().getFormattedAddress()%><br/><br/>
			<b>Contact us:</b><br/>
			Mobile:<%=vendor.getContactInfo().getPrimaryMobile()%><br/>
			WhatsApp:<%=vendor.getContactInfo().getSecondaryMobile()%><br/>
			Phone:<%=vendor.getContactInfo().getLandline()%><br/>
			Email:<%=vendor.getContactInfo().getEmail()%><br/><br/>
		</p>
	</div>
</body>
</html>
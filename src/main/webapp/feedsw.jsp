<%@page import="com.lstoars.gifplus.sevlet.IncrShareCountServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String value = request.getParameter("value");
	IncrShareCountServlet.openFeed = Boolean.valueOf(value);
	out.println(IncrShareCountServlet.openFeed);
%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
  String url = (request.getAttribute("url")==null)?null:((String)request.getAttribute("url"));
  if(url==null)
     out.print("error:no url to go!");
  else{
     url = WebConstant.doContextUrl(url);
     //response.sendRedirect(url);
%>
 <jsp:forward page="<%=url %>"/>
<% } %>

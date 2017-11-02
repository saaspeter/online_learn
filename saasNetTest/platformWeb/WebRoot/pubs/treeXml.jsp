<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ page import="platformWeb.base.KeyInMemoryConstant" %>
<% 
    response.setContentType("text/xml"); //set the formmat the xml
    if(request.getAttribute(KeyInMemoryConstant.treeXmlKey)!=null)
       out.println((String)request.getAttribute(KeyInMemoryConstant.treeXmlKey)); 
%>
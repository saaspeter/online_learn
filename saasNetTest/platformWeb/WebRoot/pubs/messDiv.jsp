<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="platformWeb.base.KeyInMemoryConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

	  
 <% String disMessKey = request.getParameter(KeyInMemoryConstant.DisMessKey)==null?null:(request.getParameter(KeyInMemoryConstant.DisMessKey));
    String disMessArg0Key = request.getParameter(KeyInMemoryConstant.DisMessArg0Key)==null?null:(request.getParameter(KeyInMemoryConstant.DisMessArg0Key));
    String bundle = request.getParameter("bundle")==null?null:(request.getParameter("bundle"));
    // 查看request中的值
    if(disMessKey==null){
       disMessKey = request.getAttribute(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessKey));
    }
    if(disMessArg0Key==null){
       disMessArg0Key = request.getAttribute(KeyInMemoryConstant.DisMessArg0Key)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessArg0Key));
    }
    if(bundle==null){
       bundle = request.getAttribute("bundle")==null?null:((String)request.getAttribute("bundle"));
    }
    // 显示信息
    if(disMessKey!=null&&disMessKey.trim().length()>0){
      if(disMessArg0Key==null||disMessArg0Key.trim().length()<1){
         if(bundle==null||bundle.trim().length()<1){
  %>
    <bean:message key="<%=disMessKey %>"/>
 <% }else{
  %>
    <bean:message key="<%=disMessKey %>" bundle="<%=bundle %>"/>
 <%}}else if(bundle==null||bundle.trim().length()<1){ %>
    <bean:message key="<%=disMessKey %>" arg0="<%=disMessArg0Key %>"/>
 <%}else{ %>
    <bean:message key="<%=disMessKey %>" bundle="<%=bundle %>" arg0="<%=disMessArg0Key %>"/>
 <%}} %>
     
 <% if(bundle==null||bundle.trim().length()<1){  %>
    <html:messages id="messStr" message="true">
        <logic:present name="messStr">
           <li><bean:write name="messStr"/></li>
        </logic:present>
    </html:messages>
 <% }else{ %> 
	<html:messages id="messStr" message="true" bundle="<%=bundle %>">
	    <logic:present name="messStr">
           <li><bean:write name="messStr"/></li>
        </logic:present>
    </html:messages>
 <%}%>
 
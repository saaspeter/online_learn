<%@ page import="netTestWeb.base.KeyInMemoryConstant" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	  
 <% String disMessKey = request.getParameter(KeyInMemoryConstant.DisMessKey)==null?null:(request.getParameter(KeyInMemoryConstant.DisMessKey));
    String disMessArg0Key = request.getParameter(KeyInMemoryConstant.DisMessArg0Key)==null?null:(request.getParameter(KeyInMemoryConstant.DisMessArg0Key));
    String bundle = (request.getParameter("bundle")==null)?null:(request.getParameter("bundle"));
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
 <% }else{ %>
    <bean:message key="<%=disMessKey %>" bundle="<%=bundle %>"/>
 <%}}else if(bundle==null||bundle.trim().length()<1){ %>
    <bean:message key="<%=disMessKey %>" arg0="<%=disMessArg0Key %>"/>
 <%}else{ %>
    <bean:message key="<%=disMessKey %>" bundle="<%=bundle %>" arg0="<%=disMessArg0Key %>"/>
 <%}} %>
     <html:messages id="messStr" message="true">
        <li><bean:write name="messStr"/></li>
    </html:messages>
    <html:errors bundle="BizKey"/>
    
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTestWeb.base.KeyInMemoryConstant,netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="jsSuffix" name="sysconstitemForm" property="jsSuffix" type="java.lang.String"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>子选项编辑</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/sysMag/sysconstitem.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstitemForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstitemForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.itemid" value="<bean:write name="sysconstitemForm" property="vo.itemid"/>">
	 <input type="hidden" name="vo.sysconstcode" value="<bean:write name="sysconstitemForm" property="vo.sysconstcode"/>">
	  <bean:define id="editType" name="sysconstitemForm" property="editType" type="Integer"></bean:define>
	 <%boolean show=false;if(editType!=null&&(editType.intValue()==WebConstant.editType_edit)) show=true;%>
	
	  <div id="fieldsTitleDiv"><bean:write name="sysconstitemForm" property="vo.labelname"/></div>
	  
	  <div id="displayMessDiv">
	      <% String disMessKey = request.getParameter(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getParameter(KeyInMemoryConstant.DisMessKey));
	         if(disMessKey!=null&&disMessKey.trim().length()>0){
	       %>
	         <bean:message key="<%=disMessKey %>" />
	      <% } %>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">所属常量:</div>
			   <div class="field"> 
			     <bean:write name="sysconstitemForm" property="constname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">子选项值:</div>
			   <div class="field"> 
			     <input type="text" name="vo.value" value="<bean:write name="sysconstitemForm" property="vo.value"/>" <%if(!show) out.print("readonly=\"readonly\"");%> usage="notempty,max-length:16" fie="子选项值"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">排列顺序:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orderno" value="<bean:write name="sysconstitemForm" property="vo.orderno"/>" <%if(!show) out.print("readonly=\"readonly\"");%> usage="int+" fie="排列顺序"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">参数1:</div>
			   <div class="field"> 
			     <input type="text" name="vo.param1" value="<bean:write name="sysconstitemForm" property="vo.param1"/>" <%if(!show) out.print("readonly=\"readonly\"");%> usage="max-length:8" fie="参数1"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">参数2:</div>
			   <div class="field"> 
			     <input type="text" name="vo.param2" value="<bean:write name="sysconstitemForm" property="vo.param2"/>" <%if(!show) out.print("readonly=\"readonly\"");%> usage="max-length:8" fie="参数2"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
	  	 <%if(show){ %>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
		 <%} %>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div style="height:20px;width100%;background:#eeeeee">子选项不同语言国家设置：</div>
	  <div style="width:100%;height:450px;">
	      <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>/sysMag/sysconstitemval.do?method=list&queryVO.itemid=<bean:write name="sysconstitemForm" property="vo.itemid"/>" scrolling="auto" frameborder="1"></iframe>
      </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

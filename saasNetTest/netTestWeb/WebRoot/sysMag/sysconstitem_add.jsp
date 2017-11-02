<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.KeyInMemoryConstant,netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="jsSuffix" name="sysconstitemForm" property="jsSuffix" type="java.lang.String"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>新增子选项</title>

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
	 <input type="hidden" name="vo.sysconstcode" value="<bean:write name="sysconstitemForm" property="vo.sysconstcode"/>">
	  <div style="height:10px;"></div>
	  <div id="fieldsTitleDiv">新增子选项</div>
	  
	  <div id="displayMessDiv">
	      <% String disMessKey = request.getAttribute(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessKey));
	         if(disMessKey!=null&&disMessKey.trim().length()>0){
	       %>
	         <li><bean:message key="<%=disMessKey %>" /></li>
	      <% } %>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
            <li>
			   <div class="fieldLabel">所属常量：</div>
			   <div class="field"> 
			     <bean:write name="sysconstitemForm" property="constname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
              
            <li>
			   <div class="fieldLabel">子选项名:</div>
			   <div class="field"> 
			     <input type="text" name="vo.labelname" style="width:250px;" value="<bean:write name="sysconstitemForm" property="vo.labelname"/>" usage="notempty,max-length:60" fie="子选项名"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">语言选择:</div>
			   <div class="field"> 
			      <html:select name="sysconstitemForm" property="vo.localeid" style="width:156px;">
					<html:optionsCollection name="sysconstitemForm" property="localesList"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
              		

		    <li>
			   <div class="fieldLabel">子选项值：</div>
			   <div class="field"> 
			     <input type="text" name="vo.value" value="<bean:write name="sysconstitemForm" property="vo.value"/>" usage="notempty,max-length:16" fie="子选项值"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">排列顺序:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orderno" value="<bean:write name="sysconstitemForm" property="vo.orderno"/>" usage="int+" fie="排列顺序"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">参数1:</div>
			   <div class="field"> 
			     <input type="text" name="vo.param1" value="<bean:write name="sysconstitemForm" property="vo.param1"/>" usage="max-length:8" fie="参数1"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">参数2:</div>
			   <div class="field"> 
			     <input type="text" name="vo.param2" value="<bean:write name="sysconstitemForm" property="vo.param2"/>" usage="max-length:8" fie="参数2"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li>
			<html:reset><bean:message key="netTest.commonpage.reset"/></html:reset>
			</li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>

	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

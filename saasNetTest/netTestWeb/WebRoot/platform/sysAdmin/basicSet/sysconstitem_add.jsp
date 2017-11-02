<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>新增子选项</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/edit.css">
    <script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/basicset/sysconstitem.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstitemForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstitemForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.sysconstcode" value="<bean:write name="sysconstitemForm" property="vo.sysconstcode"/>">
	  <div style="height:10px;"></div>
	  <div class="fieldsTitleDiv">新增子选项</div>
	  
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
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
			     <input type="text" name="vo.labelname" style="width:250px;" value="<bean:write name="sysconstitemForm" property="vo.labelname"/>" exp="^.{1,60}$" tip="子选项名，不能为空且不超过60个字!" fie="子选项名"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">语言选择:</div>
			   <div class="field"> 
			      <html:select name="sysconstitemForm" property="vo.localeid" style="width:250px;">
					 <html:optionsSaas localeListSet="true"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
              		

		    <li>
			   <div class="fieldLabel">子选项值：</div>
			   <div class="field"> 
			     <input type="text" name="vo.value" style="width:250px;" value="<bean:write name="sysconstitemForm" property="vo.value"/>" exp="^\S{1,16}$" tip="子选项值，不能为空且不超过16个字符!" fie="子选项值"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">排列顺序:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orderno" style="width:250px;" value="<bean:write name="sysconstitemForm" property="vo.orderno"/>" usage="int+" fie="排列顺序"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">参数1:</div>
			   <div class="field"> 
			     <input type="text" name="vo.param1" style="width:250px;" value="<bean:write name="sysconstitemForm" property="vo.param1"/>" exp="^\S{0,8}$" tip="参数1，不超过8个字符!"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">参数2:</div>
			   <div class="field"> 
			     <input type="text" name="vo.param2" style="width:250px;" value="<bean:write name="sysconstitemForm" property="vo.param2"/>" exp="^\S{0,8}$" tip="参数2，不超过8个字符!"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="bigButton" onclick="submitForm('editForm'); return  false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" class="bigButton" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>

	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

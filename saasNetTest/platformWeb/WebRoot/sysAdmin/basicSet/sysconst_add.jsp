<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.KeyInMemoryConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>系统常量编辑</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script language=JavaScript src="../../styles/script/pageAction.js"></script>
    <script type="text/javascript">
      <!--
      function setDisable(selObj){ //v3.0
	     itemValue = selObj.options[selObj.selectedIndex].value;
         if(itemValue!=null&&itemValue=="1"){
            document.getElementById("valuewayId").disabled="disabled";
         }else{
            document.getElementById("valuewayId").disabled="";
         }
	  }
	  //-->
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/basicset/sysconst.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstForm" property="backUrl"/>"/>
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstForm" property="backUrlEncode"/>"/>
	 <input type="hidden" name="vo.sysconstid" value="<bean:write name="sysconstForm" property="vo.sysconstid"/>"/>
	  <div style="height:25px"></div>
	  <div id="fieldsTitleDiv">新增系统常量</div>
	  <div style="height:20px"></div>
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
	     
	        <li>
			   <div class="fieldLabel">常量名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="sysconstForm" property="vo.name"/>" exp="^\S{1,20}$" tip="常量名称，不能为空且不超过20个字!" fie="常量名称" style="width:300px;"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">常量编码:</div>
			   <div class="field"> 
			     <input type="text" name="vo.sysconstcode" value="<bean:write name="sysconstForm" property="vo.sysconstcode"/>" exp="^\S{1,64}$" tip="常量编码，不能为空且不超过32个字符!" fie="常量编码" style="width:300px;"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
	     
		    <li>
			   <div class="fieldLabel">所属系统:</div>
			   <div class="field"> 
			       <html:select name="sysconstForm" property="vo.syscode" style="width:300px;">
		               <html:optionsCollection name="sysconstForm" property="sysList"/>
		           </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">取值方式:</div>
			   <div class="field"> 
                   <html:select name="sysconstForm" property="vo.getvalueway" onchange="setDisable(this)" style="width:300px;">
	                   <html:optionsSaas consCode="Const.Common.getValueWay"/>
		           </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">取值:</div>
			   <div class="field"> 
			     <input id="valuewayId" type="text" name="vo.valueway" value="<bean:write name="sysconstForm" property="vo.valueway"/>" exp="^\S{0,64}$" tip="取值，不能超过64个字符!" fie="取值" disabled="disabled" style="width:300px;"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">语言选择:</div>
			   <div class="field"> 
			      <html:select name="sysconstForm" property="vo.localeid" style="width:300px;">
					<html:optionsSaas localeListSet="true"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div style="height:50px"></div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>

	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

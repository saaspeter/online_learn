<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="quesdifficultForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="quesdifficultForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>难度设置</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/wareques/saveQuesdifficult.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="quesdifficultForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="quesdifficultForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.difficultid" value="<bean:write name="quesdifficultForm" property="vo.difficultid"/>">
	 <input type="hidden" name="vo.shopid" value="<bean:write name="quesdifficultForm" property="vo.shopid"/>"/>
	
	  <div id="fieldsTitleDiv">编辑难度</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">难度名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.difficultname" value="<bean:write name="quesdifficultForm" property="vo.difficultname"/>" usage="notempty,max-length:32" fie="难度名称" style="width:200px"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">难度系数:</div>
			   <div class="field"> 
			     <input type="text" name="vo.difficultvalue" value="<bean:write name="quesdifficultForm" property="vo.difficultvalue"/>" usage="notempty,num-range:0:10" fie="难度系数" style="width:200px"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel">状态:</div>
			   <div class="field"> 
			     <html:select name="quesdifficultForm" property="vo.status" style="width:200px">
			         <html:optionsSaas consCode="common.const.commonstatus"/>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

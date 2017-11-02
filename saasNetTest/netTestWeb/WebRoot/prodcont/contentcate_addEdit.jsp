<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="contentcateForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="contentcateForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; 
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>知识点</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script language=JavaScript src="../styles/script/checkform.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/prodcont/saveContentcate.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value='/prodcont/listContentcatemag.do?queryVO.productbaseid=<bean:write name="contentcateForm" property="vo.productbaseid"/>&queryVO.pid=<bean:write name="contentcateForm" property="vo.pid"/>'>
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value='/prodcont/listContentcatemag.do?queryVO.productbaseid=<bean:write name="contentcateForm" property="vo.productbaseid"/>&queryVO.pid=<bean:write name="contentcateForm" property="vo.pid"/>'>
	 <input type="hidden" name="vo.contentcateid" value="<bean:write name="contentcateForm" property="vo.contentcateid"/>" />
	 <input type="hidden" name="vo.productbaseid" value="<bean:write name="contentcateForm" property="vo.productbaseid"/>" />
	 <input type="hidden" name="vo.pid" value="<bean:write name="contentcateForm" property="vo.pid"/>" />
	 <input type="hidden" name="vo.path" value="<bean:write name="contentcateForm" property="vo.path"/>" />
	
	  <div id="fieldsTitleDiv">编辑课程目录</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">课程目录:</div>
			   <div class="field"> 
			     <input type="text" class="userInput" name="vo.contentcatename" value="<bean:write name="contentcateForm" property="vo.contentcatename"/>" usage="notempty,max-length:128" fie="课程内容目录名" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">上级目录:</div>
			   <div class="field"> 
			     <bean:write name="contentcateForm" property="parentcateName"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">同级显示顺序:</div>
			   <div class="field"> 
			     <input type="text" class="userInput" name="vo.disorder" value="<bean:write name="contentcateForm" property="vo.disorder"/>" usage="int-range:1:99999" fie="同级显示顺序" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">描述:</div>
			   <div class="field"> 
			     <input type="text" name="vo.description" class="userInput" value="<bean:write name="contentcateForm" property="vo.description"/>" usage="max-length:128" fie="描述" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

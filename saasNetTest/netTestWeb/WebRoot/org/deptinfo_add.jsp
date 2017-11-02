<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="deptinfoForm" property="jsSuffix" type="java.lang.String"/>
    <title>编辑单位</title>

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
	<html:form styleId="editForm" action="/org/savedeptinfo.do" method="post">
	 <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="deptinfoForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="deptinfoForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.pid" value="<bean:write name="deptinfoForm" property="queryVO.pid"/>"/>
	 <input type="hidden" name="vo.path" value="<bean:write name="deptinfoForm" property="vo.path"/>"/>
	 <input type="hidden" name="vo.orglevel" value="<bean:write name="deptinfoForm" property="vo.orglevel"/>"/>
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
	     <ul>
              

		    <li>
			   <div class="fieldLabel">单位名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orgname" style="width:250px" usage="notempty,max-length:40" fie="单位名称"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">单位简称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orgnamesim" style="width:250px" usage="max-length:40" fie="单位简称"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">上级单位:</div>
			   <div class="field"> 
			     <bean:write name="deptinfoForm" property="queryVO.pidName"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">单位描述:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orgdesc" style="width:250px" usage="max-length:40" fie="单位描述"/>
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

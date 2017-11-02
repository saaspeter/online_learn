<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑学习内容</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script language="JavaScript" type="text/JavaScript">
    <!--
       
       
    //-->
    </script>
  </head>
  
  <bean:define id="productbaseidVar" name="learncontentForm" property="vo.productbaseid" type="java.lang.Long"></bean:define>
  <bean:define id="productnameVar" name="learncontentForm" property="vo.productname" type="java.lang.String"></bean:define>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/learncont/addLearncontent.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="learncontentForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="learncontentForm" property="backUrlEncode"/>">
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
	     <ul>
	     
	     	<li>
			   <div class="fieldLabel">学习内容名称:</div>
			   <div class="field"> 
			      <input type="text" name="vo.caption" style="width:200px" value="<bean:write name="learncontentForm" property="vo.caption"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
	     
	        <li>
			   <div class="fieldLabel">课程:</div>
			   <div class="field"> 
			      <input name="vo.productbaseid" type="hidden" value="<%=productbaseidVar %>" >
			      <%=productnameVar %>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">学习内容类型:</div>
			   <div class="field"> 
			   	   <bean:writeSaas name="learncontentForm" property="vo.contenttype" consCode="netTest.Learncontent.contenttype"/>
		           <input type="hidden" name="vo.contenttype" value="<bean:write name="learncontentForm" property="vo.contenttype"/>" > 
			   </div>
			   <div class="fieldDesc"></div>
			</li>
	        
	        <li>
			   <div class="fieldLabel">知识点:</div>
			   <div class="field"> 
			       <input type="hidden" id="contentcateidId" name="vo.contentcateid" value="<bean:write name="learncontentForm" property="vo.contentcateid"/>" >
             	   <input type="text" id="contentcatenameId" style="width:200px" name="vo.contentcatename" class="readonly" value="<bean:write name="learncontentForm" property="vo.contentcatename"/>" readonly="readonly" style="width:150px"/>
       			   <img src="../styles/imgs/ico/search.gif" alt="选择知识点" onclick="selectContcate()">
       			   <img src="../styles/imgs/ico/reset.gif" alt="删除知识点" onclick="removeContcate()">
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">上传文件:</div>
			   <div class="field"> 
			     <input type="file" name="uploadFile" style="width:350px">
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

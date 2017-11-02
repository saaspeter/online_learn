<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="exerquestypeForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="exerquestypeForm" property="editType" type="java.lang.Integer"/>
  <%String removequestypeStr=request.getParameter("removequestypeStr"); 
    boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
    } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>练习题型</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script language=JavaScript src="../styles/script/commonlogic.js"></script>
    <script language="javascript">
	<!--

	//-->
	</script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/exercise/saveExerquestype.do" method="post">
	 <input type="hidden" name="vo.exerid" value="<bean:write name="exerquestypeForm" property="vo.exerid"/>" />
	 <input type="hidden" name="vo.patternid" value="<bean:write name="exerquestypeForm" property="vo.patternid"/>" />
	 <%if(isDisable){ %>
	 <input type="hidden" name="vo.questype" value="<bean:write name="exerquestypeForm" property="vo.questype"/>" />
	 <%} %>
	  <div id="fieldsTitleDiv">试卷题型</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">所属题型:</div>
			   <div class="field"> 
			       <html:select name="exerquestypeForm" property="vo.questype" style="width:200px" disabled="<%=isDisable %>" >
					   <html:optionsSaas consCode="Question.QuesType" removeStr="<%=removequestypeStr %>"/>
				   </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">题型排列顺序:</div>
			   <div class="field"> 
			      <input name="vo.disorder" value="<bean:write name="exerquestypeForm" property="vo.disorder"/>" style="width:200px"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">题型说明:</div>
			   <div class="field"> 
			     <textarea name="vo.questypenote" rows="2" cols="50" usage="max-length:640" fie="题型说明"><bean:write name="exerquestypeForm" property="vo.questypenote"/></textarea>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

   		 </ul>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

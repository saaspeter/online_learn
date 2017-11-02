<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="exerExamForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="userstatus" name="exerExamForm" property="exeruserVO.status" type="java.lang.Short"/>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>练习信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	
    <script type="text/javascript">
	   
          function beginTest(){
              document.getElementById("editForm").submit();
          }
	   
	</script>
	
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/exercise/enterExercise.do" method="post">
	 <input type="hidden" name="exerid" value="<bean:write name="exerExamForm" property="exerVO.exerid"/>" />
	 
	  <div id="fieldsTitleDiv">
	      <bean:write name="exerExamForm" property="exerVO.exername"/>   
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div class="fieldDisClass">
	     <ul>
			
			<li>
			   <div class="fieldLabel">创建时间:</div>
			   <div class="field"> 
			       <bean:write name="exerExamForm" property="exerVO.createtime" format="yyyy/MM/dd"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

            <li>
			   <div class="fieldLabel">练习知识点:</div>
			   <div class="field"> 
			       <bean:write name="exerExamForm" property="exerVO.contentcatename" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>

			<li>
			   <div class="fieldLabel">我的练习状态:</div>
			   <div class="field"> 
			       <bean:writeSaas name="exerExamForm" property="exeruserVO.status" consCode="netTest.ExerUser.Status"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
            <li>
			   <div class="fieldLabel">练习备注:</div>
			   <div class="field"> 
			       <bean:writeSaas name="exerExamForm" property="exerVO.exerdesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		 </ul>
	  </div>
	  <div style="height:120px;">&nbsp;</div>
	  <div id="functionBarButtomDiv">
	      <button type="button" onclick="beginTest()" class="bigButton">开始练习答题</button>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

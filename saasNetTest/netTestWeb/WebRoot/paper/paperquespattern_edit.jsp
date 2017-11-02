<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="paperquestypeForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="paperquestypeForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>试卷题型</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script language=JavaScript src="../styles/script/commonlogic.js"></script>
    <script language="javascript">
	<!--
	   function submitPage(){
	      submitForm('editForm');
	   }
	   
	   function changequestype(selObj){
          if(selObj==null){
             return;
          }
          var itemValue = selObj.options[selObj.selectedIndex].label;
          document.getElementById("questypenameId").value = itemValue;
       }

	//-->
	</script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/paper/savepaperquespatt.do" method="post">
	 <input type="hidden" name="vo.paperid" value="<bean:write name="paperquestypeForm" property="vo.paperid"/>" />
	 <input type="hidden" name="vo.patternid" value="<bean:write name="paperquestypeForm" property="vo.patternid"/>" />
	 <input type="hidden" name="vo.quesscore_old" value="<bean:write name="paperquestypeForm" property="vo.quesscore"/>" />
	 <input type="hidden" name="vo.questypeid" value="<bean:write name="paperquestypeForm" property="vo.questypeid"/>" />
	
	  <div id="fieldsTitleDiv">试卷题型</div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><font color="red">*</font>所属题型:</div>
			   <div class="field"> 
			       <html:select name="paperquestypeForm" property="vo.questype" style="width:200px" disabled="<%=isDisable %>" onchange="changequestype(this)">
					   <html:optionsSaas consCode="Question.QuesType"/>
				   </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel"><font color="red">*</font>题型名称:</div>
			   <div class="field"> 
			     <input type="text" id="questypenameId" name="vo.questypename" value="<bean:write name="paperquestypeForm" property="vo.questypename"/>" usage="notempty,max-length:50" fie="题型名称" style="width:200px" />
			     
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="red">*</font>每题分数:</div>
			   <div class="field"> 
			      <input name="vo.quesscore" value="<bean:write name="paperquestypeForm" property="vo.quesscore"/>" usage="notempty,numt+" fie="每题分数" style="width:200px" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">题型说明:</div>
			   <div class="field">
			     <textarea name="vo.questypenote" rows="2" cols="50" usage="max-length:640" fie="题型说明"><bean:write name="paperquestypeForm" property="vo.questypenote"/></textarea>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">每页题目数:</div>
			   <div class="field"> 
			      <input name="vo.pagesize" value="<bean:write name="paperquestypeForm" property="vo.pagesize"/>" usage="int+" fie="每页题目数" style="width:200px" title="默认为所有题目显示在一个题型页面上"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">题型排列顺序:</div>
			   <div class="field"> 
			      <input name="vo.disorder" value="<bean:write name="paperquestypeForm" property="vo.disorder"/>" usage="int+" fie="题型排列顺序" style="width:200px"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

   		 </ul>
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitPage();"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

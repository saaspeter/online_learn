<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="warequesForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="filetype" name="warequesForm" property="vo.filetype" type="java.lang.Short"/>
<bean:define id="fileurl" name="warequesForm" property="vo.fileurl" type="java.lang.String"/>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>阅读理解</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/swfobject.js"></script>
    <script language="javascript">
	<!--
	          	   
	//-->
	</script>
  </head>
  
  <body> 
	<div class="editPage">


	  <div style="width:100%;padding-top:2px;margin-top:20px;background-color:#DCEAFC"><font style="font-weight: bold;">题目属性</font></div>
      <div>
	  <table class="formTable2">
	       <tr>
	          <td align="left">题目类型：<bean:writeSaas name="warequesForm" property="vo.questype" consCode="Question.QuesType" /></td>
	          <td align="left">所在题库：<bean:write name="warequesForm" property="vo.warename" /></td>
	          <td align="left">题目难度：<bean:writeSaas name="warequesForm" property="vo.difficultid" consCode="Question.Difficult"/></td>
	       </tr>
	       <tr>
	          <td align="left">知识点：<bean:write name="warequesForm" property="vo.contentcatename"/></td>
	          <td align="left">创建时间：<bean:write name="warequesForm" property="vo.quescreatetime" format="yyyy-MM-dd HH:mm:ss"/></td>
	          <td></td>
	       </tr>
	  </table>
	  </div>
	  <div style="width:100%;padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">阅读材料</font></div>
	  <div>
	      <table class="formTable2">
		     <tr>
		        <td align="left" >
	                <bean:write name="warequesForm" property="vo.question" filter="false"/>
	            </td>
	         </tr>
	         <tr style="background: #eeeeee">
		        <td align="left" id="container"> 
		            <%if(QuestionConstant.FileType_pict.equals(filetype)){ %>
		                <img src="<%=WebConstant.FileContext %><bean:write name="warequesForm" property="vo.fileurl"/>" height="100">
		            <%} %>
		            <%if(QuestionConstant.FileType_voice.equals(filetype)){ %>
		                <script type="text/javascript">
							var s1 = new SWFObject("../styles/player.swf","container","328","20","9","#123456");
							s1.addParam("allowfullscreen","false");
							s1.addParam("allowscriptaccess","always");
							s1.addParam('flashvars','file=<%=fileurl %>');
							s1.write("container");
						</script>
		            <%} %>
		        </td>
	         </tr>
	      </table>
	  </div>
	 	  
	  <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题</font></div>
	  <logic:iterate id="subquesVO" name="warequesForm" property="vo.subquesList" indexId="indques" type="netTest.wareques.vo.Question">
          <div>
		      <table class="formTable2">
		         <tr style="background-color:#DCEAFC">
		             <td width="16"><%=indques+1 %>.&nbsp;</td>
		             <td><bean:write name="subquesVO" property="question"/>&nbsp;&nbsp;</td>
		         </tr>
		         <logic:equal value="<%=QuestionConstant.Rowitems_One.toString() %>" name="warequesForm" property="vo.rowitems">
	             <logic:iterate id="quesItemVO" name="subquesVO" property="itemList" indexId="inditem">
	             <tr>
	                <td align="center" width="16"> 
	                   <logic:equal name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
	                      <img src="../styles/imgs/ico/check.gif" border="0"/>
	                   </logic:equal>
	                </td>
	                <td>
	                   &nbsp;<bean:write name="quesItemVO" property="quesitemcontent"/>
	                </td>
	             </tr>
	             </logic:iterate>
	             </logic:equal>
	             <logic:equal value="<%=QuestionConstant.Rowitems_AllInOneRow.toString() %>" name="warequesForm" property="vo.rowitems">
	             <tr>
	                <td align="center" width="16"></td>
	                <td>
	                   <logic:iterate id="quesItemVO" name="subquesVO" property="itemList" indexId="inditem">
		                   <span style="margin-right:10px;margin:4px;padding:2px; border: 1px solid #cccccc;">
		                   <logic:equal name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
		                      <img src="../styles/imgs/ico/check.gif" border="0" style="vertical-align: middle"/>
		                   </logic:equal>
		                   <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=inditem %>]+'.)');</script></font>
		                   <bean:write name="quesItemVO" property="quesitemcontent"/>
		                   </span>
	                   </logic:iterate>
	                </td>
	             </tr>
	             </logic:equal>
	          </table>
		  </div>
	  </logic:iterate>
	  
	  <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">解题思路</font></div>
	  <div>
	      <table class="formTable">
		     <tr>
		        <td align="left" >
	      			<bean:write name="warequesForm" property="answerVO.solvedesc"/>
	      		</td>
	      	 </tr>
	      </table>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
	  	    <li><button type="button" onclick="parent.clearDiv();return false;">关闭</button></li>
		 </ul>
	  </div>
	  <br>
	  <div id="buttomDiv"></div>

	</div>
	
  </body>
</html:html>

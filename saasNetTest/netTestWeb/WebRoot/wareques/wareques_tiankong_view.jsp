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
    <title>填空题</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script src="../styles/flashplayer/mediaelement/mediaelement-and-player.min.js"></script>
	<link rel="stylesheet" href="../styles/flashplayer/mediaelement/mediaelementplayer.min.css" />
  </head>
  
  <body> 
	<div class="editPage">
	 <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="warequesForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="warequesForm" property="backUrlEncode"/>">
	
	  <div id="fieldsTitleDiv">查看题目</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

      <div style="float:left;margin:2px;width: 99%;">
	  <div style="padding-top:2px;margin-top:20px;background-color:#DCEAFC"><font style="font-weight: bold;">题目属性</font></div>
	  <div>
		  <table class="formTable">
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
	  <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font color="red">*</font><font style="font-weight: bold;">题目</font></div>
	  <div>
	      <table class="formTable2">
		     <tr>
		        <td colspan="2">
                    <bean:writeTK name="warequesForm" property="vo.question" filter="false" transfer="true" showIndentWay="2"/>
	            </td>
	         </tr>
	         <tr style="background: #eeeeee">
		        <td align="left" id="container"> 
		            <%if(QuestionConstant.FileType_pict.equals(filetype)){ %>
		                <img src="<%=WebConstant.FileContext %><bean:write name="warequesForm" property="vo.fileurl"/>" height="100">
		            <%} %>
		            <%if(QuestionConstant.FileType_voice.equals(filetype)){ %>
						<audio id="audio1" src="<%=fileurl %>" type="audio/mp3" controls="controls"></audio>
		            <%} %>
		        </td>
	         </tr>
	      </table>
	  </div>
	  
	  <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">答案说明</font></div>
	  <div>
	      <table class="formTable">
		     <tr>
		        <td align="left" >
	      			<bean:write name="warequesForm" property="answerVO.solvedesc" filter="false"/>
	      		</td>
	      	 </tr>
	      </table>
	  </div>
	  
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

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
    <title>判断题</title>
    <bean:size id="itemNoVar" name="warequesForm" property="itemList"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/swfobject.js"></script>
    <script type="text/javascript" src="../styles/flashplayer/jwplayer/jwplayer.js"></script>
    <script language="javascript">
	<!--
	   <% if(itemNoVar==null) itemNoVar = 0; %>
	   var itemNo = <%=itemNoVar %>;
	   var itemNum = <%=itemNoVar %>;
	   
	//-->
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/wareques/saveWareques.do" method="post" enctype="multipart/form-data">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="warequesForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="warequesForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.quesid" value="<bean:write name="warequesForm" property="vo.quesid"/>">
	 <input id="prodidId" type="hidden" name="vo.productbaseid" value="<bean:write name="warequesForm" property="vo.productbaseid"/>">
		  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
          
      <div style="float:left;margin:2px;width: 100%;">
	  <div style="width:100%;padding-top:2px;margin-top:20px;background-color:#DCEAFC"><font style="font-weight: bold;">题目属性</font></div>
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
	  <div style="width:100%;padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font color="red">*</font><font style="font-weight: bold;">题干</font></div>
	  <div>
	      <table class="formTable">
		     <tr>
		        <td align="left" >
	                <bean:write name="warequesForm" property="vo.question" format="false"/>
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
	  <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">答案</font></div>
	  <div>
	      <table class="formTable">
	         <tbody>
             <tr>
                <td align="left">&nbsp;&nbsp;
                    <logic:equal name="warequesForm" property="vo.answersim" value="<%=QuestionConstant.IsRight_right.toString() %>">正确</logic:equal>
                    <logic:equal name="warequesForm" property="vo.answersim" value="<%=QuestionConstant.IsRight_wrong.toString() %>">错误</logic:equal>
                </td>
             </tr>
             </tbody>
           </table>
	  </div>
	  
	  <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">答案说明</font></div>
	  <div>
	      <table class="formTable">
		     <tr>
		        <td align="left" >
	      			<bean:write name="warequesForm" property="answerVO.solvedesc"/>
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
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
		
  </body>
</html:html>

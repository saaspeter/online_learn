<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="questypeshopForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="questypeshopForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>自定义题型</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/wareques/saveQuestypeshop.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="questypeshopForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="questypeshopForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.questypeid" value="<bean:write name="questypeshopForm" property="vo.questypeid"/>">
	 
	 
	  <div id="fieldsTitleDiv">自定义题型</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">题型名：</div>
			   <div class="field"> 
			     <input type="text" name="vo.questypename" value="<bean:write name="questypeshopForm" property="vo.questypename"/>" <%=disableStr %> style="width:200px" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">题型来源：</div>
			   <div class="field"> 
			       <html:select name="questypeshopForm" property="vo.questypevalue" disabled="<%=isDisable %>" style="width:200px">
						<html:optionsSaas consCode="Question.QuesType"/>
				   </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li> 
			<%if(isDisable){ %>
			<li>
			   <div class="fieldLabel">创建时间：</div>
			   <div class="field"> 
			       <bean:write name="questypeshopForm" property="vo.createdate" format="yyyy-MM-dd HH:mm:ss"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            <%} %>
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

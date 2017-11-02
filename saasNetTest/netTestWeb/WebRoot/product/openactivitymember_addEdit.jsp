<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.vo.Openactivity" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="openactivitymemberForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="openactivitymemberForm" property="editType" type="java.lang.Integer"/>
<bean:define id="activitystatus" name="openactivitymemberForm" property="vo.activityvo.status" type="java.lang.Short"></bean:define>
  <%String disableStr=""; boolean isDisable=false;
    if(Openactivity.Status_Ended.equals(activitystatus)){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/joinOpenactivity.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="openactivitymemberForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="openactivitymemberForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.activityid" value="<bean:write name="openactivitymemberForm" property="vo.activityid"/>">
	 <input type="hidden" name="vo.userid" value="<bean:write name="openactivitymemberForm" property="vo.userid"/>">
	
	  <div id="fieldsTitleDiv">个人信息加入活动信息</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
          <%if(Openactivity.Status_Ended.equals(activitystatus)){ %>
                                  活动已结束，不能修改用户信息
          <%} %>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
            
		    <li>
			   <div class="fieldLabel">名字:</div>
			   <div class="field"> 
			     <input type="text" name="vo.displayname" value='<bean:write name="openactivitymemberForm" property="vo.displayname"/>' <%=disableStr %>/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">电话:</div>
			   <div class="field"> 
			     <input type="text" name="vo.phone" value='<bean:write name="openactivitymemberForm" property="vo.phone"/>' <%=disableStr %>/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">其他联系方式:</div>
			   <div class="field"> 
			     <input type="text" name="vo.othercontactinfo" placeholder="qq号，微信号等" value='<bean:write name="openactivitymemberForm" property="vo.othercontactinfo"/>' <%=disableStr %>/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

            <%if(editType.intValue()==WebConstant.editType_edit){ %>
		    <li>
			   <div class="fieldLabel">加入状态:</div>
			   <div class="field"> 
			     <html:select name="openactivitymemberForm" property="vo.joinstatus" styleClass="select_second" disabled="<%=isDisable %>">
				    <html:optionsSaas consCode="OpenActivityMember.JoinStatus"/>
				 </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            <%} %>
            
		    <li>
			   <div class="fieldLabel">备注:</div>
			   <div class="field"> 
			     <input type="text" name="vo.note" value="<bean:write name="openactivitymemberForm" property="vo.note"/>" <%=disableStr %>/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
	  	    <%if(Openactivity.Status_Scheduled.equals(activitystatus)){ %>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<%} %>
			<li><button type="button" onclick="parent.clearDiv();"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

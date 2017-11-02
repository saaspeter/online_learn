<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="usernotificationForm" property="jsSuffix" type="java.lang.String"/>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>
    <%String opentype = request.getParameter("opentype"); %>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language="javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language="javascript" src="../styles/script/pageAction.js"></script>
    <script language="javascript">
	<!--
	   function save1(){
	        var touseridStr = document.getElementById("touseridStrId").value;
	        var messcode = document.getElementById("messcodeId").value;
	        var touserloginame = document.getElementById("touserloginameId").value;
	        var content = document.getElementById("contentId").value;
	        var param = "touseridStr="+touseridStr+"&vo.messcode="+messcode+"&touserloginame="+touserloginame+"&vo.content="+content;
		    var rtnObj = toAjaxUrl("savenotification.do", false, param);
            if(rtnObj.result=="1"){ 
                if(rtnObj.tip!=null && rtnObj.tip!=''){
                   alert(rtnObj.tip);
                   parent.clearDiv();
                }
            }else if(rtnObj.result=="2"){
                alert(rtnObj.tip);
                parent.clearDiv();
            }else{
                alert(getMessage("systemError"));
            }
	   }
       
	//-->
	</script>

  </head>
  
  <body>
	<div class="editPage">
	  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="usernotificationForm" property="backUrl"/>">
      <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="usernotificationForm" property="backUrlEncode"/>">
     
	 <input type="hidden" id="touseridStrId" name="touseridStr" value="<bean:write name="usernotificationForm" property="touseridStr"/>" />
     
	  <div id="fieldsTitleDiv">新消息</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabelNewdiv">标题:</div>
			   <div class="field"> 
			      <input type="text" id="messcodeId" name="vo.messcode" value='<bean:write name="usernotificationForm" property="vo.messcode"/>'/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabelNewdiv">收件人:</div>
			   <div class="field"> 
			      <input type="text" id="touserloginameId" name="touserloginame" value="<bean:write name="usernotificationForm" property="touserloginame"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabelNewdiv">内容:</div>
			   <div class="field"> 
			      <textarea id="contentId" rows="6" cols="50" name="vo.content"></textarea>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div style="height:50px">&nbsp;</div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="bigButton" onclick="save1();"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" class="bigButton" onclick="<%if("1".equals(opentype)){ %>getAndToUrl('backUrl');<%} else {%>parent.clearDiv();<%}%>return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>

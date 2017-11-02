<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑资源页面</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">
      <!--

	  //-->
    </script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/securityManage/saveResourcesvalue.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="resourcesvalueForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="resourcesvalueForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.rescvalueid" value="<bean:write name="resourcesvalueForm" property="vo.rescvalueid"/>">
	 <input type="hidden" name="vo.id" value="<bean:write name="resourcesvalueForm" property="vo.id"/>"/>
	 <bean:define id="rescvalueid" name="resourcesvalueForm" property="vo.rescvalueid"></bean:define>
	 <bean:define id="removeStr" name="resourcesvalueForm" property="removeStr" type="java.lang.String"></bean:define>
	 <%boolean show = false; if(rescvalueid!=null) show = true;%>
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="submitForm('editForm');"><bean:message key="platform.commonpage.save" bundle="platform.pagemess"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset" bundle="platform.pagemess"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back" bundle="platform.pagemess"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title='<bean:message key="platform.commonpage.help" bundle="platform.pagemess"/>'><img src="../styles/imgs/help.jpg"></a>
		  </div>

	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord" bundle="platform.pagemess"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">语言设置:</div>
			   <div class="field">   
			     <html:select name="resourcesvalueForm" property="vo.localeid" style="width:150px;" disabled="<%=show %>" >
    				 <html:optionsSaas localeListSet="true" removeStr="<%=removeStr %>"/>
				 </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">资源名:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="resourcesvalueForm" property="vo.name"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">资源描述:</div>
			   <div class="field"> 
			     <input type="text" id="rescDescnId"  name="vo.descn" value="<bean:write name="resourcesvalueForm" property="vo.descn"/>" size="50"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset" bundle="platform.pagemess"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back" bundle="platform.pagemess"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

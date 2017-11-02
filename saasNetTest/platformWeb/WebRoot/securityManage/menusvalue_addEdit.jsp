<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="platformWeb.base.WebConstant"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑菜单页面</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/securityManage/saveMenusvalue.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="menusvalueForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="menusvalueForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.menuvalueid" value="<bean:write name="menusvalueForm" property="vo.menuvalueid"/>">
	 <input type="hidden" name="vo.id" value="<bean:write name="menusvalueForm" property="vo.id"/>"/>
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>

	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">语言设置:</div>
			   <div class="field"> 
			     <input type="text" name="vo.localeid" value="<bean:write name="menusvalueForm" property="vo.localeid"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">资源名:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="menusvalueForm" property="vo.name"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">菜单名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.title" value="<bean:write name="menusvalueForm" property="vo.title"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">菜单提示信息:</div>
			   <div class="field"> 
			     <input type="text" name="vo.tooltip" value="<bean:write name="menusvalueForm" property="vo.tooltip"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">资源描述:</div>
			   <div class="field"> 
			     <input type="text" id="rescDescnId"  name="vo.descn" value="<bean:write name="menusvalueForm" property="vo.descn"/>" size="50"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  
	  <div style="background:#eeeeee">菜单国际化设置</div>
	  <div style="width:100%;height:230px;">
		   <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>/securityManage/listMenusvalue.do?queryVO.id=<bean:write name="resourcesForm" property="vo.id"/>" scrolling="auto" frameborder="0"></iframe>
      </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

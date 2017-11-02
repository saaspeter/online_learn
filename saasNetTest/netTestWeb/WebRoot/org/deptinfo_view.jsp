<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看单位</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv">查看单位情况</div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

            <li>
			   <div class="fieldLabel">单位名称:</div>
			   <div class="field"> 
			     <bean:write name="deptinfoForm" property="vo.orgname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">上级单位:</div>
			   <div class="field"> 
			     <bean:write name="deptinfoForm" property="vo.pidName"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

			
		    <li>
			   <div class="fieldLabel">单位描述:</div>
			   <div class="field"> 
			     <bean:write name="deptinfoForm" property="vo.orgdesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>



		    <li>
			   <div class="fieldLabel">下级单位个数:</div>
			   <div class="field"> 
			      <bean:write name="deptinfoForm" property="vo.deptchildnum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>

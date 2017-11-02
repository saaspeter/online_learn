<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">

  </head>
  
  <body>
	<div class="editPage">
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">商店:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.shopid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">题库名称:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.warename"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">创建时间:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.warecreatetime" format="yyyy-MM-dd"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>	

		    <li>
			   <div class="fieldLabel">所属产品:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.productname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">题目数量:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.warequesnum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">创建人:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.creatorid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">描述:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.waredesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>

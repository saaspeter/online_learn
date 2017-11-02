<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看产品类别</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
  	 <bean:define id="categoryidPage" name="productcategoryForm" property="vo.categoryid" type="java.lang.Long"/>
	 <bean:define id="categoryLevelPage" name="productcategoryForm" property="vo.categorylevel" type="java.lang.Short"/>
	 <bean:define id="categoryPidPage" name="productcategoryForm" property="vo.pid" type="java.lang.Long"/>
	<div class="listPage">
	
	  <div id="errorDisplayDiv"></div>
	
	  <div id="fieldsTitleDiv">查看记录</div>
	  <div id="fieldDisDiv">
	     <ul>
	        <li>
			   <div class="fieldLabel">语言选择:</div>
			   <div class="field"> 
			      <html:select name="productcategoryForm" property="queryVO.localeid" onchange='<%="jumpMenu('self',this,0,'/productcategory/viewproductcategory.do?queryVO.categoryid="+categoryidPage+"&queryVO.localeid=')" %>'>
					  <html:optionsSaas localeListSet="true"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		    <li>
			   <div class="fieldLabel">目录名称:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="vo.categoryname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">目录级别:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="vo.categorylevel"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">目录简述:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="vo.categorydesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">目录详述:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="vo.categorydesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		 </ul>
	  </div>

	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看产品类别</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
  	 <bean:define id="categoryidPage" name="productcategoryForm" property="viewQueryVO.categoryid" type="java.lang.Long"/>
	 <bean:define id="categoryLevelPage" name="productcategoryForm" property="viewQueryVO.categorylevel" type="java.lang.Short"/>
	 <bean:define id="categoryPidPage" name="productcategoryForm" property="viewQueryVO.pid" type="java.lang.Long"/>
	<div class="listPage">
	  <div id="functionLineDiv">

		  <div id="help">
		       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
		  </div>

	  </div>
	
	  <div id="errorDisplayDiv"></div>
	
	  <div id="fieldsTitleDiv">查看记录</div>
	  <div id="fieldDisDiv">
	     <ul>
	        <li>
			   <div class="fieldLabel">语言选择:</div>
			   <div class="field"> 
			      <html:select name="productcategoryForm" property="viewQueryVO.localeid" onchange="<%="jumpMenu('self',this,0,'/productcategory/productcategory.do?method=editPage&editType="+WebConstant.editType_view+"&viewQueryVO.categoryid="+categoryidPage+"&viewQueryVO.categorylevel="+categoryLevelPage+"&viewQueryVO.pid="+categoryPidPage+"&viewQueryVO.localeid=')" %>">
					<html:optionsCollection name="productcategoryForm" property="localesList"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		    <li>
			   <div class="fieldLabel">目录名称:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="queryVO.categoryname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">目录级别:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="viewQueryVO.categorylevel"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">显示顺序:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="viewQueryVO.disorder"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">目录简述:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="viewQueryVO.categorydesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">目录详述:</div>
			   <div class="field"> 
			      <bean:write name="productcategoryForm" property="viewQueryVO.categorydesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		 </ul>
	  </div>

	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>

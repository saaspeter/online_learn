<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="shopvalueForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="shopvalueForm" property="editType" type="java.lang.Integer"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑商店简介</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../../styles/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">

	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/saveshopdescript.do" method="post">
     <input type="hidden" name="vo.shopvalueid" value="<bean:write name="shopvalueForm" property="vo.shopvalueid"/>">
	 
	  <div class="fieldsTitleDiv"><bean:write name="shopvalueForm" property="vo.shopname"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="width:95%">
		 <textarea cols="100" id="editor1" name="vo.shopdesc" rows="6" usage="max-length:10000" tip="商店简介内容过长"><bean:write name="shopvalueForm" property="vo.shopdesc"/></textarea>
	     <script type="text/javascript">
	         var p_editor = CKEDITOR.replace( 'editor1',
	         {
	      		toolbar : 'MyFull',
	      		height : '400px', 
	   	        filebrowserImageUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Image&rootobjecttype=shop&sourceType=shopintroduce&sourceId=<bean:write name="shopvalueForm" property="vo.shopvalueid"/>'
	    	 });
	     </script>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="goUrl('/shop/viewshopdescedit.do');return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

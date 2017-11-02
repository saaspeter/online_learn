<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.vo.Productbase" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="productForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="productbaseidVar" name="productForm" property="vo.productbaseid" type="java.lang.Long"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>课程简介</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/ckeditor/ckeditor.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/saveproductdesc.do" method="post">
     <input type="hidden" name="vo.productbaseid" value="<%=productbaseidVar %>">
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="width:98%">
		 <textarea cols="95" id="editor1" name="vo.productdesc" rows="6"><bean:write name="productForm" property="vo.productdesc"/></textarea>
	     <script type="text/javascript">
	         var p_editor = CKEDITOR.replace( 'editor1',
	         {
	      		toolbar : 'MyFull',
	      		height : '400px', 
	   	        filebrowserImageUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Image&rootobjecttype=shop&parentObjectType=<%=Productbase.ObjectType %>&parentObjectId=<%=productbaseidVar %>&sourceType=productdesc'
	    	 });
	     </script>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="goUrl('/product/viewproddescedit.do?vo.productbaseid=<bean:write name="productForm" property="vo.productbaseid"/>');return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
  
   <script type="text/javascript" language="javascript">  
	   parent.iFrameHeight("iframecontent1", 630);
   </script>
  
</html:html>

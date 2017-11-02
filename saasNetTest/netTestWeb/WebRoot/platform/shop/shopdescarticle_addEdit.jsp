<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.shop.vo.Shopdescarticle" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="shopdescarticleForm" property="jsSuffix" type="java.lang.String"/>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/ckeditor/ckeditor.js"></script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/saveshopdescarticle.do" method="post">
     <input type="hidden" name="vo.articleid" value="<bean:write name="shopdescarticleForm" property="vo.articleid"/>">
	
	  <div id="fieldsTitleDiv">培训教师介绍</div>

	  <div id="fieldDisDiv">
	     <table class="formTable">
	         <tr>
	             <td>
	                 <div style="width:90%">
						 <textarea cols="80" id="editor1" name="vo.content" rows="15" usage="notempty,max-length:10000" tip="教师简介内容过长"><bean:write name="shopdescarticleForm" property="vo.content"/></textarea>
					     <script type="text/javascript">
					         var p_editor = CKEDITOR.replace( 'editor1',
					         {
					        	toolbar : 'MyFull',
					      		height : '200px', 
					   	        filebrowserImageUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Image&rootobjecttype=shop&sourceType=<%=Shopdescarticle.ObjectType %>&sourceId=<bean:write name="shopdescarticleForm" property="vo.articleid"/>'
					    	 });
					     </script>
					 </div>
	             </td>
	         </tr>
	     </table>
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="goUrl('/shop/viewshopdescarticleedit.do');return false;">取消返回</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

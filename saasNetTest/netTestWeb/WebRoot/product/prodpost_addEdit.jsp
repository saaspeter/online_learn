<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.shop.vo.Shoppost" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="coursepostForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="coursepostForm" property="editType" type="java.lang.Integer"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑公告</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
	   function submitPost(){
          submitForm('editForm');
	   }
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/savecoursepost.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="coursepostForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="coursepostForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.id" value="<bean:write name="coursepostForm" property="vo.id"/>">
	 <input type="hidden" name="vo.productbaseid" value="<bean:write name="coursepostForm" property="vo.productbaseid"/>">
	
	  <div id="fieldsTitleDiv">
                           课程通知
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="width:90%;text-align: center">
		 <table style="width: 100%;">
		     <tr>
		         <td style="width:45px;text-align: right"></td>
		         <td style="text-align: center"><bean:write name="coursepostForm" property="vo.productname"/></td>
		         <td></td>
		     </tr>
		     		     
			 <tr>
		         <td></td>
		         <td>
		             <textarea cols="110" id="editor1" name="vo.content" rows="3" usage="max-length:400" tip="课程通知"><bean:write name="coursepostForm" property="vo.content"/></textarea>
				     <script type="text/javascript">
				         var p_editor = CKEDITOR.replace( 'editor1',
				         {
				      		toolbar : 'MySimplest',
				      		height : '100px', 
				    	 });
				     </script>
		         </td>
		         <td></td>
		      </tr>				    
		 </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitPost();"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

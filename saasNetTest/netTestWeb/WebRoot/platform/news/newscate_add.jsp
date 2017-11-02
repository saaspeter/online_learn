<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.constant.NewscategoryConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="newscategoryForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="defaultlocaleid" name="newscategoryForm" property="defaultlocaleid" type="java.lang.Long"/>
       
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
	<script type="text/javascript">

	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/news/saveNewscategory.do" method="post">
     <input type="hidden" name="vo.localeid" value="<bean:write name="newscategoryForm" property="defaultlocaleid"/>" />
	 <input type="hidden" name="vo.categoryid" value="<bean:write name="newscategoryForm" property="vo.categoryid"/>" />
	
	  <div id="fieldsTitleDiv">新增新闻分类</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="width:100%">
		 <table class="formTable" width="95%">
		     <tr>
		         <td style="width:30%;text-align: right">名称:</td>
		         <td style="width:60%;text-align: left"><input type="text" name="vo.name" value="" /></td>
		         <td></td>
		     </tr>
		     
		     <tr>
		         <td style="width:30%;text-align: right">类型:</td>
		         <td style="width:60%;text-align: left">
		             <select name="vo.type">
		                 <option value="<%=NewscategoryConstant.Type_SystemPost %>">System Post</option>
		                 <option value="<%=NewscategoryConstant.Type_SocialPost %>">Social discussion</option>
		             </select>
		         </td>
		         <td></td>
		     </tr>
		     		     
			 <tr>
		         <td style="width:30%;text-align: right">默认语言:</td>
		         <td>
		             <bean:writeSaas showLocaleName="true" name="newscategoryForm" property="defaultlocaleid"/>
		         </td>
		         <td></td>
		      </tr>			    
		 </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

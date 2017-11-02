<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
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

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   function submit(){
	      var oEditor = FCKeditorAPI.GetInstance("content");
	      var content = oEditor.GetXHTML(true);
	      if(content==null||content==''){
	         alert('请填写内容');
	         return;
	      }

	      document.getElementById("IdPostContent").value = content;
          submitForm('editForm');
	   }
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/news/saveNewscategory.do" method="post">
     <input type="hidden" name="vo.localeid" value="<bean:write name="newscategoryForm" property="defaultlocaleid"/>" />
	 <input type="hidden" name="vo.moduleno" value="<bean:write name="newscategoryForm" property="vo.moduleno"/>" />
	 <input type="hidden" name="vo.categoryid" value="<bean:write name="newscategoryForm" property="vo.categoryid"/>" />
	
	  <div id="fieldsTitleDiv">新增新闻分类</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="width:100%">
		 <table>
		     <tr>
		         <td style="width:30%;text-align: right">名称:</td>
		         <td style="width:60%;text-align: left"><input type="text" name="vo.name" value="" /></td>
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
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

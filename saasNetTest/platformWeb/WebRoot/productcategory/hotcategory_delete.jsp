<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant,commonWeb.base.BaseActionBase" %>
<%@ include file="/pubs/taglibs.jsp"%>

<% 
   String jsSuffix = BaseActionBase.getLoginInfo(true).getJsSuffix();
   if(jsSuffix==null||jsSuffix.trim().length()<1){
	  jsSuffix = "default";
   }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script language=JavaScript src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   
	   function del(){
	      document.forms[0].submit();
	   }
	   
	</script>
  </head>
  <%
     String categoryid = request.getParameter("categoryid");
     String categoryname = request.getParameter("categoryname");
     String parentCategoryid = request.getParameter("parentCategoryid");
     String localeid = request.getParameter("localeid");
     
     if(parentCategoryid==null){
        parentCategoryid = "";
     }
     if(categoryid==null){
        categoryid = "";
     }
     if(categoryname==null){
        categoryname = "";
     }
   %>
  <body>
      <div class="titleBar">删除热门目录</div>
  
      <html:form action="/productcategory/delHotcategory.do" method="post">
          <input type="hidden" name="vo.categoryid" value="<%=categoryid %>" />
          <input type="hidden" name="vo.pid" value="<%=parentCategoryid %>" />
          <input type="hidden" name="vo.localeid" value="<%=localeid %>" />
          <div style="height: 30px;"></div>
          <div style="padding-left: 40px;">删除热门目录级别:</div>
	      <div style="padding-left: 40px;">   
		     <select name="scope">
		        <option value="1">本国家热门目录设置</option>
		        <option value="2">所有国家热门目录设置</option>
		     </select>
	      </div>
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">操作目录:&nbsp;&nbsp;<%=categoryname %></div>
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">
	         <button type="button" onclick="del();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
	         <button type="button" onclick="parent.clearDiv();">取消</button>
	      </div> 
      </html:form> 
  </body>
</html:html>

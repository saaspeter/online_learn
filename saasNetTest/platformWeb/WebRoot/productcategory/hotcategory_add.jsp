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
	   
	   function selectCate_CB(ids,names,param){
          if(ids!=null&&ids!=""){
             document.getElementById("Id_categoryname").value = names;
             document.getElementById("Id_categoryid").value = ids;
          }
          clearDiv();
       }
	   
	   function save(){
	      document.forms[0].submit();
	   }
	   
	</script>
  </head>
  <%
     String parentCategoryid = request.getParameter("parentCategoryid");
     String parentCategoryname = request.getParameter("parentCategoryname");
     String localeid = request.getParameter("localeid");
     if(parentCategoryid==null){
        parentCategoryid = "";
     }
     if(parentCategoryname==null){
        parentCategoryname = "";
     }
   %>
  <body>
      <div class="titleBar">添加热门目录</div>
  
      <html:form action="/productcategory/saveHotcategory.do" method="post">
          <input type="hidden" name="vo.pid" value="<%=parentCategoryid %>" />
          <input type="hidden" name="vo.localeid" value="<%=localeid %>" />
          <div style="height: 30px;"></div>
          <div style="padding-left: 40px;">添加热门目录级别:</div>
	      <div style="padding-left: 40px;">   
		     <select name="scope">
		        <option value="1">添加本国家热门目录设置</option>
		        <option value="2">添加到所有国家热门设置</option>
		     </select>
	      </div>
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">父目录:&nbsp;&nbsp;<%=parentCategoryname %></div>
	      
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">选择添加热门目录:</div>
	      <div style="padding-left: 40px;">
	         <input type="text" id="Id_categoryname" name="" class="readonly" value="" readonly="readonly" style="width:200px"/>
	         <input type="hidden" id="Id_categoryid" name="vo.categoryid" value="" />
	         <button type="button" onclick="newDiv('/productcategory/prodcate_select_tree.jsp?',1,1,300,400);">...</button>
	      </div> 
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">
	         <button type="button" onclick="save();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
	         <button type="button" onclick="parent.clearDiv();">取消</button>
	      </div> 
      </html:form> 
  </body>
</html:html>

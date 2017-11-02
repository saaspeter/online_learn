<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <%
       String showtype = request.getParameter("showtype");
       String userproductid = request.getParameter("userproductid");
       String url1 = WebConstant.WebContext+"/product/viewUserproduct.do?vo.userproductid="+userproductid;
       String url2 = WebConstant.WebContext+"/userproduct/listuserprodstatuslog.do?queryVO.userproductid="+userproductid;
       String url = url1;
       
       if("log".equals(showtype)){
    	   url = url2;
       }
       
    %>
    <title>单位人员信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   function switchtab(tabno){
		   var iframe = document.getElementById("contentIframeId");
		   var tabli1 = document.getElementById("tabId1");
		   var tabli2 = document.getElementById("tabId2");
		   if(tabno=='tabId1'){
		      if(tabli1.className!='selectTab'){
		         tabli1.className = 'selectTab';
		         tabli2.className = '';
		         iframe.src = "<%=url1 %>";
		      }
		   }else if(tabno=='tabId2'){
		      if(tabli2.className!='selectTab'){
		         tabli1.className = '';
		         tabli2.className = 'selectTab';
		         iframe.src = "<%=url2 %>";
		      }
		   }
		}
	   
	</script>
  </head>
  
  <body>
	<div class="editPage">

      <div style="height:35px; width:95%;">
	      <ul class="tabnav">
	          <li id="tabId1" <%if("product".equals(showtype)){out.print("class='selectTab'"); } %>><a href="javascript:void(0);" onclick="switchtab('tabId1');">用户课程信息</a></li>
	          <li id="tabId2" <%if("log".equals(showtype)){out.print("class='selectTab'"); } %>><a href="javascript:void(0);" onclick="switchtab('tabId2');">课程状态记录</a></li>
	      </ul>
	  </div>
	  
	  <div style="height:300px;padding-left:2px;">
	      <iframe id="contentIframeId" frameborder="0" style="border:0; width:99%;height:100%;overflow: auto;" src="<%=url %>"></iframe>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <button type="button" onclick="parent.clearDiv();return false;">关闭窗口</button>
	  </div>

	</div>
  </body>
</html:html>

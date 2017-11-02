<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <%
       String showtype = request.getParameter("showtype");
       String userid = request.getParameter("userid");
       String userproductid = request.getParameter("userproductid");
       String url1 = WebConstant.WebContext+"/orguser/viewdeptuser.do?queryVO.userid="+userid;
       String url2 = WebConstant.WebContext+"/product/edituserprod.do?vo.userproductid="+userproductid;
       String url3 = WebConstant.WebContext+"/userproduct/listuserprodstatuslog.do?queryVO.userid="+userid+"&queryVO.userproductid="+userproductid;
       String url = url1;
       
       if("product".equals(showtype)){
    	   url = url2;
       }else if("log".equals(showtype)){
           url = url3;
       }
       
    %>
    <title>单位人员信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<script type="text/javascript">
	   function switchtab(tabno){
		   var iframe = document.getElementById("contentIframeId");
		   var tabli1 = document.getElementById("tabId1");
		   var tabli2 = document.getElementById("tabId2");
		   var tabli3 = document.getElementById("tabId3");
		   if(tabno=='tabId1'){
		      if(tabli1.className!='selectTab'){
		         tabli1.className = 'selectTab';
		         tabli2.className = '';
		         tabli3.className = '';
		         iframe.src = "<%=url1 %>";
		      }
		   }else if(tabno=='tabId2'){
		      if(tabli2.className!='selectTab'){
		         tabli1.className = '';
		         tabli2.className = 'selectTab';
		         tabli3.className = '';
		         iframe.src = "<%=url2 %>";
		      }
		   }else if(tabno=='tabId3'){
		      if(tabli3.className!='selectTab'){
		         tabli1.className = '';
		         tabli2.className = '';
		         tabli3.className = 'selectTab';
		         iframe.src = "<%=url3 %>";
		      }
		   }
		}
		
		var flushparent = "0";
		function setflushflag(){
		    flushparent = "1";
		}
		
		function closeDivBtn(){
		   if(flushparent=='1'){
		      parent.location.reload();
		   }else {
		      parent.clearDiv();
		   }
		}
	   
	</script>
  </head>
  
  <body>
	<div class="editPage">
      
      <div style="width:100%;margin: 0px;height:1.5em">
	      <ul class="tabnav">
	          <li id="tabId1" <%if("user".equals(showtype)){out.print("class='selectTab'"); } %>><a href="javascript:void(0);" onclick="switchtab('tabId1');">用户信息</a></li>
	          <li id="tabId2" <%if("product".equals(showtype)){out.print("class='selectTab'"); } %>><a href="javascript:void(0);" onclick="switchtab('tabId2');">用户课程信息</a></li>
	          <li id="tabId3" <%if("log".equals(showtype)){out.print("class='selectTab'"); } %>><a href="javascript:void(0);" onclick="switchtab('tabId3');">课程状态记录</a></li>
	      </ul>
	  </div>
	  
	  <div style="margin: 0px;height:90%;">
	      <iframe id="contentIframeId" frameborder="0" style="border:0; width:100%;height:90%;overflow: auto;" src="<%=url %>"></iframe>
	  </div>
	  
	</div>
  </body>
</html:html>

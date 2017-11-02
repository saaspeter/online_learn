<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" media="screen" href="../../styles/css/tab/simpleTab2.css" />
	<script language=JavaScript src="../../styles/script/pageAction.js"></script>
	<% String shopid = request.getParameter("shopid");
	   String shopname = request.getParameter("shopname");
	   if(shopid==null){
		   shopid = "";
	   }
	   String url1=WebConstant.WebContext+"/shop/viewshopeditpage.do?queryVO.shopid="+shopid;
	   String url2=WebConstant.WebContext+"/shop/listshopcontactpublic.do?queryVO.shopid="+shopid; 
	   String url3=WebConstant.WebContext+"/shop/shopStatusChangePage.do?queryVO.shopid="+shopid; 
	   
	   String backurl = request.getParameter("backUrlEncode");
	%>
	<script language="javascript">
	<!--
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

	//-->
	</script>
</head>

<body>
   <div id="centerContent" style="width:80%;margin-left: 5%;margin-right: 10%;">
      <div id="functionLineDiv">
		  <button type="button" onclick="goUrl('<%=backurl %>');return false;">go back</button>
		  <span style="margin-left: 150px;"><%=shopname %></span>
	  </div>
      <div style="height:auto;">
		  <ul class="tabnav">
		    <li id="tabId1" class='selectTab'><a href="javascript:void(0);" onclick="switchtab('tabId1');">商店基本信息</a></li>
		    <li id="tabId2" class=''><a href="javascript:void(0);" onclick="switchtab('tabId2');">商店联系信息</a></li>
			<li id="tabId3" class=''><a href="javascript:void(0);" onclick="switchtab('tabId3');">修改商店状态</a></li>
		  </ul>
	  </div>
	  <div>
	      <iframe id="contentIframeId" frameborder="0" style="border:0; width:100%;height:100%;overflow: auto;" src="<%=url1 %>"></iframe>
	  </div>
   </div>
</body>
</html>
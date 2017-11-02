<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<html>
<head>
	<title>选择人员</title>
	<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/tab/simpleTab2.css" />
	<% String productid = request.getParameter("productid");
	   if(productid==null){
	      productid = "";
	   }
	   String url2=WebConstant.WebContext+"/org/selectorgusermain.do";
	   String url1=WebConstant.WebContext+"/userproduct/seluserbyproduct.do?queryVO.productbaseid="+productid; %>
	<script language="javascript">
	<!--
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
		
		function selUserCB(ids,names){
          if(ids!=null&&ids!=""){
             parent.selUserCB(ids,names);
          }
        }
        
        function doSelAllProdUser(productid){
           if(productid!=null&&productid!=''){
              parent.doSelAllProdUser(productid);
           }
        }
	//-->
	</script>
</head>

<body>
   <div id="centerContent">
      <div style="height:auto; width:100%;">
		  <ul class="tabnav">
		    <li id="tabId1" class='selectTab'><a href="javascript:void(0);" onclick="switchtab('tabId1');">学习课程人员选择</a></li>
			<li id="tabId2" class=''><a href="javascript:void(0);" onclick="switchtab('tabId2');">单位人员选择</a></li>
		  </ul>
	  </div>
	  <div>
	      <iframe id="contentIframeId" frameborder="0" style="border:0; width:100%;height:100%;overflow: auto;" src="<%=url1 %>"></iframe>
	  </div>
   </div>
</body>
</html>
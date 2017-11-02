<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.learncont.constant.LearncontentConstant, netTest.learncont.vo.Learncontent" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="contentVar" name="learncontentForm" property="vo.content"/>
<bean:define id="contentidVar" name="learncontentForm" property="vo.contentid"/>
<bean:define id="captionnameVar" name="learncontentForm" property="vo.caption" type="java.lang.String"/>
<%
   if(contentVar==null){
	   contentVar = "";
   }
   
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>学习内容</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link href="../styles/syntaxhighlighter/styles/shCore.css" rel="stylesheet" type="text/css" />
	<link href="../styles/syntaxhighlighter/styles/shThemeDefault.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/syntaxhighlighter/scripts/shCore.js"></script>
	<script type="text/javascript" src="../styles/syntaxhighlighter/scripts/shAutoloader.js"></script>

  </head>
  
  <body>
    <div>
    
		<div class="editPage">
		
		  <div id="fieldsTitleDiv"><bean:write name="learncontentForm" property="vo.caption"/>
		       <img src="../styles/imgs/ico/link_edit.png" style="width: 20px;height: 20px;" onclick="document.getElementById('pagelinkId').style.display='block';" title="显示本页站内链接地址，可在别的页面中引用">
		  </div>
		  <div id="pagelinkId" style="text-align: center;display: none;">/learncont/viewLearncontent.do?vo.contentid=<bean:write name="learncontentForm" property="vo.contentid"/></div>
		  <div id="errorDisplayDiv"></div>
		  <div style="width: 100%;text-align: center;"></div>
		  <p>
		  <div style="width:90%;margin:10px;text-align:left;overflow: auto;">
		  <%=contentVar %>
		  </div>
		  
		</div>
		
		<div style="width: 100%;text-align: center;margin-top:50px;">
	      <a href="javascript:void(0)" onclick="parentGo('<%=request.getContextPath() %>/learncont/autolearn.do?queryVO.productbaseid=<bean:write name="learncontentForm" property="vo.productbaseid"/>&objecttype=<%=Learncontent.ObjectType %>&objectid=<%=contentidVar %>');return false;">继续学习下一个</a>
	    </div>
	
	</div>
	<script type="text/javascript">
	
	   window.onload=function(){
		   backcallParent();
	   }
	
	   function backcallParent(){
		  if(typeof(parent.setProductBar)!='undefined'){
			  parent.setProductBar('<bean:write name="learncontentForm" property="vo.productname"/>');
		  }
	   }
	
	</script>
  </body>
</html:html>

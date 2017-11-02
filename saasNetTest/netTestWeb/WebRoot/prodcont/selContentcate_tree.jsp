<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="contentcateForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="selectType" name="contentcateForm" property="selectType" type="java.lang.String"/>
    <bean:define id="productbaseid" name="contentcateForm" property="queryVO.productbaseid" />
    <bean:define id="productName" name="contentcateForm" property="productName" />
    <title>课程章节</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
	<script type="text/javascript">
	    var retObj = new Object();
        <%if(selectType!=null&&"2".equals(selectType)){ %>
             inputType = "checkbox";
        <%}else if(selectType!=null&&"1".equals(selectType)){ %>
             inputType = "radio";
        <%} %>
        
        var param = '';
        <% if(request.getParameter("param")!=null&&request.getParameter("param").trim().length()>0){ %>
              param = '<%=request.getParameter("param") %>';
        <% } %>

		var treeUrl = "<%=WebConstant.WebContext %>/prodcont/genecontcatetree.do?queryVO.productbaseid=<%=productbaseid %>&queryVO.pid=";
		var tree = new WebFXLoadTree("<%=CommonConstant.TreeTopnodePid.toString() %>","<%=productName %>",treeUrl+<%=CommonConstant.TreeTopnodePid.toString() %>);
		
		function doSelect(){
           retObj = getCheckedValues(tree);
           if(retObj.ids==""){
              alert(getMessage("selectOneMsg"));
              return;
           }
           if(typeof(parent.selContcateCB)!="undefined"){
		      parent.selContcateCB(retObj.ids,retObj.names,param);
		   }
        }

	</script>
  </head>
  
  
  <body>
    
	<div id="funDiv">
	    <button type="button" onclick="doSelect();return false;">确定</button>&nbsp;&nbsp;&nbsp;
	    <button type="button" onclick="parent.clearDiv();return false;">取消</button>
	</div>
	<div id="treeDiv">
	   <script type="text/javascript">
		
		document.write(tree);
		tree.select();
		
		function linkAction(){

		}
	   </script>
	</div>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant,commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>产品目录树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
  </head>
  <%
    String syscode = null;
    if(request.getParameter("syscode")!=null&&request.getParameter("syscode").trim().length()>0)
      syscode = request.getParameter("syscode").trim();
    String syscodeParam = "";
    if(syscode!=null&&syscode.trim().length()>0)
       syscodeParam = "&syscode="+syscode;
   %>
  <body>
    <input type="hidden" id="rightUrl" value="/productcategory/productcategory.do?method=list&queryVO.pid=">
	
	<div id="treeDiv">
	<script type="text/javascript">
		var treeUrl = "<%=WebConstant.WebContext %>/productcategory/productcategory.do?method=geneTreeXml<%=syscodeParam %>&queryVO.pid=";
		var tree = new WebFXLoadTree("<%=CommonConstant.TreeTopnodePid.toString() %>","目录列表",treeUrl+'<%=CommonConstant.TreeTopnodePid.toString() %>');
		document.write(tree);
		//tree.select();
		
		function linkAction(){
		   parent.onclickTree(tree.getSelected().pk,tree.getSelected().text);
		}
		
	</script>
	</div>
  </body>
</html:html>

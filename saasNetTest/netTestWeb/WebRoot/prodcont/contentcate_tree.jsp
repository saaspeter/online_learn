<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>课程内容目录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
  </head>
  <bean:define id="productbaseid" name="contentcateForm" property="queryVO.productbaseid"></bean:define>
  <bean:define id="localeid" name="contentcateForm" property="localeid"></bean:define>
  <bean:define id="productName" name="contentcateForm" property="productName"></bean:define>
  <bean:define id="listType" name="contentcateForm" property="listType"></bean:define>
  <bean:define id="selectType" name="contentcateForm" property="selectType"></bean:define>
  <% String url = "/prodcont/listContentcatemag.do";
     if(listType!=null&&"2".equals(listType)){
        url = "/prodcont/listContentcatesel.do";
     }
   %>
  <body>
    <input type="hidden" id="rightUrl" value="<%=url %>?queryVO.productbaseid=<%=productbaseid %>&selectType=<%=selectType %>&queryVO.pid=">
	<input type="hidden" name="queryVO.productbaseid" value="<%=productbaseid %>" />
	<input type="hidden" name="localeid" value="<%=localeid %>" />
	<div id="funDiv">
	    <button type="button" onclick="goUrl('/prodcont/toContcateTree.do?queryVO.productbaseid=<%=productbaseid %>&selectType=<%=selectType %>&localeid=<%=localeid %>');return false;">刷新</button>
	</div>
	<div id="treeDiv">
	<script type="text/javascript">
		// webFXTreeConfig.rootIcon		= "images/xp/folder.png";
		// webFXTreeConfig.openRootIcon	= "images/xp/openfolder.png";
		// webFXTreeConfig.folderIcon		= "images/xp/folder.png";
		// webFXTreeConfig.openFolderIcon	= "images/xp/openfolder.png";
		// webFXTreeConfig.fileIcon		= "images/xp/file.png";
		// webFXTreeConfig.lMinusIcon		= "images/xp/Lminus.png";
		// webFXTreeConfig.lPlusIcon		= "images/xp/Lplus.png";
		// webFXTreeConfig.tMinusIcon		= "images/xp/Tminus.png";
		// webFXTreeConfig.tPlusIcon		= "images/xp/Tplus.png";
		// webFXTreeConfig.iIcon			= "images/xp/I.png";
		// webFXTreeConfig.lIcon			= "images/xp/L.png";
		// webFXTreeConfig.tIcon			= "images/xp/T.png";

		var treeUrl = "<%=WebConstant.WebContext %>/prodcont/genecontcatetree.do?queryVO.productbaseid=<%=productbaseid %>&queryVO.pid=";
		var tree = new WebFXLoadTree("<%=CommonConstant.TreeTopnodePid.toString() %>","<%=productName %>",treeUrl+<%=CommonConstant.TreeTopnodePid.toString() %>);
		document.write(tree);
		tree.select();
		
		function linkAction(){
		   showRightFromTree(tree.getSelected().pk,tree.getSelected().text);
		}
	</script>
	</div>
  </body>
</html:html>

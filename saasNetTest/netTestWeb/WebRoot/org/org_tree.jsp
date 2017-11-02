<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>机构目录树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>

  </head>
  
  <body>
    <input type="hidden" id="selectNode" value="<bean:write name="orgbaseForm" property="vo.orgbaseid"/>" />
    <input type="hidden" id="rightUrl" value="<%=WebConstant.WebContext %>/org/listdeptinfo.do?queryVO.pid=" />
	<div id="funDiv">
	    <button type="button" onclick="goUrl('/org/orgtreemag.do?queryVO.pid=<bean:write name="orgbaseForm" property="queryVO.pid"/>&selectType=<bean:write name="orgbaseForm" property="selectType"/>&orgRange=<bean:write name="orgbaseForm" property="orgRange"/>');return false;">刷新</button>
	</div>
	<div id="treeDiv">

	<script type="text/javascript">
		webFXTreeConfig.rootIcon		= "../styles/imgs/tree/shop.gif";
		webFXTreeConfig.openRootIcon	= "../styles/imgs/tree/shop.gif";
		webFXTreeConfig.folderIcon		= "../styles/imgs/tree/xp/folder.png";
		webFXTreeConfig.openFolderIcon	= "../styles/imgs/tree/xp/openfolder.png";
		webFXTreeConfig.fileIcon		= "../styles/imgs/tree/xp/folder.png";
		// webFXTreeConfig.lMinusIcon		= "images/xp/Lminus.png";
		// webFXTreeConfig.lPlusIcon		= "images/xp/Lplus.png";
		// webFXTreeConfig.tMinusIcon		= "images/xp/Tminus.png";
		// webFXTreeConfig.tPlusIcon		= "images/xp/Tplus.png";
		// webFXTreeConfig.iIcon			= "images/xp/I.png";
		// webFXTreeConfig.lIcon			= "images/xp/L.png";
		// webFXTreeConfig.tIcon			= "images/xp/T.png";	
		var treeUrl = "<%=WebConstant.WebContext %>/org/geneorgtreexml.do?treeAction=<bean:write name="orgbaseForm" property="treeAction"/>&vo.pid=";
		var tree = new WebFXLoadTree("<bean:write name="orgbaseForm" property="vo.orgbaseid"/>","<bean:write name="orgbaseForm" property="vo.orgname"/>", treeUrl+'<bean:write name="orgbaseForm" property="vo.orgbaseid"/>');
		
		document.write(tree);
		tree.select();
		
		function linkAction(){
		   parent.showRight(tree.getSelected().pk,tree.getSelected().text);
		   return ;
		}
	</script>
	</div>
  </body>
</html:html>

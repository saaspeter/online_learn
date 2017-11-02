<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>资源目录树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
  </head>
 
  <% String action = "listResources.do?"; 
     String rescListType = request.getParameter("rescListType");
     if(rescListType!=null&&"1".equals(rescListType)){
        String roleId = request.getParameter("roleId");
        action = "listRoleResc.do?"+"rolesVO.id="+roleId+"&";
     }
  %>
  <body>
    <input type="hidden" id="rightUrl" value="/securityManage/<%=action %>queryVO.pid=">
	<div id="funDiv">
	    <button type="button" onclick="goUrl('/securityManage/toRescfoldTreePage.do');return false;">刷新</button>
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
		var treeUrl = webContext+"/securityManage/toRescfoldTreeXml.do?queryVO.pid=";
		var tree = new WebFXLoadTree("<%=CommonConstant.TreeTopnodePid.toString() %>","全部菜单",treeUrl+<%=CommonConstant.TreeTopnodePid.toString() %>);
		document.write(tree);
		tree.select();
		showRightFromTree(<%=CommonConstant.TreeTopnodePid.toString() %>,'');
		
		function linkAction(){
		   showRightFromTree(tree.getSelected().pk,tree.getSelected().text);
		}
	</script>
	</div>
  </body>
</html:html>

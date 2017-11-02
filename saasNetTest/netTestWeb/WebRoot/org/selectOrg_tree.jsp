<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="orgbaseForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="selectType" name="orgbaseForm" property="selectType" type="java.lang.String"></bean:define>
    <title>机构目录树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
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
        // define tree
        webFXTreeConfig.rootIcon		= "../styles/imgs/tree/shop.gif";
		webFXTreeConfig.openRootIcon	= "../styles/imgs/tree/shop.gif";
		webFXTreeConfig.folderIcon		= "../styles/imgs/tree/xp/folder.png";
		webFXTreeConfig.openFolderIcon	= "../styles/imgs/tree/xp/openfolder.png";
		webFXTreeConfig.fileIcon		= "../styles/imgs/tree/xp/folder.png";

		var treeUrl = "<%=WebConstant.WebContext %>/org/geneorgtreexml.do?treeAction=<bean:write name="orgbaseForm" property="treeAction"/>&vo.pid=";
		var tree = new WebFXLoadTree("<bean:write name="orgbaseForm" property="vo.orgbaseid"/>","<bean:write name="orgbaseForm" property="vo.orgname"/>", treeUrl+'<bean:write name="orgbaseForm" property="vo.orgbaseid"/>');
        // end define tree
        
        function doSelect(){
           retObj = getCheckedValues(tree);
           if(retObj.ids==""){
              alert(getMessage("selectOrg_tree_selOneDept"));
              return;
           }
           if(typeof(parent.selectOrg_CB)!="undefined"){
		      parent.selectOrg_CB(retObj.ids,retObj.names,param);
		   }
        }
        
    </script>
  </head>
  
  <body>
    <input type="hidden" id="selectNode" value="<bean:write name="orgbaseForm" property="vo.orgbaseid"/>">

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

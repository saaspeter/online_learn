<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="topcategoryid" name="productcategoryForm" property="vo.categoryid" type="java.lang.Long"></bean:define>
<bean:define id="topcategoryname" name="productcategoryForm" property="vo.categoryname" type="java.lang.String"></bean:define>

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

  <body>
    <input type="hidden" id="rightUrl" value="/productcategory/listproductcategory.do?queryVO.pid=">
	
	<div id="treeDiv">
	<script type="text/javascript">
		var treeUrl = "<%=WebConstant.WebContext %>/productcategory/prodcategorytree.do?queryVO.pid=";
		var tree = new WebFXLoadTree("<%=topcategoryid %>","<%=topcategoryname %>",treeUrl+'<%=topcategoryid %>');
		document.write(tree);
		//tree.select();
		
		function linkAction(){
		   var pkk = tree.getSelected().pk;
		   if(pkk!='<%=topcategoryid %>'){
		      parent.onclickTree(pkk, tree.getSelected().text);
		   }
		}
	</script>
	</div>
	<div style="margin-top:20px;margin-left:10px;font-size: smaller;">
	      <a href="mailto:service.tomylearn@outlook.com?subject=发送电子邮件申请新目录" title="发邮件到service.tomylearn@outlook.com申请">没有想要的目录?</a>
	</div>
  </body>
</html:html>

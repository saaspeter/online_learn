<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="topcategoryid" name="productcategoryForm" property="vo.categoryid" type="java.lang.Long"></bean:define>
<bean:define id="topcategoryname" name="productcategoryForm" property="vo.categoryname" type="java.lang.String"></bean:define>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="localeid" name="productcategoryForm" property="localeid" type="java.lang.Long"/>
    <title>产品目录树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
	<script language=JavaScript src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    function loadpage(selObj){
	       if(selObj==null){
	    	   document.location.reload();
           }
           var old_localeid = '<%=localeid %>';
           var itemValue = selObj.options[selObj.selectedIndex].value;
           if(itemValue==old_localeid){ return; }
           var url = '/productcategory/toprodcategorytreepage.do'+
        		     '?syscode=<%=WebConstant.SysCode_Platform %>&editType=<%=WebConstant.editType_view %>&localeid='
        		     +itemValue;
           goUrl(url);
	    }
	</script>
  </head>

  <body>
    <input type="hidden" id="rightUrl" value="/productcategory/listproductcategory.do?queryVO.pid=">
	<div id="funDiv">
	    <html:select name="productcategoryForm" property="localeid" onchange="loadpage(this)">
		    <html:optionsSaas localeListSet="true"/>
	    </html:select>
	    <button type="button" onclick="loadpage(null);">刷新</button>
	</div>
	<div id="treeDiv">
	<script type="text/javascript">
		var treeUrl = '<%=WebConstant.WebContext+"/productcategory/prodcategorytree.do?inadminuse=1&localeid="+localeid+"&queryVO.pid=" %>';
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
  </body>
</html:html>

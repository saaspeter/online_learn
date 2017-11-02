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
    <% String selectType = request.getParameter("selectType"); %>
    <title>产品目录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
	<script type="text/javascript">
	    checkParent = 1;
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
        // end define tree
	    
	    function doSelect(){
           retObj = getCheckedValues(tree);
           if(retObj.ids==""){
              alert(getMessage("selectOneMsg"));
              return;
           }
           if(typeof(parent.selectCate_CB)!="undefined"){
		      parent.selectCate_CB(retObj.ids,retObj.names,param);
		   }
        }
	</script>
  </head>
  
  <body>
    <input type="hidden" id="rightUrl" value="/productcategory/listproductcategory.do?queryVO.pid=">
    <div id="funDiv">
	    <button type="button" onclick="doSelect();return false;">确定</button>&nbsp;&nbsp;&nbsp;
	    <button type="button" onclick="parent.clearDiv();return false;">取消</button>
	</div>
    
	<div id="treeDiv">
	<script type="text/javascript">
        
		var treeUrl = "<%=WebConstant.WebContext %>/productcategory/prodcategorytree.do?localeid=<%=localeid %>&queryVO.pid=";
		var tree = new WebFXLoadTree("<%=topcategoryid %>","<%=topcategoryname %>",treeUrl+'<%=topcategoryid %>');
		document.write(tree);
		tree.select();
		
		function linkAction(){
			var pkk = tree.getSelected().pk;
		}
		
	</script>
	</div>
	<div style="margin-top:20px;margin-left:10px;font-size: smaller;">
	      <a href="mailto:service.tomylearn@outlook.com?subject=发送电子邮件申请新目录" title="发邮件到service.tomylearn@outlook.com申请">没有想要的目录?</a>
	</div>
  </body>
</html:html>

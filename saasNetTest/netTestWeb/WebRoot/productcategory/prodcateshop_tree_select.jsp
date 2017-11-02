<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <%String shopidVar = request.getParameter("vo.shopid"); %>
    <title>选择课程目录</title>
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
		inputType = "radio";
	    
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
    
    <div id="funDiv">
	    <button type="button" onclick="doSelect();return false;">确定</button>&nbsp;&nbsp;&nbsp;
	    <button type="button" onclick="parent.clearDiv();return false;">取消</button>
	</div>
    
	<div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
    </div>
	
	<div id="treeDiv">
	<script type="text/javascript">
		var treeUrl = "<%=WebConstant.WebContext %>/productcategory/shoprodcateTree.do?vo.shopid=<%=shopidVar %>&vo.pid=";
		var tree = new WebFXLoadTree("<%=CommonConstant.TreeTopnodePid.toString() %>","课程目录",treeUrl+'<%=CommonConstant.TreeTopnodePid.toString() %>');
		document.write(tree);
		tree.select();
		
		function linkAction(){
		   
		}
		
	</script>
	</div>
  </body>
</html:html>

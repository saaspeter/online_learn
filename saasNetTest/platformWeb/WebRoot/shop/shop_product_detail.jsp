<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本店课程</title>
<html:base/>
<%String shopidVar = request.getParameter("shopid"); %>
<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
<script language=JavaScript src="../styles/script/pageAction.js"></script>
<script type="text/javascript" src="../styles/script/xtree.js"></script>
<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
<script type="text/javascript">
function linkUrl(url,id){
    if(typeof(tree)!='undefined'&&tree.getSelected()!=null){
       tree.getSelected().deSelect();
    }
    document.getElementById("bestsellId").className="";
    document.getElementById("freeId").className="";
    document.getElementById(id).className="selectA";
	var iframe = document.getElementById("contentIframeId");
	iframe.src = url;
    
}

</script>

<style type="text/css">
<!--

.selectA{
   display: block;
   color: selectedtext;
   background: selected;
   background-color:#CCCCFF;
}

.titleBarTop{
	background-color: #DCEAFC;
    width:100%;
    height:22px;
    font-weight: bold;
    border-top:1px solid #0000FF;
}

#content_all{
  width:900px;
  margin:0 auto;
  margin-left: 15px;
}

#leftbar{
   width:160px;
   height:500px;
   float:left;
   border-right: 1px solid #CCCCCC;
   margin-right: 10px;
}

#leftbar ul {
   margin:0px;
}

#leftbar ul li {
	clear:left;
	margin-top:5px;
	margin-left: 5px;
}

#maincontent{
    float:left;
    width: 600px;
}
-->
</style>
</head>
<body>
<input type="hidden" id="rightUrl" value="<%=WebConstant.WebContext %>/product/listshopproduct.do?queryVO.shopid=<%=shopidVar %>&queryVO.categoryid=">
<div id="content_all">
	<jsp:include flush="true" page="shop_prodcategory_left.jsp"></jsp:include>
	<div id="maincontent">  
	    <iframe id="contentIframeId" frameborder="0" style="border: 0;width: 750px;height: 500px;" src="<%=WebConstant.WebContext %>/product/viewCourseToBuy.do?shopid=<bean:write name="vo" property="shopid"/>&queryVO.productbaseid=<bean:write name="vo" property="productbaseid"/>"></iframe>
	</div>
</div>
</body>
</html>
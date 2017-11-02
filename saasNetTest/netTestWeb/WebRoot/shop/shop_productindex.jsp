<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,netTest.product.constant.ProductConstant"%>
<%@ include file="/pubs/taglibs.jsp" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="netTest.page.common.title"/></title>
<html:base/>
<% String shopidVar = request.getParameter("shopid"); %>
<% 
   String productidVar = request.getParameter("productid");
   String contenturl = "/"+CommonConstant.WebContext_Education+"/product/listshopopenproduct.do?queryVO.promotable="+ProductConstant.Promotable_yes+"&queryVO.shopid="+shopidVar+"&title=推荐课程";
   if(productidVar!=null&&productidVar.trim().length()>0){
       contenturl = "/"+CommonConstant.WebContext_Education+"/product/viewCourseToBuy.do?shopid="+shopidVar+"&queryVO.productbaseid="+productidVar;
   }
%>
<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
<link href="/<%=CommonConstant.WebContext_Education %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<style type="text/css">
<!--

*{
   margin: 0px;
}

#menu2{
  display: block;
  color: #667;
  background-color: #ec8;
}

#leftbar1{
   float:left;
   width:18%;
   height: 500px;
   overflow: auto;
}

.selectA{
   display: block;
   color: selectedtext;
   background: selected;
   background-color:#CCCCFF;
}

#maincontent1{
    float:left;
    width: 80%;
    margin-left: 5px;
}

iframe { 
    min-height: 700px;
    height: 700px;
}
-->
</style>
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

</head>
<body>
<input type="hidden" id="rightUrl" value="/<%=CommonConstant.WebContext_Education %>/product/listshopopenproduct.do?queryVO.shopid=<%=shopidVar %>&queryVO.categoryid=">
<div class="col-xs-12 col-md-9 center-block">

    <jsp:include flush="true" page='<%="banner_shop.jsp?shopid="+shopidVar %>'></jsp:include>

	<div style="height:15px; clear:both;"></div>
	
	<div>
		<div id="leftbar1">
		    <jsp:include flush="true" page='<%="shop_prodcategory_left.jsp?shopid="+shopidVar %>'></jsp:include>
		</div>
		<div id="maincontent1">  
		    <iframe id="contentIframeId" frameborder="0" style="border:0; width:100%;height:100%;overflow: no;" src="<%=contenturl %>"></iframe>
		</div>
	</div>

    <jsp:include flush="true" page="../foot.jsp"></jsp:include>
</div>
<script type="text/javascript">
function adjustFrameHeight(inputHeight) { 
	 var ifm= document.getElementById("contentIframeId"); 
	 var heightVar = 0;
	 if(typeof(inputHeight)!='undefined' && inputHeight!=null){
		 heightVar = inputHeight;
	 }else {
		 heightVar = 1000;
	 }
	 if(ifm != null && heightVar != null) {
	    ifm.style.height = (heightVar+270)+'px';
		document.getElementById("maincontent1").style.height = (heightVar+230)+'px';
   } 
	
}  
</script>
</body>
</html>
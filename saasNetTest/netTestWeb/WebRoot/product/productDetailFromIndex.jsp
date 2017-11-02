<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="netTest.page.common.title"/></title>
<html:base/>
<% String shopidVar = request.getParameter("shopid"); 
   String productidVar = request.getParameter("productid");
   String contenturl = "";
   if(productidVar!=null&&productidVar.trim().length()>0){
       contenturl = WebConstant.WebContext+"/product/viewCourseToBuy.do?shopid="+shopidVar+"&queryVO.productbaseid="+productidVar;
   }
%>
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/banner.css" />
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>

	<style type="text/css">
	<!--
	
	#menu3{
	  display: block;
	  color: #667;
	  background-color: #ec8;
	}
	
	#content_all{
	  margin:0 auto;
	  margin-left: 15px;
	}
	
	#leftbar{
	   width:14%;
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
	    width: 84%;
	    height: 600px;
	    min-height: 600px;
	}
	
	iframe { 
	    min-height: 700px;
	    height: 700px;
	}
	
	-->
	</style>

	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/shopcheck.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
</head>
<body>
<div class="col-xs-12 col-md-9 center-block">
	<jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
	
	<div class="navlistBar">
	    培训课程 &gt; 查看课程详细信息
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div id="content_all">
		<div id="leftbar">
		   <table style="text-align: left;border: 0px;margin-top: 50px;">
		       <tr>
		           <td>课程所属商店:</td>
		       </tr>
		       <tr>
		           <td id="shopnameTrId"></td>
		       </tr>
		       <tr>
		           <td style="padding-top: 30px;"><a href="<%=WebConstant.WebContext %>/shop/shop_productindex.jsp?shopid=<%=shopidVar %>">查看该商店其他课程</a></td>
		       </tr>
		   </table>
		</div>
		<div id="maincontent">  
		    <iframe id="contentIframeId" frameborder="0" style="border: 0;width:100%;height:100%;overflow: auto;" src="<%=contenturl %>"></iframe>
		</div>
	</div>
	
	<jsp:include flush="true" page="/foot.jsp"></jsp:include>
</div>
<script type="text/javascript">
	shopcheck.getShopMini(<%=shopidVar %>,null,function CB_get(rtn){
        document.getElementById("shopnameTrId").innerHTML=rtn.shopname;
     });
	
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
			document.getElementById("maincontent").style.height = (heightVar+230)+'px';
	     } 
	}  
</script>
</body>
</html>
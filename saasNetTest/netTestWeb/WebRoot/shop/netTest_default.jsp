<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试系统管理</title>
<html:base/>
<%   
   String shopidVar = request.getParameter("shopid"); 
%>
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
		<!--
		
		*{
		   margin: 0px;
		}
		
		#menu5{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
		#leftbar1{
		   float:left;
		   width: 13%;
		   min-height: 520px;
		}
		
		#maincontent1{
		    float:left;
		    width: 85%;
		    min-height: 700px;
		    height: 700px;
		    border-left: 1px #cccccc solid;
		    margin-left: 6px;
		}
		
		#maincontent1 iframe{
	        width:100%;
	        border:none;
	        overflow: auto;
	        min-height:700px; /* for modern browsers */

        }
		-->
	</style>

	<script type="text/javascript" src="../styles/script/vendor/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/menu.js"></script>
	<script type="text/javascript">
	 function toRightUrl(mainUrl){
	    mainUrl = doContextUrl(mainUrl);
	    document.getElementById("contentIframeId").src=mainUrl;
	 }
	</script>
</head>
<body>
<div class="col-xs-12 col-md-9 center-block">

    <jsp:include flush="true" page='<%="../shop/banner_shop.jsp?shopid="+shopidVar %>'></jsp:include>

	<div style="height:15px; clear:both;"></div>

	<div>
		<div id="leftbar1">
		    <jsp:include page="../leftbar_netTest.jsp"></jsp:include>
		</div>
		
		<div id="maincontent1">  
		    <iframe id="contentIframeId" frameborder="0" src="" scrolling="auto"></iframe>
		</div>
	</div>
	
	<jsp:include flush="true" page="../foot.jsp"></jsp:include>
</div>
    <script type="text/javascript">
    function iFrameHeight(iframeid, contentdivId) {   
		 var ifm= document.getElementById(iframeid); 
  	     var subWeb = ifm.contentDocument;   
		 if(typeof(subWeb)=='undefined'){
		    subWeb = ifm.document;
		 }
	 	 if(ifm != null && subWeb != null) {
		    var finalHeight = subWeb.body.scrollHeight;
		    ifm.height = finalHeight;
		    finalHeight = finalHeight + 15;
		    finalHeight = finalHeight + "px";
		    document.getElementById(contentdivId).style.height = finalHeight;
		 }
	  }   
	  
	  window.onload = function(){
		  var firsturl = '<%=request.getContextPath() %>'+getFirstUrl();
		  document.getElementById("contentIframeId").src = firsturl;
		  iFrameHeight('contentIframeId','maincontent1');
	  };
        
	</script>
</body>

</html>
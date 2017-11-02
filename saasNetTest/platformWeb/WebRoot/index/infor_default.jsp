<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/banner.css" />
<style type="text/css">
<!--

#menu1{
  display: block;
  color: #667;
  background-color: #ec8;
}

#leftAD {
	float:left;
	background-color:#FFFFFF;
	margin-top:5em;
	width: 120 px;
}

#content {
    width:1000px;
    left: 50%;
	position:relative;
	clear: both;
	margin-left: -500px;
}

#rightAD {
	height:800px;
	float:right;
	clear:right;
	margin-top:5em;
	background-color:#FFFFFF;
}

#maincontent{
   width:790px;
   float:right;
   text-align: left;
   border:1px solid #cccccc;
}

#leftbar{
   width:200px;
   height:500px;
   float:left;
}

#first {
	
}

#first div.off {
	height:33px;
	background:url(<%=WebConstant.WebContext %>/styles/imgs/tab/tabs_0.gif) repeat-x bottom;
	border:1px #cccccc solid;
	
	float:left;
	cursor:pointer;
	border-bottom-color:#000;
	line-height:32px;
	z-index:40;
	position:relative;
	text-align: center;
}

#first div.on {
	height:33px;
	position:relative;
	padding-top:1px;
	background:url(<%=WebConstant.WebContext %>/styles/imgs/tab/tabs_2.gif) repeat-x bottom;
	float:left;
	cursor:pointer;
	border:1px #cccccc solid;
	
	border-bottom:0;
	z-index:100;
	line-height:33px;
	text-align: center;
}

div.hide {
	width:0px;
	display:none;
	overflow:hidden;
}

div.show {
	clear:left;
	width:180px;
	height:430px;
	padding:5px;
	top:-1px;
	position:relative;
	border:1px solid #cccccc;
	z-index:50;
	overflow:auto;
}

.clear {
	clear:both;
}

-->
</style>

<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/tab/simpleTab1.js"></script>
<script type="text/javascript">
   function onclickTree(id,name){     
      switchCategory_CB(id, name);
   }
   
   function switchCategory_CB(id, name){
      if(id==null||id==""||id=='-1'){
         return;
      }
      document.location.href = '<%=WebConstant.WebContext %>/index/listInfonews.do?categoryid='+id+'&categoryname='+name;
   }
</script>
</head>

<body>

<div id="content">

<jsp:include flush="true" page="banner.jsp"></jsp:include>

<div style="height:10px; clear:both;"></div>

<div style="height:auto; width:100%; text-align: left">
	<div id="leftbar">
	     <div id="first">
	        <div class="on" style="width:94px" id="simpletab1">热门目录</div>
			<div class="off" style="width:94px" id="simpletab2">全部目录</div>
		 </div>
		 <div class="show" id="simpletab1_cont">
	         <iframe frameborder="0" width="100%" height="100%" src="<%=WebConstant.WebContext %>/productcategory/listHotcategory.do?tourl=<%=WebConstant.WebContext %>/index/listInfonews.do" scrolling="auto"></iframe>
	     </div>
		 <div class="hide" id="simpletab2_cont">
	         <iframe frameborder="0" width="100%" height="100%" src="<%=WebConstant.WebContext %>/productcategory/productcategory_tree.jsp" scrolling="auto"></iframe>
	     </div>
	</div>
    <div id="maincontent" >ggggggggggggggggggggggggg</div>
</div>

</div>

<div style="height:30px; clear:both;"></div>

<jsp:include flush="true" page="foot.jsp"></jsp:include>

</body>
</html>

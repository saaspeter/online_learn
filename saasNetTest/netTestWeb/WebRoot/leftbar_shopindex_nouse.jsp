<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>考试平台功能栏</title>
  <link rel="stylesheet" type="text/css" href="styles/css/leftMenu.css">
  <script type="text/javascript" src="styles/script/pageAction.js"></script>
  <script type="text/javascript" src="styles/script/menu.js"></script>
  
  <style type="text/css">
	<!--
	
	.active{
		font-weight: bold;
		color: #0000FF;
	}
	.normal{
		font-weight: normal;
		color: #000000;
	}
	
	.titleBarTop{
		background-color: #DCEAFC;
	    width:100%;
	    height:22px;
	    font-weight: bold;
	    border-top:1px solid #0000FF;
	}
	
	.moreStyle{
	    font-size: small;
	    text-align: right;
	    padding-right: 15px;
	    list-style: none;
	}
	
	#content_all{
	  width:900px;
	  margin:0 auto;
	  margin-left: 15px;
	}
	
	#leftbar{
	   width:200px;
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

	<div id="leftbar">
	    <div style="height:20px"></div>
	    <div style="height:120px">
	       <div class="titleBarTop">布告栏</div>
	       <div>
	          <ul>
	             <li><a href="">最新通知</a></li>
	             <li><a href="">商店简介</a></li>
	          </ul>
	       </div>
	    </div>
	    <div style="height:120px">
	       <div class="titleBarTop">课程表</div>
	       <div>
	          <ul>
	             <li><a href="">课程1</a></li>
	             <li><a href="">课程2</a></li>
	             <li class="moreStyle"><a href="">&nbsp;更多</a></li>
	          </ul>
	       </div>
	    </div>
	    <div style="height:120px">
	       <div class="titleBarTop">不知道</div>
	       <div>
	       1234455
	       </div>
	    </div>
	</div>

</body>
</html>
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店介绍</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script type="text/javascript" src="../../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	  <div id="fieldsTitleDiv">您还没有添加: 教师介绍</div>
      <div>
          <button style="font-size: larger;" onclick="goUrl('/shop/addshopdescarticle.do');return false;">添加介绍</button>
      </div>
	  
	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>

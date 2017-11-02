<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style type="text/css">

#bannermenu1{
  display: block;
  color: #667;
  background-color: #ec8;
}

</style>
</head>
<body>

<div id="content">

	<jsp:include flush="true" page="banner_user.jsp"></jsp:include>

	<div class="navlistBar">
	    我的首页
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;height: 25px;">
	    <form action="" method="get">
	       <input type="text" name="productname" style="width:300px;height: 20px;" value=""/> 
	       <button type="button" onclick="document.forms[0].submit();">搜索</button>
	    </form>
	</div>

    <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	</div>
     
    <div style="width: 100%;margin-bottom: 10px;">
	    1234
    </div>
    
</div>

<div style="height:30px; clear:both;"></div>

<jsp:include flush="true" page="../index/foot.jsp"></jsp:include>

</body>
</html>
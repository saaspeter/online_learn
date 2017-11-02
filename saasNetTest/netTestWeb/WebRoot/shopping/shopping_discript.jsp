<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物指南</title>
<style type="text/css">
<!--
.STYLE1 {font-size: x-large}
.STYLE2 {
	color: #74BC43;
	font-weight: bold;
	font-size: x-large;
}
-->
</style>
</head>

<body>
<div style="width:100%;height:100px;">
  
</div>
<div style="width:100%;height:100px;">
  <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td align="center">&nbsp;</td>
      <td align="center" bgcolor="#FFFF99"><p>第一步：搜索商品，</p>
      <p> 加入购物车</p></td>
      <td align="center"><span class="STYLE2">----&gt;</span></td>
      <td align="center" bgcolor="#FFFF99"><p>第二步：查看购物车，</p>
      <p>生成订单</p></td>
      <td align="center"><span class="STYLE2">----&gt;</span></td>
      <td align="center" bgcolor="#FFFF99"><p>第三步：查看订单，</p>
      <p>购物结账</p></td>
      <td align="center"><span class="STYLE2">----&gt;</span></td>
      <td align="center" bgcolor="#FFFF99">第四步：使用商品</td>
    </tr>
  </table>
</div>
<div style="width:100%;height:70px;">
  
</div>
<div style="width:100%;height:100px;text-align:center">
   <a target="_new" href="<%=WebConstant.WebContext %>/shopping/searchProductList.do" class="STYLE1">现在开始订购</a></div>
<div>
  
</div>
</body>
</html>
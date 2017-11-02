<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%
   String shopid = request.getParameter("shopid");
   if(request.getAttribute("shopid")!=null){
      shopid = String.valueOf(((Long)request.getAttribute("shopid")).longValue());
   }
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">


</script>

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

#leftbar{
   width:200px;
   height:490px;
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

-->
</style>
</head>
<body>

	<div id="leftbar">
	    <div style="height:80px">
	       <div class="titleBarTop">布告栏</div>
	       <div>
	          <ul>
	             <li><a href="<%=WebConstant.WebContext %>/shop/shoppostviewlist.do?queryVO.shopid=<%=shopid %>" >最新通知</a></li>
	          </ul>
	       </div>
	    </div>
	    <div style="height:120px">
	       <div class="titleBarTop">商店信息</div>
	       <div>
	          <ul>
	             <li><a href="javascript:void(0);" style="">商店介绍</a></li>
	             <li><a href="javascript:void(0);">师资力量</a></li>
	             <li><a href="javascript:void(0);">联系地址</a></li>
	             <li class="moreStyle"><a href="">&nbsp;更多</a></li>
	          </ul>
	       </div>
	    </div>
	    <div style="height:120px">
	       <div class="titleBarTop">友情链接</div>
	       <div>
	       1234455
	       </div>
	    </div>
	</div>

</body>
</html>
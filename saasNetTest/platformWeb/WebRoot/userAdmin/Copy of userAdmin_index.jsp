<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8" />
<title>个人天地</title>

<script type="text/javascript">
function nTabs(thisObj,tag){
	var tabList = document.getElementById("maincontent").childNodes;
	for(i=0; i <tabList.length; i++)
	{
		if (tabList[i].id == tag)
		{   
			thisObj.className = "active"; 
    		document.getElementById(tabList[i].id).style.display = "block";
		}else{
			document.getElementById(tabList[i].id+"_bar").className = "normal"; 
			document.getElementById(tabList[i].id).style.display = "none";
		}
	} 
}
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

#top{
	width:900px;
	margin:0 auto;
	border: 1px solid #dddddd;
	margin-bottom: 2px;
}
#content{
  width:900px;
  margin:0 auto;
}
#leftbar{
   width:100px;
   height:450px;
   float:left;
   border:1px solid #cccccc;
}
#leftbar ul {
   list-style:none;margin:0px;
}
#leftbar ul li {
	clear:left;
	margin:10px;
	margin-left: 20px;
}
#maincontent{

	height:450px;
    border:1px solid #cccccc;
}
#ggl{
	display:block;
}
#ksxx{
	display:;
}
#wdkc{
	display:none;
}
#xfjl{
	display:none;
}
#grzh{
	display:none;
}
#grsz{
	display:none;
}
#gmsp{
	display:none;
}
-->
</style>
</head>
<body>
<div id="top">
<ul>
<li>公司的logo</li>
<li>下午好，用户：<% if(request.getAttribute("userName")!=null) out.print((String)request.getAttribute("userName")); %></li>
<li><a href="#">安全退出</a>, <a href="index.html">返回首页</a></li>
</ul>
</div>
<div id="content">
<div id="leftbar">
<ul>
　　　　<li id="ggl_bar" class="active"><a href="#" onclick="nTabs(this,'ggl');">公告栏</a></li>

　　　　<li id="ksxx_bar" class="normal"><a href="#" onclick="nTabs(this,'ksxx');">考试学习</a></li>
 
　　　　<li id="wdkc_bar" class="normal"><a href="#" onclick="nTabs(this,'wdkc');">我的课程</a></li>

        <li id="gmsp_bar" class="normal"><a href="#" onclick="nTabs(this,'gmsp');">购买商品</a></li>

　　　　<li id="xfjl_bar" class="normal"><a href="#" onclick="nTabs(this,'xfjl');">消费记录</a></li>

　　　　<li id="grzh_bar" class="normal"><a href="#" onclick="nTabs(this,'grzh');">个人账户</a></li>

　　　　<li id="grsz_bar" class="normal"><a href="#" onclick="nTabs(this,'grsz');">个人设置</a></li>

　　　　<li class="normal"><a href="#">进入bbs</a></li>
</ul>
</div>
<div id="maincontent">
   <div id="ggl">
       包括了以下内容：
	   <li>系统级公告：某种产品已到期，是否需费；账户余额不足以支付现有产品的下月开销！</li>
	   <li>产品级公告：下月考试，考试时间；考试结果；商店管理员发出的公告，可以按培训商店划分！</li>
   </div>
   <div id="ksxx">
     <table width="100%" border="0" cellpadding="0" cellspacing="0">
       <tr>
         <td>&nbsp;</td>
         <td height="20">&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
       </tr>
       <tr>
         <td colspan="4" align="center" bgcolor="#0099FF"><strong>培训商店列表</strong></td>
        </tr>
	   <tr>
         <td colspan="4" height="10" align="center"></td>
       </tr>
       <tr>
         <td bgcolor="#FFFFCC">1.</td>
         <td bgcolor="#FFFFCC">英语之家</td>
         <td bgcolor="#FFFFCC"><a href="yyzj.html">进入商店首页</a></td>
         <td bgcolor="#FFFFCC"><a href="netTest/netTest_learner.html">直接进入学习</a></td>
       </tr>
       <tr>
         <td bgcolor="#FFFFFF">&nbsp;</td>
         <td bgcolor="#FFFFFF">&nbsp;</td>
         <td bgcolor="#FFFFFF">&nbsp;</td>
         <td bgcolor="#FFFFFF">&nbsp;</td>
       </tr>
       <tr>
         <td bgcolor="#FFFFFF">2.</td>
         <td bgcolor="#FFFFFF">普特英语</td>
         <td bgcolor="#FFFFFF"><a href="yyzj.html">进入商店首页</a></td>
         <td bgcolor="#FFFFFF"><a href="netTest/netTest_learner.html">直接进入学习</a></td>
       </tr>
     </table>
   </div>
   <div id="wdkc">
     <table width="100%" border="0" cellpadding="3" cellspacing="0">
       <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
       </tr>
       <tr>
         <td colspan="6" align="center" bgcolor="#0099FF"><strong>商店列表</strong></td>
        </tr>
       <tr>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
       </tr>
       <tr>
         <td colspan="6" bgcolor="#66CCFF">英语之家</td>
        </tr>
       <tr>
         <td align="right">1.</td>
         <td>四级名师指导</td>
         <td>开始时间：2007-8-1</td>
         <td>结束时间：2008-1-31</td>
         <td>花费金额：20元/月</td>
         <td><a href="#">查看详细</a></td>
       </tr>
       <tr>
         <td align="right">2.</td>
         <td>六级名师指导</td>
         <td>开始时间：2007-8-1</td>
         <td>结束时间：2008-1-31</td>
         <td>花费金额：400元</td>
         <td><a href="#">查看详细</a></td>
       </tr>
       <tr>
         <td colspan="6" align="left" bgcolor="#66CCFF">普特英语</td>
        </tr>
       <tr>
         <td align="right">1.</td>
         <td>外贸英语</td>
         <td>开始时间：2007-8-1</td>
         <td>结束时间：2009-1-31</td>
         <td>花费金额：10元/月</td>
         <td><a href="#">查看详细</a></td>
       </tr>
     </table>
   </div>
   <div id="gmsp">
       购买商品应连接到商品列表中，应该可以购买多个商店的商品
   </div>
   <div id="xfjl">
       消费记录应包括：
	   <li>消费产品</li>
	   <li>消费时间</li>
	   <li>消费金额</li>
	   <li>类型（购买、退订）</li>
	   <li>总计</li>
	   <li>所剩余额</li>
   </div>
   <div id="grzh">
        个人账户应包括：
	   <li>注册日期</li>
	   <li>充值记录</li>
	   <li>花费总额</li>
	   <li>所剩余额</li>
	   <li>下月应支出</li>
   </div>
   <div id="grsz">
        个人信息应包括：（太多了）
	   <li>注册日期</li>
	   <li>账号名称</li>
	   <li>人员信息</li>
	   <li>所选语言</li>
	   <li>等等</li>
   </div>
</div>
</div>
</body>
</html>

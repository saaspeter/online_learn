<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonWeb.security.authentication.UserinfoSession,commonTool.constant.CommonConstant,netTestWeb.base.WebConstant,commonWeb.base.BaseActionBase" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
   String shopid = request.getParameter("shopid");
   UserinfoSession info = BaseActionBase.getLoginInfo(true);
   String loginname = info.getLoginname();
   if(loginname==null){ loginname = ""; } 
   String displayname = BaseActionBase.showDisplayName();
   if((shopid==null||"".equals(shopid)||"null".equals(shopid))&&info.getShopid()!=null)
     { shopid=info.getShopid().toString(); }
   String productUrl = request.getContextPath()+"/usercontext/listMymagProd.do?shopid="+shopid;
  %>
<authz:privilege res='<%="/product/product_tree_main.jsp?shopid="+shopid %>'>
    <% productUrl = request.getContextPath()+"/product/product_tree_main.jsp?shopid="+shopid; %>
</authz:privilege>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商店的标识</title>
    <link rel="stylesheet" type="text/css" media="screen" href="/<%=CommonConstant.WebContext_Education %>/styles/css/banner.css" />

	<style type="text/css">
	<!--
	#top{
		border: 1px solid #dddddd;
		margin-bottom: 2px;
	}
	-->
	</style>
	<script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/menu.js"></script>
	<script type='text/javascript' src='/<%=CommonConstant.WebContext_Education %>/dwr/interface/shopcheck.js'></script>
    <script type='text/javascript' src='/<%=CommonConstant.WebContext_Education %>/dwr/engine.js'></script>
</head>

<body>
	
	<div style="background: #eeeeff;padding: 5px;">
	    <table style="width: 100%;border: 0px;" cellpadding="0" cellspacing="0">
	       <tr>
	           <td style="text-align: left;">
	               <%if(loginname!=null && !"".equals(loginname)){ %>
	                    欢迎：<%=displayname %> &nbsp;&nbsp;
					  <a href="/<%=CommonConstant.WebContext_Education %>/user/myhomepage.do">我的空间</a>&nbsp;|
					  <a href="/<%=CommonConstant.WebContext_Education %>/shopping/shoppingcartList.do" target="_shoppingcart">购物车</a>&nbsp;|
				      <a href="/<%=CommonConstant.WebContext_Education %>/j_spring_security_logout">退出登录</a>
	               <%}else { %>
	               <a href="/<%=CommonConstant.WebContext_Education %>/customers/adduser.do">用户注册</a>&nbsp;&nbsp;
	               <a href="/<%=CommonConstant.WebContext_Education %>/tologin.do">登陆</a>
	               <%} %>
	           </td>
	           <td style="text-align: right;">
	               
		   		   &nbsp;&nbsp;&nbsp;
		   		   <a href="#">帮助</a>
	           </td>
	       </tr>
	    </table>
	</div>
	
	<div id="banner">
	   <table style="border: 0px; width: 100%;">
	       <tr>
	          <td align="left" style="width:330px;">
	              <img style="width:310px; height:65px;" id="shoplogoId" name="" src="/"  alt="公司logo" />
	          </td>
	          <td id="shopnameId" style="text-align: left;font-size: 30px;font-weight: bold;"></td>
	       </tr>
	   </table>
	</div>
	
	<div id="navigation">
	    <ul>
	      <li><a id="menu1" href="/<%=CommonConstant.WebContext_Education %>/shop/toshop.do?shopid=<%=shopid %>">首页</a></li>
	      <li><a id="menu2" href="/<%=CommonConstant.WebContext_Education %>/shop/shop_productindex.jsp?shopid=<%=shopid %>" >培训课程</a></li>
	      <li><a id="menu21" href="/<%=CommonConstant.WebContext_Education %>/product/listOpenactivity.do?shopid=<%=shopid %>&showtype=1" >公开活动</a></li>
	      <li><a id="menu3" href="/<%=CommonConstant.WebContext_Education %>/social/listleavemess.do?queryVO.objecttype=shop&shopid=<%=shopid %>" >商店留言</a></li>
	      <li><a href="javascript:void(0)">&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
	      <authz:privilege res='<%="/shop/shopmagmessage.do?shopid="+shopid %>'>
	         <li><a id="menu4" href="/<%=CommonConstant.WebContext_Education %>/shop/shop_manageindex.jsp?shopid=<%=shopid %>" >管理商店</a></li>
	      </authz:privilege>
	      <authz:privilege res='<%="/product/showprodmagmess.do?shopid="+shopid %>'>
	         <li onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
	             <a id="menu5" href="/<%=CommonConstant.WebContext_Education %>/product/showprodmagmess.do?shopid=<%=shopid %>" >管理学习系统</a>
	             <ul>
		             <li><a href="/<%=CommonConstant.WebContext_Education %>/product/showprodmagmess.do?shopid=<%=shopid %>">信息台</a></li>
		             <li><a href="<%=productUrl %>">我的课程</a></li>
		             <li><a href="<%=request.getContextPath() %>/product/addproduct.do?bakurltype=2">开设新课程</a></li>
		         </ul>
	         </li>
	      </authz:privilege>
	    </ul>
	</div>
    <script type="text/JavaScript">
    <!-- 
        var shoplogoimg = '/<%=CommonConstant.WebContext_Education %>/styles/imgs/logo_platform.jpg';
        shopcheck.getShopMini(<%=shopid %>,null,function CB_get(rtn){
           var content = rtn.shopname;
           if(rtn.isauthen==1){
              content = content+"&nbsp;<img src='../styles/imgs/verified.gif' title='verified shop'/>";
           }
           document.getElementById("shopnameId").innerHTML = content;
           if(rtn.bannerimg!=null && rtn.bannerimg!=''){
        	   shoplogoimg = '<%=WebConstant.FileContext %>'+rtn.bannerimg;
           }
           document.getElementById("shoplogoId").src=shoplogoimg;
        });
    //-->
    </script>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.constant.ShopConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:size id="createshopsize" name="shopForm" property="shopList"/>
<%
   String messagekey = request.getParameter("messagekey");
   
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>开设的商店</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">

		#bannermenu4{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
	
	</style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
   
  <tiles:insert page="/userAdmin/banner_user.jsp"></tiles:insert>
  
  <div class="listPage">
  <html:form styleId="list" action="/shop/myOwnShops.do" method="post">
  
  <div class="navlistBar alertFont" style="text-align: center;">&nbsp;
      <%if(messagekey!=null && messagekey.trim().length()>0){ %>
      <bean:message key="<%=messagekey %>"/>
      <%} %>
  </div>

  <div style="height:auto; width:100%;">
     <ul class="tabnav">
	    <li class='selectTab'><a href="javascript:void(0)">我的学校</a></li>
		<li><a href="<%=WebConstant.WebContext %>/shop/listmyshopapp.do">申请学校记录</a></li>
	 </ul>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="10px"></td>
        <td style="width:310px;"></td>
        <td style="font-size: large;">我创建的学校
            &nbsp;&nbsp;<img title="创建新学校" src="../styles/imgs/add2.png" style="cursor: pointer;" onclick="window.open('/netTest/shop/applyNewShop.do')">
        </td>
      </tr>
      </thead>
      <logic:present name="shopForm" property="shopList">
	  <logic:iterate id="vo" name="shopForm" property="shopList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td style="padding-top:20px;padding-bottom: 20px;">
            <a href='<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>&loadauthority=1' target="shoppage" title='点击进入学校首页'>
               <img style="width:310px; height:65px;border: 0px;" id="shoplogoId" name="" src="<%=WebConstant.FileContext %><bean:write name="vo" property="bannerimg"/>"  alt="公司logo" />
            </a>
        </td>

        <td>
            <a href='<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>&loadauthority=1' target="shoppage" title='点击进入学校首页'>
            <font style="font-size:x-large;"><bean:write name="vo" property="shopname"/></font></a>
            (学校代号:<font style="font-size:x-large;"><bean:write name="vo" property="shopcode"/></font>)&nbsp;&nbsp;
            <img src="<%=WebConstant.WebContext %>/styles/imgs/detail.png" onclick="newDiv('<%=WebConstant.WebContext %>/shop/viewshoppage.do?queryVO.shopid=<bean:write name="vo" property="shopid"/>',0,1,500,450);return false;" 
                 style="cursor: pointer;width: 23px;" title='点击查看学校基本信息'/>&nbsp;&nbsp;
            <logic:equal name="vo" property="isauthen" value="<%=ShopConstant.IsAuthen_no.toString() %>">
                <img src="<%=WebConstant.WebContext %>/styles/imgs/noVerified.gif" title='未认证学校，请在"管理商店-->商店基本信息"页面申请认证'/>
            </logic:equal>
            <logic:equal name="vo" property="isauthen" value="<%=ShopConstant.IsAuthen_pass.toString() %>">
                <img src="<%=WebConstant.WebContext %>/styles/imgs/verified.gif" title="已认证学校"/>
            </logic:equal>
            
            <br><br>我是学校创建者，创建于: <bean:write name="vo" property="regisdate" format="yyyy-MM-dd"/>&nbsp; &nbsp; 
            当前运行状态:<a href="javascript:void(0)" onclick="newDiv('<%=WebConstant.WebContext %>/shop/listshopstatus.do?vo.shopid=<bean:write name="vo" property="shopid"/>&shopname=<bean:write name="vo" property="shopname"/>',0,1,500,350);return false;" title="状态变化详情">
            <bean:writeSaas name="vo" property="shopstatus" consCode="ShopConstant.ShopStatus"/>
            <img src="../styles/imgs/more.png" style="border: none;width: 20px;height: 20px;">
            </a>
            &nbsp;&nbsp;
            <a class="messLink" href="<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>&loadauthority=1" target="shoppage">进入管理学校</a>
        </td>

      </tr>
      </logic:iterate>
      </logic:present>
      <%if(createshopsize<1){ %>
         <tr>
            <td style="padding-left:10em;" colspan="3">
                <a href="javascript:void(0)" class="messLink font2Large" onclick="window.open('/netTest/shop/applyNewShop.do')">创办我的学校</a>
            </td>
	     </tr>
      <%} %>
      <tr>
         <td>&nbsp;</td>
      </tr>
    </table>
    
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="10px"></td>
        <td style="width:310px;"></td>
        <td style="font-size: large;">我加入的学校</td>
      </tr>
      </thead>
      <logic:present name="shopForm" property="usershopList">
	  <logic:iterate id="vo" name="shopForm" property="usershopList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td style="width:310px;">
            <a href='<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>&loadauthority=1' target="shoppage">
               <img style="width:310px; height:65px;" id="shoplogoId" name="" src="<%=WebConstant.FileContext %><bean:write name="vo" property="shopbannerimg"/>"  alt="公司logo" />
            </a>
        </td>    

        <td>
            <a href="<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>&loadauthority=1" target="shoppage">
            <font style="font-size:x-large;"><bean:write name="vo" property="shopname"/></font></a>
            (<bean:write name="vo" property="shopcode"/>)<br><br>
                                  加入时间: <bean:write name="vo" property="joindate" format="yyyy-MM-dd"/>,&nbsp;
                                  我在学校的状态: <bean:writeSaas name="vo" property="usershopstatus" consCode="UsershopConstant.UserShopStatus"/>,&nbsp;
            <a class="messLink" href="<%=WebConstant.WebContext %>/product/myUserproduct.do?shopid=<bean:write name="vo" property="shopid"/>">查看我在该校的学习课程</a>
        </td>

      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  <jsp:include flush="true" page="/foot.jsp"></jsp:include>
  </html:form>
  </div>
  </div>
  </body>
</html:html>

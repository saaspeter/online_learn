<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.constant.ShopConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>培训机构商店列表</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../../styles/script/movediv.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/shop/listshop.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">	
  
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div style="color:#ff0000;">语言仍有问题，当用某种语言查询出结果，再预览时却不是用该语言描述的商店信息</div>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">
        &nbsp; 
         商店: 
		<input type="text" name="searchtext" class="searchTxt_second" value="<bean:write name="shopForm" property="searchtext"/>" /> 
		&nbsp;
		商店注册地:
         <html:select name="shopForm" property="queryVO.localeid" >
            <option></option>
			<html:optionsSaas localeListSet="true"/>
		 </html:select>
		&nbsp;
		商店状态:
		 <html:select name="shopForm" property="queryVO.shopstatus" >
            <option></option>
			<html:optionsSaas consCode="ShopConstant.ShopStatus"/>
		 </html:select>
		&nbsp;
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		&nbsp;
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="netTest.commonpage.complexQuery"/></a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
              <td>Property one</td>
              <td>Property two</td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/shop/applyNewShop.do');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
		  </ul>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table id="dataTableId" class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>商店名</td>
        <td>注册时间</td>
        <td>商店人数</td>
        <td>商店状态</td>
        <td>管理商店</td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="idx">
      <tr class="<%=(idx%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="shopid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td>
            <a href="javascript:void(0);" onclick="newDiv('/shop/viewshoppage.do?queryVO.shopid=<bean:write name="vo" property="shopid"/>',0,1,500,350);"><bean:write name="vo" property="shopname"/></a>
            (<bean:write name="vo" property="shopcode"/>)
            <logic:equal value="<%=ShopConstant.IsAuthen_pass.toString() %>" name="vo" property="isauthen">
                <img src="../../styles/imgs/verified.gif" title="verified shop"/>
            </logic:equal>
        </td>    

        <td><bean:write name="vo" property="regisdate" format="yyyy-MM-dd"/></td>

        <td><bean:write name="vo" property="usernum"/></td>

        <td>
            <a href="javascript:void(0)" onclick="newDiv('/shop/listshopstatus.do?vo.shopid=<bean:write name="vo" property="shopid"/>&shopname=<bean:write name="vo" property="shopname"/>',0,1,500,350);return false;" title="状态变化详情">
            <bean:writeSaas name="vo" property="shopstatus" consCode="ShopConstant.ShopStatus"/>
            <img src="../../styles/imgs/more.png" style="border: none;width: 20px;height: 20px;">
            </a>
        </td>
        <td>
            <img src="../../styles/imgs/edit.png" style="cursor:pointer;" title="修改" onclick="goUrl('/platform/shop/shopMagAdminMain.jsp?shopid=<bean:write name="vo" property="shopid"/>&shopname=<bean:write name="vo" property="shopname"/>&backUrlEncode=','backUrlEncode');return false;"/>
            <a href="<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>" target="aaa">进入</a>
            &nbsp;|&nbsp;<a href="<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>&loadauthority=1" target="aaa">登录管理</a>
            &nbsp;|<img src="../../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/shop/deleteShop.do?shopid=<bean:write name="vo" property="shopid"/>&backUrl=');return false;"/>
        </td>

      </tr>
      </logic:iterate>
      </logic:present>
      </tbody>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
         changeComplexSearch("complexSearchDiv","<bean:write name="shopForm" property="complexSearchDivStatus"/>");
       }
     //-->
  </script>
  </body>
</html:html>

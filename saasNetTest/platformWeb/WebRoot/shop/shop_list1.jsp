<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>培训机构商店列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
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
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">
        &nbsp; 
         商店名: 
		<input type="text" name="queryVO.shopname" value="<bean:write name="shopForm" property="queryVO.shopname"/>"/>
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
		<input type="submit" name="Submit" value="<bean:message key="platform.commonpage.query"/>" />
		&nbsp;
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="platform.commonpage.complexQuery"/></a>
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
			 <li><button type="button" onclick="addRecord('/shop/applyNewShop.do');return false;"><bean:message key="platform.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/shop/editshoppage.do?queryVO.shopid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.modify"/></button></li>
             <li><button type="button" onclick="modifyRecord('list','keys','/shop/shopStatusChangePage.do?queryVO.shopid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;">商店状态变更</button></li>			 
			 <li><button type="button" onclick="deleteRecord('list','keys','/shop/deleteShop.do','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete"/></button></li>
			 <li><button type="button" onclick="document.forms[0].submit()"><bean:message key="platform.commonpage.refreash"/></button></li>
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
    <table class="listDataTable" >
      <tr class="head">
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        
        <td>商店名</td>
                
        <td>注册时间</td>

        <td>商店人数</td>

        <td>商店状态</td>
        
        <td>管理商店</td>

      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="shopid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td>
            <a href="javascript:newDiv('/shop/viewshoppage.do?queryVO.shopid=<bean:write name="vo" property="shopid"/>',0,1,500,350);"><bean:write name="vo" property="shopname"/></a>
            (<bean:write name="vo" property="shopcode"/>)
        </td>    

        <td><bean:write name="vo" property="regisdate" format="yyyy-MM-dd"/></td>

        <td><bean:write name="vo" property="usernum"/></td>

        <td>
            <a href="javascript:void(0)" onclick="newDiv('#/<%=CommonConstant.WebContext_Platform %>/shop/listshopstatus.do?vo.shopid=<bean:write name="vo" property="shopid"/>&shopname=<bean:write name="vo" property="shopname"/>',0,1,500,350);return false;" title="状态变化详情">
            <bean:writeSaas name="vo" property="shopstatus" consCode="ShopConstant.ShopStatus"/>
            <img src="../styles/imgs/more.png" style="border: none;width: 20px;height: 20px;">
            </a></td>
        <td><a href="/<%=CommonConstant.WebContext_Education %>/shop/toShop.do?shopid=<bean:write name="vo" property="shopid"/>" target="aaa">进入商店</a></td>

      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
         changeComplexSearch("complexSearchDiv","<bean:write name="shopForm" property="complexSearchDivStatus"/>");
         setListTableStyle();
       }
     //-->
  </script>
  </body>
</html:html>

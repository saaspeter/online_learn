<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>申请商店列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	 <!--
	   function flushPage(){
	       document.getElementById("list").submit();
	   }
	   	   
     //-->
     </script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/shop/listshopapp.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopappForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopappForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">商店名: 
		<input type="text" name="queryVO.shopname" value="<bean:write name="shopappForm" property="queryVO.shopname"/>"/>
		&nbsp;
		商店注册地:
         <html:select name="shopappForm" property="queryVO.localeid" >
            <option></option>
			<html:optionsSaas localeListSet="true"/>
		 </html:select>
		&nbsp;
		审核状态:
		<html:select name="shopappForm" property="queryVO.appstatus" >
            <option></option>
            <html:optionsSaas consCode="platform.ShopappConstant.AppStatus"/>
		 </html:select>
		<input type="submit" name="Submit" value="<bean:message key="platform.commonpage.query"/>" />
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
			 <li><button type="button" onclick="modifyRecord('list','keys','/shop/editmyshopappPage.do?queryVO.shopappid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;">查看审批</button></li>
			 <li><button type="button" onclick="batchProcessDiv('list','keys','/shop/shopapp.do?method=approveEditPageBatch&keyStr=',650,200,'<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>');return false;">批量审批</button></li>
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
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>

        <td>商店</td>

        <td>申请日期</td>

        <td>审核状态</td>
        
        <td></td>

        <td>操作日期</td>

      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr id="trId<bean:write name="vo" property="shopappid"/>" class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="shopappid"/>" onClick="selectInfo(this,'clickedRow')"></td>

        <td><a href="javascript:void(0);" onclick="javascript:newDiv('/shop/editshopappPage.do?queryVO.shopappid=<bean:write name="vo" property="shopappid"/>',0,1,650,400);return false;"><bean:write name="vo" property="shopname"/></a>
            (<bean:write name="vo" property="shopcode"/>)
        </td>

        <td><bean:write name="vo" property="appdate" format="yyyy-MM-dd"/></td>

        <td>
            <bean:writeSaas name="vo" property="appstatus" consCode="platform.ShopappConstant.AppStatus"/>
        </td>
        
        <td><a href="#" onclick="newDiv('/shop/editshopappPage.do?queryVO.shopappid=<bean:write name="vo" property="shopappid"/>',0,1,650,400);return false;"><img alt="detail" src="../styles/imgs/detail.png" style="border: 0"/></a></td>

        <td><bean:write name="vo" property="replydate" format="yyyy-MM-dd"/></td>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="shopappForm" property="complexSearchDivStatus"/>");
	   }
     //-->
  </script>
  </body>
</html:html>

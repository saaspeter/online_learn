<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>申请商店列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/shop/shopapp.do?method=listall" method="post">
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
			<html:optionsCollection name="shopappForm" property="localesList"/>
		 </html:select>
		&nbsp;
		审核状态:
		<html:select name="shopappForm" property="queryVO.appstatus" >
            <option></option>
			<html:optionsCollection name="shopappForm" property="appstatusList"/>
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
			 <li><button type="button" onclick="modifyRecord('list','keys','/shop/shopapp.do?method=viewPage&queryVO.shopappid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;">查看审批</button></li>
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
        
        <td>商店代号</td>

        <td>注册国家</td>

        <td>是否试用</td>

        <td>申请日期</td>

        <td>审核状态</td>

        <td>上次审核状态</td>

        <td>操作日期</td>

      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="shopappid"/>" onClick="selectInfo(this,'clickedRow')"></td>

        <td><a href="javascript:newDiv('/shop/shopapp.do?method=viewPage&queryVO.shopappid=<bean:write name="vo" property="shopappid"/>',0,1,600,300);"><bean:write name="vo" property="shopname"/></a></td>

		<td><bean:write name="vo" property="shopcode"/></td>

        <td><bean:write name="vo" property="localeid"/></td>

        <td></td>

        <td><bean:write name="vo" property="appdate" format="yyyy-MM-dd"/></td>

        <td><bean:write name="vo" property="appstatus"/></td>

        <td><bean:write name="vo" property="appstatus0"/></td>

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
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>

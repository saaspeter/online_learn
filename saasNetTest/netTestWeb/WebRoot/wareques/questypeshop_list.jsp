<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="questypeshopForm" property="jsSuffix" type="java.lang.String"/>
    <title>自定义题型</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/wareques/listQuestypeshop.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="questypeshopForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="questypeshopForm" property="backUrlEncode"/>">
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">题型名: 
		<input type="text" name="queryVO.questypename" value="<bean:write name="questypeshopForm" property="queryVO.questypename"/>"/>
		题型来源: 
		<html:select name="questypeshopForm" property="queryVO.questypevalue" >
		     <html:option value=""></html:option>
			 <html:optionsSaas consCode="Question.QuesType"/>
	    </html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>
  <!-- complex Search start -->

  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/wareques/addQuestypeshop.do');"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="delRec('list','keys','/wareques/deleteQuestypeshop.do');"><bean:message key="netTest.commonpage.delete"/></button></li>
			 
		  </ul>
	  </div>

  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>题型名</td>
        <td>创建类型</td>
        <td>题型来源</td>
        <td>创建日期</td>
        <td>操作</td>
      </tr>
      </thead>
      <logic:present name="questypeshopForm" property="list">
	  <logic:iterate id="vo" name="questypeshopForm" property="list" indexId="ind">
      <tr>
        <td>
            <logic:equal name="vo" property="createtype" value="2">
               <input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="questypeid"/>' value="<bean:write name="vo" property="questypeid"/>" onClick="selectInfo(this,'clickedRow')">
               <input id='pkId<bean:write name="vo" property="questypeid"/>Name' type="hidden" value="<bean:write name="vo" property="questypename"/>"/>
            </logic:equal>
        </td>
        <td><a href="javascript:newDiv('/wareques/viewQuestypeshop.do?queryVO.questypeid=<bean:write name="vo" property="questypeid"/>',0,1,600,400);">          
            <bean:write name="vo" property="questypename"/></a>
        </td>
        <td><bean:write name="vo" property="createtype"/></td>
        <td><bean:writeSaas name="vo" property="questypevalue" consCode="Question.QuesType"/></td>
        <td><bean:write name="vo" property="createdate" format="yyyy-MM-dd"/></td>
        <td>
            <logic:equal name="vo" property="createtype" value="2">
                <a href="javascript:void(0);" onclick="goUrl('/wareques/editQuestypeshop.do?queryVO.questypeid=<bean:write name="vo" property="questypeid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
                <a href="javascript:void(0);" onclick="goUrl('/wareques/deleteQuestypeshop.do?vo.questypeid=<bean:write name="vo" property="questypeid"/>&backUrlEncode=','backUrlEncode');return false;">删除</a>
            </logic:equal>
        </td>
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
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>

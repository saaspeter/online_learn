<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.paper.constant.PaperConstant,netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="productId" name="paperForm" property="sessionProductid" type="java.lang.Long"/>
<bean:define id="productName" name="paperForm" property="sessionProductname" type="java.lang.String"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="paperForm" property="jsSuffix" type="java.lang.String"/>
    <title>试卷列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
  </head>
    <%if(productId==null){
       productName = "所有可用产品";
    } %>
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/paper/listPaper.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="paperForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="paperForm" property="backUrlEncode"/>">
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=(productId==null)?"":productId.toString() %>">
  <input id="prodnameId" type="hidden" name="productname" value="">
  <div class="navlistBar">
      <a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_operatorMag %>');" title="点击选择产品"><%=productName %></a> &gt; 试卷管理
  </div>
  <div id="firstLineDiv">
      <div id="searchDiv">
         试卷名: 
		<input type="text" name="queryVO.papername" value="<bean:write name="paperForm" property="queryVO.papername"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
	  </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <button type="button" style="margin-left:30px;" class="button green fontBold" onclick="goUrl('/paper/addPaper1.do');return false;"><bean:message key="netTest.commonpage.add"/></button>
		  <button type="button" style="margin-left:30px;" class="button green fontBold" onclick="goUrl('/paper/importpaperfilepage.do?backUrlEncode=','backUrlEncode');">从文件导入试卷</button>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <%if(productId==null){ %> 请选择课程信息，否则不能查询 <%} %>
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="5px"></td>
        <td>试卷名</td>
        <td>所属产品</td>
        <td>创建时间</td>
        <td>操作</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td><a href="javascript:void(0)" onclick="newDiv('/paper/viewPaper.do?queryVO.paperid=<bean:write name="vo" property="paperid"/>',0,1,800,550);return false;">
            <bean:write name="vo" property="papername"/></a>
            <logic:notEqual name="vo" property="phase" value="<%=PaperConstant.PaperPhase_finished.toString() %>">
               &nbsp;(状态:<bean:writeSaas name="vo" property="phase" consCode="netTest.PaperConstant.PaperPhase"/>)
            </logic:notEqual>
        </td>
        <td><bean:write name="vo" property="productname"/></td>
        <td><bean:write name="vo" property="createtime" format="yyyy-MM-dd"/></td>
        <td>
            <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/paper/editPaper.do?queryVO.paperid=<bean:write name="vo" property="paperid"/>');return false;"/>&nbsp;
		    <img src="../styles/imgs/exam.png" title="测试" style="cursor:pointer;" onclick="openWin('/exam/selfExamInit.do?paperVO.paperid=<bean:write name="vo" property="paperid"/>',750,650,'no','yes');return false;"/>&nbsp;
		    <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/paper/deletePaper.do?vo.paperid=<bean:write name="vo" property="paperid"/>&backUrl=');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </body>
</html:html>

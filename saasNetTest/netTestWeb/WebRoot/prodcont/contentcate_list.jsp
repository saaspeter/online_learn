<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="contentcateForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="listType" name="contentcateForm" property="listType" type="java.lang.String"/>
    <bean:define id="selectType" name="contentcateForm" property="selectType" type="java.lang.String"/>
    <title>课程章节管理</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  <% String url = "/prodcont/listContentcatemag.do";
     if(listType!=null&&"2".equals(listType)){
        url = "/prodcont/listContentcatesel.do";
     }
   %>
  <body>
  <div class="listPage">
  <html:form styleId="list" action="<%=url %>" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="contentcateForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="contentcateForm" property="backUrlEncode"/>">
  <input type="hidden" name="queryVO.productbaseid" value="<bean:write name="contentcateForm" property="queryVO.productbaseid"/>"/>
  <input type="hidden" id="parentId" name="queryVO.pid" value="<bean:write name="contentcateForm" property="queryVO.pid"/>"/>
  <input type="hidden" name="selectType" value="<%=selectType %>"/>
  <input type="hidden" name="listType" value="<%=listType %>"/>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <%if(listType!=null&&"2".equals(listType)){ %>
		     <li><button type="button" onclick="doSelect();return false;">确定返回</button></li>
		     <li><button type="button" onclick="parent.parent.clearDiv();return false;">取消</button></li>
		     <%}else{ %>
		     <li style="margin-left:20px;"><img src="../styles/imgs/back.png" title="返回课程目录列表" style="cursor:pointer;" onclick="parent.goUrl('/prodcont/onlyviewprodcate.do?productbaseid=<bean:write name="contentcateForm" property="queryVO.productbaseid"/>&canedit=1');return false;"/></li>
			 <li style="margin-left:20px;"><button type="button" class="ModerateButton" onclick="addRecord('/prodcont/addContentcate.do?vo.productbaseid=<bean:write name="contentcateForm" property="queryVO.productbaseid"/>&vo.pid=<bean:write name="contentcateForm" property="queryVO.pid"/>');"><bean:message key="netTest.commonpage.add"/></button></li>
			 <%} %>
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
        <td width="10px"></td>
        <td>章节</td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="contentcateForm" property="datalist">
	  <logic:iterate id="vo" name="contentcateForm" property="datalist" indexId="idx">
      <tr class="<%=(idx%2==0)?"oddRow":"evenRow" %>">
        <td>
        </td>
        <td><bean:write name="vo" property="contentcatename"/>
            <input id='pkId[<bean:write name="vo" property="contentcateid"/>]Name' type="hidden" value="<bean:write name="vo" property="contentcatename"/>"/>
        </td>
        <td>
            <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/prodcont/editContentcate.do?queryVO.contentcateid=<bean:write name="vo" property="contentcateid"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/prodcont/deleteContentcate.do?productbaseid=<bean:write name="vo" property="productbaseid"/>&vo.contentcateid=<bean:write name="vo" property="contentcateid"/>');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script type="text/javascript">
	 <!--
	   function showRight(id){
	      document.getElementById("parentId").value=id;
	      document.getElementById("backUrl").value='';
	      document.getElementById("backUrlEncode").value='';
	      document.getElementById("list").submit();
	   }
	   
	   function doSelect(){
	      // 2代表可以多选知识点
	      var backObj = selectCheck("list","keys",2,"Name");   
	      if(backObj){
	         if(typeof(parent.parent.selContcateCB)!="undefined"){
		        parent.parent.selContcateCB(backObj['ids'],backObj['names']);
		     }
	      }
	   }
	   
     //-->
  </script>
  </body>
</html:html>

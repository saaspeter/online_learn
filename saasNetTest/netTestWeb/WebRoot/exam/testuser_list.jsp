<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.exam.constant.TestinfoConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="testuserForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productid" name="testuserForm" property="testVO.productbaseid" type="java.lang.Long"/>
    <bean:define id="viewresulttype" name="testuserForm" property="testVO.viewresulttype" type="java.lang.Short"/>
    <bean:define id="opentypeVar" name="testuserForm" property="testVO.opentype" type="java.lang.Short"/>
    <bean:define id="orderColumn" name="testuserForm" property="queryVO.orderColumn" type="java.lang.String"></bean:define>
    <bean:define id="orderDirect" name="testuserForm" property="queryVO.orderDirect" type="java.lang.Integer"></bean:define>
    <title>考试人员列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	
	   function selUserCB(ids,names){
          if(ids!=null&&ids!=""){
             document.getElementById("userIdstrId").value=ids;
             var form = document.getElementById("list");
	         form.action = "saveTestuser.do";
	         form.submit();
          }
       }
       
       function doSelAllProdUser(productid){
           document.getElementById("addusertypeId").value='2';
           var form = document.getElementById("list");
	       form.action = "saveTestuser.do";
	       form.submit();
       }
       
       function addTestUser(){
          var url = "/orguser/selectUserMain.jsp?productid=<%=productid %>";
          newDiv(url,1,1,720,400);
       }
       
       function deleteusers(){
           var url = '/exam/deleteTestuser.do?queryVO.testid=<bean:write name="testuserForm" property="queryVO.testid"/>';
           delRec('list','keys',url);
       }
       
       function order(perp){
          var orderColumnVar = document.getElementById("orderColumnId").value;
          var orderDirectVar = document.getElementById("orderDirectId").value;
          if(perp!=''){
             if(perp==orderColumnVar){
                if(orderDirectVar=="1"){
                   orderDirectVar = "2";
                }else if(orderDirectVar=="2"){
                   orderDirectVar = "1";
                }
             }else {
                orderDirectVar = "1";
             }
             document.getElementById("orderColumnId").value = perp;
             document.getElementById("orderDirectId").value = orderDirectVar;
             document.getElementById("list").submit();
          }
       }
	
	</script>
  </head>
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exam/listtestuser.do" method="post">
  <input id="testidId" type="hidden" name="queryVO.testid" value="<bean:write name="testuserForm" property="queryVO.testid"/>" />
  <input type="hidden" name="queryVO.productbaseid" value="<%=productid %>" />
  <input id="userIdstrId" type="hidden" name="userIdstr" value="<bean:write name="testuserForm" property="userIdstr"/>" />
  <input id="orderColumnId" type="hidden" name="queryVO.orderColumn" value="<%=orderColumn %>" />
  <input id="orderDirectId" type="hidden" name="queryVO.orderDirect" value="<%=orderDirect %>" />
  <input id="addusertypeId" type="hidden" name="addusertype" />
  
  <div class="navlistBar">
     <a href="javascript:void(0)" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="testuserForm" property="queryVO.testid"/>');" title="考试详细信息"><bean:write name="testuserForm" property="testVO.testname"/></a>
     &gt; 考试人员
  </div>
  <div id="firstLineDiv">
      <div id="searchDiv">
         考生登录帐号: 
		<input type="text" name="queryVO.loginname" value="<bean:write name="testuserForm" property="queryVO.loginname"/>"/>
		考试状态: 
		 <html:select name="testuserForm" property="queryVO.status">
	        <html:optionsSaas consCode="netTest.TestuserConstant.status"/>
	     </html:select>
	     
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
      
	  <div id="functionButtonDiv">
		  <%if(TestinfoConstant.Teststatus_unStart.equals(viewresulttype)||TestinfoConstant.Teststatus_started.equals(viewresulttype)){ %>
		  <button type="button" style="margin-left:10px;" class='button green fontBold' onclick="addTestUser();return false;">新增人员</button>
		  <%} %>
		  <%if(TestinfoConstant.Teststatus_unStart.equals(viewresulttype)){ %>
		  <button type="button" style="margin-left:10px;" class="button green fontBold" onclick="deleteusers();"><bean:message key="netTest.commonpage.delete"/></button>
		  <%} %>
		  <button type="button" style="margin-left:10px;" class="button green fontBold" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="testuserForm" property="queryVO.testid"/>');">返回考试详情</button>
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
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>用户</td>
        <td>考试状态</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
         <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="testuserid"/>' value="<bean:write name="vo" property="testuserid"/>" onClick="selectInfo(this,'clickedRow')"></td>
         <td><bean:write name="vo" property="displayname"/></td>
         <td><bean:writeSaas name="vo" property="status" consCode="netTest.TestuserConstant.status"/></td>
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

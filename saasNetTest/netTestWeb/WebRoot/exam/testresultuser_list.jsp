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
    <bean:define id="orderColumn" name="testuserForm" property="queryVO.orderColumn" type="java.lang.String"></bean:define>
    <bean:define id="orderDirect" name="testuserForm" property="queryVO.orderDirect" type="java.lang.Integer"></bean:define>
    <title>考试人员列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	       
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
     <a href="javascript:void(0)" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="testuserForm" property="queryVO.testid"/>');" title="考试结果详细信息"><bean:write name="testuserForm" property="testVO.testname"/></a>
     &gt; 考试人员
  </div>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">
          别称: 
		<input type="text" name="queryVO.displayname" value="<bean:write name="testuserForm" property="queryVO.displayname"/>"/>
		 考生考试状态: 
		 <html:select name="testuserForm" property="queryVO.status">
	        <html:option value=""></html:option>
		    <html:optionsSaas consCode="netTest.TestuserConstant.status"/>
	     </html:select>
	     
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
      
	  <div id="functionButtonDiv">
		  <ul>
		     <li><button type="button" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="testuserForm" property="queryVO.testid"/>');">返回考试详情</button></li>
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
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>别名(账号)</td>
        <td>考试状态</td>
        <td><a href="#" onclick="order('totalscore');return false;">总分</a><%if("totalscore".equals(orderColumn)&&orderDirect!=null){ if(orderDirect==1){out.print("&uarr;");} else if(orderDirect==2){out.print("&darr;");} }  %></td>
	    <td><a href="#" onclick="order('objectscore');return false;">客观题得分</a><%if("objectscore".equals(orderColumn)&&orderDirect!=null){ if(orderDirect==1){out.print("&uarr;");} else if(orderDirect==2){out.print("&darr;");} }  %></td>
	    <td><a href="#" onclick="order('isqualify');return false;">是否及格</a><%if("isqualify".equals(orderColumn)&&orderDirect!=null){ if(orderDirect==1){out.print("&uarr;");} else if(orderDirect==2){out.print("&darr;");} }  %></td>
	    <td><a href="#" onclick="order('ordernoall');return false;">名次</a><%if("ordernoall".equals(orderColumn)&&orderDirect!=null){ if(orderDirect==1){out.print("&uarr;");} else if(orderDirect==2){out.print("&darr;");} }  %></td>
	    <td>查看考卷</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
         <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="testuserid"/>' value="<bean:write name="vo" property="testuserid"/>" onClick="selectInfo(this,'clickedRow')"></td>
         <td><bean:write name="vo" property="displayname"/></td>
         <td><bean:writeSaas name="vo" property="status" consCode="netTest.TestuserConstant.status"/></td>
         <td><bean:write name="vo" property="totalscore"/></td>
	     <td><bean:write name="vo" property="objectscore"/></td>
	     <td><bean:writeSaas name="vo" property="isqualify" consCode="netTest.TestuserConstant.IsQualify"/></td>
	     <td><bean:write name="vo" property="ordernoall"/></td>
	     <td><a href="#" onclick="openWin('/exam/seeExamerResult.do?testid=<bean:write name="vo" property="testid"/>&testuserid=<bean:write name="vo" property="testuserid"/>&paperVO.paperid=<bean:write name="vo" property="paperid"/>&paperversion=<bean:write name="testuserForm" property="testVO.paperversion"/>&testuserVO.displayname=<bean:write name="vo" property="displayname"/>',750,650,'yes','yes');return false;">查看</a>
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

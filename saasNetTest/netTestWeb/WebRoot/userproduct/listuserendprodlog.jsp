<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userprodbuylogForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productidVar" name="userprodbuylogForm" property="queryVO.productbaseid" type="java.lang.Long"/>
    <bean:define id="productnameVar" name="userprodbuylogForm" property="queryVO.productname" type="java.lang.String"/>
    <bean:define id="selectproductscope" name="userprodbuylogForm" property="selectproductscope" type="java.lang.Integer"/>
    <% String url = "/userproduct/listuserprodmag.do";
	   String productidStrVar = (productidVar==null)?"":productidVar.toString();
	   productnameVar = (productnameVar==null)?"":productnameVar;
       if(selectproductscope==1) {
    	   url = "/userproduct/listoneproduser.do?queryVO.productbaseid="+productidStrVar+"&queryVO.productname="+productnameVar;
       }
    %>
    <title>学员产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script language="javascript">
	<!--   
	   function clearproduct(){
	      document.getElementById("productidId").value='';
	      document.getElementById("productnameId").value='';
	   }
	   
	   function switchproductview(selObj){
	      if(selObj==null){
             return;
          }
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue!="1"){ return; }
          goUrl('<%=url %>');
	   }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/userproduct/listuserendprodlog.do" method="post">
  <input type="hidden" name="userid" value="<bean:write name="userprodbuylogForm" property="userid"/>">
  <input id="productidId" type="hidden" name="queryVO.productbaseid" >

  <%if(selectproductscope==1){ %>
  <div class="navlistBar">
     <%=productnameVar %> &gt; 删除的学员课程
  </div>
  <%} %>

  <div id="firstLineDiv">
      <div id="searchDiv">
        <%if(selectproductscope==0){ %>
          课程名: 
		<input id="productnameId" type="text" name="queryVO.productname" readonly="readonly" value="<%=productnameVar %>" onclick="selectProd(this);"/>
		<img src="../styles/imgs/ico/reset.gif" alt="清除" onclick="clearproduct()">
		&nbsp;<%} %>
		学员帐号: 
		<input type="text" name="queryVO.loginname" value="<bean:write name="userprodbuylogForm" property="queryVO.loginname"/>"/>
		&nbsp;
		查看类型:
		<select name="selectproducttype" id="selectproducttypeId" onchange="switchproductview(this);">
		   <option value="1">正在使用的课程</option>
		   <option value="2" selected="selected">删除的学员课程</option>
		</select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv" >
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
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
        <td>用户</td>
        <td>课程</td>
        <td>删除时间</td>
      </tr>
      </thead>
	  <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><bean:write name="vo" property="displayname"/></td>
        <td><bean:write name="vo" property="productname"/></td>
        <td>
            <bean:write name="vo" property="happendate" format="yyyy-MM-dd HH:mm"/>
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

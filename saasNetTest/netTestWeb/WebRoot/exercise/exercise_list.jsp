<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="productId" name="exerciseForm" property="sessionProductid" type="java.lang.Long"/>
<bean:define id="productName" name="exerciseForm" property="sessionProductname" type="java.lang.String"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="exerciseForm" property="jsSuffix" type="java.lang.String"/>
    <title>练习列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
  </head>

  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exercise/listExerMag.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="exerciseForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="exerciseForm" property="backUrlEncode"/>">
  <input id="prodidId" type="hidden" name="productbaseid" value="">
  <input id="prodnameId" type="hidden" name="productname" value="">
  
  <div class="navlistBar">
      <a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_operatorMag %>');" title="点击选择产品"><%=productName %></a> &gt; 考试设置
  </div>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">
         练习名: 
		<input type="text" name="queryVO.exername" value="<bean:write name="exerciseForm" property="queryVO.exername"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/exercise/toAddExerPage.do');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
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
      <%if(productId==null){ %>请点击左上角选择课程<%} %>
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table id="dataTableId" class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>练习名</td>
        <td>课程章节</td>
        <td>创建时间</td>
        <td>操作</td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr id='<%="dataTrId"+ind %>' class='<%=(ind%2==0)?"oddRow":"evenRow" %>' >
        <td>
            <input id='pkId<bean:write name="vo" property="exerid"/>Name' type="hidden" value="<bean:write name="vo" property="exername"/>"/>
        </td>
        <td>
            <bean:write name="vo" property="exername"/>
        </td>
        <td><bean:write name="vo" property="contentcatename"/></td>
        <td><bean:write name="vo" property="createtime" format="yyyy-MM-dd"/></td>
        <td><a href="#" onclick="goUrl('/exercise/listExeruser.do?queryVO.exerid=<bean:write name="vo" property="exerid"/>&backUrlEncode=','backUrlEncode');return false;">查看练习人员</a>
            | <a href="#" onclick="goUrl('/exercise/editExercise.do?vo.exerid=<bean:write name="vo" property="exerid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
            | <a href="#" onclick="openWin('/exercise/preEnterExercise.do?exerid=<bean:write name="vo" property="exerid"/>',750,550,'yes','yes');return false;">测试</a>
            | <a href="#" onclick="deleteExercise('<bean:write name="vo" property="exerid"/>','<%="dataTrId"+ind %>');return false;">删除</a>
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
	   
	   function deleteExercise(exerid, trId){
	       if(!confirm('确定删除?')){
	          return;
	       }
	       var url = "<%=WebConstant.WebContext%>/exercise/delExercise.do?vo.exerid="+exerid;
           var param = null;
	       var rtnObj = toAjaxUrl(url, false, param);
           if(rtnObj.result=="1"){ 
              showMessagePage(rtnObj.info); 
              var ddTable = document.getElementById("dataTableId").tBodies[0];
	       	  var rowNo = document.getElementById(trId).sectionRowIndex;
	          ddTable.deleteRow(rowNo);
           }else {
              showMessagePage(rtnObj.info);
           }
	   }
	   
     //-->
  </script>
  </body>
</html:html>

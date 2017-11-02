<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,netTest.product.constant.UserproductConstant, commonTool.constant.PayTypeConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="userid" name="userproductForm" property="userid" type="java.lang.Long"/>
    <bean:define id="userhasprod" name="userproductForm" property="userhasprod" type="java.lang.String"/>
    <title>学员产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="/<%=CommonConstant.WebContext_Education%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/iframe/iframeResizer.contentWindow.min.js"></script>
	<script language="javascript">
	<!--
	   function switchproductview(selObj){
	      if(selObj==null){
             return;
          }
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue!="2"){ return; }
          goUrl('/userproduct/listuserendprodlog.do?selectproductscope=1&queryVO.productbaseid=<bean:write name="userproductForm" property="queryVO.productbaseid"/>'
                 +'&queryVO.productname=<bean:write name="userproductForm" property="queryVO.productname"/>');
	   }

	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/userproduct/listoneproduser.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userproductForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="%2F<%=CommonConstant.WebContext_Education%>%2Fuserproduct%2Flistoneproduser.do%3FqueryVO.productbaseid=<bean:write name="userproductForm" property="queryVO.productbaseid"/>">
  <input id="thispageUrlId" type="hidden" value="/userproduct/listoneproduser.do?queryVO.productbaseid=<bean:write name="userproductForm" property="queryVO.productbaseid"/>&queryVO.productname=<bean:write name="userproductForm" property="queryVO.productname"/>">
  <input id="productidId" type="hidden" name="queryVO.productbaseid" value="<bean:write name="userproductForm" property="queryVO.productbaseid"/>">
  <input type="hidden" name="queryVO.productname" value="<bean:write name="userproductForm" property="queryVO.productname"/>">

  <div class="fieldsTitleDiv">
       <bean:write name="userproductForm" property="productvo.productname"/>
  </div>

  <div id="firstLineDiv">
      <div id="searchDiv">
         学员帐号: 
		<input type="text" name="queryVO.loginname" value="<bean:write name="userproductForm" property="queryVO.loginname"/>"/>
		&nbsp;使用方式:
		<html:select name="userproductForm" property="queryVO.produsetype">
		   <html:option value=""></html:option>
		   <html:optionsSaas consCode="netTest.UserproductConstant.ProdUseType"/>
		</html:select>
		&nbsp;查看类型:
		<select name="selectproducttype" id="selectproducttypeId" onchange="switchproductview(this);">
		   <option value="1" selected="selected">正在使用的课程</option>
		   <option value="2">删除的学员课程</option>
		</select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv" >
      <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" class="button green fontBold" onclick="getAndToUrl('backUrl');return false;">返回列表</button></li>
			 <li><button type="button" class="button green fontBold" onclick="goUrl('/userproduct/addprodmoreuserpage.do?vo.productbaseid=<bean:write name="userproductForm" property="queryVO.productbaseid"/>&vo.productname=<bean:write name="userproductForm" property="queryVO.productname"/>&backUrlEncode=','backUrlEncode');return false;">新增课程人员</button></li>
		  </ul>
	  </div>
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
        <td>使用方式</td>
        <td>使用状态</td>
        <td></td>
      </tr>
      </thead>
	  <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.product.vo.Userproduct">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><a href="javascript:void(0)" onclick="newDiv('/userproduct/userproduct_mag_main.jsp?showtype=user&userid=<bean:write name="vo" property="userid"/>&userproductid=<bean:write name="vo" property="userproductid"/>',0,1,650,400);">
               <bean:write name="vo" property="displayname"/>
            </a>
        </td>
        <td>
		    <%if(UserproductConstant.ProdUseType_operatorMag.equals(vo.getProdusetype())){ %>
                  管理课程
            <%}else { %>
			    <bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/><br>
	            <font style="font-size: smaller">
	            <%if(PayTypeConstant.PayType_useday_month.equals(vo.getPaytype())
	              ||(PayTypeConstant.PayType_useday_somedays.equals(vo.getPaytype()))){ %>
	                (&nbsp;使用时间:<bean:write name="vo" property="startdate" format="yyyy-MM-dd"/> -- <bean:write name="vo" property="enddate" format="yyyy-MM-dd"/>&nbsp;)
	            <%} else if(PayTypeConstant.PayType_usetimes_sometimes.equals(vo.getPaytype())){ %>
	                (&nbsp;共:<bean:write name="vo" property="status"/>次, 剩余:<%=vo.getUselimitnum()-vo.getUsenum() %>次&nbsp;)
	            <%} %></font>
            <%} %>
		</td>
        <td><bean:writeSaas name="vo" property="status" consCode="UserProduct.status"/></td>
        <td style="white-space: nowrap">
            <img src="../styles/imgs/edit.png" title="查看编辑" style="cursor:pointer;" onclick="newDiv('/userproduct/userproduct_mag_main.jsp?showtype=product&userid=<bean:write name="vo" property="userid"/>&userproductid=<bean:write name="vo" property="userproductid"/>',0,1,650,400);return false;"/>&nbsp;&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/product/deluserprod.do?vo.userproductid=<bean:write name="vo" property="userproductid"/>&userid=<%=userid %>','thispageUrlId');return false;"/>
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

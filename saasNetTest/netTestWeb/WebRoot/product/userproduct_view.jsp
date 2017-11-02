<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.PayTypeConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="paytype" name="userproductForm" property="vo.paytype" type="java.lang.Short"/>
    <bean:define id="produsetype" name="userproductForm" property="vo.produsetype" type="java.lang.Short"/>
    <bean:define id="statusVar" name="userproductForm" property="vo.status" type="java.lang.Short"/>
    <bean:define id="caneditproduct" name="userproductForm" property="caneditproduct" type="java.lang.Boolean"/>
    <title>学员学习课程</title>
    <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    
	<script type="text/javascript">
      
	</script>
  </head>

  <body>
  <div class="editPage">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userproductForm" property="backUrlEncode"/>">
  <input name="vo.userproductid" value="<bean:write name="userproductForm" property="vo.userproductid"/>" type="hidden">

  <div>学员学习课程</div>
  <div style="height: 25px;"></div>

  <div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div>
     <table class="formTable2" style="width: 99%">
        <tr><td>产品名称:</td>
            <td colspan="3">
                <a href="<%=WebConstant.WebContext %>/product/productDetailFromIndex.jsp?shopid=<bean:write name="userproductForm" property="vo.shopid"/>&productid=<bean:write name="userproductForm" property="vo.productbaseid"/>&urlType=2" target="productviewpage" title="打开课程介绍页面">
                   <bean:write name="userproductForm" property="vo.productname"/>
                </a>
            </td>
        </tr>
        <tr><td>产品所在商店:</td>
            <td colspan="3">
                <a href="javascript:void(0);return false;" onclick="window.open('<%=WebConstant.WebContext %>/shop/toshop.do?shopid=<bean:write name="userproductForm" property="vo.shopid"/>&loadauthority=1','shopwin');return false;"><bean:write name="userproductForm" property="vo.shopname"/></a>
                <img src="../styles/imgs/mail_send.png" style="cursor: pointer;width: 20px;" title="send message to shop admin" onclick="parent.goUrl('/userAdmin/addnotification.do?objectadmintype=shop&objectid=<bean:write name="userproductForm" property="vo.shopid"/>');return false;"/>
            </td>
        </tr>
        <tr>
            <td>开始使用时间:</td><td>&nbsp;<bean:write name="userproductForm" property="vo.startdate" format="yyyy-MM-dd HH:mm:ss"/></td>
            <td>付款方式:</td><td><bean:writeSaas name="userproductForm" property="vo.paytype" consCode="Common.PayTypeConstant.PayType"/></td>
        </tr>
        <tr>
            <td>到期时间:</td>
            <td>
                &nbsp;<%if(PayTypeConstant.PayType_useday_month.equals(paytype)||PayTypeConstant.PayType_useday_somedays.equals(paytype)){ %>
                    <bean:write name="userproductForm" property="vo.enddate" format="yyyy-MM-dd"/>
                <%} %>
            </td>
            <td>状态:</td>
            <td>
			   <bean:writeSaas name="userproductForm" property="vo.status" consCode="UserProduct.status"/>
            </td>
        </tr>
        <tr id="statuschangedescId" style="display: none">
            <td>状态改变描述:</td>
            <td id="statuschangetdId" colspan="3">
                <input id="statusdescInputId" name="statusdesc" type="text" style="width: 100%">
            </td>
        </tr>
        <tr>
            <td>上次学习时间:</td>
            <td><bean:write name="userproductForm" property="vo.lastaccesstime" format="yyyy-MM-dd HH:mm"/></td>
            <td>使用方式:</td>
            <td colspan="3">
               <bean:writeSaas name="userproductForm" property="vo.produsetype" consCode="netTest.UserproductConstant.ProdUseType"/>
            </td>
        </tr>
     </table>
  </div>

  </div>

  </body>
</html:html>

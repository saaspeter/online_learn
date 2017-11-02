<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>生成订单</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	
	<style type="text/css">
		#bannermenu3{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
	</style>
	
	<script type="text/javascript">
	<!--    
       function delProd(productbaseid,msg){
          if(productbaseid==null||productbaseid=="")
          {
             return;   
          }
          if(msg!=null&&msg!=""&&confirm(msg)){
             var url = "/shopping/delProdFromCart.do?orderprodVO.productbaseid="+productbaseid
                     +"&backUrlEncode="+document.getElementById("backUrlEncode").value;
             goUrl(url);
          }
	   }
	//-->
	</script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="navlistBar">
        课程订单&nbsp;&gt;&gt;&nbsp;购物车
  </div>
  
  <div class="fieldsTitleDiv">
      购物车课程列表
  </div>
  <div class="listPage">
  <html:form styleId="cartFormId" action="/order/geneOrder.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shoppingCartForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shoppingCartForm" property="backUrlEncode"/>">
 
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <logic:notEmpty name="shoppingCartForm" property="prodList">
  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>产品名称</td>
        <td>产品价格</td>
        <td>所属商店</td>
        <td>删除</td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="shoppingCartForm" property="prodList">
	  <logic:iterate id="vo" name="shoppingCartForm" property="prodList"  indexId="ind">
	  <tr>
        <td></td>
        <td><a href="javascript:void(0);" onclick="newDiv('/product/viewCourseToBuy.do?queryVO.productbaseid=<bean:write name="vo" property="productbaseid"/>',0,1,800,600);"><bean:write name="vo" property="productname"/></a></td>
        <td><bean:write name="vo" property="productprice"/></td>
        <td><bean:write name="vo" property="shopname"/></td>
        <td><a href="#" onclick="delProd('<bean:write name="vo" property="productbaseid"/>','确定要取消该产品?');return false;">取消</a></td>
      </tr>
      </logic:iterate>
      </logic:present>
      <tr>
        <td colspan="3" style="text-align: right;font: bold;">合计:&nbsp;&nbsp;</td>
        <td colspan="3"><bean:write name="shoppingCartForm" property="custorder.fullcost"/></td>
      </tr>
      <logic:notEmpty name="shoppingCartForm" property="payinfo">
      <tr>
        <td colspan="5">
                                支付方式: <bean:write name="shoppingCartForm" property="payinfo"/>
            &nbsp;(<font color="red">本付款信息是有学校自行输入，本网站不做校验，请自行与学校联系确认</font>)
        </td>
      </tr>
      </logic:notEmpty>
      <tbody/>
    </table>
  </div>
  
  <div style="clear: both;height: 20px;"></div>
  <div style="margin-left: 30px;">客户备注:&nbsp;&nbsp;
       <textarea name="notes" rows="2" cols="90" usage="max-length:200" fie="客户备注"><bean:write name="shoppingCartForm" property="custorder.notes"/></textarea>
  </div>
  
  <div style="clear: both;height: 20px;"></div>
  
  <div style="clear: both;width:100%;height:40px;text-align:center;">
      <button type="button" onclick="submitForm('cartFormId');return false;" style="height: 40px;font-size: large;">提交订单</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <button type="button" onclick="goUrl('/shopping/cancelShoppingOrder.do');return false;" style="height: 40px;font-size: large;">清空购物车</button>
  </div>
  </logic:notEmpty>

  <logic:empty name="shoppingCartForm" property="prodList">
     <div style="text-align: center;padding-top: 8%;">您目前购物车中没有任何物品</div>
  </logic:empty>
  
  <div id="buttomDiv"></div>
  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </html:form>
  </div>
  </div>

  </body>
</html:html>

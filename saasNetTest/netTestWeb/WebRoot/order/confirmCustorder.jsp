<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="netTest.order.constant.OrderConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<bean:define id="orderid" name="custorderForm" property="vo.orderid"></bean:define>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认审批</title>
<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">

<script type="text/javascript" src="../styles/script/pageAction.js"></script>
<style>
div{
   margin:2px;
}
</style>
<script type="text/javascript">
<!--  

    function changeOption(seltype, value){
       if(seltype=='1'){
          document.getElementById("appstautsId").value=value;
          document.getElementById('trOrgId').style.display='';
       }else if(seltype=='2'){
          document.getElementById("appstautsId").value=value;
          document.getElementById('trOrgId').style.display='none';
       }
    }
       
//-->
</script>
</head>
<body>
<div id="fieldsTitleDiv">审核订单</div>

<div>
     <html:form styleId="editForm" action="/order/approveOrder.do" method="post">
     <input type="hidden" name="vo.orderid" value="<%=orderid%>"/>
     <table  class="formTable">
         <tr>
            <td width="30%">请选择审批结果:</td>
            <td>
               <label for="approveId">
			     <input id="approveId" type="radio" name="approveradio" checked="checked" value="<%=OrderConstant.OrderStatus_approve%>" onclick="changeOption('1','<%=OrderConstant.OrderStatus_approve%>');"/>审批通过该订单
		       </label>
		       <label for="denyId">
		         <input id="denyId" type="radio" name="approveradio" value="<%=OrderConstant.OrderStatus_deny%>" onclick="changeOption('2','<%=OrderConstant.OrderStatus_deny%>');"/>否决该订单
		       </label>
		       <input id="appstautsId" name="vo.orderstatus" type="hidden" value='<%=OrderConstant.OrderStatus_approve%>'/>
            </td>
         </tr>
         <tr>
            <td>请填写审批说明:</td>
            <td>
               <textarea id="appnotesId" name="vo.appnotes" rows="2" cols="35" usage="max-length:200" tip="审批说明长度不能大于200个字"></textarea>
            </td>
         </tr>
         <logic:present name="custorderForm" property="usershopVO">
         <tr>
            <td colspan="2">申请者已经存在商店中，名称为:<bean:write name="custorderForm" property="usershopVO.nickname"/>(<bean:write name="custorderForm" property="usershopVO.loginname"/>)</td>
         </tr>
         </logic:present>
         <logic:notPresent name="custorderForm" property="usershopVO">
         <tr id="trOrgId">
            <td colspan="2">
                申请者不在单位中商店单位中，如果想将该申请者加入商店单位中，可以在课程人员列表中将该用户加入本商店单位中，这样用户将得到更多的内部员工功能
            </td>
         </tr>
         </logic:notPresent>
     </table>
     </html:form>
</div>
<br>
   <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
   </div>
<br><br><br>
<div id="functionBarButtomDiv"><button type="button" onclick="submitForm('editForm');return false;">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" onclick="parent.clearDiv();return false;">取消</button></div>
</body>
</html>
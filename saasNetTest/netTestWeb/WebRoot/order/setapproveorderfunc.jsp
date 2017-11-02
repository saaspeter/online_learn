<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="platform.constant.SysfunctionitemConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String shopid = request.getParameter("shopid");
   if(shopid==null) {shopid="";}
   String setoption = request.getParameter("setoption");  %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">

<script type="text/javascript" src="../styles/script/pageAction.js"></script>
<style>
div{
   margin:2px;
}
</style>
<script type="text/javascript">
<!--  

    function showOption(option){ 
       if(option=="1"){ 
           document.getElementById("autoapproveId").checked="checked";
       }else if(option=="0"){ 
           document.getElementById("manapproveId").checked="checked";
       }else{
           alsert('system error!');
           parent.clearDiv();
       }
    }
    
    function showapproveset(){
       var optionvar = null;
       <%if(setoption!=null && !"".equals(setoption)){ %>
           optionvar = '<%=setoption %>';
       <%}else { %>
            var rtnObj = toAjaxUrl("/shop/showorderapproveconfig.do?shopid=<%=shopid %>",false);
            optionvar = rtnObj.result;
        <%} %>
        showOption(optionvar);
    }
    
    function saveoption(){
        var checkvalue = getCheckedValue("approveorderoptionname");
        var url = null;
        if(checkvalue=='1'){
           url = "/shop/saveorderapprovefunc.do?shopid=<%=shopid %>";
        }else if(checkvalue=='-1'){
           url = "/shop/deletefunction.do?shopid=<%=shopid %>&functioncode=<%=SysfunctionitemConstant.Edu_OrderAutoApprove %>";
        }
        if(url!=null){
           var rtnObj = toAjaxUrl(url,false);
           if(rtnObj.result=='1'){
              alert('设置成功');
              parent.showorderapproveset();
              parent.clearDiv();
           }else {
              alert(rtnObj.info);
           }
        }
    }
       
//-->
</script>
</head>
<body>
<div id="fieldsTitleDiv">自动审核订单设置</div>

<div>
     <table class="formTable">
         <tr>
            <td>自动审批订单设置:</td>
            <td>
               <label for="autoapproveId">
			     <input id="autoapproveId" name="approveorderoptionname" type="radio" value="1" onclick=""/><bean:message key="netTest.page.order.approveSet.autoApproved"/>
		       </label>
		       <label for="manapproveId">
		         <input id="manapproveId" name="approveorderoptionname" type="radio" value="-1" onclick=""/><bean:message key="netTest.page.order.approveSet.manaualApproved"/>
		       </label>
            </td>
         </tr>
         <tr>
            <td colspan="2"><bean:message key="netTest.page.order.approveSet.autoApproved"/>:<bean:message key="netTest.page.order.approveSet.autoApprovedTip"/></td>
         </tr>
     </table>
</div>
<br>
<div id="functionBarButtomDiv"><button type="button" onclick="saveoption();return false;">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" onclick="parent.clearDiv();return false;">取消</button></div>
    <script type="text/javascript">
	 <!--
       window.onload=function(){
           showapproveset();
       }
     -->
     </script>
</body>
</html>
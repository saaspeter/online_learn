<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.product.constant.UserproductConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<html>
<head>
    <html:base />
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
	<title>选择人员</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script language="javascript">
	<!--		
		function selUserCB(ids,names){
          if(ids!=null&&ids!=""){
             addMultiSelOpt(ids,names,"selectUserId","userIdStrId","usernameStrId");
             clearDiv();
          }
        }
        
        function selectUser(){
           newDiv('/org/selectorgusermain.do',1,1,650,450);
        }

	//-->
	</script>
</head>

<body>
   <div class="navlistBar">
       <bean:write name="userproductForm" property="vo.productname"/> &gt; 新增学习人员
   </div>
   
   <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userproductForm" property="backUrl"/>">
  
   <div style="text-align: center;margin: 50px;">
      <html:form styleId="formId" action="/userproduct/saveprodmoreuser.do" method="post">
          <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userproductForm" property="backUrlEncode"/>">
	      <input type="hidden" name="vo.productbaseid" value="<bean:write name="userproductForm" property="vo.productbaseid"/>"/>
	      <table class="formTable2" style="width:90%;">
	         <tr>
	            <td>使用课程方式:</td>
	            <td>
	                <input type="radio" name="vo.produsetype" value="<%=UserproductConstant.ProdUseType_userNormal.toString() %>" checked="checked">使用课程学习
	                <input type="radio" name="vo.produsetype" value="<%=UserproductConstant.ProdUseType_operatorMag.toString() %>">管理课程
	            </td>
	         </tr>
	         <tr>
	            <td>选择人员:</td>
	            <td>
	                <input id="userIdStrId" name="useridStr" type="hidden">
	                <input id="usernameStrId" type="hidden">
	                <select id="selectUserId" name="" multiple="multiple" size="12" style="width:80%;">
			        </select>
		            <img src="../styles/imgs/ico/search.gif" alt="选择人员" onclick="selectUser();return false;">
		            <img src="../styles/imgs/ico/reset.gif" alt="删除人员" onclick="rmMultiSelOpt('selectUserId','userIdStrId','usernameStrId');">
	            </td>
	         </tr>
	      </table>
      </html:form>
   </div>
   <div id="functionBarButtomDiv">
  	  <ul>
	     <li><button class="bigButton" type="button" onclick="submitForm('formId');return  false;"><bean:message key="netTest.commonpage.save"/></button></li>
		 <li><button class="bigButton" type="button" onclick="goUrl('/userproduct/listoneproduser.do?queryVO.productbaseid=<bean:write name="userproductForm" property="vo.productbaseid"/>&queryVO.productname=<bean:write name="userproductForm" property="vo.productname"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
	  </ul>
   </div>
   
</body>
</html>
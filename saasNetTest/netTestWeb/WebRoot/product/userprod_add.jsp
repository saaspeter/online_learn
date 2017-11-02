<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.PayTypeConstant, netTest.product.constant.UserproductConstant, commonTool.biz.logicImpl.ConstantLogicImpl, netTest.exception.ExceptionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="userid" name="userproductForm" property="vo.userid" type="java.lang.Long"/>
    <bean:define id="localeid" name="userproductForm" property="localeid" type="java.lang.Long"/>
    <title>新增学员学习课程</title>
    <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/userproduct.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    
	<script type="text/javascript">
	
	   function getPayType(paytype){
	      var paytypeArr = {};
	      paytypeArr[0] = ['<%=PayTypeConstant.PayType_free %>','<%=ConstantLogicImpl.getInstance().getLabel("Common.PayTypeConstant.PayType",PayTypeConstant.PayType_free.toString(),localeid ) %>'];
	      paytypeArr[1] = ['<%=PayTypeConstant.PayType_once_nolimit %>','<%=ConstantLogicImpl.getInstance().getLabel("Common.PayTypeConstant.PayType",PayTypeConstant.PayType_once_nolimit.toString(),localeid ) %>'];
	      paytypeArr[2] = ['<%=PayTypeConstant.PayType_useday_month %>','<%=ConstantLogicImpl.getInstance().getLabel("Common.PayTypeConstant.PayType",PayTypeConstant.PayType_useday_month.toString(),localeid ) %>'];
	      paytypeArr[3] = ['<%=PayTypeConstant.PayType_useday_somedays %>','<%=ConstantLogicImpl.getInstance().getLabel("Common.PayTypeConstant.PayType",PayTypeConstant.PayType_useday_somedays.toString(),localeid ) %>'];
	      paytypeArr[4] = ['<%=PayTypeConstant.PayType_usetimes_sometimes %>','<%=ConstantLogicImpl.getInstance().getLabel("Common.PayTypeConstant.PayType",PayTypeConstant.PayType_usetimes_sometimes.toString(),localeid ) %>'];
	      var paytypename = '';
	      for(var i=0;i<5;i++){
	         if(paytypeArr[i][0]==paytype){
	            paytypename = paytypeArr[i][1];
	         }
	      }
	      return paytypename;
	   }
       
       function errShow(msg){
          if('<%=ExceptionConstant.Error_User_HasProduct %>'==msg){
             showMessagePage('<bean:message key="<%=ExceptionConstant.Error_User_HasProduct %>"/>');
          }else {
             alert(getMessage("systemError"));
          }
       }
       
       function selProduct_CB(id,name){
          document.getElementById("displayMessDiv").innerHTML='';
          clearDiv();
          DWREngine.setErrorHandler(errShow);
          userproduct.getNonUserProduct(<%=userid %>,id,<%=localeid %>,function CB_getproduct(prodobj){
	            if(prodobj!=null){
	               document.getElementById("productidId").value=id;
                   document.getElementById("productnameId").value=name;
	            
		           document.getElementById("catenameId").innerHTML=prodobj.categoryname;
		           document.getElementById("priceId").innerHTML=((prodobj.productprice=='null')?"":prodobj.productprice);
		           document.getElementById("paytypeId").innerHTML=getPayType(prodobj.paytype);
		           document.getElementById("usenumId").innerHTML=((prodobj.uselimitnum==null)?"":prodobj.uselimitnum);
		        }
	         }
	       );
       }
       
       function post(){
          var useridVar = document.getElementById("useridId").value;
          var productidVar = document.getElementById("productidId").value;
          if(useridVar!='' && productidVar!=''){
             document.getElementById("editForm").submit();
          }else if(productidVar==''){
             alert('请选择产品!');
          }else {
             alert(getMessage("systemError"));
          }
       }
	</script>
  </head>

  <body>
  <div class="editPage">
  <html:form styleId="editForm" action="/product/saveoneuserprod.do" method="post">
  <input id="useridId" name="vo.userid" type="hidden" value="<%=userid %>">
  <input id="productidId" name="vo.productbaseid" type="hidden">

  <div>用户新增课程：</div>
  <div style="height: 25px;"></div>
  
  <div>请选择产品:<input id="productnameId" type="text" value="" style="width:275px;" readonly="readonly" onclick="selectProd(this);"></div>
  <div style="margin: 5px;">
       <html:radio name="userproductForm" property="vo.produsetype" tagName="vo.produsetype" value="<%=UserproductConstant.ProdUseType_userNormal.toString() %>" >使用课程学习</html:radio>
       <html:radio name="userproductForm" property="vo.produsetype" tagName="vo.produsetype" value="<%=UserproductConstant.ProdUseType_operatorMag.toString() %>" >管理课程</html:radio>
  </div>

  <div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div style="height: 25px;"></div>

  <div>
     <table style="width: 99%;" class="formTable">
        <tr>
            <td>所属目录:&nbsp;</td><td id="catenameId">N/A</td>
            <td>产品价格:&nbsp;</td><td id="priceId">N/A</td>
        </tr>
        <tr>
            <td>付款方式:&nbsp;</td><td id="paytypeId">N/A</td>
            <td>使用期限:&nbsp;</td><td id="usenumId">N/A</td>
        </tr>
     </table>
  </div>

     <div id="functionBarButtomDiv">
	  	 <ul>
		     <li><button type="button" onclick="post();"><bean:message key="netTest.commonpage.save"/></button></li>
			 <li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		 </ul>
	  </div>

  </html:form>
  </div>

  </body>
</html:html>

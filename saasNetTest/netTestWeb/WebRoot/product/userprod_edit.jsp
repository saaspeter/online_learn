<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.PayTypeConstant, netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <%String savetype = request.getParameter("savetype"); %>
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="paytype" name="userproductForm" property="vo.paytype" type="java.lang.Short"/>
    <bean:define id="produsetype" name="userproductForm" property="vo.produsetype" type="java.lang.Short"/>
    <bean:define id="statusVar" name="userproductForm" property="vo.status" type="java.lang.Short"/>
    <bean:define id="caneditproduct" name="userproductForm" property="caneditproduct" type="java.lang.Boolean"/>
    <title>编辑学员学习课程</title>
    <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <link rel="stylesheet" type="text/css" href="../styles/css/beautifulButton.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    
	<script type="text/javascript">
		   
	  function post(){
	      var produsetype = getCheckedValue("vo.produsetype");
	      var status_new = getValue(document.getElementById("statusId"));
          if(produsetype!='<%=produsetype %>'||status_new!='<%=statusVar %>'){ 
             if(status_new!='<%=statusVar %>'){
                var desctxt = document.getElementById("statusdescInputId").value;
                if(desctxt==null||desctxt==''||desctxt.length>40){
                   alert('状态改变描述不能为空并且长度小于40个字!');
                   return;
                }
             }
             submitForm("editForm");
          }else{
             parent.parent.clearDiv();
          }
      }
      
	</script>
  </head>

  <body>
  <div class="editPage">
  <html:form styleId="editForm" action="/product/saveoneuserprod.do" method="post">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userproductForm" property="backUrlEncode"/>%26savetype%3Dupdate">
  <input name="vo.userproductid" value="<bean:write name="userproductForm" property="vo.userproductid"/>" type="hidden">
  <div style="margin: 10px;">编辑学员学习课程</div>

  <div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div>
     <table class="formTable2">
        <tr>
            <td class="keytd">产品名称:</td><td colspan="3"><bean:write name="userproductForm" property="vo.productname"/></td>
        </tr>
        
        <tr>
            <td class="keytd">开始使用时间:</td><td>&nbsp;<bean:write name="userproductForm" property="vo.startdate" format="yyyy-MM-dd HH:mm"/></td>
            <td class="keytd">到期时间:</td>
            <td>
                &nbsp;<%if(PayTypeConstant.PayType_useday_month.equals(paytype)||PayTypeConstant.PayType_useday_somedays.equals(paytype)){ %>
                    <bean:write name="userproductForm" property="vo.enddate" format="yyyy-MM-dd HH:mm"/>
                <%} %>
            </td>
            
        </tr>
        <tr>
            <td class="keytd">付款方式:</td><td><bean:writeSaas name="userproductForm" property="vo.paytype" consCode="Common.PayTypeConstant.PayType"/></td>
            <td class="keytd">上次学习时间:</td>
            <td><bean:write name="userproductForm" property="vo.lastaccesstime" format="yyyy-MM-dd HH:mm"/></td>
        </tr>
        <tr>
            <td class="keytd">状态:</td>
            <td>
               <html:select styleId="statusId" name="userproductForm" property="vo.status" style="font-size: large;">
				   <html:optionsSaas consCode="UserProduct.status"/>
			   </html:select>
            </td>
            <td class="keytd">使用方式:</td>
            <td>
                <%if(caneditproduct!=null && caneditproduct){ %>
                   <label for="radio1">
                   <html:radio styleId="radio1" name="userproductForm" property="vo.produsetype" tagName="vo.produsetype" value="<%=UserproductConstant.ProdUseType_userNormal.toString() %>" >使用课程学习</html:radio>
                   </label>
                   <br>
                   <label for="radio2">
                   <html:radio styleId="radio2" name="userproductForm" property="vo.produsetype" tagName="vo.produsetype" value="<%=UserproductConstant.ProdUseType_operatorMag.toString() %>" >管理课程</html:radio>
                   </label>
                <%}else{ %>
                   <bean:writeSaas name="userproductForm" property="vo.produsetype" consCode="netTest.UserproductConstant.ProdUseType"/>
                <%} %>
            </td>
        </tr>
        <tr>
            <td class="keytd"><font color="#ff0000">*</font>状态改变描述:</td>
            <td id="statuschangetdId" colspan="3">
                <input id="statusdescInputId" name="statusdesc" type="text" fie="状态改变描述" usage="notempty,max-length:60" style="width: 100%">
            </td>
        </tr>
     </table>
  </div>

     <div style="text-align: center;padding-top: 20px;">
	  	 <button type="button" class=".button medium green" onclick="post();return false;"><bean:message key="netTest.commonpage.save"/></button>
	 </div>

  </html:form>
  </div>
  <script type="text/javascript">
      <%if("update".equals(savetype)){ %>
          if(typeof(parent.setflushflag)!="undefined"){
        	  parent.setflushflag();
        	  parent.closeDivBtn();
          }else {
        	  alert('non');
          }
      <%} %>
  </script>
  </body>
</html:html>

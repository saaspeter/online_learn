<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="wareForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="wareForm" property="editType" type="java.lang.Integer"/>
  <% boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
    } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑题库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type="text/javascript" src="../styles/script/utiltool.js"></script>
    <script language="javascript">
	<!--
	   
       function selUserProdCB(ids,names){
          selUserProdCB_base(ids,names);
       }
	//-->
	</script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/wareques/saveWare.do" method="post">
	 <input type="hidden" name="vo.shopid" value="<bean:write name="wareForm" property="vo.shopid"/>" />
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="wareForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="wareForm" property="backUrlEncode"/>">
     <input type="hidden" name="vo.wareid" value="<bean:write name="wareForm" property="vo.wareid"/>">
	 <input type="hidden" id="productidId" name="vo.productbaseid" value="<bean:write name="wareForm" property="vo.productbaseid"/>" />
	 
	  <div id="fieldsTitleDiv"><%if(!isDisable){ %>新增<%}else{ %>编辑<%} %>题库</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><font color="red">*</font>题库名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.warename" value="<bean:write name="wareForm" property="vo.warename"/>" usage="notempty,max-length:60" fie="题库名称"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel"><font color="red">*</font>所属课程:</div>
			   <div class="field"> 
			     <bean:write name="wareForm" property="vo.productname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

            <%if(isDisable){ %>		
            <li>
			   <div class="fieldLabel">创建人:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.creatorname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">创建时间:</div>
			   <div class="field"> 
			      <bean:write name="wareForm" property="vo.warecreatetime" format="yyyy-MM-dd"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            <%} %>

		    <li>
			   <div class="fieldLabel">描述:</div>
			   <div class="field"> 
			     <input type="text" name="vo.waredesc" value="<bean:write name="wareForm" property="vo.waredesc"/>" usage="max-length:250" fie="题库名称"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div style="height:50px">&nbsp;</div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="bigButton" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" class="bigButton" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="editType" name="productcategoryvalueForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>产品目录国家语言设置</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>

  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/productcategory/saveProductcategoryvalue.do" method="post">&nbsp;
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productcategoryvalueForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productcategoryvalueForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.categoryvalueid" value="<bean:write name="productcategoryvalueForm" property="vo.categoryvalueid"/>">
	 <input type="hidden" name="vo.categoryid" value="<bean:write name="productcategoryvalueForm" property="vo.categoryid"/>">
	 <bean:define id="selectedLocaleStr" name="productcategoryvalueForm" property="selectedLocaleStr" type="java.lang.String"></bean:define>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord" bundle="platform.pagemess"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">国家地区:</div>
			   <div class="field"> 
			     <html:select name="productcategoryvalueForm" property="vo.localeid" style="width:150px;" disabled="<%=isDisable %>">
					  <html:optionsSaas localeListSet="true" removeStr="<%=selectedLocaleStr %>"/>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">目录名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.categoryname" value="<bean:write name="productcategoryvalueForm" property="vo.categoryname"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">目录描述:</div>
			   <div class="field"> 
			     <input type="text" name="vo.categorydesc" value="<bean:write name="productcategoryvalueForm" property="vo.categorydesc"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="platform.commonpage.save" bundle="platform.pagemess"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back" bundle="platform.pagemess"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

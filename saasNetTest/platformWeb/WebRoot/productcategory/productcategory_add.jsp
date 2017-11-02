<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.productcategory.productcategory_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
   <script language="javascript">
	<!--
	   function selectSys(){
	      newDiv("/subsysMag/listSubsys.do",1,0,670,450);
	   }
	   
       function selectSysCallBack(ids,names){
          if(ids!=null&&ids!=""){
             document.getElementById("syscodesStrId").value=ids;
             document.getElementById("syscodesStrNamesId").value=names;
          }
          clearDiv();
       }
	//-->
	</script>

  </head>
  
  <body>
	<div class="listPage">
	<html:form styleId="editForm" action="/productcategory/productcategory.do?method=save" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productcategoryForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productcategoryForm" property="backUrlEncode"/>">	 
	 <input type="hidden" name="vo.pid" value="<bean:write name="productcategoryForm" property="vo.pid"/>">
     <input type="hidden" name="vo.categorylevel" value="<bean:write name="productcategoryForm" property="vo.categorylevel"/>"/>
	 <input id="syscodesStrId" type="hidden" name="vo.syscodesStr" value=""> 
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
		  <div id="toUrl">

		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="errorDisplayDiv"></div>
	  
	  <div id="fieldDisDiv">
	     <ul>
	        <li>
			   <div class="fieldLabel">语言选择:</div>
			   <div class="field"> 
			      <html:select name="productcategoryForm" property="vo.localeid" style="width:300px">
					<html:optionsCollection name="productcategoryForm" property="localesList"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.categoryname"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.categoryname" style="width:300px" usage="notempty" exp="^\S{0,60}$" tip="不能为空，<bean:message key="commonWeb.js.checkform.noMoreThan" bundle="BizKey" arg0="60"/>" fie="目录名称" value="<bean:write name="productcategoryForm" property="vo.categoryname"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">上级目录:</div>
			   <div class="field"><bean:write name="productcategoryForm" property="vo.parentName"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.categorylevel"/>:</div>
			   <div class="field"> <bean:write name="productcategoryForm" property="vo.categorylevel"/>
			   </div>
			   <div class="fieldDesc"><br></div>
			</li>
			<li>
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.disorder"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.disOrder" style="width:300px" usage="int+" tip="显示级别必须为正整数!" value="<bean:write name="productcategoryForm" property="vo.disOrder"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.categorydesc"/>:</div>
			   <div class="field"> 
			     <textarea rows="4" cols="" name="vo.categorydesc" style="width:300px"><bean:write name="productcategoryForm" property="vo.categorydesc"/></textarea>
			   </div>
			   <div class="fieldDesc"><bean:message key="platform.page.productcategory.productcategory_addEdit.jsp.fieldDesc.account"/></div>
			</li>
			<li>
			   <div class="fieldLabel">适用系统:</div>
			   <div class="field"> 
			     <textarea id="syscodesStrNamesId" rows="4" cols="60" readonly="readonly" style="width:300px"></textarea>
			     <button type="button" onclick="selectSys();return false;">选择系统</button>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<div id="fieldDisPlus"></div>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

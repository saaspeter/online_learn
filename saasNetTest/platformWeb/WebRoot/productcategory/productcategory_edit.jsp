<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant,platformWeb.base.KeyInMemoryConstant"%>
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
    <script language="JavaScript" type="text/JavaScript">
    <!--
       function submitPage(saveType){
          if(saveType!=null&&saveType.length>0){
            document.getElementById("saveType").value = saveType;
          }
          return submitForm("editForm");
       }
       
    //-->
    </script>
  </head>
  
  <body>
	<div class="listPage">
	<html:form styleId="editForm" action="/productcategory/productcategory.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productcategoryForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productcategoryForm" property="backUrlEncode"/>">
	
	 <input type="hidden" name="vo.categoryid" value="<bean:write name="productcategoryForm" property="queryVO.categoryid"/>">
	 <input type="hidden" name="vo.pid" value="<bean:write name="productcategoryForm" property="queryVO.pid"/>">
	 <input type="hidden" name="vo.categoryvalueid" value="<bean:write name="productcategoryForm" property="queryVO.categoryvalueid"/>">
	 <input type="hidden" id="saveType" name="saveType" value="1">
	 <bean:define id="categoryidPage" name="productcategoryForm" property="queryVO.categoryid" type="java.lang.Long"/>
	 <bean:define id="categoryLevelPage" name="productcategoryForm" property="queryVO.categorylevel" type="java.lang.Short"/>
	 <bean:define id="categoryPidPage" name="productcategoryForm" property="queryVO.pid" type="java.lang.Long"/>
	 
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="if(submitPage('<%=WebConstant.saveType_editPage%>')){newDiv('../pubs/saveDiv.jsp',1,0);} return false;"><bean:message key="platform.commonpage.save"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
		  <div id="toUrl">
		      <bean:message key="platform.commonpage.toUrl"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="platform.commonpage.listPage"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">修改表单</div>
	  
	  <div id="displayMessDiv">
	     <% String disMessKey = request.getAttribute(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessKey));
            String disMessArg0Key = request.getAttribute(KeyInMemoryConstant.DisMessArg0Key)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessArg0Key));
            if(disMessKey!=null&&disMessKey.trim().length()>0){
            if(disMessArg0Key==null||disMessArg0Key.trim().length()<1){
         %>
         <bean:message key="<%=disMessKey %>" bundle="BizKey"/>
         <% }else{%>
         <bean:message key="<%=disMessKey %>" bundle="BizKey" arg0="<%=disMessArg0Key %>"/>
         <%}} %>
	  </div>
	  
	  <div id="fieldDisDiv">
	     <ul>
		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.categoryname"/>:</div>
			   <div class="field"> 
			       <bean:write name="productcategoryForm" property="vo.categoryname"/>
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
			     <input type="hidden" name="vo.categorylevel" value="<bean:write name="productcategoryForm" property="vo.categorylevel"/>"/>
			   </div>
			   <div class="fieldDesc"><br></div>
			</li>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitPage('<%=WebConstant.saveType_editPage%>')){newDiv('../pubs/saveDiv.jsp',1,0);} return false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  
	  <div>
	     <TABLE WIDTH="100%" bgcolor="#eeeeee" align="center" cellSpacing="0">
            <TBODY>
            <TR><TD height="3"></TD></TR>
            <TR>
               <TD width=1></TD>
          	   <TD id="tab1" bgcolor="#0099ff" width="130px"  align="center" onclick="showTab(1,2)">国家语言设置</TD>
          	   <TD width=1></TD>
          	   <TD id="tab2" bgcolor="#00ccff" width="130px"  align="center" onclick="showTab(2,2)">适用系统</TD>
          	   <TD></TD>
        	</TR>
            </TBODY>
        </TABLE>
	 </div>
	   
	   <div id="content1" style="width:100%;height:400px;">
	       <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>/productcategory/listProductcategoryvalue.do?queryVO.categoryid=<bean:write name="productcategoryForm" property="queryVO.categoryid"/>" scrolling="auto" frameborder="0"></iframe>
	   </div>
	   
	   <div id="content2" style="width:100%;height:400px;display:none">
	       <iframe width="100%" height="100%"  src="<%=WebConstant.WebContext %>/productcategory/listSysproductcate.do?queryVO.categoryid=<bean:write name="productcategoryForm" property="queryVO.categoryid"/>" scrolling="auto" frameborder="0"></iframe>
       </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>
	</div>

  </body>
</html:html>

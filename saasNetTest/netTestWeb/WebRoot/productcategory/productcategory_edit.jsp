<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>修改产品目录</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/JavaScript">
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
	<html:form styleId="editForm" action="/productcategory/saveproductcategory.do" method="post">
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
				 <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="platform.commonpage.save" bundle="platform.pagemess"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back" bundle="platform.pagemess"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">修改表单</div>
	  
	  <div id="displayMessDiv">
	     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="fieldDisDiv">
	     <ul>
		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.categoryname" bundle="platform.pagemess"/>:</div>
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
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.categorylevel" bundle="platform.pagemess"/>:</div>
			   <div class="field"> <bean:write name="productcategoryForm" property="vo.categorylevel"/>
			     <input type="hidden" name="vo.categorylevel" value="<bean:write name="productcategoryForm" property="vo.categorylevel"/>"/>
			   </div>
			   <div class="fieldDesc">第几级目录</div>
			</li>
			<li>
			   <div class="fieldLabel"><bean:message key="platform.page.productcategory.productcategory.disorder" bundle="platform.pagemess"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.disOrder" style="width:300px" usage="int+" tip="显示级别必须为正整数!" value="<bean:write name="productcategoryForm" property="vo.disOrder"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">状态:</div>
			   <div class="field">
			        <html:select name="productcategoryForm" property="vo.status">
			           <html:optionsSaas consCode="common.const.commonstatus" param1="1"/>
			        </html:select>
			   </div>
			   <div class="fieldDesc"><br></div>
			</li>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="platform.commonpage.save" bundle="platform.pagemess"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back" bundle="platform.pagemess"/></button></li>
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

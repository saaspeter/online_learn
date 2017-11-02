<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.PayTypeConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productid" name="userproductForm" property="queryVO.productbaseid" type="java.lang.Long"/>
    <title>学员产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
	   function doSelect(){
          var formObj = document.getElementById("list");
          var num = 0;
          var id = "";
          var name = "";
          var ids = "";
          var names = "";
          for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == "keys" && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
				ids = ids+id+",";
			}
		  }
		  if(num==0){
		     showMessagePage(getMessage("mulSelUsr_list_selOneUser_default"));
		     return;
		  }else if(typeof(parent.selUserCB)!="undefined"){
		     parent.selUserCB(ids,names);
		  }else {
		     alert('system.error');
		  }
       }
       
       function doAllSelect(){
          parent.doSelAllProdUser('<%=productid %>');
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/userproduct/seluserbyproduct.do" method="post">
  <input type="hidden" name="queryVO.productbaseid" value="<%=productid %>">
  
  <div id="firstLineDiv">
      <div id="searchDiv">
         学员帐号: 
		<input type="text" name="queryVO.loginname" value="<bean:write name="userproductForm" property="queryVO.loginname"/>"/>
		&nbsp;&nbsp;
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv" >
  	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="doSelect();return false;">选择指定人员</button></li>
			 <li><button type="button" onclick="doAllSelect();return false;">选择全部课程人员</button></li>
			 <li><button type="button" onclick="parent.parent.clearDiv();return false;">取消</button></li>
		  </ul>
	  </div>
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles2.jsp"></tiles:insert>
      </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>用户</td>
        <td>课程</td>
      </tr>
      </thead>
	  <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.product.vo.Userproduct">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><bean:write name="vo" property="displayname"/></td>
        <td><bean:write name="vo" property="productname"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </body>
</html:html>

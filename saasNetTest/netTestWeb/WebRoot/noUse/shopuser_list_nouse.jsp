<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="usershopForm" property="jsSuffix" type="java.lang.String"/>
	<bean:define id="shopid" name="usershopForm" property="sessionShopid" type="java.lang.Long"/>
    <title>机构人员</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/orguserLogic.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	<script type="text/javascript">
	   function getOrg(useridVar, textid){
	        var textObj = document.getElementById(textid);
	        var text = textObj.innerHTML;
	        if(text==null||text==''){
		        orguserLogic.selectOrgForUser(useridVar, <%=shopid %>,
	               function CB_rtn(orguserVO){
	                  if(orguserVO!=null){
	                     var orgname = orguserVO.orgname;
	                     var textObj = document.getElementById(textid);
	     	             textObj.innerHTML = orgname;   
	     	          }
		           }
	     	    );
     	    }
	    }
	    
	   function selectProd(){
	      var inputobj = document.getElementById("productnameId");
	      var inputobjsize = getElementOffset(inputobj);
	      var divobj = newDiv('/usercontext/listCanuseprod.do?actionType=2',1,0,272,300,inputobjsize.x,inputobjsize.y+25);
	   }

       function selProduct_CB(id,name){
          document.getElementById("productidId").value=id;
          document.getElementById("productnameId").value=name;
          clearDiv();
       }
       
       function selOrg(){
	        var url = "/org/selectorgtree.do?selectType=1";
	      	newDiv(url,1,1,300,400);
	    }
	    
	    function selectOrg_CB(id,name,param){
	         if(id!=null&&id!=""){
	            document.getElementById("orgbaseidId").value=id;
	            document.getElementById("orgnameId").value=name;
	         }
	         clearDiv();
        }

	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/usershop/listmyshopuser.do" method="post">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input id="productidId" type="hidden" name="queryVO.productbaseid" value="<bean:write name="usershopForm" property="queryVO.productbaseid"/>" >
  <input id="orgbaseidId" type="hidden" name="queryVO.orgbaseid" value="<bean:write name="usershopForm" property="queryVO.orgbaseid"/>" >
  <input type="hidden" name="queryVO.order_By_Clause" value="<bean:write name="usershopForm" property="queryVO.order_By_Clause"/>">
  <input type="hidden" name="roletype" value="<bean:write name="usershopForm" property="roletype"/>">
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">人员别名: 
		<input type="text" name="queryVO.nickname" value="<bean:write name="usershopForm" property="queryVO.nickname"/>"/>
		所在单位:
		<input type="text" id="orgnameId" name="orgname" value="<bean:write name="usershopForm" property="orgname"/>" onclick="selOrg();" readonly="readonly"/>
		学习课程:
		<input id="productnameId" type="text" name="productname" value="<bean:write name="usershopForm" property="productname"/>" onclick="selectProd();" readonly="readonly"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="netTest.commonpage.complexQuery"/></a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
          <td>登录名:</td>
              <td><input type="text" name="queryVO.loginname" value="<bean:write name="usershopForm" property="queryVO.loginname"/>"/></td>
              <td>人员状态:</td>
              <td>
			     <html:select name="usershopForm" property="queryVO.usershopstatus">
			        <html:option value=""></html:option>
				    <html:optionsSaas consCode="UsershopConstant.UserShopStatus"/>
			     </html:select>
              </td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>姓名(别名)</td>
        <td>所在单位</td>
        <td>状态</td>
        <td>操作</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>" onmouseover="getOrg('<bean:write name="vo" property="userid"/>', 'tdId<%=ind %>');">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><a href="#">
            <bean:write name="vo" property="name"/>(<bean:write name="vo" property="nickname"/>)</a>
        </td>
        <td id="tdId<%=ind %>"></td>
        <td><bean:writeSaas name="vo" property="usershopstatus" consCode="UsershopConstant.UserShopStatus"/></td>
        <td></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <!-- page list -->
  <div style="margin-top: 25px; text-align: center;">
     <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
  </div>
  <!-- page list -->
  </html:form>
  </div>
  <script type="text/javascript">
	 <!--
       window.onload=function(){
		    changeComplexSearch("complexSearchDiv","<bean:write name="usershopForm" property="complexSearchDivStatus"/>");
	   }
     //-->
  </script>
  </body>
</html:html>

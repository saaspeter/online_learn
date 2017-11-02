<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>机构人员</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<bean:define id="jsSuffix" name="usershopForm" property="jsSuffix" type="java.lang.String"/>
	<bean:define id="shopid" name="usershopForm" property="sessionShopid" type="java.lang.Long"/>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
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
	     	             textObj.innerHTML = orguserVO.orgname;
	     	          }
		           }
	     	    );
     	    }
	    }
	    
	    function selUser(){
          var formObj = document.getElementById("list");
          var num = 0;
          var id = "";
          var name = "";
          var ids = "";
          var names = "";
          for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == "keys" && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				name = document.getElementById((formObj.elements[i].id+"name")).value;
				num++;
				ids = ids+id+",";
				names = names+name+";";
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
       
        function selOrg(){
	        var url = "/org/selectorgtree.do?selectType=1";
	      	newDiv(url,1,1,300,300);
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
  <html:form styleId="list" action="/usershop/selshopuserpage.do" method="post">
  <input type="hidden" name="roletype" value="<bean:write name="usershopForm" property="roletype"/>" />
  <input id="orgbaseidId" type="hidden" name="queryVO.orgbaseid" value="<bean:write name="usershopForm" property="queryVO.orgbaseid"/>" >
  <input id="prodidId" type="hidden" name="queryVO.productbaseid" value="<bean:write name="usershopForm" property="queryVO.productbaseid"/>" >
  <input id="productnameId" type="hidden" name="productname" value="<bean:write name="usershopForm" property="productname"/>" >
  
  <div id="firstLineDiv">
      
      <div id="searchDiv">
          课程:&nbsp;<bean:write name="usershopForm" property="productname"/>&nbsp;
          账号: 
		<input type="text" name="queryVO.loginname" value="<bean:write name="usershopForm" property="queryVO.loginname"/>"/>
		 单位:
		<input type="text" id="orgnameId" name="orgname" value="<bean:write name="usershopForm" property="orgname"/>" onclick="selOrg();" readonly="readonly"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>
  
  <div id="functionLineDiv">
      <div id="functionButtonDiv">
		  <ul>
			  <li><button type="button" onclick="selUser();return false;">确定</button></li>
      		  <li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		  </ul>
	  </div>
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles2.jsp"></tiles:insert>
      </div>

  </div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>名称</td>
        <td>所在单位</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>" onmouseover="getOrg('<bean:write name="vo" property="userid"/>', 'tdId<%=ind %>');">
        <td><input type="checkbox" id="user<%=ind %>" name="keys" value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')">
            <input type="hidden" id="user<%=ind %>name" value="<bean:write name="vo" property="loginname"/>" />
        </td>
        <td><bean:write name="vo" property="nickname"/>(<bean:write name="vo" property="loginname"/>)</td>
        <td id="tdId<%=ind %>"></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>

  </html:form>
  </div>

  </body>
</html:html>

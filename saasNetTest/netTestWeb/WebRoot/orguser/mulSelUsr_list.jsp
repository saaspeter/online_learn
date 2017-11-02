<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="orguserForm" property="jsSuffix" type="java.lang.String"/>
    <title>机构人员</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   // 供点击数时调用的方法，如果没够该方法，则调用默认地址
	   function showRight(id){
	      var url = "<%=WebConstant.WebContext %>/orguser/selectorguser.do?queryVO.orgbaseid="+id;
	      document.location.href=url;
	   }
	   
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
				name = document.getElementById((formObj.elements[i].id+"name")).value;
				num++;
				ids = ids+id+",";
				names = names+name+";";
			}
		  }
		  if(num==0){
		     showMessagePage(getMessage("mulSelUsr_list_selOneProd"));
		     return;
		  }else if(typeof(parent.selUserCB)!="undefined"){
		     parent.selUserCB(ids,names);
		  }else if(typeof(parent.parent.selUserCB)!="undefined"){
		     parent.parent.selUserCB(ids,names);
		  }
       }
	   
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/orguser/listorguser.do" method="post">
  <input type="hidden" name="urlType" value="<bean:write name="orguserForm" property="urlType"/>" />
  <input type="hidden" name="queryVO.orgbaseid" value="<bean:write name="orguserForm" property="queryVO.orgbaseid"/>" />
  <input type="hidden" name="queryVO.orgnameStr" value="<bean:write name="orguserForm" property="queryVO.orgnameStr"/>" />
  <div id="firstLineDiv">
      <div id="searchDiv">
          人员姓名: 
		<input type="text" name="queryVO.nickname" value="<bean:write name="orguserForm" property="queryVO.nickname"/>"/>
		查询范围:<html:select name="orguserForm" property="queryVO.isIncludeChild">
                     <html:option value="1">不查询下级单位</html:option>
                     <html:option value="2">查询下级单位</html:option> 
                 </html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="doSelect();return false;">确定选择</button></li>
			 <li><button type="button" onclick="parent.parent.clearDiv();return false;">取消</button></li>
		  </ul>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles2.jsp"></tiles:insert>
      </div>
      <!-- page list -->
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
        <td>学员姓名</td>     
        <td>单位</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="index">
      <tr class="<%=(index%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" id="user<%=index %>" name="keys" value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><bean:write name="vo" property="nickname"/>(<bean:write name="vo" property="loginname"/>)
            <input type="hidden" id="user<%=index %>name" value="<bean:write name="vo" property="nickname"/>(<bean:write name="vo" property="loginname"/>)">
        </td>
	    <td><bean:write name="vo" property="orgname"/></td>
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

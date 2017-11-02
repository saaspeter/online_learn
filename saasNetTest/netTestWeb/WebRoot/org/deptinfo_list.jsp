<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="deptinfoForm" property="jsSuffix" type="java.lang.String"/>
    <title>机构列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type="text/javascript">
	   function modifythis(id){
	     var backUrlEncode = document.getElementById("backUrlEncode").value;
	     var url = "<%=WebConstant.WebContext %>/org/editdeptinfo.do?queryVO.orgbaseid="+id
	               +"&backUrlEncode="+backUrlEncode;
	     document.location.href = url;
	   }
	   
	   // 供点击树时调用的方法，如果没够该方法，则调用默认地址
	   function showRight(id){
	      var url = "<%=WebConstant.WebContext %>/org/listdeptinfo.do?queryVO.pid="+id;
	      document.location.href=url;
	   }
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/org/listdeptinfo.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="deptinfoForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="deptinfoForm" property="backUrlEncode"/>">
  <input id="queryVO.pid" type="hidden" name="queryVO.pid" value="<bean:write name="deptinfoForm" property="queryVO.pid"/>">
  <bean:define id="locale" name="deptinfoForm" property="locale" type="java.util.Locale"/>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">单位名称: 
		<input type="text" name="queryVO.orgname" value="<bean:write name="deptinfoForm" property="queryVO.orgname"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
      <div style="float: left;">
        &gt;&gt;<a href="" onclick="modifythis('<bean:write name="deptinfoForm" property="orgbase.orgbaseid"/>');return false;"><bean:write name="deptinfoForm" property="orgbase.orgname"/></a>
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('<%=WebConstant.WebContext %>/org/adddeptinfo.do?queryVO.pid=<bean:write name="deptinfoForm" property="queryVO.pid"/>');return false;">新增单位</button></li>
             
		  </ul>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
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
        <td width="20px"></td>
        <td>单位名称</td>
	    <td>单位简称</td>
		<td>操作</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        
        <td><a href="javascript:newDiv('<%=WebConstant.WebContext %>/org/viewdeptinfo.do?queryVO.orgbaseid=<bean:write name="vo" property="orgbaseid"/>',0,1,500,300);"><bean:write name="vo" property="orgname"/></a></td>

        <td><bean:write name="vo" property="orgnamesim"/></td>
             
        <td>
            <img src="../styles/imgs/org_arch.png" title="添加下级单位" style="cursor:pointer;width: 22px;" onclick="addRecord('<%=WebConstant.WebContext %>/org/adddeptinfo.do?queryVO.pid=<bean:write name="vo" property="orgbaseid"/>');return false;"/>
            <img src="../styles/imgs/edit.png" title="修改" style="cursor:pointer;" onclick="goUrl('<%=WebConstant.WebContext %>/org/editdeptinfo.do?queryVO.orgbaseid=<bean:write name="vo" property="orgbaseid"/>&backUrlEncode=','backUrlEncode');return false;"/>
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="deleteSingleRec('<%=WebConstant.WebContext %>/org/deldeptinfo.do?vo.orgbaseid=<bean:write name="vo" property="orgbaseid"/>');return false;"/>
        </td>
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

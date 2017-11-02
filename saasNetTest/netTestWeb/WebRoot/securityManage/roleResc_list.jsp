<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>角色资源列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script language="javascript">
	<!--  
       function selectCallBack(ids){
          if(ids!=null&&ids!=""){
             document.getElementById("rescIds").value=ids;
          }
          clearDiv();
          document.getElementById("list").action="saveRoleResc.do";
          document.forms[0].submit();
       }
       
       function showThisMenu(){
          var displayMenu = document.getElementById("displayMenuId").checked;
          var form = document.forms[0];
          form.action = "showThisMenu.do";
          if(displayMenu==true){
             document.getElementById("includeMenuCheckId").value = "1";
             form.submit();
          } else{
             if(confirm('取消显示菜单将删除该菜单下所有的权限资源，确定执行吗？')){
                document.getElementById("includeMenuCheckId").value = "0";
             }else{
                document.getElementById("displayMenuId").checked = "checked";
             }
          }
          
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listRoleResc.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="roleRescForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="roleRescForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input id="rescIds" type="hidden" name="rescIds" value="">
  <input type="hidden" name="rolesVO.id" value="<bean:write name="roleRescForm" property="rolesVO.id"/>"/>
  <input type="hidden" name="queryVO.pid" value="<bean:write name="roleRescForm" property="queryVO.pid"/>"/>
  <input id="includeMenuCheckId" type="hidden" name="includeMenuCheck" value="" />
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">资源: 
		<input type="text" name="queryVO.name" value="<bean:write name="roleRescForm" property="queryVO.name"/>"/>
		<input type="submit" name="Submit" value="查询" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;">高级搜索</a>
      </div>
  </div>
   
  <div class="topContentDiv">
	 &gt;&gt;&gt;角色名：<bean:write name="roleRescForm" property="rolesVO.name"/>
  </div>
   
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
              <td>Property one</td>
              <td>Property two</td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  
  
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="newDiv('/securityManage/listRescsForRoleAdd.do?queryVO.roleId=<bean:write name="roleRescForm" property="rolesVO.id"/>&queryVO.pid=<bean:write name="roleRescForm" property="queryVO.pid"/>',1,1,600,500);return false;">新增权限</button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/securityManage/deleteRoleResc.do','请选择一条记录!','确定要删除吗?');return false;">删除</button></li>
		     <li><button type="button" onclick="window.parent.topGoback();return false;">返回角色列表</button></li>
		     <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		         <label for="displayMenuId"><html:checkbox styleId="displayMenuId" name="roleRescForm" property="includeMenuCheck" value="1" onclick="showThisMenu()">显示该菜单</html:checkbox></label>
		     </li>
		  </ul>
	  </div>
	  <!-- page list -->
      
      <!-- page list -->
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>资源名</td>

        <td>资源类型</td>

	    <td>链接</td>
        
        <td>状态</td>

        <td>资源描述</td>
      </tr>
      <logic:present name="roleRescForm" property="rescList">
	  <logic:iterate id="vo" name="roleRescForm" property="rescList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td>
        <a href="javascript:newDiv('/securityManage/viewResourcePage.do?vo.id=<bean:write name="vo" property="id"/>',0,1,600,400);">
           <bean:write name="vo" property="name"/>
        </a>
        </td>

        <td><bean:write name="vo" property="resType"/></td>
        
        <td><bean:write name="vo" property="resString"/></td>
        
        <td><bean:write name="vo" property="status"/></td>

        <td><bean:write name="vo" property="descn"/></td>

      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
		    changeComplexSearch('complexSearchDiv','<bean:write name="roleRescForm" property="complexSearchDivStatus"/>');
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>

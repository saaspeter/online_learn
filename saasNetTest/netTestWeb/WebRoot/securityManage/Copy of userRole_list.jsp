<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant, netTestWeb.base.WebConstant, netTest.orguser.constant.OrguserConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="useridVar" name="userRoleForm" property="vo.userId"></bean:define>
    <bean:define id="shopidVar" name="userRoleForm" property="vo.shopid"></bean:define>
    <bean:size id="rolesize" name="userRoleForm" property="roleList"/>
    <title>用户角色列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/orguserLogic.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script language="javascript">
	<!--  
       function selectCallBack(ids){
          if(ids!=null&&ids!=""){
             document.getElementById("roleIds").value=ids;
          }
          clearDiv();
          document.getElementById("list").action="saveUserRole.do";
          document.forms[0].submit();
       }
	//-->
	</script>
  </head>
  
  <body>
  <div>
  
  <html:form styleId="list" action="/securityManage/listUserRole.do" method="post">
  <input type="hidden" name="usersVO.userid" value="<bean:write name="userRoleForm" property="usersVO.userid"/>"/>
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userRoleForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userRoleForm" property="backUrlEncode"/>">
  <input id="roleIds" type="hidden" name="roleIds" value="">
  <input type="hidden" name="syscode" value="<%=CommonConstant.SysCode_Education %>">

  <div id="magOrgDivId" style="display:none; height:30px; margin-left: 5px;margin-right: 2px; padding-top: 5px; background-color: #eeeeee; padding-left:25;">
      可以管理的单位: &nbsp;&nbsp;
      <span id="spantxtId"></span>
      <select id="magOrgSelectId"></select>&nbsp;&nbsp;
      <img id="imgMagorgEditId" src='../styles/imgs/edit.png' alt='编辑' style='cursor:pointer;' onclick="switchMagOrgDisplay('edit');return false;"/>&nbsp;&nbsp;
      <img id="imgMagorgSaveId" src="../styles/imgs/save.png" alt="保存" style="cursor:pointer;" onclick="saveMagOrg();return false;"/>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <li><button type="button" onclick="newDiv('/securityManage/listRolesForUserAdd.do?queryVO.userId=<bean:write name="userRoleForm" property="usersVO.userid"/>&syscode=<%=CommonConstant.SysCode_Education %>',1,1,600,500);return false;">新增角色</button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/securityManage/deleteUserRole.do','请选择一条记录!','确定要删除吗?');return false;">删除角色</button></li>
		  </ul>
	  </div>

  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>角色</td>

        <td>描述</td>
      </tr>
      <logic:present name="userRoleForm" property="roleList">
	  <logic:iterate id="vo" name="userRoleForm" property="roleList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')"></td>       
        <td>
           <bean:write name="vo" property="name"/>
        </td>
        <td><bean:write name="vo" property="descn"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>

  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
	       setListTableStyle();
	       initLoadMagOrg();
	   }
	   
	   function switchMagOrgDisplay(type){
	       var spantxt = document.getElementById("spantxtId");
	       var selectObj = document.getElementById("magOrgSelectId");
	       var imgMagorgEdit = document.getElementById("imgMagorgEditId");
	       var imgMagorgSave = document.getElementById("imgMagorgSaveId");
             
	       if(type=='save'){
	          spantxt.style.display = "";
              selectObj.style.display = "none";
              imgMagorgEdit.style.display = "";
              imgMagorgSave.style.display = "none";
	       }else if(type=='edit'){
	          spantxt.style.display = "none";
              selectObj.style.display = "";
              imgMagorgEdit.style.display = "none";
              imgMagorgSave.style.display = "";
	       }
	   }
	   
	   function initLoadMagOrg(){
	      <%if(rolesize>0){ %>
	           orguserLogic.selectOrgForUser(<%=useridVar %>, <%=shopidVar %>,
	               function CB_rtn(list){
	                  var magOrgname = '';
	                  var selectObj = document.getElementById("magOrgSelectId");
		              for(var i=0;i<list.length;i++){
		                  if(list[i].ismag==1){
		                     magOrgname = list[i].orgname;
		                     var varitem = new Option(list[i].orgname, list[i].orgbaseid); 
		                     varitem.selected = "selected";
                             selectObj.options.add(varitem); 
		                  }else {
		                     selectObj.options.add(new Option(list[i].orgname, list[i].orgbaseid));
		                  }
		              }
		              
		              var blankitem = new Option('',''); 
		              if(magOrgname==''){
		                 blankitem.selected = "selected";
		              }
                      selectObj.options.add(blankitem); 
		               
		              if(magOrgname!=''){
		     	         document.getElementById("spantxtId").innerHTML = magOrgname;
		     	         switchMagOrgDisplay('save');
		     	      }else{
		     	         switchMagOrgDisplay('edit');
		     	      }
		     	      document.getElementById("magOrgDivId").style.display="";
		           }
     	       );
     	       
	       <%} %>
	   }
	   
	   function saveMagOrg(){
	      var selectObj = document.getElementById("magOrgSelectId");
	      var orgidVar = selectObj.options[selectObj.selectedIndex].value;
	      var orgnameVar = selectObj.options[selectObj.selectedIndex].text;
	      if(orgidVar==''){
	         alert('请选择单位!');
	         return;
	      }
	      var url = "/<%=CommonConstant.WebContext_Education%>/orguser/orguser.do?method=setUserOrgMag&vo.userid="+<%=useridVar %>
	                +"&vo.orgbaseid="+orgidVar;
	      var rtnObj = toAjaxUrl(url, false);
          if(rtnObj.result=="1"){ // forward the success page,if success
             showMessagePage(getMessage("operation_success"));
             var spantxt = document.getElementById("spantxtId");
             spantxt.innerHTML = orgnameVar;
             switchMagOrgDisplay("save");
          }else {
             alert(getMessage("systemError"));
          }
	   }
	   
	   
     //-->
  </script>
  </body>
</html:html>

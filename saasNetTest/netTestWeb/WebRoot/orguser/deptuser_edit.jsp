<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="editType" name="orguserForm" property="editType" type="java.lang.Integer"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="orguserForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="shopcode" name="orguserForm" property="shopcode" type="java.lang.String"></bean:define>
    <bean:define id="userid" name="orguserForm" property="vo.userid" type="java.lang.Long"></bean:define>
    <bean:define id="shopid" name="orguserForm" property="vo.shopid" type="java.lang.Long"></bean:define>

    <title>编辑单位人员信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

	<script type="text/javascript">

	   function saveNickname(){
	   // js check输入内容
	       var formObj = document.getElementById("formId1");
	       if(g_check.checkForm(formObj)){
		       var nickname = document.getElementById("nicknameId").value;
		       var url = "<%=WebConstant.WebContext %>/usershop/updatenickname.do?";
		       var param = "vo.userid=<%=userid %>&vo.shopid=<%=shopid %>&vo.nickname="+nickname;
		       var rtnObj = toAjaxUrl(url, false, param);
	           if(rtnObj.result=="1"){ 
	              showMessagePage(rtnObj.info); 
	              document.getElementById('nicknameId').className='readonly';
	           }else if(rtnObj.result=="-2"){
	              showMessagePage(rtnObj.info);
	           }else {
	              alert(getMessage("systemError"));
	           }
	       }
	   }
	   
	   function delThisUser(){
	       if(!confirm(getMessage("confirmDeleteMsg"))){
	          return;
	       }
	       var url = doContextUrl('/orguser/delUserDept.do?userid=<%=userid %>');
	       var backUrlEncode = document.getElementById("backUrlEncode").value;
	       url = url+"&backUrlEncode="+backUrlEncode;
	       goUrl(url);
	   }
	   
	</script>
  </head>
  
  <body>
	<div class="editPage">
	
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="orguserForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="orguserForm" property="backUrlEncode"/>">

	  <div id="functionLineDiv">
	      <table style="width: 100%">
	         <tr>
	            <td width="70px;"><button type="button" onclick="getAndToUrl('backUrl');return false;">&lt;&lt;返回</button></td>
	            <td style="text-align: center;"><bean:write name="orguserForm" property="vo.loginname"/></td>
	         </tr>
	      </table>
	  </div> 
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

      <div style="height:auto; width:100%;">
	      <ul class="tabnav">
	          <li id="tab1" class='selectTab'><a href="javascript:void(0);" onclick="showTab(1,3)">基本信息</a></li>
		      <li id="tab2"><a href="javascript:void(0);" onclick="showTab(2,3)">课程信息</a></li>
		      <li id="tab3"><a href="javascript:void(0);" onclick="showTab(3,3)">权限管理</a></li>
	      </ul>
	  </div>
	   
	  <div id="content1" class="fieldDisDiv">
	     <form id="formId1">
	     <ul>
		    <li>
			   <div class="fieldLabel">用户登录名:</div>
			   <div class="field"> 
			      <bean:write name="orguserForm" property="vo.loginname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">用户名称:</div>
			   <div class="field"> 
			      <bean:write name="orguserForm" property="vo.displayname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">备注名称:</div>
			   <div class="field"> 
			     <input id="nicknameId" type="text" class="readonly" name="vo.nickname" value="<bean:write name="orguserForm" property="vo.nickname"/>" usage="notempty" exp="^\S{1,24}$" tip="备注名称长度不能超过24个字" onclick="this.className=''"/>
			     <img src="../styles/imgs/save.png" alt="保存" style="cursor:pointer;" onclick="saveNickname();return false;"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="该信息需要用户自己修改更新"/>电子邮件:</div>
			   <div class="field"> 
			     <bean:write name="orguserForm" property="vo.email"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="该信息需要用户自己修改更新"/>电话:</div>
			   <div class="field"> 
			     <bean:write name="orguserForm" property="contactinfo.telephone"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="该信息需要用户自己修改更新"/>性别:</div>
			   <div class="field"> 
			     <bean:writeSaas name="orguserForm" property="vo.gender" consCode="Sysconst.Gender"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="该信息需要用户自己修改更新"/>注册地:</div>
			   <div class="field"> 
			     <bean:writeSaas name="orguserForm" property="vo.localeid" showLocaleName="true"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="该信息需要用户自己修改更新"/>所在时区:</div>
			   <div class="field"> 
			       <span id="spanTimezoneId"></span>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		 </ul>
		 </form>
	  </div>
	  
	   <div id="content2" style="width:100%;height:420px;display:none">
	       <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>/product/listshopprodforuser.do?userid=<bean:write name="orguserForm" property="vo.userid"/>" scrolling="auto" frameborder="0"></iframe>
	   </div>
	   
	   <div id="content3" style="width:100%;height:420px;display:none">
	      <iframe width="100%" height="100%"  src="<%=WebConstant.WebContext %>/shopuserManage/listShopUserRole.do?queryVO.userid=<bean:write name="orguserForm" property="vo.userid"/>&syscode=<%=CommonConstant.SysCode_Education %>" scrolling="auto" frameborder="0"></iframe>
       </div>
	  
	  <div id="buttomDiv"></div>

	</div>
	<script type="text/javascript">
        var timezone = '<bean:write name="orguserForm" property="vo.timezone"/>';
        var timezoneValue = '';
        for(var i=0;i<timezoneArr.length;i++){
            if(timezoneArr[i][0]==timezone){
               timezoneValue = timezoneArr[i][1];
               document.getElementById("spanTimezoneId").innerHTML = timezoneValue;
               break;
            }
        }
    </script>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="orguserForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="shopcode" name="orguserForm" property="shopcode" type="java.lang.String"></bean:define>
    <bean:define id="userid" name="orguserForm" property="vo.userid" type="java.lang.Long"></bean:define>
    <bean:define id="shopid" name="orguserForm" property="vo.shopid" type="java.lang.Long"></bean:define>
    <%boolean canedituser = false; %>
    <authz:privilege res="/usershop/updatenickname.do">
        <%canedituser = true; %>
    </authz:privilege>
    
    <title>单位人员信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <%if(canedituser){ %>
	<script type="text/javascript">
	   
	   function saveNickname(){
	       var formObj = document.getElementById("formId1");
	       if(g_check.checkForm(formObj)){
		       var nickname = document.getElementById("nicknameId").value;
		       var nicknameClass = document.getElementById('nicknameId').className;
		       if(nicknameClass!=null&&nicknameClass=='readonly'){
		          return;
		       }
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
	   
	   function switchdeptinput(){
	       document.getElementById("span_dept_id").style.display="block";
	       document.getElementById("functionlinkId").style.display="none";
	   }
	   
	   function selectOrg_CB(ids,names,param){
	      if(ids!=null&&ids!=""){
	         document.getElementById("deptIdStrId").value=ids;
	         document.getElementById("deptNameStrId").value=names;
	         clearDiv();
	      }
	   }
	   
	   function savedept(){
	      var orgid = document.getElementById("deptIdStrId").value;
	      var backurl = encodeURIComponent("<%=WebConstant.WebContext %>/orguser/viewdeptuser.do?queryVO.userid=")+'<%=userid %>';
	      goUrl('/orguser/putuserintodept.do?vo.userid=<%=userid %>&vo.orgbaseid='+orgid
	            +'&backUrlEncode='+backurl);
	   }
	   
	</script>
	<%} %>
  </head>
  
  <body>
	<div class="editPage">
		  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
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
			        <%if(canedituser){ %>
			        <input id="nicknameId" type="text" name="vo.nickname" value="<bean:write name="orguserForm" property="vo.nickname"/>" style="width:180px;" class="readonly" usage="notempty" exp="^\S{1,24}$" tip="备注名称长度不能超过24个字" onclick="this.className='';"/>
			        <img src="../styles/imgs/save.png" alt="保存" style="cursor:pointer;width:20px" onclick="saveNickname();return false;" title="保存"/>
			        <%}else { %>
			            <bean:write name="orguserForm" property="vo.nickname"/>
			        <%} %>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">电子邮件:</div>
			   <div class="field"> 
			     <bean:write name="orguserForm" property="vo.email"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">注册地:</div>
			   <div class="field"> 
			     <bean:writeSaas name="orguserForm" property="vo.localeid" showLocaleName="true"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在时区:</div>
			   <div class="field"> 
			       <span id="spanTimezoneId"></span>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在单位:</div>
			   <div class="field"> 
			       <logic:present name="orguserForm" property="vo.orgbaseid">
			       <bean:writeSaas name="orguserForm" property="vo.orgname"/>
			       </logic:present>
			       <logic:notPresent name="orguserForm" property="vo.orgbaseid">
			          <span id="span_dept_id" style="display: none;">
	                     <input id="deptIdStrId" type='hidden' value='<bean:write name="orguserForm" property="sessionorgid"/>'/>
	                     <input id='deptNameStrId' type='text' style='width: 200px;' readonly='readonly' value='<bean:write name="orguserForm" property="sessionorgname"/>'/>
	                     <button type="button" onclick="newDiv('/org/selectorgtree.do?selectType=1',1,1,300,300);return false;">选择</button>
	                     <img src="../styles/imgs/save.png" alt="保存" style="cursor:pointer;width:20px" onclick="savedept();return false;" title="保存"/>
	                  </span>
			          <span id="functionlinkId">N/A,<a href="javascript:void(0)" onclick="switchdeptinput();return false;">将其加入商店单位</a><img src="../styles/imgs/help.gif" title="如果人员成为商店单位人员后，可以查看并学习商店内部课程，和别的一些管理功能"/></span>
			       </logic:notPresent>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		 </ul>
		 </form>
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

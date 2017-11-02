<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="orguserForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="shopcode" name="orguserForm" property="shopcode" type="java.lang.String"></bean:define>
	<%String syscode = CommonConstant.SysCode_Education; %>
    <title>新增单位人员信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<style type="text/css">
	
	   .input_on{
		   border:2px solid #FF99CC;
		   width: 250px;
		   font-size: larger;
		}
		
		.user_input{
            width: 250px;
			font-size: larger;
		}
	
	   .login_fieldDesc{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#999;
		}
		
	</style>
	
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/usershop.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	<script type="text/javascript">
	
	   // 供点击数时调用的方法，如果没够该方法，则调用默认地址
	   function showRight(id){
	      var url = "<%=WebConstant.WebContext %>/orguser/listorguser.do?queryVO.orgbaseid="+id;
	      document.location.href=url;
	   }
	   
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	      checkElement(obj,1);
	   }
	   
	   // 注册时检查用户是否存在,包括商店内该系统中的用户名是否重复和用户的全局用户名是否重复
	  function checkShopUser(nameId,nameshopId,shopcode,syscode,exeFunc)
	  {
	     var nameObj = document.getElementById(nameId);
	  	 var nameshopObj = document.getElementById(nameshopId);
	  	 if(nameshopObj.value==null||nameshopObj.value==""){
	  	 	showMessagePage(getMessage("loginnameTip"));
	  	 	return;
	  	 }
	  	 if(shopcode==null||shopcode==""||syscode==null||syscode==""){
	  	 	nameshopObj.value = "";
	  	 	nameObj.value = "";
	  	 	alert(getMessage("systemError"));
	  	 	return;
	  	 }
	     var depuMess = nameshopObj.value+","+getMessage("loginnameDepulicate");
	     if(nameshopObj!=null){
	        usershop.isExists(nameshopObj.value,shopcode,syscode,function CB_checkShopUser(rtn){
	           if(typeof(exeFunc)!="undefined"&&(exeFunc!=null)){
	           	  exeFunc(rtn);           	
	           }
	           if(rtn!=null&&rtn.result=="1"){
	           	  nameshopObj.value = "";
	  	 	      nameObj.value = "";
			      showMessagePage(depuMess);
			      return false;
			   }else if(rtn!=null&&rtn.result=="0"){
			   	  nameObj.value = rtn.loginname;
			      showMessagePage(getMessage("loginnameCanuse"));
			      return true;
	           }else{
	           	  nameshopObj.value = "";
	  	 	      nameObj.value = "";
	           	  alert(getMessage("systemError"));
	           }
	        }
	        );
	     }
	  }
	   
	   var checkuserback = function(retVal){
	      if(retVal!=null){
	         if(retVal.result=="1"){
	            submitForm('editForm');
	         }
	      }
	   };
	   
	   function checkEmail(emailId){
          var emailObj = document.getElementById(emailId);
	  	  var retArr = null;
	      if(emailObj!=null&&emailObj.value!=''){
	         dwr.engine.setAsync(false);
	         customer.isExistsEmail(emailObj.value,function CB_callback(rtn){
		        retArr = CB_checkEmail(emailObj.value, rtn);
		     });
	      }
	      return retArr;
	   }
	   
	   function CB_checkEmail(inputvalue, rtn){
	       var result = null;
	       var message = "";
	       field_mess = "userEmailDepulicate";
	       canuse_mess = "userEmailCanuse";
	       if(rtn==null||rtn==true){
           	  message = inputvalue+","+getMessage(field_mess);
		      result = false;
		   }else if(rtn!=null&&rtn==false){
		      message = getMessage(canuse_mess);
		      result = true;
           }else{
           	  alert(getMessage("systemError"));
           }
           var retArr = new Array(result, message);
           return retArr;
	   }
	   
	   function submitThis(){
	      var loginname = document.getElementById("nameId").value;
	      if(loginname==null||loginname==""){
	         checkShopUser('nameId','nameshopId','<%=shopcode %>','<%=syscode %>',checkuserback);
	      }else{
	         submitForm('editForm');
	      }
	   }
	   
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/orguser/savedeptuser.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="orguserForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="orguserForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.orguserid" value="<bean:write name="orguserForm" property="vo.orguserid"/>">
	 <input type="hidden" name="vo.userid" value="<bean:write name="orguserForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.orgbaseid" value="<bean:write name="orguserForm" property="vo.orgbaseid"/>"/>
	 <input type="hidden" name="vo.usershopid" value="<bean:write name="orguserForm" property="vo.usershopid"/>"/>

	  <div id="fieldsTitleDiv">单位人员信息</div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
	     
	        <li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="用户在本商店中的名称，不能重复"/><font color="#ff0000">*</font>用户在本店中名称:</div>
			   <div class="field"> 
                  <input id="nameshopId" class="user_input" type="text" name="vo.nickname" value="<bean:write name="orguserForm" property="vo.nickname"/>" usage="notempty" exp="^\S{1,24}$" fct="checkShopUser('nameId','nameshopId','<%=shopcode %>','<%=syscode %>');" tip="用户名称长度不能超过24个字" fctMsg="该名称已被注册" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);" />
			      <span id="nameshopId_tip"></span>
			   </div>
			   <div id="nameshopId_tip_td" class="login_fieldDesc"><i>&nbsp;</i></div>
			</li>
			
            <li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="用户的真正登录名，用户第一次登录后更改，这里默认生成"/>自动生成的全局帐号:</div>
			   <div class="field"> 
                  <input id="nameId" type="text" name="vo.loginname" class="readonly" disabled="disabled" value="<bean:write name="orguserForm" property="vo.loginname"/>" />
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在单位:</div>
			   <div class="field"> 
                  <bean:write name="orguserForm" property="vo.orgname"/>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>电子邮件:</div>
			   <div class="field"> 
			      <input id="emailId" type="text" class="user_input" name="vo.email" value="" usage="notempty,email" fct="checkEmail('emailId')" fctMsg="电子邮件已被注册" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			      <span id="emailId_tip"></span>
			   </div>
			   <div id="emailId_tip_td" class="login_fieldDesc"><i>&nbsp;</i></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="#ff0000"></font>电话:</div>
			   <div class="field"> 
			      <input id="telephoneId" type="text" class="user_input" name="contactinfo.telephone" value="" usage="telphone" tip="格式 +86 0102222222" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			      <span id="telephoneId_tip"></span>
			   </div>
			   <div id="telephoneId_tip_td" class="login_fieldDesc"><i>&nbsp;</i></div>
			</li>
			
			<li>
			   <div class="fieldLabel">国家语言:</div>
			   <div class="field"> 
			      <html:select name="orguserForm" property="vo.localeid" styleClass="font_input">
					 <html:optionsSaas localeListSet="true"/>
			      </html:select>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在时区:</div>
			   <div class="field"> 
			      <select id="timezoneId" name="vo.timezone" class="font_input"></select>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">性别:</div>
			   <div class="field"> 
				  <html:select name="orguserForm" property="vo.gender" styleClass="font_input">
				     <html:option value=""></html:option>
					 <html:optionsSaas consCode="Sysconst.Gender"/>
			      </html:select>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitThis();return  false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
  <script type="text/javascript">
     var defaultTimezone = '';
     loadTimezoneList('timezoneId', defaultTimezone);
  </script>
</html:html>

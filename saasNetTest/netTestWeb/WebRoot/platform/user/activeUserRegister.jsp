<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="true">
  <head>
    <html:base />
    <%String loginname = (String)request.getAttribute("loginname"); 
      String useridStr = (String)request.getAttribute("userid"); 
      useridStr = (useridStr==null)?"":useridStr;
      String jsSuffix = (String)request.getAttribute("jsSuffix"); 
      String logintypeStr = (String)request.getAttribute("logintype"); 
      Short logintype = null;
      if(logintypeStr!=null&&logintypeStr.trim().length()>0){
    	  logintype = new Short(logintypeStr);
      }
      String email = (String)request.getAttribute("email"); 
      email = (email==null)?"":email;
      String validatecode = request.getParameter("validatecode"); 
      
      boolean isSocialLogin = CommonConstant.isSocialLogin(logintype);
      boolean allowChangeEmail = false;
      if(isSocialLogin && CommonConstant.LoginType_SocialLogin_QQ.equals(logintype)){
    	  allowChangeEmail = true;
      }
    %>
    <title>激活用户帐号</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/edit.css">
	<style>
	    body{ margin:0px }	
	    .login_fieldDesc{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#999;
		}
		
		.input_on{
		   border:2px solid #FF99CC;
		   width: 250px;
		   font-size: larger;
		}
		
		.user_input{
            width: 300px;
            font-size: 22px;
            border:#aaaaaa 1px solid;
		}
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript'>
       
	   function saveloginame(){
	       var formObj = document.getElementById("formId");
	       if(g_check.checkForm(formObj)){
			  //var checkRtnArr = checkUserLoginame("loginameId");
	          //if(checkRtnArr[0]){
	        	 var emailVar = null;
	        	 <% if(allowChangeEmail){ %>
	        	 emailVar = document.getElementById("emailId").value;
	        	 <% } %>
	             var loginame = document.getElementById("loginameId").value;
	             var passwd = document.getElementById("passId").value;
		         var url = "<%=WebConstant.WebContext %>/customers/activeuserregister.do?";
		         var param = "vo.loginname="+loginame+"&newpassword="+passwd;
		         <% if(!isSocialLogin) {%>
		             param = param + "&vo.email=<%=email %>&validatecode=<%=validatecode %>";
		         <% }else { %>
		             param = param + "&vo.userid=<%=useridStr %>";
		             if(emailVar!=null){
		            	 param = param + "&vo.email=" + emailVar;
		             }
		         <% } %>
		         var rtnObj = toAjaxUrl(url, false, param);
	             if(rtnObj.result=="1"){ 
	            	<%if(isSocialLogin){ %>
	            	    goUrl('<%=WebConstant.WebContext %>/customers/viewuser.do');
	            	<%} else {%>
	            	    alert('更新成功，请重新登录!'); 
	                    goUrl('<%=WebConstant.WebContext %>/j_spring_security_logout');
	                <%} %>
	             }else {
	                alert(getMessage("systemError"));
	             }
	          //}
	       }
	   }
	   
	   function checkUser(nameId, checktype){
          var nameObj = document.getElementById(nameId);
	  	  var retArr = null;
	      if(nameObj!=null&&nameObj.value!=''){
	         dwr.engine.setAsync(false);
	         if(checktype==2){
	            customer.isExistsEmail(nameObj.value,function CB_callback(rtn){
		           retArr = CB_checkUser(nameObj.value, rtn , checktype);
		        });
	         }else {
	            customer.isExistsLoginname(nameObj.value,function CB_callback(rtn){
		           retArr = CB_checkUser(nameObj.value, rtn , checktype);
		        });
	         }
	      }
	      return retArr;
	   }
		   
	   function CB_checkUser(inputvalue, rtn, checktype){
	       var result = null;
	       var message = "";
	       var field_mess = "loginnameDepulicate";
	       var canuse_mess = "loginnameCanuse";
	       if(checktype==2){
	          field_mess = "userEmailDepulicate";
	          canuse_mess = "userEmailCanuse";
	       }
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
	   
	   function checkUserLoginame(loginameId){
          var nameObj = document.getElementById(loginameId);
	  	  var retArr = null;
	      if(nameObj!=null&&nameObj.value!=''){
	         dwr.engine.setAsync(false);
             customer.isExistsLoginname(nameObj.value, function CB_callback(rtn){
		        retArr = CB_checkUserLoginame(nameObj.value, rtn);
		     });
	      }
	      return retArr;
	   }
	   
	   function CB_checkUserLoginame(inputvalue, rtn){
	       var result = null;
	       var message = "";
	       var field_mess = "loginnameDepulicate";
	       var canuse_mess = "loginnameCanuse";
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
	   
	   function checkPass(id1, id2){
	      var pass1 = document.getElementById(id1).value;
	      var pass2 = document.getElementById(id2).value;
	      var result = true;
	      var message = '';
	      if(pass2!=pass1){
	         result = false;
	         message = "两次输入的密码不相同";
	      }
	      var retArr = new Array(result, message);
	      return retArr;
	   }
	   
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	      checkElement(obj,1);
	   }

	</script>
    
  </head>
  
  <body>
	<div class="centerstyle">
	  <jsp:include flush="true" page="../../index/banner_short.jsp"></jsp:include>
	  <div id="fieldsTitleDiv">设置用户登录名</div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;text-align: center;">
          您还未设置登录用户名和密码，设置完成后就可以自由学习了
	  </div>

      <form id="formId" action="<%=WebConstant.WebContext %>/customers/activeuserregister.do">
	  <div id="fieldDisDiv">
	     <ul>
	     
	        <li>
			   <div class="fieldLabel">自动生成的用户登录名:</div>
			   <div class="field"> 
                  <%=loginname %>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
			
            <li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>输入登录名:</div>
			   <div class="field"> 
                  <input id="loginameId" type="text" name="vo.loginname" class="user_input" value="" autocomplete="off" usage="notempty" exp="^[^_\s]\S{3,29}$" fct="checkUser('loginameId',1)" tip="登录名不能为空，长度在4-30，不能以下划线开头" fctMsg="该登录名已被注册" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
                  <br/>
                  <span class="login_fieldDesc">登录名必须唯一，且长度在4-30，不能以下划线开头</span>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>注册email:</div>
			   <div class="field"> 
			   <%if(allowChangeEmail){ %>
			       <input id="emailId" type="text" name="vo.email" class="user_input" value="<%=email %>" autocomplete="off" usage="notempty,email,max-length:128" fct="checkUser('emailId',2)" fctMsg="电子邮件已被注册" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			       <br/>
			       <span id="emailId_tip" class="login_fieldDesc">常用邮箱,用于通知您最新消息</span>
			   <%} else { out.print(email); } %>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>输入密码:</div>
			   <div class="field"> 
                  <input id="passId" type="password" name="newpassword" class="user_input" value="" autocomplete="off" usage="notempty" exp="^\S{4,20}$" tip="密码必须在4 - 20位之间" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
                  <br/>
                  <span class="login_fieldDesc">密码必须在4 - 20位之间</span>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>请再次输入新密码:</div>
			   <div class="field"> 
                  <input id="pass2Id" type="password" name="pass2" value="" autocomplete="off" class="user_input" fct="checkPass('pass2Id','passId')" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
                  <br/>
                  <span class="login_fieldDesc"></span>
			   </div>
			   <div class="login_fieldDesc"></div>
			</li>
		 </ul>
	  </div>
	  </form>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button class="bigButton" style="padding-left: 30px;padding-right: 30px; " type="button" onclick="saveloginame();return false;"><bean:message key="netTest.commonpage.save"/></button>
		    </li>
		    
		 </ul>
	  </div>
	</div>
  </body>
</html>

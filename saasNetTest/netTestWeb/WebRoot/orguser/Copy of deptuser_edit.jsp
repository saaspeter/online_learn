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
	 <%String syscode = CommonConstant.SysCode_Education; %>
    <title>编辑单位人员信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	<script type="text/javascript">
	   // 供点击树时调用的方法，如果没够该方法，则调用默认地址
	   function showRight(id){
	      var url = "<%=WebConstant.WebContext %>/orguser/orguser.do?method=list&queryVO.orgbaseid="+id;
	      document.location.href=url;
	   }
	   
	   var checkuserback = function(retVal){
	      if(retVal!=null){
	         if(retVal=="1"){
	            submitForm('editForm');
	         }
	      }
	   };
	   
	   function submitThis(){
	      var loginname = document.getElementById("nameId").value;
	      if(loginname==null||loginname==""){
	         checkUser('nameId','nameshopId','<%=shopcode %>','<%=syscode %>',checkuserback);
	      }else{
	         submitForm('editForm');
	      }
	   }
	   
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/orguser/orguser.do?method=savedeptuser" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="orguserForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="orguserForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.orguserid" value="<bean:write name="orguserForm" property="vo.orguserid"/>">
	 <input type="hidden" name="vo.userid" value="<bean:write name="orguserForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.orgbaseid" value="<bean:write name="orguserForm" property="vo.orgbaseid"/>"/>
	 <input type="hidden" name="vo.usershopid" value="<bean:write name="orguserForm" property="vo.usershopid"/>"/>

	  <div id="functionLineDiv">
		  <div id="fieldsTitleDiv">单位人员信息</div>
	  </div> 
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	      <table width="100%" align="center"  bgcolor="#efefef" border="1px" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
		    <tr>
		       <td width="150px" align="right">用户登录名:</td>
		       <td>
		          <input id="nameId" type="text" name="vo.loginname" readonly="readonly" value="<bean:write name="orguserForm" property="vo.loginname"/>" disabled="disabled" style="width:300px" />
		       </td>
		       <td width="150px" align="right">用户在本系统的代号:</td>
		       <td>
                  <input id="nameshopId" type="text" name="vo.nickname" value="<bean:write name="orguserForm" property="vo.nickname"/>" disabled="disabled" style="width:300px"/>
		       </td>
		    </tr>
		    
		    <tr>
		       <td width="150px" align="right">**姓名:</td>
		       <td>
		          <input type="text" name="vo.name" value="<bean:write name="orguserForm" property="vo.name"/>" style="width:300px" usage="notempty,max-length:64" fie="姓名"/>
		       </td>
		       <td width="150px" align="right">所在国家:</td>
		       <td>
                  <html:select name="orguserForm" property="vo.localeid" disabled="true" style="width:300px">
					 <html:optionsSaas localeListSet="true"/>
			      </html:select>
		       </td>
		    </tr>
		    
		    <tr>
		       <td width="150px" align="right">性别:</td>
		       <td>
				  <html:select name="orguserForm" property="vo.gender" style="width:300px">
				     <html:option value=""></html:option>
					 <html:optionsSaas consCode="Sysconst.Gender"/>
			      </html:select>
		       </td>
		       <td width="150px" align="right">年龄:</td>
		       <td>
		          <input type="text" name="vo.age" value="<bean:write name="orguserForm" property="vo.age"/>" style="width:300px"/>
		       </td>
		    </tr>
		 </table>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitThis();"><bean:message key="netTest.commonpage.save"/></button></li>
			
			<li><button type="button" onclick="getAndToUrl('backUrl');"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  
	  <div>
	    <TABLE WIDTH="100%" bgcolor="#eeeeee" align="center" cellSpacing="0">
	        <TBODY>
		        <TR><TD height="3"></TD></TR>
		        <TR>
		           <TD width=1></TD>
		           <TD id="tab1" bgcolor="#0099ff" width="130px"  align="center" onclick="tab(1)">操作产品管理</TD>
		           <TD width=1></TD>
		           <TD id="tab2" bgcolor="#00ccff" width="130px"  align="center" onclick="tab(2)">权限角色管理</TD>
		           <TD></TD>
		        </TR>
	        </TBODY>
        </TABLE>
	  </div>
	  
	  <div id="content1" style="width:100%;height:420px;">
	       <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>/orguser/listdeptuserprod.do?queryVO.userid=<bean:write name="orguserForm" property="vo.userid"/>&queryVO.shopid=<bean:write name="orguserForm" property="vo.shopid"/>" scrolling="auto" frameborder="0"></iframe>
	   </div>
	   
	   <div id="content2" style="width:100%;height:230px;display:none">
	      <iframe width="100%" height="100%"  src=""></iframe>
       </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

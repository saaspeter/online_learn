<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<% String orgbaseid = request.getParameter("vo.orgbaseid"); %>
<% String orgname = request.getParameter("vo.orgname"); %>

<html>
   <head>
      <bean:define id="jsSuffix" name="orguserForm" property="jsSuffix" type="java.lang.String"/>
	  <title>导入单位用户</title>
	  
	  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	  <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	  <style type="text/css">
	
	   .font_input{
			font-size: 20px;
	    }
		
	</style>
	  
	  <script language="JavaScript" src="../styles/script/pageAction.js"></script>
	  <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
      <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
	  <script language="JavaScript" type="text/JavaScript">
	      
	       
	  </script>
   </head>

<body>
	<html:form styleId="editForm" action="/orguser/importorguser.do" method="post" enctype="multipart/form-data">
	    <input type="hidden" id="orgbaseidID" name="vo.orgbaseid" value="<%=orgbaseid %>" />    
	    
	    <div class="fieldsTitleDiv">
			单位导入Excel格式用户
	    </div>

		<div class="editPage">
			<table width="95%" class="formTable2">
               <tr>
                  <td align="center" width="25%"><font color="red">*</font>所在单位:</td>
                  <td>
		 			  <%=orgname %>
                  </td>
               </tr>
               <tr>
                  <td align="center" width="25%">国家语言:</td>
                  <td>
		 			  <html:select name="orguserForm" property="vo.localeid" styleClass="font_input">
						 <html:optionsSaas localeListSet="true"/>
				      </html:select>
                  </td>
               </tr>
               <tr>
                  <td align="center" width="25%">所在时区:</td>
                  <td>
		 			  <select id="timezoneId" name="vo.timezone" class="font_input" style="width: 80%"></select>
                  </td>
               </tr>
               <tr>
                  <td align="center"><font color="red">*</font>导入文件:</td>
                  <td>
                      <input id="fileId" type="file" name="userFile" size="40" usage="notempty,excel" fie="导入文件"/>
                  </td>
               </tr>
               <tr>
                  <td align="center" width="25%">Excel文件格式说明:</td>
                  <td>
                      1. 第一行文件格式列说明, 包括其中的列说明<br>
                      2. 第一列为用户在shop中的名称，在商店内不能重复，系统会自动生成登录帐号<br>
                      3. 电话格式为: 国家代码 电话号码，例如: +86 0102222222<br>
                      4. 性别为 男 或 女<br>
                      5. 最大上传数据为1000条<br>
                  </td>
               </tr>
               <tr id="exapleFile_excel_Id" style="display: none;">
                  <td align="center" width="25%">样例文件下载:</td>
                  <td>
                      <a href="<%=WebConstant.WebContext %>/pubs/download.jsp?filename=orguser_excel_demo.xls">orguser_excel_demo</a>
                  </td>
               </tr>
            </table>
		</div>
		
		<div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	    </div>
		
		<div style="text-align: center;">
		    <button type="button" onclick="submitForm('editForm');return false;">导入</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    <button type="button" onclick="window.history.back();return false;">取消</button>
		</div>
    </html:form>
    
    <script language=JavaScript>
	 <!--
       window.onload=function(){
		   var orgidvar = '<%=orgbaseid %>';
		   if(orgidvar==null||orgidvar==''){
			   alert('system error, required parameter!');
			   window.history.back();
		   }

		   var defaultTimezone = '';
		   loadTimezoneList('timezoneId', defaultTimezone);
	   }
     //-->
    </script>
</body>
</html>

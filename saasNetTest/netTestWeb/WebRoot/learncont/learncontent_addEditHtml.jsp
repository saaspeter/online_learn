<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="learncontentForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="learncontentForm" property="editType" type="java.lang.Integer"/>
<bean:define id="contentidVar" name="learncontentForm" property="vo.contentid" type="java.lang.Long"/>
<bean:define id="productbaseidVar" name="learncontentForm" property="vo.productbaseid" type="java.lang.Long"></bean:define>
<bean:define id="productnameVar" name="learncontentForm" property="vo.productname" type="java.lang.String"></bean:define>
  <% if(productnameVar==null) {productnameVar="";}
     %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>新增学习资料</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	   function submitPost(){
          submitForm('editForm');
	   }
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/learncont/saveLearncontent.do" method="post">
	 <input type="hidden" name="vo.contentid" value="<%=contentidVar %>"/>
     <input id="prodidId" type="hidden" name="vo.productbaseid" value="<%=productbaseidVar %>" >
     <input type="hidden" name="recurltype" value="2" />
	
	  <div id="fieldsTitleDiv">学习资料</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div>
		 <table class="formTable2">
		     <tr>
		         <td style="text-align: right"><font color="red">*</font>学习内容名称:</td>
		         <td colspan="3" style="text-align: left">
		             <input type="text" style="width:75%" name="vo.caption" value="<bean:write name="learncontentForm" property="vo.caption"/>" usage="notempty,max-length:100" fie="学习内容名称"/>
		             <%if(editType.intValue()==WebConstant.editType_edit) { %>
		             &nbsp;(创建于:<bean:write name="learncontentForm" property="vo.createtime" format="yyyy-MM-dd"/>)
		             <%} %>
		         </td>
		     </tr>
		     
		     <tr>
		         <td style="text-align: right">课程:</td>
		         <td style="text-align: left"><%=productnameVar %></td>
		         <td style="text-align: right">课程章节:</td>
		         <td>
		             <input type="hidden" id="contentcateidId" name="vo.contentcateid" value="<bean:write name="learncontentForm" property="vo.contentcateid"/>" >
             		 <input type="text" id="contentcatenameId" name="vo.contentcatename" class="readonly" value="<bean:write name="learncontentForm" property="vo.contentcatename"/>" readonly="readonly" style="width:150px"/>
       			 	 <img src="../styles/imgs/ico/search.gif" alt="选择课程章节" onclick="selectContcate('<%=productbaseidVar %>')">
       			 	 <img src="../styles/imgs/ico/reset.gif" alt="删除章节" onclick="removeContcate()">
		         </td>
		     </tr>
		     
		     <tr>
		         <td style="text-align: right">学习内容类型:</td>
		         <td style="text-align: left">
		             <bean:writeSaas name="learncontentForm" property="vo.contenttype" consCode="netTest.Learncontent.contenttype"/>
		             <input name="vo.contenttype" type="hidden" value="<bean:write name="learncontentForm" property="vo.contenttype"/>" > 
		         </td>
		         <td style="text-align: right">资料使用类型:</td>
		         <td>
		             <html:select name="learncontentForm" property="vo.istry">
					     <html:optionsSaas consCode="netTest.LearncontentConstant.IsTry"/>
					 </html:select>
			     </td>
		     </tr>
		     <tr>
		         <td style="text-align: right">学习顺序:</td>
		         <td colspan="3" style="text-align: left">
		             <input name="vo.orderno" type="text" style="width: 5em;" value='<bean:write name="learncontentForm" property="vo.orderno"/>' >
			      <img src="../styles/imgs/help.png" style="cursor: pointer;" title="在相同章节中学习的顺序，数值小则先学习">
		         </td>
		     </tr>
		     <%if(editType.intValue()==WebConstant.editType_edit) { %>
		     <tr>
		         <td style="text-align: right">站内链接地址:</td>
		         <td colspan="3" style="text-align: left">
		             /learncont/viewLearncontent.do?vo.contentid=<bean:write name="learncontentForm" property="vo.contentid"/>
		         </td>
		     </tr>
		     <%} %>
		 </table>
	  </div>
	  
	  <div style="width:95%;padding-left:10px;padding-top:7px;padding-bottom:7px;margin-top:10px;margin-bottom:10px;text-align:left;background-color:#DCEAFC">
	         <font style="font-weight: bold;">内容输入</font>
	         <img src="../styles/imgs/help.png" title="在相同章节中学习的顺序，数值小则先学习" style="cursor: pointer;">
	  </div>
	  <div style="width:95%">     
	      <textarea cols="80" id="editor1" name="vo.content" rows="20"><bean:write name="learncontentForm" property="vo.content"/></textarea>
	      <script type="text/javascript">
	         var p_editor = CKEDITOR.replace( 'editor1',
	         {
	      		toolbar : 'MyFull', 
	      		height : '400px',
	      		filebrowserUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=File&rootobjecttype=shop&parentObjectType=product&parentObjectId=<%=productbaseidVar %>&sourceType=learncontent&sourceId=<%=contentidVar %>',  
	   	        filebrowserImageUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Image&rootobjecttype=shop&parentObjectType=product&parentObjectId=<%=productbaseidVar %>&sourceType=learncontent&sourceId=<%=contentidVar %>',  
	   	        filebrowserFlashUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Flash&rootobjecttype=shop&parentObjectType=product&parentObjectId=<%=productbaseidVar %>&sourceType=learncontent&sourceId=<%=contentidVar %>'
	    	 });
	       </script>
	  </div>
	  
	  <%if(editType.intValue()==WebConstant.editType_edit) { %>
	  <div style="width:95%;padding-left:10px;padding-top:7px;padding-bottom:7px;margin-top:10px;margin-bottom:10px;text-align:left;background-color:#DCEAFC">
	        <font style="font-weight: bold;">附件可下载文件</font>&nbsp;&nbsp;&nbsp;&nbsp;
	        <img src="../styles/imgs/add2.png" title="<bean:message key="netTest.commonpage.add"/>" style="cursor:pointer;margin: 0px;border:0px;" onclick="newDiv('/learncont/addLearncontent.do?vo.parentid=<%=contentidVar %>&vo.productbaseid=<%=productbaseidVar %>');return false;"/>
	  </div>
	  <%} %>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="bigButton" onclick="submitPost();"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" class="bigButton" onclick="goUrl('/learncont/listLearncontent.do?productbaseid=<%=productbaseidVar %>&queryVO.contentcateid='+document.getElementById('contentcateidId').value);return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>

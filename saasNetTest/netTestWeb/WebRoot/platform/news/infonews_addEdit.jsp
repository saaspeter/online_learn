<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="infonewsForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="infonewsForm" property="editType" type="java.lang.Integer"/>
<bean:define id="pklong" name="infonewsForm" property="vo.id" type="java.lang.Long"/>

  <%String strname="新增";
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      strname = "编辑";
    }
  %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title><%=strname %>咨询信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/banner.css" />
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/edit.css">
	<style type="text/css">
		<!--
		
		#leftAD {
			float:left;
			background-color:#FFFFFF;
			margin-top:5em;
			width: 120 px;
		}
		#content {
		    width:1000px;
		    left: 50%;
			position:relative;
			clear: both;
			margin-left: -500px;
		}
		
		#rightAD {
			height:800px;
			float:right;
			clear:right;
			margin-top:5em;
			background-color:#FFFFFF;
		}
		
		-->
	</style>
	
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
	   function submitPost(){
          submitForm('editForm');
	   }
	   if ( CKEDITOR.env.ie && CKEDITOR.env.version < 9 ){
		   CKEDITOR.tools.enableHtml5Elements( document );
	   }
			
	</script>
  </head>
  
  <body>
    <div id="content">
    <html:form styleId="editForm" action="/index/saveInfonews.do" method="post">

	<div style="height:5px; clear:both;"></div>
	
	 <input type="hidden" name="vo.id" value="<bean:write name="infonewsForm" property="vo.id"/>" />
	 <input type="hidden" name="vo.refid" value="<bean:write name="infonewsForm" property="newscategoryid"/>" />
	 <input type="hidden" name="newscategoryid" value="<bean:write name="infonewsForm" property="newscategoryid"/>" />
	 <input type="hidden" name="categoryname" value="<bean:write name="infonewsForm" property="categoryname"/>" />
	 <input type="hidden" name="categoryid" value="<bean:write name="infonewsForm" property="categoryid"/>" />
	 
	  <div id="fieldsTitleDiv"><%=strname %>咨询信息</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="width:90%">
		 <table cellpadding="0" cellspacing="0" class="noborderTable" width="95%">
		     <tr>
		         <td style="width:15%;text-align: right">标题:</td>
		         <td style="text-align: left" colspan="3"><input type="text" name="vo.caption" class="userInput" style="width: 100%;" value="<bean:write name="infonewsForm" property="vo.caption"/>" /></td>
		     </tr>
		     
		     <tr>
		         <td style="width:15%;text-align: right">所在目录:</td>
		         <td style="width:30%;"><bean:write name="infonewsForm" property="categoryname"/></td>
		         <td style="width:15%;text-align: right">子分类:</td>
		         <td><bean:write name="infonewsForm" property="newscategoryname"/></td>
		     </tr>
		     		     		     
			 <tr>
		         <td style="width:15%;text-align: right">内容:</td>
		         <td colspan="3">
				    <textarea cols="80" id="editor1" name="vo.content" rows="6"><bean:write name="infonewsForm" property="vo.content" /></textarea>
				    <script type="text/javascript">
				         var p_editor = CKEDITOR.replace( 'editor1',
				         {
				      		toolbar : 'MyFull',
				      		height : '400px',   
				   	        filebrowserImageUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Image&pathprefix=<%=WebConstant.FilePathPrefix_PlatformSystem %>&rootobjecttype=infonews&rootobjectid=<%=pklong %>',  
				   	        filebrowserFlashUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Flash&pathprefix=<%=WebConstant.FilePathPrefix_PlatformSystem %>&rootobjecttype=infonews&rootobjectid=<%=pklong %>'
				         });
				    </script>
		         </td>
		         <td></td>
		      </tr>
				    
		 </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitPost();"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button type="button" onclick="goUrl('<%=WebConstant.WebContext %>/index/listInfonewsMag.do?categoryid=<bean:write name="infonewsForm" property="categoryid"/>&categoryname=<bean:write name="infonewsForm" property="categoryname"/>');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  
      <div style="height:20px; clear:both;"></div>

      <jsp:include flush="true" page="../../foot.jsp"></jsp:include>

	</html:form>
	</div>
  </body>
</html:html>

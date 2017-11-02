<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="infonewsForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="infonewsForm" property="editType" type="java.lang.Integer"/>
<bean:define id="contentVar" name="infonewsForm" property="vo.content" />
  <%String disableStr=""; boolean isDisable=false; String strname="新增";
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; 
      strname = "编辑";
    }   
  %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><%=strname %>咨询信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <link rel="stylesheet" type="text/css" href="../styles/css/banner.css" />
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
	
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
	<script type="text/javascript">
	   function submitPost(){
	      var oEditor = FCKeditorAPI.GetInstance("content");
	      var content = oEditor.GetXHTML(true);
	      if(content==null||content==''){
	         alert('请填写内容');
	         return;
	      }

	      document.getElementById("IdPostContent").value = content;
          submitForm('editForm');
	   }
	</script>
  </head>
  
  <body>
    <div id="content">
    <jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
    <div class="navlistBar">
	    <bean:write name="infonewsForm" property="categoryname"/> &gt; 咨询信息
    </div>

	<html:form styleId="editForm" action="/index/saveInfonews.do" method="post">

	<div style="height:5px; clear:both;"></div>
	
	 <input type="hidden" name="vo.id" value="<bean:write name="infonewsForm" property="vo.id"/>" />
	 <input type="hidden" name="vo.refid" value="<bean:write name="infonewsForm" property="newscategoryid"/>" />
	 <input type="hidden" name="newscategoryid" value="<bean:write name="infonewsForm" property="newscategoryid"/>" />
	 <input type="hidden" name="categoryname" value="<bean:write name="infonewsForm" property="categoryname"/>" />
	 <input type="hidden" name="categoryid" value="<bean:write name="infonewsForm" property="categoryid"/>" />
	 <input id="IdPostContent" type="hidden" name="vo.content" value=""/>
	
	  <div id="fieldsTitleDiv"><%=strname %>咨询信息</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="width:90%">
		 <table cellpadding="0" cellspacing="0" class="formTable" width="95%">
		     <tr>
		         <td style="width:15%;text-align: right">标题:</td>
		         <td style="text-align: left" colspan="3"><input type="text" name="vo.caption" style="width:500px;" value="<bean:write name="infonewsForm" property="vo.caption"/>" /></td>
		     </tr>
		     
		     <tr>
		         <td style="width:15%;text-align: right">所在目录:</td>
		         <td style="width:30%;"><bean:write name="infonewsForm" property="categoryname"/></td>
		         <td style="width:15%;text-align: right">子分类:</td>
		         <td><bean:write name="infonewsForm" property="newscategoryname"/></td>
		     </tr>
		     
		     <tr>
		         <td style="width:15%;text-align: right">状态:</td>
		         <td style="text-align: left" colspan="3">
		            <html:select name="infonewsForm" property="vo.status" style="width:200px">
					   <html:optionsSaas consCode="common.const.commonstatus"/>
			        </html:select>
		         </td>
		     </tr>
		     		     
			 <tr>
		         <td style="width:15%;text-align: right">内容:</td>
		         <td colspan="3">
		             <span style="width:800px">
				        <script type="text/javascript">
				            var oFCKeditor = new FCKeditor('content');//传入参数为表单元素（由FCKeditor生成的input或textarea）的name
				            oFCKeditor.BasePath=webContext+'/fckeditor/';//指定FCKeditor根路径，也就是fckeditor.js所在的路径
				            oFCKeditor.Height='400px';
				            
				            oFCKeditor.ToolbarSet='Default';//指定工具栏
				            oFCKeditor.Value='<%=contentVar %>';
				            oFCKeditor.Create();
				        </script>
				    </span>
		         </td>
		         <td></td>
		      </tr>
				    
		 </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitPost();"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="goUrl('<%=WebConstant.WebContext %>/index/listInfonews.do?categoryid=<bean:write name="infonewsForm" property="categoryid"/>&categoryname=<bean:write name="infonewsForm" property="categoryname"/>');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  
<div style="height:20px; clear:both;"></div>

<jsp:include flush="true" page="../index/foot.jsp"></jsp:include>

	</html:form>
	</div>
  </body>
</html:html>

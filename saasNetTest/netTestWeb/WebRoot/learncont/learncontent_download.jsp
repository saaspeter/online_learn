<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.UserproductConstant,netTest.learncont.constant.LearncontentConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="learncontentForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productname" name="learncontentForm" property="sessionProductname" type="java.lang.String"/>
    <bean:define id="productidVar" name="learncontentForm" property="sessionProductid" type="java.lang.Long"/>
    <bean:define id="contentcateidVar" name="learncontentForm" property="queryVO.contentcateid" type="java.lang.Long"/>
    <bean:define id="selectCatevoVar" name="learncontentForm" property="usecatevo" type="netTest.prodcont.vo.Contentcate"/>
    <% Long pidVar = null;
       if(selectCatevoVar!=null){
          pidVar = selectCatevoVar.getPid();
       }
       
       %>
    <title>学习内容</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<style type="text/css">
		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
		.display_none{
		    diaplay: none;
		}
	</style>
	
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   
	   function gochapter(contentcateid){
	      document.getElementById("contentcateidId").value = contentcateid;
	      document.getElementById("list").submit();
	   }
	   	    	   
	</script>
  </head>

  <body>
  
  <div>
  <p>
  
  <div style="height: 30px;padding-left: 8px;padding-top:5px;background-color: #dddddd;">
      <font style="font-weight: bold;">下载附件列表</font>
  </div>
  
  <div class="dashLine"></div>

  <div id="DataTableDiv">
    <table id="learnDataTableId" class="listDataTable" >
      <tbody>
      <logic:present name="learncontentForm" property="usecatevo">
	  <logic:iterate id="vo" name="learncontentForm" property="usecatevo.downloadlearnlist" type="netTest.learncont.vo.Learncontent" indexId="ind">
      <tr id='<%="learnDataTrId"+ind %>' class='<%=(ind%2==0)?"oddRow":"evenRow" %>'>
        <td>
           <img src="../styles/imgs/filetype/<bean:write name="vo" property="fileicon"/>" >
           <%
              String linkfilesourceVar = vo.getLinkfilesource();
              if(LearncontentConstant.Linktype_Local.equals(vo.getLinktype())){
        	     linkfilesourceVar = WebConstant.FileContext+vo.getLinkfilesource();
              }
           %> 
           <a href='<%=linkfilesourceVar %>' title="preview" target="_learncontent">
              <bean:write name="vo" property="caption"/></a>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
      </tbody>
   </table>
   </div>
  
  </div>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title><bean:message key="netTest.page.common.title"/>-系统留言</title>
    <link rel="stylesheet" type="text/css" href="../styles/css/leftMenu.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/list.css" />
    <link href="<%=WebConstant.WebContext %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <style type="text/css">
        .{
		    margin:0px;
		}
		
		#menu6{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
		.parentCommentTdStyle{
		   border-bottom:solid 3px #EEEEEE;
		}
		
		.subCommentTdStyle{
		   background: #FEEFEF;
		   padding: 3px;
		}
		
		.subCommentFunDivStyle{
		   text-align: right;
		   clear: both;
		   width: 100%;
		}
		
    </style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  <% boolean hasright = false; %>
  <authz:authorize ifAnyGranted="ROLE_SysAdmin,ROLE_BizDataAdmin">
      <%hasright = true; %>
  </authz:authorize>
  
  <body>
  
  <div class="col-xs-12 col-md-9 center-block">
  <html:form styleId="list" action="/social/listleavemess.do">
  <input type="hidden" name="queryVO.objecttype" value="system"/>
  <input type="hidden" name="queryVO.objectid" value="<%=WebConstant.SysCode_Education %>"/>
  <input type="hidden" id="pageIndexId" name="pageIndex"/>
  <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
  
      <jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
  
      <div style="float:left; margin-top: 20px;" class="col-md-2 hidden-xs">
	       <ul id="nav">
	          <li class="menu"><a href="../about/aboutsite.jsp"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.introduceSite"/></a></li>
	          <li class="menu"><a href="../about/aboutsite.jsp#functions"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.functions"/></a></li>
	          <li class="menu"><a href="../about/aboutsite.jsp#contactInfo"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.contactInfo"/></a></li>
	          <li class="press"><a href="../social/listleavemess.do?queryVO.objecttype=system"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.systemAdvice"/></a></li>
	       </ul>
	  </div>
  
      <div style="float: left; margin-top: 20px; min-height: 400px;" class="col-xs-12 col-md-10">
      <div class="fieldsTitleDiv">留言列表</div>
	  <div id="DataTableDiv" style="margin: 7px; width: 99%;">
	       <table id="comtableId" style="margin:2px; width: 100%;" cellspacing="5px;" cellpadding="5px;">
	          <tbody>
	             <logic:present name="pageList">
	             <logic:iterate id="vo" name="pageList" indexId="ind" type="platform.social.vo.Leavemessage">
	             <input type="hidden" id="commentId<%=vo.getMessid() %>" value="<%=vo.getMessid() %>">
	             <tr id="trId<%=vo.getMessid() %>">
	                 <td class="parentCommentTdStyle">
	                     <table style="width: 100%; border: 0px;" cellpadding="0" cellspacing="0">
	                         <tr>
	                             <td id="tdId<%=vo.getMessid() %>">&diams;&nbsp;&nbsp;<%=vo.getContent() %></td>
	                         </tr>
	                         <tr>
	                             <td align="right"> <bean:write name="vo" property="updatedate" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;发表
	                                 <%if(hasright){ %>
	                                 &nbsp; &nbsp;<img title="reply" src="../styles/imgs/reply.png" style="cursor: pointer;" onclick="addCommentPage('<%=vo.getMessid() %>'); return false;">
		                 			 &nbsp; &nbsp;<img title="delete" src="../styles/imgs/delete.png" style="cursor: pointer;" onclick="deleteComment('trId<%=vo.getMessid() %>', '<%=vo.getMessid() %>', null); return false;">
		                 			 <%} %>
	                             </td>
	                         </tr>
	                         <tr>
	                             <td>
	                                 <table id="subcomtableId<%=vo.getMessid() %>" style="width: 98%; margin-left:10px;"  cellspacing="4px;" cellpadding="4px;">
					                 <tbody>
					                     <logic:present name="vo" property="subList">
					                     <logic:iterate id="subvo" name="vo" property="subList" type="platform.social.vo.Leavemessage">
					                         <tr id="trId<%=subvo.getMessid() %>" >
				                 				<td id="tdId<%=subvo.getMessid() %>" class="subCommentTdStyle">
					                         	    &nbsp; &nbsp;&nbsp;<%=subvo.getContent() %>
					                         	    <div class="subCommentFunDivStyle">
					                         	       <bean:write name="subvo" property="updatedate" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;回复
					                         	       <%if(hasright){ %>
					                         	       &nbsp; &nbsp;<img title="delete" src="../styles/imgs/delete.png" style="cursor: pointer;" onclick="deleteComment('trId<%=subvo.getMessid() %>', '<%=subvo.getMessid() %>', '<%=vo.getMessid() %>'); return false;">
					                         	       <%} %>
					                         	    </div>
					                         	</td>
					                         </tr>
					                     </logic:iterate>
					                     </logic:present>
					                 </tbody>
					                 </table>
	                             </td>
	                         </tr>
	                     </table>
	                 </td>
	             </tr>
	             </logic:iterate>
	             </logic:present>
	          </tbody>
	       </table>
	  </div>
      
	      <jsp:include flush="true" page="/pubs/footpage.jsp"></jsp:include>
	      
	      <div style="width: 100%;text-align: center; margin-top: 20px;margin-bottom: 15px;">
	         <button style="font-size: large;" type="button" onclick="addCommentPage();">新增留言</button>
	      </div>
      </div>
 
  </html:form>
  
  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </div>
  <script type="text/javascript">

	 function addCommentPage(parentid){
		 var width = 700;
		 var parentStr = "";
		 var sourceObjStr = '?vo.objecttype=system&vo.objectid=<%=WebConstant.SysCode_Education %>';
		 if(typeof(parentid)!='undefined' && parentid!=null && parentid!=''){
			 parentStr = "&vo.parent="+parentid;
		 }
		 newDiv('addleavemess.do'+sourceObjStr+parentStr, 1, 1, width, 300);
	 }
	 
	 function shownewcomment(id, parentid){
		 var topcommentIdStr = "comtableId";
		 if(parentid!=null && parentid!=''){
			 topcommentIdStr = "subcomtableId"+parentid;
		 }
		 clearDiv();
		 if(id!=null&&id!=''){
	         var url = "<%=WebConstant.WebContext %>/social/getsingleleavemess.do?vo.messid="+id;
	         var rtnObj = toAjaxUrl(url,false);
	         if(rtnObj.result=="1"){ 
	        	 var newTr = document.getElementById(topcommentIdStr).tBodies[0].insertRow(-1);
	             newTr.id = "trId"+id;  
	             var newTd1 = newTr.insertCell(0); 
	             if(parentid!=null && parentid!='') {
	            	 newTd1.id = "tdId"+id;
	            	 newTd1.className = "subCommentTdStyle";
	            	 newTd1.innerHTML = "&nbsp; &nbsp;&nbsp;"+rtnObj.info + 
	            	 		"<div class=\"subCommentFunDivStyle\">"+
              	    	       "&nbsp; &nbsp;<img title=\"delete\" src=\"../styles/imgs/delete.png\" style=\"cursor: pointer;\" onclick=\"deleteComment('trId"+id+"', '"+id+"', '"+parentid+"'); return false;\">"+
              	    		"</div>";
	             }else {
	            	 newTd1.className = "parentCommentTdStyle";
	             	 newTd1.innerHTML = 
				             "<table style=\"width: 100%; border: 0px;\" cellpadding=\"0\" cellspacing=\"0\">"+
			                    "<tr>"+
			                        "<td id=\"tdId"+id+"\">&diams;&nbsp;&nbsp;"+rtnObj.info+"</td>"+
			                    "</tr>"+
			                    "<tr>"+
			                        "<td align=\"right\">&nbsp; &nbsp;"+
			                             <%if(hasright){ %>
			                             "<img title=\"reply\" src=\"../styles/imgs/reply.png\" style=\"cursor: pointer;\" onclick=\"addCommentPage('"+id+"'); return false;\">"+
			                			 "&nbsp; &nbsp;<img title=\"delete\" src=\"../styles/imgs/delete.png\" style=\"cursor: pointer;\" onclick=\"deleteComment('trId"+id+"', '"+id+"', null); return false;\">"+
			                             <%} %>
			                        "</td>"+
			                    "</tr>"+
			                    "<tr>"+
			                        "<td>"+
			                            "<table id=\"subcomtableId"+id+"\" style=\"width: 98%; margin-left:10px;\"  cellspacing=\"4px;\" cellpadding=\"4px;\">"+
						                 "<tbody>"+
						                 "</tbody>"+
						                 "</table>"+
			                        "</td>"+
			                    "</tr>"+
			                "</table>";
	             	
	         	 }
	         }else if(rtnObj.result=="2"){
	        	alert(rtnObj.info);
	         }else{
	            alert("systemError");
	         }
		 }
	 }
	 
	 <%if(hasright){ %>
	 function deleteComment(trId, id, parentid){
		 if(!confirm('confrim to delete it?')){
			 return;
		 }
		 var commentId = "comtableId";
		 if(parentid!=null && parentid!=''){
			 commentId = "subcomtableId"+parentid;
		 }
		 var ddTable = document.getElementById(commentId).tBodies[0];
		 var url = "<%=WebConstant.WebContext %>/social/delsysleavemess.do?vo.messid="+id;
		 var rtnObj = toAjaxUrl(url,false);
         if(rtnObj.result=="1"){ // forward the success page,if success
            var rowNo = document.getElementById(trId).sectionRowIndex;
	        ddTable.deleteRow(rowNo);
         }else if(rtnObj.result=="2"){
        	alert(rtnObj.info);
         }else{
            alert("systemError");
         }
	 }
	 <%} %>
	 
  </script>
  </body>
</html:html>

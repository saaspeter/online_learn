<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="objecttypeVar" name="commentsForm" property="queryVO.objecttype" type="java.lang.String"></bean:define>
    <bean:define id="objectidVar" name="commentsForm" property="queryVO.objectid" type="java.lang.String"></bean:define>
    <bean:define id="objectnameVar" name="commentsForm" property="queryVO.objectname" type="java.lang.String"></bean:define>
    <bean:define id="subclassidVar" name="commentsForm" property="queryVO.subclassid" type="java.lang.String"></bean:define>
    <bean:define id="subclassnameVar" name="commentsForm" property="queryVO.subclassname" type="java.lang.String"></bean:define>
    <bean:define id="sessuserid" name="commentsForm" property="sessuserid" type="java.lang.Long"></bean:define>
    <% String managepriv = request.getParameter("managepriv"); %>
    <title>list comments</title>
    <link rel="stylesheet" type="text/css" href="../styles/css/list.css">
    <style type="text/css">
        .{
		    margin:0px;
		}
		
		.parentCommentTdStyle{
		   border-bottom:solid 3px #EEEEEE;
		   word-break:break-all;
		}
		
		.subCommentTdStyle{
		   background: #FEEFEF;
		   padding: 3px;
		   word-break:break-all;
		}
		
		.subCommentFunDivStyle{
		   text-align: right;
		   clear: both;
		   width: 100%;
		}
		
    </style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
    
  <body>
  <div style="width: 98%">
  <html:form styleId="listFormId" action="/social/listComments.do" method="post">
    <input type="hidden" name="queryVO.objecttype" value="<%=objecttypeVar %>"/>
    <input type="hidden" name="queryVO.objectid" value="<%=objectidVar %>"/>
    <input type="hidden" name="queryVO.subclassid" value="<%=objectidVar %>"/>
    <input type="hidden" name="queryVO.objectname" value="<%=objectnameVar %>"/>
    <input type="hidden" name="queryVO.subclassname" value="<%=subclassnameVar %>"/>
    <input type="hidden" id="pageIndex_id" name="pageIndex"/>
    <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
    <div id="DataTableDiv" style="margin: 7px; width: 100%;">
       <table id="comtableId" style="margin:2px; width: 100%;" cellspacing="5px;" cellpadding="5px;">
          <tbody>
             <logic:present name="pageList">
             <logic:iterate id="vo" name="pageList" indexId="ind" type="platform.social.vo.Comments">
             <input type="hidden" id="commentId<%=vo.getCommentid() %>" value="<bean:write name="vo" property="commentid"/>">
             <tr id="trId<%=vo.getCommentid() %>">
                 <td class="parentCommentTdStyle">
                     <table style="width: 100%; border: 0px;word-break:break-all;" cellpadding="0" cellspacing="0">
                         <tr>
                             <td id="tdId<%=vo.getCommentid() %>" style="word-break:break-all;"><%=vo.getContent() %></td>
                         </tr>
                         <tr>
                             <td align="right">
                                 <bean:writeDate name="vo" property="updatedate" format="MM-dd HH:mm"/>
                                 &nbsp;<bean:write name="vo" property="creatordisplayname"/>
                                 &nbsp; &nbsp;<img title="reply" src="../styles/imgs/reply.png" style="cursor: pointer;" onclick="addCommentPage('<%=vo.getCommentid() %>'); return false;">
	                 			 <%if("1".equals(managepriv)||(sessuserid!=null&&sessuserid.equals(vo.getCreatorid()))) { %>
	                 			 &nbsp; &nbsp;<img title="modify" src="../styles/imgs/edit.png" style="cursor: pointer;" onclick="modifyCommentPage('<bean:write name="vo" property="commentid"/>'); return false;">
	                 			 &nbsp; &nbsp;<img title="delete" src="../styles/imgs/delete.png" style="cursor: pointer;" onclick="deleteComment('trId<%=vo.getCommentid() %>', '<%=vo.getCommentid() %>', null); return false;">
                                 <%} %>
                             </td>
                         </tr>
                         <tr>
                             <td>
                                 <table id="subcomtableId<%=vo.getCommentid() %>" style="width: 98%; margin-left:10px;"  cellspacing="4px;" cellpadding="4px;">
				                 <tbody>
				                     <logic:present name="vo" property="subList">
				                     <logic:iterate id="subvo" name="vo" property="subList" type="platform.social.vo.Comments">
				                         <tr id="trId<%=subvo.getCommentid() %>" >
			                 				<td id="tdId<%=subvo.getCommentid() %>" class="subCommentTdStyle">
				                         	<%=subvo.getContent() %>
				                         	    <div class="subCommentFunDivStyle">
				                         	         <bean:writeDate name="subvo" property="updatedate" format="MM-dd HH:mm"/>
				                         	         <bean:write name="subvo" property="creatordisplayname"/>
				                         	         <%if("1".equals(managepriv)||(sessuserid!=null&&sessuserid.equals(subvo.getCreatorid()))) { %>
				                         	         &nbsp; &nbsp;<img title="modify" src="../styles/imgs/edit.png" style="cursor: pointer;" onclick="modifyCommentPage('<%=subvo.getCommentid() %>'); return false;">
	                 			 				     &nbsp; &nbsp;<img title="delete" src="../styles/imgs/delete.png" style="cursor: pointer;" onclick="deleteComment('trId<%=subvo.getCommentid() %>', '<%=subvo.getCommentid() %>', '<%=vo.getCommentid() %>'); return false;">
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
    
    <jsp:include flush="true" page="/pubs/pagetiles_bottom.jsp"></jsp:include>
    
    <div style="width: 97%;text-align: center; margin-top: 20px;">       
       <a href="javascript:void(0)" onclick="addCommentPage();">新增comments</a>
    </div>
  </html:form>
  </div>
  <script language=JavaScript>
	 function modifyCommentPage(id){
		 var width = document.body.clientWidth * 0.8;
		 newDiv('editComments.do?vo.commentid='+id, 1, 1, width, 300);
	 }
	 
	 function updateContent(id){
		 clearDiv();
		 document.getElementById("tdId"+id).innerHTML="none";
		 if(id!=null&&id!=''){
	         var url = "getSingleComment.do?vo.commentid="+id;
	         var rtnObj = toAjaxUrl(url,false);
	         if(rtnObj.result=="1"){
	             document.getElementById("tdId"+id).innerHTML = rtnObj.info;
	         }else if(rtnObj.result=="2"){
	        	alert(rtnObj.info);
	         }else{
	            alert("systemError");
	         }
		 }
	 }
	 
	 function addCommentPage(parentid){
		 var width = document.body.clientWidth * 0.8;
		 var parentStr = "";
		 var objecttypeVarJs = '<%=objecttypeVar %>';
		 if(objecttypeVarJs=='null'){objecttypeVarJs='';}
		 var objectidVarJs = '<%=objectidVar %>';
		 if(objectidVarJs=='null'){objectidVarJs='';}
		 var objectnameVarJs = '<%=objectnameVar %>';
		 if(objectnameVarJs=='null'){objectnameVarJs='';}
		 var subclassidVarJs = '<%=subclassidVar %>';
		 if(subclassidVarJs=='null'){subclassidVarJs='';}
		 var subclassnameVarJs = '<%=subclassnameVar %>';
		 if(subclassnameVarJs=='null'){subclassnameVarJs='';}
		 var sourceObjStr = '?vo.objecttype='+objecttypeVarJs+'&vo.objectid='+objectidVarJs
		                    +'&vo.objectname='+objectnameVarJs+'&vo.subclassid='+subclassidVarJs
		                    +'&vo.subclassname='+subclassnameVarJs;
		 if(objecttypeVarJs==null||objecttypeVarJs==''||objectidVarJs==null||objectidVarJs==''){
		    alert('请选择课程!');
		    return;
		 }
		 if(typeof(parentid)!='undefined' && parentid!=null && parentid!=''){
			 parentStr = "&vo.parent="+parentid;
		 }
		 newDiv('addComments.do'+sourceObjStr+parentStr, 1, 1, width, 400);
	 }
	 
	 function deleteComment(trId, id, parentid){
		 if(!confirm('confrim to delete it?')){
			 return;
		 }
		 var commentId = "comtableId";
		 if(parentid!=null && parentid!=''){
			 commentId = "subcomtableId"+parentid;
		 }
		 var ddTable = document.getElementById(commentId).tBodies[0];
		 var url = "deleteComments.do?vo.commentid="+id;
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
	 
	 function shownewcomment(id, parentid){
		 var topcommentIdStr = "comtableId";
		 if(parentid!=null && parentid!=''){
			 topcommentIdStr = "subcomtableId"+parentid;
		 }
		 clearDiv();
		 if(id!=null&&id!=''){
	         var url = "getSingleComment.do?vo.commentid="+id;
	         var rtnObj = toAjaxUrl(url,false);
	         if(rtnObj.result=="1"){ 
	        	 var newTr = document.getElementById(topcommentIdStr).tBodies[0].insertRow(-1);
	             newTr.id = "trId"+id;  
	             var newTd1 = newTr.insertCell(0); 
	             if(parentid!=null && parentid!='') {
	            	 newTd1.id = "tdId"+id;
	            	 newTd1.className = "subCommentTdStyle";
	            	 newTd1.innerHTML = rtnObj.info + 
	            	 		"<div class=\"subCommentFunDivStyle\">"+
              	    	       "&nbsp; &nbsp;<img title=\"modify\" src=\"../styles/imgs/edit.png\" style=\"cursor: pointer;\" onclick=\"modifyCommentPage('"+id+"'); return false;\">"+
		 					   "&nbsp; &nbsp;<img title=\"delete\" src=\"../styles/imgs/delete.png\" style=\"cursor: pointer;\" onclick=\"deleteComment('trId"+id+"', '"+id+"', '"+parentid+"'); return false;\">"+
              	    		"</div>";
	             }else {
	            	 newTd1.className = "parentCommentTdStyle";
	             	 newTd1.innerHTML = 
				             "<table style=\"width: 100%; border: 0px;\" cellpadding=\"0\" cellspacing=\"0\">"+
			                    "<tr>"+
			                        "<td id=\"tdId"+id+"\">"+rtnObj.info+"</td>"+
			                    "</tr>"+
			                    "<tr>"+
			                        "<td align=\"right\">"+
			                             "&nbsp; &nbsp;<img title=\"reply\" src=\"../styles/imgs/reply.png\" style=\"cursor: pointer;\" onclick=\"addCommentPage('"+id+"'); return false;\">"+
			                			 "&nbsp; &nbsp;<img title=\"modify\" src=\"../styles/imgs/edit.png\" style=\"cursor: pointer;\" onclick=\"modifyCommentPage('"+id+"'); return false;\">"+
			                			 "&nbsp; &nbsp;<img title=\"delete\" src=\"../styles/imgs/delete.png\" style=\"cursor: pointer;\" onclick=\"deleteComment('trId"+id+"', '"+id+"', null); return false;\">"+
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
  </script>
  </body>
</html:html>

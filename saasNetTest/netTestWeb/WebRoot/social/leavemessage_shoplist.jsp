<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="shopidVar" name="leavemessageForm" property="queryVO.objectid"></bean:define>
    <bean:define id="sessuserid" name="leavemessageForm" property="sessuserid" type="java.lang.Long"></bean:define>
    <title>leave message list</title>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/list.css" />
    <link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <style type="text/css">
        .{
		    margin:0px;
		}
		
		#menu3{
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
  <% boolean hasright = false; boolean showdelete = false; %>
  <authz:authorize ifAnyGranted="ROLE_ShopCreator,ROLE_ShopAdmin">
      <%hasright = true; %>
  </authz:authorize>
  
  <body>
  <html:form styleId="list" action="/social/listleavemess.do">
  <input type="hidden" name="queryVO.objecttype" value="shop"/>
  <input type="hidden" name="queryVO.objectid" value="<%=shopidVar %>"/>
  <input type="hidden" name="shopid" value="<%=shopidVar %>"/>
  <input type="hidden" id="pageIndexId" name="pageIndex"/>
  <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
  
  <div class="col-xs-12 col-md-9 center-block">
      <jsp:include flush="true" page='<%="../shop/banner_shop.jsp?shopid="+shopidVar.toString() %>'></jsp:include>
  
      <div class="fieldsTitleDiv">留言列表</div>
	  <div id="DataTableDiv" style="margin: 7px; width: 95%;min-height:450px;">
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
		                 			 <%} %>
		                 			 <%if(hasright||(sessuserid!=null&&sessuserid.equals(vo.getCreatorid()))){ %>
		                 			 &nbsp; &nbsp;<img title="delete" src="../styles/imgs/delete.png" style="cursor: pointer;" onclick="deleteComment('trId<%=vo.getMessid() %>', '<%=vo.getMessid() %>', null); return false;">
		                 			 <% showdelete=true; } %>
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
					                         	       <%if(hasright||(sessuserid!=null&&sessuserid.equals(vo.getCreatorid()))){ %>
					                         	       &nbsp; &nbsp;<img title="delete" src="../styles/imgs/delete.png" style="cursor: pointer;" onclick="deleteComment('trId<%=subvo.getMessid() %>', '<%=subvo.getMessid() %>', '<%=vo.getMessid() %>'); return false;">
					                         	       <% showdelete=true; } %>
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
         <%boolean buttonEnable = false; %>
         <authz:authorize ifAnyGranted="ROLE_RegisterMember">
         <%buttonEnable = true; %>
         </authz:authorize>
         <button style="font-size: large;" type="button" <%if(!buttonEnable){ %>disabled="disabled" title="请登录留言" <%} %> 
          onclick="addCommentPage();">新增留言</button>
      </div>
      
      <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </div>
  </html:form>
  <script type="text/javascript">

	 function addCommentPage(parentid){
		 var width = 700;
		 var parentStr = "";
		 var sourceObjStr = '?vo.objecttype=shop&vo.objectid=<%=shopidVar %>';
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
			                             "<img title=\"delete\" src=\"../styles/imgs/delete.png\" style=\"cursor: pointer;\" onclick=\"deleteComment('trId"+id+"', '"+id+"', null); return false;\">"+
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
	 
	 <%if(showdelete){ %>
	 function deleteComment(trId, id, parentid){
		 if(!confirm('confrim to delete it?')){
			 return;
		 }
		 var commentId = "comtableId";
		 if(parentid!=null && parentid!=''){
			 commentId = "subcomtableId"+parentid;
		 }
		 var ddTable = document.getElementById(commentId).tBodies[0];
		 var url = "<%=WebConstant.WebContext %>/social/delshopleavemess.do?vo.messid="+id;
		 var rtnObj = toAjaxUrl(url,false);
         if(rtnObj.result=="1"){ // forward the success page,if success
            var rowNo = document.getElementById(trId).sectionRowIndex;
	        ddTable.deleteRow(rowNo);
         }else if(rtnObj.result=="2"){
        	alert(rtnObj.info);
         }else{
            alert("systemError"+":"+rtnObj.info);
         }
	 }
	 <%} %>
	 
  </script>
  </body>
</html:html>

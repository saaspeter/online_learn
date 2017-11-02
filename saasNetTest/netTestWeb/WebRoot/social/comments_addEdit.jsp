<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="commentid" name="commentsForm" property="vo.commentid" type="java.lang.Long"></bean:define>
    <bean:define id="objecttype" name="commentsForm" property="vo.objecttype" type="java.lang.String"></bean:define>
    <bean:define id="objectid" name="commentsForm" property="vo.objectid" type="java.lang.String"></bean:define>
    <bean:define id="objectname" name="commentsForm" property="vo.objectname" type="java.lang.String"></bean:define>
    <bean:define id="subclassid" name="commentsForm" property="vo.subclassid" type="java.lang.String"></bean:define>
    <bean:define id="subclassname" name="commentsForm" property="vo.subclassname" type="java.lang.String"></bean:define>

    <title>add comments</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<script type="text/javascript" src="../styles/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">
        // 1:查看输入内容raw  2:查看输入内容HTML
        function viewInput(type){
        	var text = '';
        	var content = document.getElementById('editor1').text;
        	if(type==1){
        		text = p_editor.document.getBody().getText(); 
        	}else if(type==2){
        		text = p_editor.document.getBody().getHtml(); 
        	}
        	alert(text);
        	alert('content:'+content);
        }
    
        function saveComments(){
        	var parent = '<bean:write name="commentsForm" property="vo.parent"/>';
   		    var content = p_editor.document.getBody().getHtml();
   		    var commentid = document.getElementById('commentidId').value;
   		    if(content==null||content==''){
   		       alert('请输入评论内容');
   		       return;
   		    }else if(content.length>600){
   		       alert('输入不能大于600字符');
   		       return;
   		    }
   		    var url = "saveComments.do";
   		    var param = "vo.objecttype=<%=objecttype %>&vo.objectid=<%=objectid %>"
   		                +"&vo.subclassid=<%=subclassid %>&vo.content="+content;
   		    if(parent!=null && parent!=''){
   		        url = "replycomments.do";
   		    	param = param + "&vo.parent=" + parent;
   		    }
   		    if(commentid!=null && commentid!=''){
		    	param = param + "&vo.commentid=" + commentid;
		    }
            var rtnObj = toAjaxUrl(url,false,param);
            if(rtnObj.result=="1"){ // forward the success page,if success
            	if(commentid==null || commentid==''){
            	    window.parent.shownewcomment(rtnObj.info, parent);
            	}else {
            		window.parent.updateContent(commentid);
            	}
            }else if(rtnObj.result=="2"){
           	   alert(rtnObj.info);
            }else{
               alert("systemError");
            }
        	
        }
    
    </script>
  </head>
  
  <body>
	<div style="text-align: center;width: 100%;">
	<html:form styleId="editForm" action="/social/saveComments.do" method="post">
      <input type="hidden" id="commentidId" name="vo.commentid" value="<%=(commentid==null)?"":commentid.toString() %>">
	  
	  <div>
	      <%if(commentid==null){ %>新增comments <%}else { %>
	              修改comments
	      <%} %>
	  </div>
	  <% if((objectname!=null&&objectname.trim().length()>0) || (subclassname!=null&&subclassname.trim().length()>0)){ %>
	  <div>
	      (<%=objectname %>   <%=subclassname %>)
	  </div>
	  <% } %>
	  <div style="width: 99%;text-align: center;">
	      <textarea cols="80" id="editor1" name="vo.content" rows="6"><bean:write name="commentsForm" property="vo.content"/></textarea>
	      <script type="text/javascript">
	         var p_editor = CKEDITOR.replace( 'editor1',
	         {
	      		toolbar : 'MySimplest',
	      		height : 120
	    	 });
	           
	       </script>
	  </div>
	  <p/>
	  <div style="width: 99%;text-align: center;">
	  	 <input type="button" value="提交" onclick="saveComments()"/>
	  	 <button type="button" onclick="window.parent.clearDiv();">取消</button>
	  </div>
	</html:form>
	</div>
  </body>
</html:html>

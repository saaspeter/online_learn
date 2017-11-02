<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.product.vo.Productbase,platform.vo.Shop"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="objectid" name="leavemessageForm" property="vo.objectid" type="java.lang.String"></bean:define>
    <bean:define id="objecttype" name="leavemessageForm" property="vo.objecttype" type="java.lang.String"></bean:define>
    <title>leave message</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">   
        function saveComments(){
        	var parent = '<bean:write name="leavemessageForm" property="vo.parent"/>';
   		    var content = document.getElementById("editor1").value;
   		    var url = "saveleavemess.do";
   		    var param = "vo.objecttype=<%=objecttype %>&vo.objectid=<%=objectid %>&vo.content="+content;
   		    if(parent!=null && parent!=''){
   		        <%if(Productbase.ObjectType.equals(objecttype)){ %>
   		             url = "replyshopleavemess.do";
   		        <%}else if(Shop.ObjectType.equals(objecttype)){  %>
   		             url = "replyprodleavemess.do";
   		        <%}else if("system".equals(objecttype)){ %>
   		             url = "replysysleavemess.do";
   		        <%} %>
   		    	param = param + "&vo.parent=" + parent;
   		    }
            var rtnObj = toAjaxUrl(url,false,param);
            if(rtnObj.result=="1"){ // forward the success page,if success
           	    window.parent.shownewcomment(rtnObj.info, parent);
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
	
      <div style="width: 99%;text-align: center;">
	      新增留言
	  </div>
	  <p/>
	  <div style="width: 99%;text-align: center;">
	      <textarea cols="65" id="editor1" name="vo.content" rows="7"><bean:write name="leavemessageForm" property="vo.content"/></textarea>
	  </div>
	  <p/>
	  <div style="width: 99%;text-align: center;">
	  	 <input type="button" value="提交" onclick="saveComments()"/>
	  	 <button type="button" onclick="window.parent.clearDiv();">取消</button>
	  </div>
	
	</div>
  </body>
</html:html>

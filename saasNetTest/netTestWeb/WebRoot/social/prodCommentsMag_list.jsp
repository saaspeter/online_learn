<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTestWeb.base.BaseForm" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="productidVar" name="commentsForm" property="productid" type="java.lang.Long"></bean:define>
    <bean:define id="productname" name="commentsForm" property="productvo.productname" type="java.lang.String"></bean:define>
    <%
      String contentcatenameVar = (request.getParameter("contentcatename")==null)?"":request.getParameter("contentcatename");
      String contentcateidVar = (request.getParameter("contentcateid")==null)?"":request.getParameter("contentcateid");
      BaseForm form = new BaseForm();
      Long shopid = form.getSessionShopid();
      String prodidStr = (productidVar==null)?"":productidVar.toString();
      String prodmagpriv = "0";
    %>
    <title>课程提问答疑</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	    
	    function searchit(){
	       var contentcateid = document.getElementById("contentcateidId").value;
	       var contentcatename = document.getElementById("contentcatenameId").value;
	       var subnum = document.getElementById("subnumId").value;
	       var url = "<%=WebConstant.WebContext %>/social/listComments.do?queryVO.objecttype=product"
	                 +"&queryVO.objectid=<%=prodidStr %>&queryVO.objectname=<%=productname %>"
	                 +"&queryVO.subclassid="+contentcateid+"&queryVO.subclassname="+contentcatename
	                 +"&queryVO.subnum="+subnum;
	       document.getElementById("commentIframeId").src = url;
	    }
	    
	    function contcate_CB_Hook(id, name){
	       document.getElementById("contentcateidId").value = id;
	       document.getElementById("contentcatenameId").value = name;
	       searchit();
	    }
	    
	    function removeContcate_CB_Hook(){
	       searchit();
	    }
	    
	    function gotaburl(tabid, url){
	       var contentcateid = document.getElementById("contentcateidId").value;
	       var contentcatename = document.getElementById("contentcatenameId").value;
	       var tabli = document.getElementById(tabid);
	       
	       if("prodCommentLiId"==tabid){
	          document.getElementById("prodCommentLiId").className = 'selectTab';
	          document.getElementById("learnCommentLiId").className = '';
	       }else if("learnCommentLiId"==tabid){
	          document.getElementById("prodCommentLiId").className = '';
	          document.getElementById("learnCommentLiId").className = 'selectTab';
	       }
	       document.getElementById("commentIframeId").src = url;
	    }
	    
	    function changeanswertype(selObj){
	       var answertype = selObj.value;
	       document.getElementById("subnumId").value = answertype;
	       searchit();
	    }
	    

	</script>
	
  </head>
  
  <authz:privilege res='<%="/product/editproduct.do?productbaseid="+prodidStr %>'>
     <% prodmagpriv = "1"; %>
  </authz:privilege>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/social/listprodcommentsmag.do" method="post">
  <input id="shopidId" type="hidden" name="shopid" value="<%=shopid %>" >
  <input id="prodidId" type="hidden" name="productid" value="<%=prodidStr %>" >
  
  <div class="fieldsTitleDiv">
       <bean:write name="commentsForm" property="productvo.productname"/>
  </div>
    
  <div id="functionLineDiv" style="background-color: #ffffff;margin-bottom:10px;">
      <div id="searchDiv" style="padding-bottom:5px;">
        <button type="button" class="btn btn-success" onclick="document.getElementById('commentIframeId').contentWindow.addCommentPage();">新增提问</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="hidden" id="subnumId" name="queryVO.subnum" />
        <label for="unanswercommentRadio1">
           <input type="radio" id="unanswercommentRadio1" name="answercommenttype" checked="checked" value="0" onclick="changeanswertype(this);"/>未回复提问
        </label>
        <label for="unanswercommentRadio2">
           <input type="radio" id="unanswercommentRadio2" name="answercommenttype" value="" onclick="changeanswertype(this);"/>全部提问
        </label>
        &nbsp;&nbsp;&nbsp;
         课程章节:
        <input type="text" id="contentcatenameId" name="queryVO.contentcatename" value="<%=contentcatenameVar %>" readonly="readonly" style="width:150px" onclick="selectContcate('<%=prodidStr %>');"/>
        <input id="contentcateidId" type="hidden" name="queryVO.contentcateid" value="<%=contentcateidVar %>">
        <img src="../styles/imgs/ico/reset.gif" alt="删除章节" onclick="removeContcate();">  
        
      </div>
  </div>
 
  <div class="dashLine"></div>
  
  <div id="DataDivId">
     <iframe id="commentIframeId" frameborder="0" width="100%" height="96%" src="<%=WebConstant.WebContext %>/social/listComments.do?queryVO.objecttype=product&queryVO.objectid=<%=prodidStr %>&queryVO.objectname=<%=productname %>&queryVO.subclassid=<%=contentcateidVar %>&queryVO.subclassname=<%=contentcatenameVar %>&queryVO.subnum=0&managepriv=<%=prodmagpriv %>"></iframe>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
    <script type="text/javascript">  
	  function iFrameHeight(iframeid, contentdivId, addtype, height) {   
		 var ifm= document.getElementById(iframeid); 
		 if(addtype=='1'&&height!=null&&height!=''){
		    document.getElementById(contentdivId).style.height = height;
		 }else {
		    var subWeb = ifm.contentDocument;  
		    if(typeof(subWeb)=='undefined'){
		       subWeb = ifm.document;
		    } 
			if(ifm != null && subWeb != null) {
			   var finalHeight = subWeb.body.scrollHeight;
			   if(addtype=='2'){
			      finalHeight = finalHeight + height+"px";
			   }
			   document.getElementById(contentdivId).style.height = finalHeight;
			} 
		 }
		   
	  }   
	  
	  window.onload = function(){
	     iFrameHeight('commentIframeId','DataDivId');
	  }
     </script>
  </body>
</html:html>

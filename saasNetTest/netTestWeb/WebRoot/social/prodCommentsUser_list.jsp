<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.vo.Productbase" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="productname" name="commentsForm" property="productname" type="java.lang.String"/>
<bean:define id="productId" name="commentsForm" property="productid" type="java.lang.Long"/>
<bean:define id="shopid" name="commentsForm" property="shopid" type="java.lang.Long"/>
<bean:define id="shopname" name="commentsForm" property="shopname" type="java.lang.String"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <%
      String contentcatenameVar = (request.getAttribute("contentcatename")==null)?request.getParameter("contentcatename"):(String)request.getAttribute("contentcatename");
      if(contentcatenameVar==null){
    	  contentcatenameVar="";
      }
      String contentcateidVar = (request.getAttribute("contentcateid")==null)?request.getParameter("contentcateid"):String.valueOf(request.getAttribute("contentcateid"));
      if(contentcateidVar==null){
    	  contentcateidVar="";
      }
      String productidVar = (productId==null)?"":productId.toString();
      
      String prodmagpriv = "0";
    %>

    <title>提问答疑</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<style type="text/css">

		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	
	</style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	    
	    function searchit(){
	       var contentcateid = document.getElementById("contentcateidId").value;
	       var contentcatename = document.getElementById("contentcatenameId").value;
	       var searchuserowner = document.getElementById("ownertypeId").value;
	       var url = "<%=WebConstant.WebContext %>/social/listComments.do?queryVO.objecttype=<%=Productbase.ObjectType %>"
	                 +"&queryVO.objectid=<%=productidVar %>&queryVO.objectname=<%=productname %>"
	                 +"&queryVO.subclassid="+contentcateid+"&queryVO.subclassname="+contentcatename
	                 +"&searchuserowner="+searchuserowner;
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
	    
	    function changecreator(selObj){
	       var ownertype = selObj.value;
	       document.getElementById("ownertypeId").value = ownertype;
	       searchit();
	    }
	</script>
	
  </head>
  
  <authz:privilege res='<%="/product/editproduct.do?productbaseid="+productId %>'>
     <% prodmagpriv = "1"; %>
  </authz:privilege>
  
  <body>
  
  <div class="listPage">
  <html:form styleId="list" action="/social/listprodcommentsuser.do" method="post">
  <input id="shopidId" type="hidden" name="shopid" value="<%=shopid %>" >
  <input id="prodidId" type="hidden" name="productid" value="<%=productidVar %>" >
    
  <div id="firstLineDiv" >
      <div id="searchDiv">
        <label for="unanswercommentRadio1">
           <input type="radio" id="unanswercommentRadio1" name="creatortype" checked="checked" value="" onclick="changecreator(this);"/>全部提问
        </label>
        <label for="unanswercommentRadio2">
           <input type="radio" id="unanswercommentRadio2" name="creatortype" value="1" onclick="changecreator(this);"/>我的提问
        </label>
        <input id="ownertypeId" type="hidden" name="searchuserowner" value="">
        &nbsp;&nbsp;
         课程章节:
        <input type="text" id="contentcatenameId" class="userInput" name="queryVO.contentcatename" value="<%=contentcatenameVar %>" readonly="readonly" onclick="selectContcate('<%=productidVar %>');"/>
        <input id="contentcateidId" type="hidden" name="queryVO.contentcateid" value="<%=contentcateidVar %>">
        <img src="../styles/imgs/ico/reset.gif" alt="删除章节" onclick="removeContcate();">  
        
      </div>
  </div>
 
  <div class="dashLine"></div>
  
  <div id="DataDivId">
     <iframe id="commentIframeId" frameborder="0" width="100%" height="100%" src="<%=WebConstant.WebContext %>/social/listComments.do?queryVO.objecttype=<%=Productbase.ObjectType %>&queryVO.objectid=<%=productidVar %>&queryVO.objectname=<%=productname %>&queryVO.subclassid=<%=contentcateidVar %>&queryVO.subclassname=<%=contentcatenameVar %>&managepriv=<%=prodmagpriv %>"></iframe>
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
	     iFrameHeight('commentIframeId','DataDivId','2',50);
	  }
     </script>
  </body>
</html:html>

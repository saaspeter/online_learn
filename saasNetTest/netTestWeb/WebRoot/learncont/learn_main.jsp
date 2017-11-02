<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.exercise.vo.Exercise, netTest.learncont.vo.Learncontent,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<%
   String productidStr = request.getParameter("productid");
   Long productid = null;
   if(productidStr!=null && productidStr.trim().length()>0){
	   productid = Long.parseLong(productidStr.trim());
   }
   String contentcateidStr = request.getParameter("contentcateid");
   Long contentcateid = null;
   if(contentcateidStr!=null && contentcateidStr.trim().length()>0){
	   contentcateid = Long.parseLong(contentcateidStr.trim());
   }
   String objectidStr = request.getParameter("objectid");
   Long objectid = null;
   if(objectidStr!=null && objectidStr.trim().length()>0){
	   objectid = Long.parseLong(objectidStr.trim());
   }
   String objecttype = request.getParameter("objecttype");
   if(objecttype!=null){
	   objecttype = objecttype.trim();
	   if("".equals(objecttype)){
		   objecttype = null;
	   }
   }
   
   String finishall = request.getParameter("finishall");
   
   String url = null;
   if(objecttype!=null && objectid!=null){
	   if(Learncontent.ObjectType.equals(objecttype)){
		   url = "/learncont/viewLearncontent.do?vo.contentid="+objectid;
	   }else if(Exercise.ObjectType.equals(objecttype)){
		   url = "/exercise/beforeDoExercise.do?exerid="+objectid;
	   }
   }
   
   String prodmagpriv = "0";
 %>

<authz:privilege res='<%="/product/editproduct.do?productbaseid="+productid %>'>
     <% prodmagpriv = "1"; %>
</authz:privilege>

<%
    String commenturl = request.getContextPath()+"/social/listComments.do?queryVO.objecttype="+objecttype+"&queryVO.objectid="+objectid+"&managepriv="+prodmagpriv;
    String downloadurl = request.getContextPath()+"/learncont/downloadlearnfile.do?productbaseid="+productid+"&queryVO.contentcateid="+contentcateid;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>学习</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<style type="text/css">
	   
	    .sideContDivShow{
	        width: 100%;clear: both;padding-top: 0px;
	        display: block;
	        height: 98%;
	    }
	    
	    .sideContDivHidden{
	        width: 100%;clear: both;padding-top: 0px;
	        display: none;
	    }
	    
	    .DataDivClass{
	    	float: left;
	        width: 68%;
	        max-height: 1000px;
	    }
	    
	    .DataDivClassExpand{
	    	float: left;
	        width: 97%;
	    }
	    
	    .sideDivClass{
	        float: left;
	        width: 30%;
	        padding: 5px;
	        overflow: auto;
	        border: 3px solid #cccccc;
	    }
	    
	    .sideTopSubItem{
	        float:left;
	        width: 33%;
	        background-color: #ffffff;
	        padding-top: 5px;
	        padding-bottom: 5px;
	        cursor: pointer;
	    }
	    
	    .sideTopSubItemOn{
	        float:left;
	        width: 33%;
	        background-color: #ec8;
	        padding-top: 5px;
	        padding-bottom: 5px;
	        cursor: pointer;
	        border-bottom: 3px solid #00ff00;
	    }
	    
	    .sideTorightDivClass{
	    	float:left;
	    	width: 12px;
	    	height: 42px;
	    	margin-right:2px;
	        background-image: url("../styles/imgs/expand_right.gif");
	        background-repeat:no-repeat;
	        background-size: 12px 42px;
	        cursor: pointer;
	    }
	    
	    .sideToleftDivClass{
	    	float:left;
	    	width: 12px;
	    	height: 42px;
	    	margin:2px;
	        background-image: url("../styles/imgs/expand_left.gif");
	        background-repeat:no-repeat;
	        background-size: 12px 42px;
	        cursor: pointer;
	    }
	   
	</style>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">
        function showtab(divId){
        	if('sideCateDiv'==divId){
        		document.getElementById('sideCateDivTop').className="sideTopSubItemOn";
        		document.getElementById('sideCommentDivTop').className="sideTopSubItem";
        		document.getElementById('sideDownloadDivTop').className="sideTopSubItem";
        		
        		document.getElementById(divId).style.display="block";
        		document.getElementById('sideCommentDiv').style.display="none";
        		document.getElementById('sideDownloadDiv').style.display="none";
        	}else if('sideCommentDiv'==divId){
        		document.getElementById('sideCateDivTop').className="sideTopSubItem";
        		document.getElementById('sideCommentDivTop').className="sideTopSubItemOn";
        		document.getElementById('sideDownloadDivTop').className="sideTopSubItem";
        		
        		document.getElementById("sideCateDiv").style.display="none";
        		document.getElementById('sideCommentDiv').style.display="block";
        		document.getElementById('sideDownloadDiv').style.display="none";
        		
        		document.getElementById("sideCommentDivIframeId").src='<%=commenturl %>';
        	}else if('sideDownloadDiv'==divId){
        		document.getElementById('sideCateDivTop').className="sideTopSubItem";
        		document.getElementById('sideCommentDivTop').className="sideTopSubItem";
        		document.getElementById('sideDownloadDivTop').className="sideTopSubItemOn";
        		
        		document.getElementById("sideCateDiv").style.display="none";
        		document.getElementById('sideCommentDiv').style.display="none";
        		document.getElementById('sideDownloadDiv').style.display="block";
        		
        		document.getElementById("sideDownloadDivIframeId").src='<%=downloadurl %>';
        	}
        }
        
        function expandSidebar(action){
        	if(action=='0'){
        		document.getElementById("sideDiv").style.display="none";
        		document.getElementById("sideToleftDiv").style.display="block";
        		document.getElementById("DataDivId").className="DataDivClassExpand";
        	}else {
        		document.getElementById("sideToleftDiv").style.display="none";
        		document.getElementById("sideDiv").style.display="block";
        		document.getElementById("DataDivId").className="DataDivClass";
        	}
        	
        }
        
        function setProductBar(productname){
        	document.getElementById("productbarId").innerHTML=productname;
        }
    </script>
  </head>
  
  <body>
  
	  <div class="editPage">
		
		  <div style="width:90%;text-align:left;margin: 10px 10px 10px 25px;padding:5px;">
		       <div style="float: left">
		       <img style="cursor: pointer; width: 302px;"
				onclick="document.location.href='/<%=CommonConstant.WebContext_Education%>';"
				src="/<%=CommonConstant.WebContext_Education%>/styles/imgs/logo_platform.jpg"
				alt="公司logo" name="logo" />
				</div>
				<div id="productbarId" style="float:left; margin-left:30px; line-height:60px; font-size:x-large;">

				</div>
		  </div>
		  
		  <div style="width: 90%;margin: 10px 10px 10px 25px;">
			  
			  <div id="DataDivId" class="DataDivClass" style="height:1000px" <%if("1".equals(finishall)){ %> style="display: none;" <%} %> >
			     <iframe id="commentIframeId" frameborder="0" width="100%" height="980px" src="<%=request.getContextPath()+url %>"></iframe>
			  </div>
			  
			  <%if("1".equals(finishall)){ %>
			  <div class="DataDivClass">
			                恭喜，您已完成所有的课程学习!
			  </div>
			  <%} %>
			  
			  <div id="sideToleftDiv" class="sideToleftDivClass" style="display: none;" title="Expand Function Bar" onclick="expandSidebar(1);">&nbsp;</div>
			  
			  <div id="sideDiv" class="sideDivClass">
			  
			      <div style="width: 100%;overflow: auto;border-bottom: 1px solid #cccccc;margin-bottom: 4px;max-height: 46px;">
			          <div id="sideTorightDiv" class="sideTorightDivClass" title="Collapse Function Bar" onclick="expandSidebar(0);">&nbsp;</div>
			          <div id="sideCateDivTop" class="sideTopSubItemOn" style="" title="课程目录" onclick="showtab('sideCateDiv');return false;"><img alt="课程目录" src="../styles/imgs/category.png"></div>
			          <div id="sideCommentDivTop" class="sideTopSubItem" style="width:30%;border-left: 1px solid #cccccc;border-right: 1px solid #cccccc;" title="讨论区" onclick="showtab('sideCommentDiv');return false;"><img alt="讨论区" src="../styles/imgs/discussion.png"></div>
			          <div id="sideDownloadDivTop" class="sideTopSubItem" style="width:30%;" title="相关下载" onclick="showtab('sideDownloadDiv');return false;"><img alt="相关下载" src="../styles/imgs/download.png"></div>
			      </div>
			      
			      <div id="sideCateDiv" class="sideContDivShow">
	                  <iframe id="sideCateDivIframeId" frameborder="0" width="100%" height="100%" src="<%=request.getContextPath()+"/learncont/listcontentsidebar.do?productbaseid="+productid+"&queryVO.contentcateid="+contentcateid %>"></iframe>
			  	  </div>
			  	
			  	  <div id="sideCommentDiv" class="sideContDivHidden">
			          <iframe id="sideCommentDivIframeId" frameborder="0" width="100%" height="100%" src=""></iframe>
			  	  </div>
			  	
			  	  <div id="sideDownloadDiv" class="sideContDivHidden">
			  	      <iframe id="sideDownloadDivIframeId" frameborder="0" width="100%" height="100%" src=""></iframe>
			  	  </div>
			  
			  </div>

	      </div>
	    
	</div>
	<div id="buttomDiv"></div>
	<jsp:include flush="true" page="/foot.jsp"></jsp:include>
  </body>
</html:html>

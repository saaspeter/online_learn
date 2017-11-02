<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.learncont.constant.LearncontentConstant, netTest.learncont.vo.Learncontent" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="contentVar" name="learncontentForm" property="vo.content" type="java.lang.String"/>
<bean:define id="linkfilesourceVar" name="learncontentForm" property="vo.linkfilesource" type="java.lang.String"/>
<bean:define id="contenttypeVar" name="learncontentForm" property="vo.contenttype" type="java.lang.Integer"/>
<bean:define id="linktypeVar" name="learncontentForm" property="vo.linktype" type="java.lang.Short"/>
<bean:define id="contentidVar" name="learncontentForm" property="vo.contentid" type="java.lang.Long"/>
<bean:define id="captionnameVar" name="learncontentForm" property="vo.caption" type="java.lang.String"/>

<%
   String conttypeStr = LearncontentConstant.getPageObjectType(contenttypeVar);
   if(LearncontentConstant.Linktype_Local.equals(linktypeVar)){
	   linkfilesourceVar = WebConstant.FileContext+linkfilesourceVar;
   }
   if(contentVar==null) {contentVar="";}
   
   String prodmagpriv = "0";
   
   boolean showonlinevideo = false;
   if((LearncontentConstant.ContentType_VIDEO.equals(contenttypeVar)
			  || LearncontentConstant.ContentType_AUDIO.equals(contenttypeVar))
	    	 && LearncontentConstant.Linktype_OnlineLINK.equals(linktypeVar)){
	   showonlinevideo = true;
   }
 %>

  <authz:privilege res='<%="/learncont/editLearncontent.do?vo.contentid="+contentidVar %>'>
     <% prodmagpriv = "1"; %>
  </authz:privilege>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    <title><bean:message key="netTest.page.common.title"/>-学习内容</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
	    if(!window.jQuery){
	       document.write("<script type='text/javascript' src='../styles/script/vendor/jquery-1.9.1.min.js'><\/script>");
	    }
	</script>
	<%if(showonlinevideo){ %>
	<link rel="stylesheet" type="text/css" href="../styles/jquery_oembed/jquery.oembed.css"> 
	<script type="text/javascript" src="../styles/jquery_oembed/jquery.oembed.min.js"></script>
	<%}else { %>
    <script src="../styles/flashplayer/mediaelement/mediaelement-and-player.min.js"></script>
	<link rel="stylesheet" href="../styles/flashplayer/mediaelement/mediaelementplayer.min.css" />
    <%} %>
  </head>
  
  <body>
	<div class="editPage">
		
	  <div id="fieldsTitleDiv">
	      <bean:write name="learncontentForm" property="vo.caption"/>
	      <img src="../styles/imgs/ico/link_edit.png" style="width: 20px;height: 20px;" onclick="document.getElementById('pagelinkId').style.display='block';" title="显示本页站内链接地址，可在别的页面中引用">
	  </div>
	  <div id="errorDisplayDiv"></div>
	  
	  <div id="pagelinkId" style="text-align: center;display: none;">/learncont/viewLearncontent.do?vo.contentid=<bean:write name="learncontentForm" property="vo.contentid"/></div>
	  <p>
	  
	  <div id="container" style="width:95%;margin:10px;padding-left:25px;text-align:center;">
	      <%if(LearncontentConstant.ContentType_VIDEO.equals(contenttypeVar)||
	    	   LearncontentConstant.ContentType_AUDIO.equals(contenttypeVar))
	        { 
	    	   if(LearncontentConstant.Linktype_OnlineLINK.equals(linktypeVar)){ %>
               <a href="<%=linkfilesourceVar %>" class="embed"></a>
               <% } else if(LearncontentConstant.ContentType_VIDEO.equals(contenttypeVar)){ %>
  			   <video width="95%" height="600" id="video1" poster="" controls="controls" preload="none">
				   <source type="video/mp4" src="<%=linkfilesourceVar %>" />
				   <!-- <track kind="subtitles" src="chapters.srt" srclang="en" /> -->
				   <!-- Fallback flash player for no-HTML5 browsers with JavaScript turned off -->
				   <object width="95%" height="600" type="application/x-shockwave-flash" data="../styles/flashplayer/mediaelement/flashmediaelement.swf"> 		
					  <param name="movie" value="../styles/flashplayer/mediaelement/flashmediaelement.swf" /> 
					  <param name="flashvars" value="controls=true&amp;file=<%=linkfilesourceVar %>" /> 		
					  <!-- Image fall back for non-HTML5 browser with JavaScript turned off and no Flash player installed -->
					  <img src="" width="95%" height="600" alt="Here we are" title="No video playback capabilities" />
				   </object>
		       </video>
               <%} else if(LearncontentConstant.ContentType_AUDIO.equals(contenttypeVar)){ %>	  
		       <audio id="audio1" src="<%=linkfilesourceVar %>" type="audio/mp3" controls="controls"></audio>
               <%} %>
         <% }else if(LearncontentConstant.ContentType_FLASH.equals(contenttypeVar)) { %>
	            <object type="application/x-shockwave-flash" data="<%=linkfilesourceVar %>" width="90%" height="240">
				   <param name="movie" value="<%=linkfilesourceVar %>">
				   <param name="loop" value="false">
				   alt : <a href="<%=linkfilesourceVar %>">file</a>
				</object>
	      <%}else { %>
	            <object data="<%=linkfilesourceVar %>" type="<%=conttypeStr %>" width="100%" height="600">
				  <a href="<%=linkfilesourceVar %>">点击下载文件</a>
				</object>
	      <%} %> 
	  </div>
	  <div style="width:95%;margin:10px;text-align:left;">
	      <%=contentVar %>
	  </div>
	  
	  <div style="width: 100%;text-align: center;margin-top:50px;">
	      <a href="javascript:void(0)" onclick="parentGo('<%=request.getContextPath() %>/learncont/autolearn.do?queryVO.productbaseid=<bean:write name="learncontentForm" property="vo.productbaseid"/>&objecttype=<%=Learncontent.ObjectType %>&objectid=<%=contentidVar %>');return false;">继续学习</a>
	  </div>

	</div>
	
	<script type="text/javascript">
    
	  var playoption = {
	      maxWidth: 700
	  };
	  
	  $(document).ready(function () {
		  <% if((LearncontentConstant.ContentType_VIDEO.equals(contenttypeVar)
				  || LearncontentConstant.ContentType_AUDIO.equals(contenttypeVar))
		    	 && LearncontentConstant.Linktype_OnlineLINK.equals(linktypeVar)){ %>
		   $("a.embed").oembed('<%=linkfilesourceVar %>',playoption);
		  <%} %>
		  backcallParent();
	  });
	  
	  function backcallParent(){
		  if(typeof(parent.setProductBar)!='undefined'){
			  parent.setProductBar('<bean:write name="learncontentForm" property="vo.productname"/>');
		  }
	  }
	  
	  function parentGo(url){
    	 parent.goUrl(url);
      }
	
	</script>
	
  </body>
</html:html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%String shopidVar = request.getParameter("shopid"); 
    if(shopidVar==null||"null".equals(shopidVar)){shopidVar="";}
    
    String productidVar = request.getParameter("productbaseid"); 
    
    String menuid_baseInfo = "id_mag_baseInfo";
    String menuid_people = "id_mag_people";
    String menuid_coursepost = "id_mag_coursepost";
    String menuid_learnDoc = "id_mag_learnDoc";
    String menuid_comments = "id_mag_comments";
    String menuid_testmag = "id_mag_testmag";
    String menuid_papermag = "id_mag_papermag";
    String menuid_waremag = "id_mag_waremag";
    String menuid_openActivity = "id_mag_openActivity";
    
    String url_baseInfo = "/product/viewproduct.do?productbaseid="+productidVar;
    String url_people = "/userproduct/listoneproduser.do?queryVO.productbaseid="+productidVar;
    String url_coursepost = "/product/listcoursepostmag.do?vo.productbaseid="+productidVar;
    String url_learnDoc = "/learncont/listLearncontent.do?productbaseid="+productidVar;
    String url_comments = "/social/listprodcommentsmag.do?productid="+productidVar;
    String url_testmag = "/exam/listTodoTestinfo.do?productbaseid="+productidVar+"&shopid="+shopidVar;
    String url_papermag = "/paper/listPaper.do?productbaseid="+productidVar;
    String url_waremag = "/wareques/listWare1.do?productbaseid="+productidVar;
    String url_openActivity = "/product/listOpenactivity.do?showtype=3&queryVO.productid="+productidVar;
    
    String url = "";
    String menuid = request.getParameter("menuid");
    if(menuid==null || "".equals(menuid) || menuid_baseInfo.equals(menuid)){
       url = url_baseInfo;
       menuid = menuid_baseInfo;
    }else if(menuid_people.equals(menuid)){
 	   url = url_people;
    }else if(menuid_coursepost.equals(menuid)){
 	   url = url_coursepost;
    }else if(menuid_learnDoc.equals(menuid)){
 	   url = url_learnDoc;
    }else if(menuid_comments.equals(menuid)){
 	   url = url_comments;
    }else if(menuid_testmag.equals(menuid)){
 	   url = url_testmag;
    }else if(menuid_papermag.equals(menuid)){
  	   url = url_papermag;
    }else if(menuid_waremag.equals(menuid)){
 	   url = url_waremag;
    }else if(menuid_openActivity.equals(menuid)){
  	   url = url_openActivity;
    }
  %>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>考试平台功能栏</title>
  <link rel="stylesheet" href="/<%=CommonConstant.WebContext_Education %>/styles/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="/<%=CommonConstant.WebContext_Education %>/styles/css/leftMenu.css">
  <script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/pageAction.js"></script>
  <script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/menu.js"></script>
  
</head>

<body>
<div style="width:100%;border: 0px;margin: 0px;">
	<ul id="nav">
	   <li class="menu"><a id="" href="javascript:void(0);" onclick="DoMenu('lxsz');return false;">&#9660课程基础管理</a> 
		   <ul id="lxsz" class="expanded">
		       <li><a id="<%=menuid_baseInfo %>" <%if(menuid_baseInfo.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('<%=menuid_baseInfo %>');toRightUrl('<%=menuid_baseInfo %>','<%=url_baseInfo %>',2);return false;"><i class="fa fa-book"></i>&nbsp;课程基本信息</a></li>
		       <li><a id="<%=menuid_people %>" <%if(menuid_people.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('id_mag_people');toRightUrl('<%=menuid_people %>','<%=url_people %>',1);return false;"><i class="fa fa-user"></i>&nbsp;课程人员管理</a></li>
		   </ul>
	   </li>
	   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('kssz');return false;" title="">&#9660课程学习管理</a>
		   <ul id="kssz" class="expanded">
 			   <li><a id="<%=menuid_learnDoc %>" <%if(menuid_learnDoc.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('id_mag_learnDoc');toRightUrl('<%=menuid_learnDoc %>','<%=url_learnDoc %>',1);return false;"><i class="fa fa-th-list"></i>&nbsp;课程学习内容</a></li>
		       <li><a id="<%=menuid_comments %>" <%if(menuid_comments.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('id_mag_comments');toRightUrl('<%=menuid_comments %>','<%=url_comments %>',1);return false;"><i class="fa fa-comment-o"></i>&nbsp;课程提问答疑</a></li>
		       <li><a id="<%=menuid_testmag %>" <%if(menuid_testmag.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('id_mag_testmag');toRightUrl('<%=menuid_testmag %>','<%=url_testmag %>',1);return false;"><i class="fa fa-graduation-cap"></i>&nbsp;考试管理</a></li>
		       <li><a id="<%=menuid_papermag %>" <%if(menuid_papermag.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('id_mag_papermag');toRightUrl('<%=menuid_papermag %>','<%=url_papermag %>',1);return false;"><i class="fa fa-file-text-o"></i>&nbsp;试卷管理</a></li>
		       <li><a id="<%=menuid_waremag %>" <%if(menuid_waremag.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('id_mag_waremag');toRightUrl('<%=menuid_waremag %>','<%=url_waremag %>',1);return false;"><i class="fa fa-database"></i>&nbsp;题库管理</a></li>
		   </ul>
	   </li>
	   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('tzhd');return false;" title="">&#9660通知活动</a>
	   	   <ul id="tzhd" class="expanded">
	   		   <li><a id="<%=menuid_coursepost %>" <%if(menuid_coursepost.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('id_mag_coursepost');toRightUrl('<%=menuid_coursepost %>','<%=url_coursepost %>',1);return false;"><i class="fa fa-newspaper-o"></i>&nbsp;课程通知</a></li>
	   		   <li><a id="<%=menuid_openActivity %>" <%if(menuid_openActivity.equals(menuid)){out.print("class='press'");} %> href="javascript:void(0);" onclick="pressMenu('<%=menuid_openActivity %>');toRightUrl('<%=menuid_openActivity %>','<%=url_openActivity %>',1);return false;"><i class="fa fa-newspaper-o"></i>&nbsp;公开活动</a></li>
	       </ul>
	   </li>
	</ul>
</div>
<script language="javascript">
	<!-- 
	 // 
	 function toRightUrl(menuid, mainUrl, loadtype){
	    if(typeof(iframego)!='undefined' && iframego==1){
	    	var url = '/shop/netTest_default.jsp?productbaseid=<%=productidVar %>&shopid=<%=shopidVar %>&menuid='+menuid;
	    	if(menuid=='<%=menuid%>'){
	    		url = '<%=url %>';
	    	}
	    	var iframegourl = doContextUrl(url);
	    	window.location.assign(iframegourl);
	    }else {
	    	mainUrl = doContextUrl(mainUrl);
		    if(loadtype==1){
		    	document.getElementById("contentIframeId").src=mainUrl;
		    }else if(loadtype=2){
		    	window.location.assign(mainUrl);
		    }
	    }
	 }
	
	 function getFirstUrl(){
		return '<%=url %>';
	 }
	 
	 <% if(menuid!=null&&!"".equals(menuid)){ %>
	 lastPressObj = document.getElementById('<%=menuid %>');
	 <%} %>
	 //-->
  </script>
</body>
</html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant, netTestWeb.base.KeyInMemoryConstant, platform.social.vo.SocialNews,commonTool.util.DateUtil" %>
<bean:define id="newscategoryid" name="infonewsForm" property="newscategoryid" type="java.lang.Long"/>
<bean:define id="newscategoryname" name="infonewsForm" property="newscategoryname" type="java.lang.String"/>
<bean:define id="indexcursor" name="infonewsForm" property="indexcursor" type="java.lang.Integer"/>
<bean:define id="socialnewslist" name="infonewsForm" property="socialnewslist" type="java.util.List"/>
<bean:define id="showtab" name="infonewsForm" property="showtab" type="java.lang.Boolean"/>
<!DOCTYPE html>
<%
   Long categoryid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
   String categoryname = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
   Integer totalsize = (socialnewslist==null)?0:(socialnewslist.size());
   int curindex = 0;
   SocialNews tempVO = null;
   int NumColumnDiv = 2;
   int rownumber = 1;
   if(totalsize!=0){
	   if(totalsize%(NumColumnDiv*3)==0){
		   rownumber = totalsize/(NumColumnDiv*3);
	   }else {
		   rownumber = (totalsize/(NumColumnDiv*3))+1;
	   }
   }
%>
<html:html lang="true">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title><bean:message key="netTest.page.common.title"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="../styles/css/list.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/tab/simpleTab2.css" />
		
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="../styles/css/socialpost.css" rel="stylesheet">
		<style type="text/css">
		    #menu2{
			  display: block;
			  color: #667;
			  background-color: #ec8;
			}
		    
		</style>
		<script type="text/javascript" src="../styles/script/pageAction.js"></script>
		<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
		<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	</head>
	<body>

<div class="col-xs-12 col-md-9 center-block">
<jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
    
    <div class="navlistBar">
	    <a href="javascript:void(-1);" onclick="switchCategory(this);return false;" title="点击更换目录">
	    <%if(categoryname==null||"null".equals(categoryname)){ %>
	         <bean:message key="netTest.commonpage.category.nullcategoryname"/>
	    <%}else { out.print(categoryname); } %>
	    </a> &gt; 咨询信息 
    </div>
    
    <form id="listFormId" action="listInfonews.do" method="get">
       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryid %>"/>
       <input type="hidden" id="Id_newscategoryid" name="newscategoryid" value="<%=CommonConstant.SearchTabPK %>"/>
       <input type="hidden" id="pageIndexId" name="pageIndex"/>
       <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
	</form>
	
	<%if(showtab){ %>
    <div style="height:auto; width:100%;padding: 5px;">
	  <ul class="tabnav">
		<logic:iterate id="tabcateVo" name="infonewsForm" property="tablist" type="platform.vo.Newscategory">
		   <li <%if(tabcateVo.getId().equals(newscategoryid)){out.print("class='selectTab'"); newscategoryname=tabcateVo.getName(); }%> ><a href="listInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=tabcateVo.getId() %>"><%=tabcateVo.getName() %></a></li>
		</logic:iterate>
		<li <%if(CommonConstant.SearchTabPK.equals(newscategoryid)){out.print("class='selectTab'"); }%> ><a href="javascript:void(0)" onclick="document.getElementById('listFormId').submit();return false;">搜索结果</a></li>
	  </ul>
	</div>
	<%} %>

  <!--main-->
  <div id="main">
   <%if(totalsize>0){
	   for(int rowIndex=0;rowIndex<rownumber;rowIndex++){ 
   %>
   <div class="row">
        <%if(curindex<totalsize){ %>
	    <div class="col-md-4 col-sm-6">
	        <%if(curindex==0) { %>
	        <div class="well"> 
	            <form class="form-horizontal" role="form">
	               <h4>What's New</h4>
	               <div class="form-group" style="padding:14px;">
	                  <textarea class="form-control" placeholder="Update your status"></textarea>
	               </div>
	               <button class="btn btn-success pull-right" type="button">Post</button><ul class="list-inline"><li><a href="#"><i class="glyphicon glyphicon-align-left"></i></a></li><li><a href="#"><i class="glyphicon glyphicon-align-center"></i></a></li><li><a href="#"><i class="glyphicon glyphicon-align-right"></i></a></li></ul>
	            </form>
	        </div>
	        <%} %>
			<% for(int eachColumnIndex=0;((curindex<totalsize)&&(eachColumnIndex<NumColumnDiv)); eachColumnIndex++){ 
				 tempVO = (SocialNews)socialnewslist.get(curindex);
				 curindex++;
			%>
	        <div class="panel panel-default">
	           <div class="panel-heading"><a href="<%=(tempVO.getSourceUrl()==null)?"javascript:void(0)":tempVO.getSourceUrl() %>" <%if(tempVO.getSourceUrl()!=null){ %>target="_newsInDetail" <%} %> class="pull-right small"><%=DateUtil.printSmart(tempVO.getCreatedTime()) %></a>
	                <h4><%=tempVO.getAuthorName() %></h4>
	           </div>
	   			<div class="panel-body">
	   			  <%if(tempVO.getImageUrlShow()!=null){ %>
	              <a href="<%=tempVO.getAttachmentClickUrl() %>" target="_news">
	                 <img src="<%=tempVO.getImageUrlShow() %>" style="width:100%">
	              </a>
	              <%} %>
	              <div class="clearfix"></div>
	              <hr>
	              <p>
                     <%=tempVO.getContent() %>
                     <%if(tempVO.getImageUrlShow()==null && tempVO.getAttachmentClickUrl()!=null){ %>
	                      <a href="<%=tempVO.getAttachmentClickUrl() %>" target="_news">more</a>
	                 <%} %>
				  </p>
	              <hr>
	              <form>
	              <div class="input-group">
	                <div class="input-group-btn">
	                <button class="btn btn-default"><i class="glyphicon glyphicon-share"></i></button>
	                </div>
	                <input type="text" class="form-control" placeholder="Add a comment..">
	              </div>
	          	  </form>
	              
	            </div>
	         </div>
	         <%} %>
	     
		</div>
		<%} %>

        <%if(curindex<totalsize){ %>
		<div class="col-md-4 col-sm-6">
	        <% for(int eachColumnIndex=0;((curindex<totalsize)&&(eachColumnIndex<NumColumnDiv)); eachColumnIndex++){ 
				 tempVO = (SocialNews)socialnewslist.get(curindex);
				 curindex++;
			%>
	        <div class="panel panel-default">
	            <div class="panel-heading"><a href="#" class="pull-right">View all</a> 
	               <h4><%=tempVO.getAuthorName() %></h4>
	            </div>
	   			<div class="panel-body">
	   			  <%if(tempVO.getImageUrlShow()!=null){ %>
	   			  <a href="<%=tempVO.getAttachmentClickUrl() %>" target="_news">
	                 <img src="<%=tempVO.getImageUrlShow() %>" style="width:100%">
	              </a>
	              <%} %>
	              <div class="clearfix"></div>
	              <hr>
	              
	              <p><%=tempVO.getContent() %>
	                 <%if(tempVO.getImageUrlShow()==null && tempVO.getAttachmentClickUrl()!=null){ %>
	                      <a href="<%=tempVO.getAttachmentClickUrl() %>" target="_news">more</a>
	                 <%} %>
	              </p>
	              
	              <hr>
	              <form>
	              <div class="input-group">
	                <div class="input-group-btn">
	                <button class="btn btn-default"><i class="glyphicon glyphicon-share"></i></button>
	                </div>
	                <input type="text" class="form-control" placeholder="Add a comment..">
	              </div>
	          	  </form>
	              
	            </div>
	         </div>
	         <%} %>
        </div>
        <%} %>

        <%if(curindex<totalsize){ %>
	  	<div class="col-md-4 col-sm-6">
	        <% for(int eachColumnIndex=0;((curindex<totalsize)&&(eachColumnIndex<NumColumnDiv)); eachColumnIndex++){ 
				 tempVO = (SocialNews)socialnewslist.get(curindex);
				 curindex++;
			%>
	        <div class="panel panel-default">
	            <div class="panel-heading"><a href="#" class="pull-right">View all</a> 
	               <h4><%=tempVO.getAuthorName() %></h4>
	            </div>
	   			<div class="panel-body">
	   			  <%if(tempVO.getImageUrlShow()!=null){ %>
	   			  <a href="<%=tempVO.getAttachmentClickUrl() %>" target="_news">
	                 <img src="<%=tempVO.getImageUrlShow() %>" style="width:100%">
	              </a>
	              <%} %>
	              <div class="clearfix"></div>
	              <hr>
	              
	              <p><%=tempVO.getContent() %>
	                 <%if(tempVO.getImageUrlShow()==null && tempVO.getAttachmentClickUrl()!=null){ %>
	                      <a href="<%=tempVO.getAttachmentClickUrl() %>" target="_news">more</a>
	                 <%} %>
	              </p>
	              
	              <hr>
	              <form>
	              <div class="input-group">
	                <div class="input-group-btn">
	                <button class="btn btn-default"><i class="glyphicon glyphicon-share"></i></button>
	                </div>
	                <input type="text" class="form-control" placeholder="Add a comment..">
	              </div>
	          	  </form>
	              
	            </div>
	         </div>
	         <%} %>
        </div>
        <%} %>
   </div><!--/row-->
   <%  }
	 }else { %>
    <div style="height:20em;"></div>
   <%} %>
   <hr>
  </div>
  
  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </div>

	<!-- script references -->
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script src="../styles/bootstrap/3.3.1/js/bootstrap.min.js"></script>
		<script src="../styles/script/social/socialpost.js"></script>
	</body>
</html:html>
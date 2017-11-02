<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="coursepostForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productId" name="coursepostForm" property="sessionProductid" type="java.lang.Long"/>
	<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
	<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
	<% String contentcateidStr = (request.getParameter("contentcateid")==null)?"":request.getParameter("contentcateid"); 
  	   String contentcatenameStr = (request.getParameter("contentcatename")==null)?"":request.getParameter("contentcatename");
	%>
    <title>课程公告</title>
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	<!--
	   #bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
	   }
	-->
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  <html:form styleId="list" action="/product/listcoursepost.do" method="post">
  <input id="prodidId" type="hidden" name="vo.productbaseid" value="<%=productId %>" >
  <input id="contentcateidId" type="hidden" name="queryVO.contentcateid" value="<%=contentcateidStr %>">
  <div class="navlistBar">
        学习考试&nbsp;&gt;&gt;&nbsp;课程信息台
      (课程:<a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_userNormal %>');" title="点击切换课程"><bean:write name="coursepostForm" property="sessionProductname"/></a>&nbsp;)
  </div>
  
  <div style="height:auto; width:100%;">
	  <ul class="tabnav">
	    <li class='selectTab'><a href="javascript:void(0)">课程通知</a></li>
	    <li><a href="<%=request.getContextPath() %>/learncont/selfLearncontent.do?productbaseid=<%=productId %>">学习课程</a></li>
		<li><a href="<%=request.getContextPath() %>/exam/listTestinfouser.do?productbaseid=<%=productId %>&contentcateid=<%=contentcateidStr %>">考试信息</a></li>
	    <li><a href="<%=request.getContextPath() %>/product/listOpenactivity.do?showtype=4&queryVO.productid=<%=productId %>">公开活动</a></li>
	  </ul>
  </div>
  
  <logic:present name="coursepostForm" property="contentcateid">
  <div style="border:1px solid #0000ff;padding: 4 4 4 20px;">
        您上次学习时间:<bean:writeDate name="coursepostForm" property="lastlearndate" formatKey="Common.DateFormatType.DateTime"/>, 学习章节: <bean:write name="coursepostForm" property="contentcatename" />, 
        <a href="../learncont/selfLearncontent.do?productbaseid=<%=productId %>">前往继续学习</a>
  </div>
  </logic:present>
  
  <div class="fieldsTitleDiv">课程通知</div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><%=ind+1 %>. </td>
        <td><bean:write name="vo" property="content" filter="false"/>
            &nbsp;<font style="font-size: smaller;">(<bean:writeDate name="vo" property="createtime" formatKey="Common.DateFormatType.DateTime"/>)</font>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
      <logic:notPresent name="pageList">
         <tr class="oddRow" style="text-align:center;"><td>暂时没有记录</td></tr>
      </logic:notPresent>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <div style="height:15px; clear:both;"></div>
	<div style="text-align: center">
	    <%if(totalPage>0){ %>
	    <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_back.gif" alt="previous page" border=0/></a>       
	   <% for(int i=1;i<totalPage+1;i++){ %>
	        <a href="<%=WebConstant.WebContext + "/order/myOrderlist.do" %>?pageIndex=<%=i %>" class="<%=(i==currentPage)?"pageNumberPress":"pageNumberNormal" %>"><%=i %></a> 
	   <% } %>
	   <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_next.gif" alt="next page" border=0/></a>
	   <% } %>
	</div>
	
	<jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </div>
  
  </body>
</html:html>

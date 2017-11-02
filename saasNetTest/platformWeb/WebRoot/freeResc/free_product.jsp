<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="platformWeb.base.WebConstant, commonTool.constant.CommonConstant"%>
<bean:define id="jsSuffix" name="cateProductForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="categoryid" name="cateProductForm" property="categoryid" type="java.lang.Long"/>
<bean:define id="categoryname" name="cateProductForm" property="categoryname" type="java.lang.String"/>
<bean:define id="newscategoryid" name="cateProductForm" property="newscategoryid" type="java.lang.Long"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>

<%  Long listTabID = 2l;
    if(newscategoryid==null) { newscategoryid=listTabID; } %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<html:base />

<title>免费课程列表</title>
<html:base />
<link rel="stylesheet" type="text/css" href="../styles/css/banner.css" />
<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
<link rel="stylesheet" type="text/css" href="../styles/css/list.css" />
<style type="text/css">
<!--

#menu3{
  display: block;
  color: #667;
  background-color: #ec8;
}

#leftAD {
	float:left;
	background-color:#FFFFFF;
	margin-top:5em;
	width: 120 px;
}
#content {
    width:1000px;
    left: 50%;
	position:relative;
	clear: both;
	margin-left: -500px;
}

#rightAD {
	height:800px;
	float:right;
	clear:right;
	margin-top:5em;
	background-color:#FFFFFF;
}

.articleList {
   text-align: left;
   list-style-type: square;
   margin: 10px;
}

.articleList li{
   margin: 10px;
   list-style-type: square;

}

.articleList li a{
   margin-right: 30px;
}

#displayMessDiv{
    color:#FF0000;
    background-color:#EEEEEE;
}

-->
</style>
<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
<script language=JavaScript src="../styles/script/pageAction.js"></script>

<script type="text/javascript">

</script>
</head>

<body>

<div id="content">

	<jsp:include flush="true" page="../index/banner.jsp"></jsp:include>

	<div class="navlistBar">
	    <a href="javascript:void(0)" onclick="switchProduct(this);" title="点击更换目录"><%=categoryname %></a> &gt; 免费资源
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;height: 25px;">
	    <form action="searchProductList.do" method="get">
	       <input type="hidden" name="categoryid" value="<%=categoryid %>"/>
	       <input type="hidden" name="categoryname" value="<%=categoryname %>"/>
	       <input type="hidden" name="newscategoryid" value="<%=CommonConstant.SearchTabPK %>"/>
	       <input type="text" name="productname" style="width:300px;height: 20px;" value="<bean:write name="cateProductForm" property="productname" />"/> 
	       <a href="javascript:void(0)" onclick="switchProduct(this);" title="点击更换目录"><%=categoryname %></a>&nbsp;&nbsp;&nbsp;
	       <button type="button" onclick="document.forms[0].submit();">搜索</button>
	    </form>
	</div>

	<div style="height:auto; width:100%;">
	  <ul class="tabnav">
	    <li <%if(listTabID.equals(newscategoryid)) {out.print("class='selectTab'"); }%> ><a href="searchProductList.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=listTabID.toString() %>">课程列表</a></li>
		<li <%if(CommonConstant.SearchTabPK.equals(newscategoryid)){out.print("class='selectTab'"); }%> ><a href="searchProductList.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=CommonConstant.SearchTabPK %>">系统商店</a></li>
	  </ul>
	</div>

    <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	</div>
     
    <div style="width: 100%;margin-bottom: 10px;">
	    <table class="listDataTable" >
	        <tr class="listDataTableHead">
	           <td>课程名称</td>
	           <td>所属商店</td>
	           <td>操作</td>
	        </tr>
	        <logic:present name="pageList">
		    <logic:iterate id="vo" name="pageList">
	        <tr>      
	           <td><a href="javascript:void(0);" onclick="window.open('<%=WebConstant.WebContext %>/shop/shop_productindex.jsp?shopid=<bean:write name="vo" property="shopid"/>&productid=<bean:write name="vo" property="productbaseid"/>&urlType=2');return false;"><bean:write name="vo" property="productname"/></a>
	               <logic:equal value="1" name="vo" property="issysgoodproduct">(荐)</logic:equal>
	           </td>      
	           <td><bean:write name="vo" property="shopname"/></td>
	           <td><a href="javascript:void(0);" onclick="window.open('<%=WebConstant.WebContext %>/shop/shop_productindex.jsp?shopid=<bean:write name="vo" property="shopid"/>&productid=<bean:write name="vo" property="productbaseid"/>&urlType=2');return false;">查看订购</a></td>
	        </tr>
	        </logic:iterate>
	        </logic:present>
	    </table>
    </div>
    
</div>

<div style="height:15px; clear:both;"></div>
<div style="text-align: center">
    <%if(totalPage>0){ %>
    <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_back.gif" alt="previous page" border=0/></a>       
   <% for(int i=1;i<totalPage+1;i++){ %>
        <a href="<%=WebConstant.WebContext %>/index/listInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&pageIndex=<%=i %>" class="<%=(i==currentPage)?"pageNumberPress":"pageNumberNormal" %>"><%=i %></a> 
   <% } %>
   <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_next.gif" alt="next page" border=0/></a>
   <% } %>
</div>
<div style="height:30px; clear:both;"></div>

<jsp:include flush="true" page="../index/foot.jsp"></jsp:include>

</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="platformWeb.base.WebConstant, commonTool.constant.CommonConstant"%>
<bean:define id="jsSuffix" name="shopForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="categoryid" name="shopForm" property="categoryid" type="java.lang.Long"/>
<bean:define id="categoryname" name="shopForm" property="categoryname" type="java.lang.String"/>
<bean:define id="newscategoryid" name="shopForm" property="newscategoryid" type="java.lang.Long"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
<bean:define id="localeid" name="shopForm" property="queryVO.localeid" type="java.lang.Long"/>

<%  
   Long listTabID = 2l;
   if(newscategoryid==null) { newscategoryid=listTabID; } 
   int rows = -1;
   String shopStr = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<html:base />

<title>公司列表</title>
<html:base />
<link rel="stylesheet" type="text/css" href="../styles/css/banner.css" />
<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
<link rel="stylesheet" type="text/css" href="../styles/css/list.css" />
	<style type="text/css">
	<!--
	
	#menu5{
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
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="../styles/script/pageAction.js"></script>
    <script language=JavaScript src="../styles/script/commonlogic.js"></script>
    <script language=JavaScript src="../styles/script/utiltool.js"></script>
	<script type='text/javascript' src='/<%=CommonConstant.WebContext_Education %>/dwr/interface/prodcateshop.js'></script>
	<script type='text/javascript' src='/<%=CommonConstant.WebContext_Education %>/dwr/engine.js'></script>

</head>

<body>

<div id="content">

	<jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
    <input type="hidden" id="Id_newscategoryid_original" value="<%=newscategoryid %>"/>
	<div class="navlistBar">
	    <a href="javascript:void(0)" onclick="switchCategory(this);return false;" title="点击更换目录"><%=categoryname %></a> &gt; 培训公司
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;height: 25px;">
	    <form action="listShopIndex.do" method="post">
	       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryid %>"/>
	       <input type="hidden" id="Id_categoryname" name="categoryname" value="<%=categoryname %>"/>
	       <input type="hidden" id="Id_newscategoryid" name="newscategoryid" value="<%=CommonConstant.SearchTabPK %>"/>
	       <input type="text" name="searchtext" style="width:300px;height: 20px;" value="<bean:write name="shopForm" property="searchtext"/>" /> 
	       &nbsp;搜索类型:
	       <html:select name="shopForm" property="searchshoptype">
	           <html:option value="1">商店名称</html:option>
	           <html:option value="2">商店代码</html:option>
	       </html:select>
	       &nbsp;商店所属类型:
	       <html:select property="queryVO.ownertype" name="shopForm">
	          <html:option value="">全部商店</html:option>
	          <html:optionsSaas consCode="platform.ShopConstant.OwnerType"/>
	       </html:select>
	       &nbsp;&nbsp;&nbsp;
	       <button type="button" onclick="document.forms[0].submit();">搜索</button>
	    </form>
	</div>

	<div style="height:auto; width:100%;">
	  <ul class="tabnav">
	    <li <%if(listTabID.equals(newscategoryid)) {out.print("class='selectTab'"); }%> ><a href="listShopIndex.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=listTabID.toString() %>">公司列表</a></li>
		<li <%if(CommonConstant.SearchTabPK.equals(newscategoryid)){out.print("class='selectTab'"); }%> ><a href="listShopIndex.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=CommonConstant.SearchTabPK %>">搜索结果</a></li>
	  </ul>
	</div>

    <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	</div>
     
    <div style="width: 100%;margin-bottom: 10px;">
	    <table class="listDataTable" >
	        <thead>
	        <tr class="listDataTableHead">
	           <td>公司名称</td>
	           <td>公司所属类型</td>
	           <td width="30%">培训课程目录</td>
	           <td>所在地</td>
	        </tr>
	        </thead>
	        <tbody>
	        <logic:present name="pageList">
		    <logic:iterate id="vo" name="pageList" indexId="ind" type="platform.vo.Shop">
	        <tr>      
	           <td>
	               <a href="javascript:void(0);" onclick="window.open('/<%=CommonConstant.WebContext_Education %>/shop/toShop.do?shopid=<bean:write name="vo" property="shopid"/>');return false;"><bean:write name="vo" property="shopname"/></a>
	               <br/>(<bean:write name="vo" property="shopcode"/>)
	           </td>      
	           <td><bean:writeSaas name="vo" property="ownertype" consCode="platform.ShopConstant.OwnerType"/></td>
	           <td id="cateTdId<%=ind %>"></td>
	           <td>&nbsp;</td>
	        </tr>
	        <% rows = ind; shopStr=shopStr+vo.getShopid()+",";  %>
	        </logic:iterate>
	        </logic:present>
	        </tbody>
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
<div style="height:20px; clear:both;"></div>

<jsp:include flush="true" page="../index/foot.jsp"></jsp:include>

  <script language=JavaScript>
	 <!--
       window.onload=function(){
            var rowsVar = <%=rows %>;
            prodcateshop.getShopCategoryNames('<%=shopStr %>', <%=localeid %>,
                function CB_check(cateArr){
                   if(cateArr!=null&&cateArr.length>0){
		              for(var temp=0;temp<=rowsVar;temp++){
		                  document.getElementById("cateTdId"+temp).innerHTML = cateArr[temp];
		              }
		           }
                }
            );
	   }
     //-->
  </script>

</body>
</html>

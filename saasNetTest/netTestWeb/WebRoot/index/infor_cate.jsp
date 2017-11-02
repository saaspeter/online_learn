<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant, netTestWeb.base.KeyInMemoryConstant"%>
<bean:define id="jsSuffix" name="infonewsForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="newscategoryid" name="infonewsForm" property="newscategoryid" type="java.lang.Long"/>
<bean:define id="newscategoryname" name="infonewsForm" property="newscategoryname" type="java.lang.String"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<html:base />
<%
   Long categoryid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
   String categoryidStr = (categoryid==null)?"":categoryid.toString();
   String categoryname = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
%>
<title><bean:message key="netTest.page.common.title"/></title>
<html:base />
<link rel="stylesheet" type="text/css" href="../styles/css/list.css" />
<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/tab/simpleTab2.css" />
<style type="text/css">
<!--

#menu2{
  display: block;
  color: #667;
  background-color: #ec8;
}

#displayMessDiv{
    color:#FF0000;
    background-color:#EEEEEE;
}

-->
</style>
<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
<script type="text/javascript" src="../styles/script/pageAction.js"></script>
<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
<script type="text/javascript" src="../styles/script/utiltool.js"></script>

<script type="text/javascript">
   function removeNews(url, ord){     
      var rtnobj = toAjaxUrl(url);
      if(rtnobj!=null){
         showMessagePage(getMessage(rtnobj.info));
         if(rtnobj.result=='1'){
            var ulobj = document.getElementById("articleUL");
            ulobj.removeChild(ulobj.childNodes[ord]);
         }
      }
   }
   
   function goPageParent(pageIndex, totalnum)
   {
      <%if(CommonConstant.SearchTabPK.equals(newscategoryid)){ %>
	  document.getElementById("pageIndexId").value = pageIndex;
	  document.getElementById("totalDataNumberId").value = totalnum;
	  document.getElementById("listFormId").submit();
	  <%}else { %>
	  var url = "listInfonews.do?categoryid=<%=categoryidStr %>&newscategoryid=<%=newscategoryid %>&pageIndex="+pageIndex+"&totalDataNumber="+totalnum;
	  goUrl(url);
	  <%} %>
   }
</script>
</head>

<body>

<div class="col-xs-12 col-md-9 center-block">

	<jsp:include flush="true" page="banner.jsp"></jsp:include>
	
	<input type="hidden" id="Id_newscategoryid_original" value="<%=newscategoryid %>"/>

	<div class="navlistBar">
	    <a href="javascript:void(-1);" onclick="switchCategory(this);return false;" title="点击更换目录">
	    <%if(categoryname==null||"null".equals(categoryname)){ %>
	         <bean:message key="netTest.commonpage.category.nullcategoryname"/>
	    <%}else { out.print(categoryname); } %>
	    </a> &gt; 咨询信息
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;height: 25px;padding: 5px;">
	    <form id="listFormId" action="listInfonews.do" method="get">
	       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryidStr %>"/>
	       <input type="hidden" id="Id_newscategoryid" name="newscategoryid" value="<%=CommonConstant.SearchTabPK %>"/>
	       <input type="text" name="queryVO.searchcontent" class="searchTxt_second" value="<bean:write name="infonewsForm" property="queryVO.searchcontent" />"/> 
	       <input type="hidden" id="pageIndexId" name="pageIndex"/>
	       <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
	       <button type="button" onclick="document.forms[0].submit();" class="searchBtn_second">搜索</button>
	    </form>
	</div>

	<div style="height:auto; width:100%;padding: 5px;">
	  <ul class="tabnav">
		<logic:present name="infonewsForm" property="tablist">
		<logic:iterate id="tabcateVo" name="infonewsForm" property="tablist" type="platform.vo.Newscategory">
		   <li <%if(tabcateVo.getId().equals(newscategoryid)){out.print("class='selectTab'"); newscategoryname=tabcateVo.getName(); }%> ><a href="listInfonews.do?categoryid=<%=categoryidStr %>&categoryname=<%=categoryname %>&newscategoryid=<%=tabcateVo.getId() %>"><%=tabcateVo.getName() %></a></li>
		</logic:iterate>
		</logic:present>
		<li <%if(CommonConstant.SearchTabPK.equals(newscategoryid)){out.print("class='selectTab'"); }%> ><a href="javascript:void(0)" onclick="document.getElementById('listFormId').submit();return false;">搜索结果</a></li>
	  </ul>
	</div>
 
    <div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	</div>
        
    <div style="width: 100%;margin-bottom: 10px;">
       <ul id="articleUL" class="articleList">
	      <logic:present name="pageList">
		  <logic:iterate id="vo" name="pageList" indexId="ind">
	         <li><a href="javascript:void(-1);" onclick="window.open('viewInfonews.do?categoryid=<%=categoryidStr %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&newscategoryname=<%=newscategoryname %>&queryVO.id=<bean:write name="vo" property="id"/>&formobile=0');"><bean:write name="vo" property="caption"/></a><bean:write name="vo" property="createtime" format="yyyy-MM-dd"/></li>
	      </logic:iterate>
	      </logic:present>
	      <logic:notPresent name="pageList">
	        <div style="height:20em;padding:3em;">
	           <%if(categoryid==null){ %>
	           <span class="alertFont">请点击选择产品目录链接查询</span>
	           <%} %>
	        </div>
	      </logic:notPresent>
       </ul>
    </div>

    <jsp:include flush="true" page="/pubs/footpage.jsp"></jsp:include>
    <jsp:include flush="true" page="../foot.jsp"></jsp:include>
</div>

</body>
</html>

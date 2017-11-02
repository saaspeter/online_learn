<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<bean:define id="categoryid" name="infonewsForm" property="categoryid" type="java.lang.Long"/>
<bean:define id="categoryname" name="infonewsForm" property="categoryname" type="java.lang.String"/>
<bean:define id="newscategoryid" name="infonewsForm" property="newscategoryid" type="java.lang.Long"/>
<bean:define id="newscategoryname" name="infonewsForm" property="newscategoryname" type="java.lang.String"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>咨询信息</title>
<html:base />
<link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/banner.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/tab/simpleTab2.css" />

<style type="text/css">
<!--

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

<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>

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
</script>
</head>

<body>

<div id="content">

	<jsp:include flush="true" page="banner.jsp"></jsp:include>

	<div class="navlistBar">
	    <a href="javascript:void(0)" onclick="switchProduct(this);" title="点击更换目录"><%=categoryname %></a> &gt; 咨询信息
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;height: 25px;">
	    <input type="text" style="width:300px;height: 20px;"/> <a href="javascript:void(0)" onclick="switchProduct(this);" title="点击更换目录"><%=categoryname %></a>&nbsp;&nbsp;&nbsp;<button>搜索</button>
	</div>

	<div style="height:auto; width:100%;">
	  <ul class="tabnav">
		<logic:present name="infonewsForm" property="tablist">
		<logic:iterate id="tabcateVo" name="infonewsForm" property="tablist" type="platform.vo.Newscategory">
		   <li <%if(tabcateVo.getId().equals(newscategoryid)){out.print("class='selectTab'"); newscategoryname=tabcateVo.getName(); }%> ><a href="listInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=tabcateVo.getId() %>"><%=tabcateVo.getName() %></a></li>
		</logic:iterate>
		</logic:present>
		<li><a href="">搜索结果</a></li>
	  </ul>
	</div>
 
    <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	</div>
        
    <div style="width: 100%;margin-bottom: 10px;">
       <ul id="articleUL" class="articleList">
	      <logic:present name="pageList">
		  <logic:iterate id="vo" name="pageList" indexId="ind">
	         <li><a href="javascript:void(-1);" onclick="window.open('viewInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&newscategoryname=<%=newscategoryname %>&queryVO.id=<bean:write name="vo" property="id"/>&formobile=0');"><bean:write name="vo" property="caption"/></a><bean:write name="vo" property="createtime" format="yyyy-mm-dd"/>&nbsp;&nbsp;
	         </li>
	      </logic:iterate>
	      </logic:present>
       </ul>
    </div>
</div>

<div style="height:15px; clear:both;"></div>
<div style="text-align: center">
    <%if(totalPage>0){ %>
    <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_back.gif" alt="previous page" border=0/></a>       
   <% for(int i=currentPage;i<totalPage+1;i++){ %>
        <a href="<%=WebConstant.WebContext %>/index/listInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&pageIndex=<%=i %>" style="padding: 3px; margin: 3px;text-decoration: none; border: solid 1px #51A6BB;"><%=i %></a> 
   <% } %>
   <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_next.gif" alt="next page" border=0/></a>
   <% } %>
</div>
<div style="height:15px; clear:both;"></div>

<jsp:include flush="true" page="../foot.jsp"></jsp:include>

</body>
</html>

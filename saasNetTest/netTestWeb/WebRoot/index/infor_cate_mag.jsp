<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant"%>
<bean:define id="jsSuffix" name="infonewsForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="categoryid" name="infonewsForm" property="categoryid" type="java.lang.Long"/>
<bean:define id="categoryname" name="infonewsForm" property="categoryname" type="java.lang.String"/>
<bean:define id="newscategoryid" name="infonewsForm" property="newscategoryid" type="java.lang.Long"/>
<bean:define id="newscategoryname" name="infonewsForm" property="newscategoryname" type="java.lang.String"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<html:base />

<title>咨询信息</title>
<html:base />
<link rel="stylesheet" type="text/css" href="../styles/css/list.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/tab/simpleTab2.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/list_style.css" />
<style type="text/css">
<!--

#menu2{
  display: block;
  color: #667;
  background-color: #ec8;
}

#content {
    width:1000px;
    left: 50%;
	position:relative;
	clear: both;
	margin-left: -500px;
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
<script type="text/javascript" src="../styles/script/movediv.js"></script>

<script type="text/javascript">
   function removeNews(url, ord){
      if(confirm(getMessage("confirmDeleteMsg"))){
          var rtnobj = toAjaxUrl(url);
	      if(rtnobj!=null){
	         showMessagePage(getMessage(rtnobj.info));
	         if(rtnobj.result=='1'){
	            var ulobj = document.getElementById("articleUL");
	            ulobj.removeChild(ulobj.childNodes[ord]);
	         }
	      }
      }
   }
   
   function deleteNewsCate(){
      var rtnobj = toAjaxUrl('../news/deleteNewscategory.do?vo.id=<%=newscategoryid %>');
      if(rtnobj!=null){
         alert(rtnobj.info);
         if(rtnobj.result=='1'){
            goUrl('listInfonewsMag.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>');
         }
      }
   }
   
   function editCatePage(){
	   <%if(newscategoryid!=null&&!CommonConstant.SearchTabPK.equals(newscategoryid)){ %>
	   newDiv('../news/editNewscategory.do?vo.id=<%=newscategoryid %>',1,1,460,400);
	   <%}%>
   }
   
   function goPage(pageIndex)
   {
      <%if(CommonConstant.SearchTabPK.equals(newscategoryid)){ %>
	  document.getElementById("pageIndex_id").value = pageIndex;
	  document.getElementById("listFormId").submit();
	  <%}else { %>
	  var url = "listInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&pageIndex="+pageIndex;
	  goUrl(url);
	  <%} %>
   }

</script>
</head>

<body>

<div id="content">
	
	<input type="hidden" id="Id_newscategoryid_original" value="<%=newscategoryid %>"/>

	<div class="navlistBar">
	    <a href="javascript:void(-1);" onclick="switchCategory(this);return false;" title="点击更换目录">
	    <%if(categoryname==null||"".equals(categoryname)){ %>
	         <bean:message key="netTest.commonpage.category.nullcategoryname"/>
	    <%}else { out.print(categoryname); } %>
	    </a> &gt; 咨询信息
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;height: 25px;">
	    <form id="listFormId" action="listInfonewsMag.do" method="get">
	       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryid %>"/>
	       <input type="hidden" id="Id_categoryname" name="categoryname" value="<%=categoryname %>"/>
	       <input type="hidden" id="Id_newscategoryid" name="newscategoryid" value="<%=CommonConstant.SearchTabPK %>"/>
	       <input type="text" name="queryVO.searchcontent" class="searchTxt_second" value="<bean:write name="infonewsForm" property="queryVO.searchcontent" />"/> 
	       <input type="hidden" id="pageIndex_id" name="pageIndex"/>
	       <button type="button" onclick="document.forms[0].submit();" class="searchBtn_second">搜索</button>
	    </form>
	</div>

	<div style="height:auto; width:100%;">
	  <ul class="tabnav">
		<logic:present name="infonewsForm" property="tablist">
		<logic:iterate id="tabcateVo" name="infonewsForm" property="tablist" type="platform.vo.Newscategory">
		   <li <%if(tabcateVo.getId().equals(newscategoryid)){out.print("class='selectTab'"); newscategoryname=tabcateVo.getName(); }%> ><a href="listInfonewsMag.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=tabcateVo.getId() %>"><%=tabcateVo.getName() %></a></li>
		</logic:iterate>
		</logic:present>
		<li <%if(CommonConstant.SearchTabPK.equals(newscategoryid)){out.print("class='selectTab'"); }%> ><a href="listInfonewsMag.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=CommonConstant.SearchTabPK %>">搜索结果</a></li>
		
		<li>&nbsp;<img src="../styles/imgs/add.png" style="cursor:pointer;" title="新增类别" onclick="newDiv('../news/addNewscategory.do?vo.categoryid=<%=categoryid %>',1,1,460,400);" /></li>
		<li>&nbsp;<img src="../styles/imgs/edit.png" style="cursor:pointer;" title="编辑当前类别" onclick="editCatePage();" /></li>
		<li>&nbsp;<img src="../styles/imgs/delete.png" style="cursor:pointer;" title="删除当前类别" onclick="deleteNewsCate();return false;"/></li>
	  </ul>
	</div>

    <div style="padding-left: 75%;"><button type="button" onclick="goUrl('addInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&newscategoryname=<%=newscategoryname %>');return false;">新增资讯信息</button></div>
 
    <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	</div>
        
    <div style="width: 100%;margin-bottom: 10px;">
       <ul id="articleUL" class="articleList">
	      <logic:present name="pageList">
		  <logic:iterate id="vo" name="pageList" indexId="ind">
	         <li><a href="javascript:void(-1);" onclick="window.open('viewInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&newscategoryname=<%=newscategoryname %>&queryVO.id=<bean:write name="vo" property="id"/>&formobile=0');"><bean:write name="vo" property="caption"/></a><bean:write name="vo" property="createtime" format="yyyy-MM-dd"/>&nbsp;&nbsp;
	             <img src="../styles/imgs/edit.png" alt="编辑" style="cursor:pointer;" onclick="goUrl('editInfonews.do?categoryid=<%=categoryid %>&categoryname=<%=categoryname %>&newscategoryid=<%=newscategoryid %>&newscategoryname=<%=newscategoryname %>&queryVO.id=<bean:write name="vo" property="id"/>');return false;"/>&nbsp;
	             <img src="../styles/imgs/delete.png" alt="删除" style="cursor:pointer;" onclick="removeNews('deleteInfonews.do?vo.id=<bean:write name="vo" property="id"/>','<%=ind %>');return false;"/>
	         </li>
	      </logic:iterate>
	      </logic:present>
       </ul>
    </div>
</div>

<div style="text-align: center;margin-top: 15px;margin-bottom: 20px;">
    <% if(totalPage>0){ %>
    <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_back.gif" title="previous page" border=0/></a>       
    <% for(int i=1;i<totalPage+1;i++){ 
           if(i==currentPage){ %>
              <font style="font-weight: bold;"><%=i %></font>
           <% }else { %>
                <a href="javascript:goPage(<%=i %>);" class="pageNumberNormal"><%=i %></a> 
    <%     }
       } %>
    <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_next.gif" title="next page" border=0/></a>
    <% } %>
</div>

<jsp:include flush="true" page="../foot.jsp"></jsp:include>

</body>
</html>

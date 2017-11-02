<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><bean:message key="netTest.page.common.title"/></title>
<link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/banner.css" />
<link href="styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<style type="text/css">
<!--

	#menu1{
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
	
	#rightAD {
		float:right;
		clear:right;
		margin-top:5em;
		background-color:#FFFFFF;
	}
	
	#maincontent{
	   width: 70%;
	   height: auto;
	   float:right;
	   text-align: left;
	   padding: 30px 10px 10px 10px;
	}
	
	#leftbar{
	   width:30%;
	   margin: 0px;
	   height:670px;
	   float:left;
	}
	
	#first {
		
	}
	
	#first div.off {
		height:33px;
		background:url(<%=WebConstant.WebContext %>/styles/imgs/tab/tabs_0.gif) repeat-x bottom;
		border:1px #cccccc solid;
		
		float:left;
		cursor:pointer;
		border-bottom-color:#000;
		line-height:32px;
		z-index:40;
		position:relative;
		text-align: center;
	}
	
	#first div.on {
		height:33px;
		position:relative;
		padding-top:1px;
		background:url(<%=WebConstant.WebContext %>/styles/imgs/tab/tabs_2.gif) repeat-x bottom;
		float:left;
		cursor:pointer;
		border:1px #cccccc solid;
		
		border-bottom:0;
		z-index:100;
		line-height:33px;
		text-align: center;
	}
	
	div.hide {
		width:0px;
		display:none;
		overflow:hidden;
	}
	
	div.show {
		clear:left;
		width:100%;
		height:90%;
		padding:5px;
		top:-1px;
		position:relative;
		border:1px solid #cccccc;
		z-index:50;
		overflow:auto;
	}
	
-->
</style>

<script type="text/javascript">
   function onclickTree(id,name){     
      switchCategory_CB(id, name);
   }
   
   function switchCategory_CB(id, name){
      if(id==null||id==""||id=='-1'){
         return;
      }
      document.location.href = '<%=WebConstant.WebContext %>/shopping/searchProductList.do?categoryid='+id+'&categoryname='+name;
   }
   
   function switchTabCate(idon, idoff){
      document.getElementById(idon).className="on";
      document.getElementById(idon+"_cont").className="show";
      document.getElementById(idoff).className="off";
      document.getElementById(idoff+"_cont").className="hide";
   }
</script>
</head>

<body>

<div class="col-xs-12 col-md-9 center-block">

<jsp:include flush="true" page="banner.jsp"></jsp:include>

<div style="height:10px; clear:both;"></div>

<div style="height:auto; width:100%; text-align: left">
	<div id="leftbar">
	     <div id="first">
	        <div class="on" style="width:50%" id="simpletab1" onclick="switchTabCate('simpletab1', 'simpletab2');return false;">热门目录</div>
			<div class="off" style="width:50%" id="simpletab2" onclick="switchTabCate('simpletab2', 'simpletab1');return false;">全部目录</div>
		 </div>
		 <div class="show" id="simpletab1_cont">
	         <iframe frameborder="0" width="100%" height="100%" src="<%=WebConstant.WebContext %>/productcategory/listHotcategory.do?tourl=<%=WebConstant.WebContext %>/index/listInfonews.do" scrolling="auto"></iframe>
	     </div>
		 <div class="hide" id="simpletab2_cont">
	         <iframe frameborder="0" width="100%" height="100%" src="<%=WebConstant.WebContext %>/productcategory/toprodcategorytreepage.do" scrolling="auto"></iframe>
	     </div>
	</div>
    <div id="maincontent">
       <div style="clear: both;width: 100%;padding-top: 50px;">
           <form action="<%=WebConstant.WebContext %>/commonsearch.do">
               <input type="hidden" name="categoryname" value="<%=request.getParameter("categoryname") %>"/>
               <input type="hidden" name="categoryid" value="<%=request.getParameter("categoryid") %>"/>
               <input type="hidden" name="searchType" value="product"/>
	           <div style="width: 100%;text-align: center;">
	              <input type="text" name="searchInput" class="searchTxt" placeholder="你想学习什么"/><button type="submit" class="btn btn-success" style="font-size: x-large;" aria-label="Left Align"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
	           </div>
           </form>
           <div style="height: 1.5em"></div>
           <div>
           推荐课程:  <logic:present name="productForm" property="productList">
             <logic:iterate id="vo" name="productForm" property="productList">
                 <a href="javascript:void(0)" onclick="window.open('<%=WebConstant.WebContext %>/product/productDetailFromIndex.jsp?shopid=<bean:write name="vo" property="shopid"/>&productid=<bean:write name="vo" property="productbaseid"/>&urlType=2','productviewpage');"><bean:write name="vo" property="productname"/></a> &nbsp;
             </logic:iterate>
             </logic:present>
           </div>
       </div>
       <hr />
       <div class="container" style="width: 100%;padding-top: 3em;">
           <div class="row">
               <div class="col-md-4">
                   <img class="" src="styles/imgs/learn.jpg" alt="Generic placeholder image" style="width: 140px; height: 140px;"/>
                   <h2>自由学习</h2>
                   <p>您可以自由选择课程学习，通过视频、音频、文档等在线学习，随时提问等待老师解答，每章节可以通过练习巩固知识点，可以参加考试测试学习效果</p>
                   <p><a class="btn btn-default" href="about/aboutsite.jsp" role="button">More »</a></p>
               </div>
               <div class="col-md-4">
                   <img class="" src="styles/imgs/teach.jpg" alt="Generic placeholder image" style="width: 140px; height: 140px;"/>
                   <h2>自由授课</h2>
                   <p>任何人可以建立自己的学校，发布自己的课程授课。轻松为课程添加视频、音频、文档、练习、考试等内容，同您的学生互动。您可以选择课程收费或免费</p>
                   <p><a class="btn btn-default" href="about/aboutsite.jsp" role="button">More »</a></p>
               </div>
               <div class="col-md-4">
                   <img class="" src="styles/imgs/enterprise_learn.jpg" alt="Generic placeholder image" style="width: 140px; height: 140px;"/>
                   <h2>机构企业</h2>
                   <p>丰富的企业机构功能，能满足公司机构内部学习要求。单位管理、人员管理；各下属单位可以独立组织学习评测；可以选择课程对内开放或对整个互联网开放</p>
                   <p><a class="btn btn-default" href="about/aboutsite.jsp" role="button">More »</a></p>
               </div>
           </div>
       </div>
    </div>
</div>

<jsp:include flush="true" page="/foot.jsp"></jsp:include>
</div>

</body>
</html>

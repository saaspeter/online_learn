<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.PayTypeConstant,commonTool.constant.CommonConstant,netTest.product.constant.ProductConstant" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String shopidVar = request.getParameter("shopid");
%>
<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
<script type="text/javascript" src="../styles/script/pageAction.js"></script>
<script type="text/javascript" src="../styles/script/xtree.js"></script>
<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
<script type="text/javascript">
function linkUrl(url,id){
    if(typeof(tree)!='undefined'&&tree.getSelected()!=null){
       tree.getSelected().deSelect();
    }
    document.getElementById("bestsellId").className="";
    document.getElementById("freeId").className="";
    document.getElementById(id).className="selectA";
	var iframe = document.getElementById("contentIframeId");
	iframe.src = url;
    
}

</script>

<style type="text/css">
<!--

.selectA{
   display: block;
   color: selectedtext;
   background: selected;
   background-color:#CCCCFF;
}

#leftbar{
   width:100%;
   height:490px;
   float:left;
   border-right: 1px solid #CCCCCC;
}

#leftbar ul {
   margin:0px;
}
#leftbar ul li {
	clear:left;
	margin:0px;
}

-->
</style>

<input type="hidden" id="rightUrl" value="/<%=CommonConstant.WebContext_Education%>/product/listshopopenproduct.do?queryVO.shopid=<%=shopidVar%>&queryVO.categoryid=">
	<div id="leftbar">
	    <div style="height:60%">
	       <div class="titleBarTop">课程分类</div>
	       <div>
	          <ul>
	             <li><a id="bestsellId" href="javascript:void(0);" onclick="linkUrl('/<%=CommonConstant.WebContext_Education%>/product/listshopopenproduct.do?queryVO.promotable=<%=ProductConstant.Promotable_yes%>&queryVO.shopid=<%=shopidVar %>&title=推荐课程','bestsellId');return false;" style="display: block">推荐课程</a></li>
	             <li><a id="freeId" href="javascript:void(0);" onclick="linkUrl('/<%=CommonConstant.WebContext_Education %>/product/listshopopenproduct.do?queryVO.paytype=<%=PayTypeConstant.PayType_free %>&queryVO.shopid=<%=shopidVar %>&title=免费课程','freeId');return false;" style="display: block">免费课程</a></li>
	          </ul>
	       </div>
	       
	       <div id="treeDiv">
			   <script type="text/javascript">
					var treeUrl = "/<%=CommonConstant.WebContext_Education %>/productcategory/shoprodcateTree.do?vo.shopid=<%=shopidVar %>&vo.pid=";
					var tree = new WebFXLoadTree("<%=CommonConstant.TreeTopnodePid.toString() %>","所有课程",treeUrl+'<%=CommonConstant.TreeTopnodePid.toString() %>');
					document.write(tree);
					//tree.select();
					
					function linkAction(){
					   var selectid = tree.getSelected().pk;
					   var selectname = tree.getSelected().text;
					   
					   document.getElementById("bestsellId").className="";
    				   document.getElementById("freeId").className="";
					   var url = document.getElementById("rightUrl").value+selectid+'&title='+selectname;
					   var iframe = document.getElementById("contentIframeId");
					   iframe.src = url;
					}
					
				</script>
			</div>
	    </div>

	</div>

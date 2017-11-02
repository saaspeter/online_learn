<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <bean:define id="childsize" name="prodcateshopForm" property="childsize" type="java.lang.Integer"></bean:define>
    <html:base />
    <%String shopidVar = request.getParameter("vo.shopid"); %>
    <title>产品目录树</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link type="text/css" rel="stylesheet" href="../styles/css/xtree.css" />
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	   <!--
	     .{
		    margin:0px;
		 }
		 #displayMessDiv{
	        color:#FF0000;
	        background-color:#EEEEEE;
	     }
	   -->
	</style>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/xtree.js"></script>
	<script type="text/javascript" src="../styles/script/xmlextras.js"></script>
	<script type="text/javascript" src="../styles/script/xloadtree.js"></script>
	<script type="text/javascript">
	    function selectCate_CB(ids,names,param){
	        if(ids!=null&&ids!=""){
	           var url = "/productcategory/saveProdcateshop.do?categoryidStr="+ids;
	           goUrl(url);
	        }
        }
        
        function manageCate(type){
           var parentmain = parent.document.getElementById("catetreeTdId");
           var parentFrame = parent.document.getElementById("catetreeFrameId");
           
           if(type=='1'){
              //parentmain.cols="400,*";
              parentmain.style.width="450px";
              parentFrame.style.width="450px";
              document.getElementById("magCateBtnId").style.display="none";
              document.getElementById("newCateBtnId").style.display="";
              document.getElementById("delCateBtnId").style.display="";
              document.getElementById("magBackBtnId").style.display="";
           }else {
              //parentmain.cols="130,*";
              parentmain.style.width="200px";
              parentFrame.style.width="200px";
              document.getElementById("magCateBtnId").style.display="";
              document.getElementById("newCateBtnId").style.display="none";
              document.getElementById("delCateBtnId").style.display="none";
              document.getElementById("magBackBtnId").style.display="none";
           }
        }
        
	</script>
  </head>
  <%
     String righturl = "/product/listshopproductmag.do?queryVO.categoryid=";
   %>
  <body style="overflow: auto;">
    <%if(childsize>0){ %>
    <input type="hidden" id="rightUrl" value="<%=righturl %>" />
	<div id="funDiv">
	    <button class="btn btn-info" id="magCateBtnId" onclick="manageCate('1');return false;">管理目录</button>
	    <button class="btn btn-info" id="newCateBtnId" onclick="newDiv('<%=WebConstant.WebContext %>/productcategory/selprodcategorytreepage.do?selectType=2',1,1,300,400);" style="display: none;">新增目录</button>
	    <button class="btn btn-info" id="delCateBtnId" onclick="deleteCate();return false;" style="display: none;">删除目录</button>
	    <button class="btn btn-info" id="magBackBtnId" onclick="manageCate('2');return false;" style="display: none;">&lt;&lt;&lt;</button>
	</div>
	
	<div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
    </div>
	
	<div id="treeDiv">
	<script type="text/javascript">
		var treeUrl = "<%=WebConstant.WebContext %>/productcategory/shoprodcateTree.do?vo.shopid=<%=shopidVar %>&vo.pid=";
		var tree = new WebFXLoadTree("<%=CommonConstant.TreeTopnodePid.toString() %>","所有目录",treeUrl+'<%=CommonConstant.TreeTopnodePid.toString() %>');
		document.write(tree);
		tree.select();
		
		function linkAction(){
		   showRightFromTree(tree.getSelected().pk,tree.getSelected().text);
		}
		
		function deleteCate(){
           var categoryid = tree.getSelected().pk;
           var catename = tree.getSelected().text;
           if(categoryid==null||categoryid=='-1'||categoryid==''){
              alert('请选择要删除的目录');
              return;
           }
           if(confirm('确定要删除目录: '+catename+' ?')){
           	   var url = '<%=WebConstant.WebContext %>/productcategory/deleteProdcateshop.do?vo.categoryid='+categoryid;
               goUrl(url);
           }
        }
		
	</script>
	</div>
	<%} else{%>
	<div id="treeDiv2">
	<br><br><br><br><br><br><br>
	<font color="#ff0000">还未定义商店课程目录, 点击</font><p>
	<button type="button" class="button green" onclick="newDiv('/productcategory/selprodcategorytreepage.do?selectType=2',1,1,300,400);">新增课程目录</button>
	</div>
	<%} %>
	<script type="text/javascript">
	 <!--
	 <%if(childsize<1){ %>
         parent.document.getElementById("catetreeTdId").style.width="450";
         parent.document.getElementById("catetreeFrameId").style.width="450";
     <%}else { %>
         parent.document.getElementById("catetreeTdId").style.width="200";
         parent.document.getElementById("catetreeFrameId").style.width="200";
     <%} %>
     //-->
    </script>
  </body>
</html:html>

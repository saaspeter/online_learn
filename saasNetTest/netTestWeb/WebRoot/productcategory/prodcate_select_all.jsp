<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <title>产品目录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
	<style type="text/css">
		<!--
	body {
	   margin:0px;
	   overflow-x:auto;
	   overflow-y:auto;
	}

	#leftbar{
	   width:100%;
	   height:460px;
	   float:left;
	}
	
	#first {
	
	}
	
	#first div.off {
		height:33px;
		background:url('<%=WebConstant.WebContext %>/styles/imgs/tab/tabs_0.gif') repeat-x bottom;
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
		background:url('<%=WebConstant.WebContext %>/styles/imgs/tab/tabs_2.gif') repeat-x bottom;
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
		width:330px;
		height:100%;
		padding:0px;
		margin: 0px;
		top:-1px;
		position:relative;
		border:1px solid #cccccc;
		z-index:50;
		overflow:auto;
	}
	
	.clear {
		clear:both;
	}
		
	-->
	</style> 

    <script type="text/javascript">
	   function onclickTree(id,name){     
	      parent.switchCategory_CB(id, name);
	   }
	   
	   function switchCategory_CB(id, name){
		   parent.switchCategory_CB(id, name);
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

    <div id="leftbar">
	     <div id="first">
	        <div class="on" style="width:120px" id="simpletab1" onclick="switchTabCate('simpletab1', 'simpletab2');return false;">热门目录</div>
			<div class="off" style="width:120px" id="simpletab2" onclick="switchTabCate('simpletab2', 'simpletab1');return false;">全部目录</div>
		 </div>
		 <div class="show" id="simpletab1_cont" style="width:99%;">
		     <iframe frameborder="0" width="99%" height="99%" src="<%=WebConstant.WebContext %>/productcategory/listHotcategory.do" scrolling="auto"></iframe>
	     </div>
		 <div class="hide" id="simpletab2_cont" style="width:99%;">
	         <iframe frameborder="0" width="99%" height="99%" src="<%=WebConstant.WebContext %>/productcategory/toprodcategorytreepage.do" scrolling="auto"></iframe>
	     </div>
	</div>
  </body>
</html:html>

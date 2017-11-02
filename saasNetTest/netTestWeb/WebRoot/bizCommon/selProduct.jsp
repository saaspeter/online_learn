<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,netTest.product.vo.Productbase"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <%
       String actionType = request.getParameter("actionType");
       String showAllProd = request.getParameter("showAllProd");
       if(actionType==null||actionType.trim().length()<1){
          actionType = "1"; // 1: flush parent page, 2: just select product
       }
       List<Productbase> prodList = (List<Productbase>)request.getAttribute("prodList");
    %>
    <title>课程列表</title>
    <style >
        *{margin:0;}
    </style>
    <script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript">
	    var dataArr = new Array();
	    <% int i = 0;
	       if("1".equals(showAllProd)){
	          i = 1; 
	    %>
	          dataArr[0] = ['-1', '所有可用课程'];
	    <% }
	       if(prodList!=null)
	       for(Productbase prod : prodList){ %>
	          dataArr[<%=i %>] = ['<%=prod.getProductbaseid() %>', '<%=prod.getProductname() %>'];
	       <% i++; }%>
		
	    function backCall(id,name){
	       <%if("1".equals(actionType)){ %>
           parent.switchProcut_CB(id,name);
           <%}else { %>
           parent.selProduct_CB(id,name);
           <%} %>
        }
	       
	</script>
  </head>
  
  <body style="margin: 0px;">
  <div><input id="indexInput" style="width: 100%;" value="" onkeyup="TipInputList.changeselect('indexInput', 'divresults', dataArr, 1, 'backCall');" ></div>
  <div id="divresults" style="margin:0px;width: 100%;padding: 0px;"></div>
    <script type="text/javascript">
	 <!--
       window.onload=function(){
		   TipInputList.initDropList('indexInput', 'divresults');
		   TipInputList.changeselect('indexInput', 'divresults', dataArr, 1, 'backCall');
		   document.getElementById("indexInput").focus();
	   };
     //-->
    </script>
  </body>
</html:html>

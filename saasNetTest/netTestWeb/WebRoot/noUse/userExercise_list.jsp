<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="exeruserForm" property="jsSuffix" type="java.lang.String"/>
	<bean:define id="productId" name="exeruserForm" property="sessionProductid" type="java.lang.Long"/>
	<bean:define id="productName" name="exeruserForm" property="sessionProductname" type="java.lang.String"/>
    <bean:define id="contentcateidVar" name="exeruserForm" property="queryVO.contentcateid" type="java.lang.Long"/>
    <title>练习列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<style type="text/css">

		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	
	</style>
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript">
		function gochapter(productid, contentcateid){
	    	document.getElementById("contentcateidId").value = contentcateid;
	    	document.getElementById("list").submit();
	    }
	</script>
  </head>

  <body>
  <div id="centerContent">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  <html:form styleId="list" action="/exercise/listUserExer.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="exeruserForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="exeruserForm" property="backUrlEncode"/>">
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=productId %>" >
  <input id="prodnameId" type="hidden" name="productname" value="">
  <input id="contentcateidId" type="hidden" name="queryVO.contentcateid" value="<%=contentcateidVar %>">
    
  <div class="navlistBar">
        学习考试&nbsp;&gt;&gt;&nbsp;我的练习
        (&nbsp;课程:<a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_userNormal %>');" title="点击切换课程"><bean:write name="exeruserForm" property="sessionProductname"/></a>&nbsp;)
  </div>
  
  <div style="float: left; width:200px;height:80%;border:0;border-right:1px solid; margin: 1px;padding:1px;overflow: auto;">
     <iframe frameborder="0" style="width:198px;height: 98%" src='${pageContext.request.contextPath}<%="/learncont/listcontentsidebar.do?productbaseid="+productId+"&queryVO.contentcateid="+contentcateidVar %>'></iframe>
  </div>
  
  <div style="float: left; width:786;border: 0;margin: 2px;overflow: auto;">
	   <div style="height:auto;">
		  <ul class="tabnav">
		    <li><a href="../product/listcoursepost.do?vo.productbaseid=<%=productId %>">信息台</a></li>
		    <li><a href="../learncont/selfLearncontent.do?productbaseid=<%=productId %>">课程资料</a></li>
			<li class='selectTab'><a href="javascript:void(0)">课程练习</a></li>
			<li><a href="../exam/listTestinfouser.do?productbaseid=<%=productId %>&contentcateid=<bean:write name="exeruserForm" property="queryVO.contentcateid"/>&contentcatename=<bean:write name="exeruserForm" property="queryVO.contentcatename"/>">考试信息</a></li>
		   </ul>
		</div>
	
	  <div class="dashLine"></div>
	  
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	
	  <div id="DataTableDiv">
	    <table id="dataTableId" class="listDataTable" >
	      <thead>
	      <tr class="listDataTableHead">
	        <td width="10px"></td>
	        <td>练习名</td>
	        <td>练习状态</td>
	        <td>操作</td>
	      </tr>
	      </thead>
	      <tbody>
	      <logic:present name="exeruserForm" property="datalist">
		  <logic:iterate id="vo" name="exeruserForm" property="datalist" indexId="ind">
	      <tr id='<%="dataTrId"+ind %>' class='<%=(ind%2==0)?"oddRow":"evenRow" %>' >
	        <td></td>
	        <td>
	            <bean:write name="vo" property="exername"/>
	        </td>
	        <td><bean:writeSaas name="vo" property="status" consCode="netTest.ExerUser.Status"/></td>
	        <td>
	            <a href="#" onclick="openWin('/exercise/beforeDoExercise.do?exerid=<bean:write name="vo" property="exerid"/>',750,550,'yes','yes');return false;">进入测试</a>
	        </td>
	      </tr>
	      </logic:iterate>
	      </logic:present>
	      </tbody>
	    </table>
	  </div>
  </div>
  
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </div>
  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </body>
</html:html>

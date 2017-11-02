<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.constant.UserproductConstant, netTest.exam.constant.TestuserConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="jsSuffix" name="testuserForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="productId" name="testuserForm" property="sessionProductid" type="java.lang.Long"/>
<%String contentcateidStr = (request.getParameter("contentcateid")==null)?"":request.getParameter("contentcateid"); 
  String contentcatenameStr = (request.getParameter("contentcatename")==null)?"":request.getParameter("contentcatename");
  String productIdStr = (productId==null)?"":productId.toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>考试列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">

		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	
	</style>
	
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	<script type="text/javascript">

        function enter(testid){
            goUrl("/exam/enterTestHall.do?vo.testid="+testid);
        }
        
	</script>
  </head>

  <body>
  <div class="col-xs-12 col-md-9 center-block">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  <html:form styleId="list" action="/exam/listTestinfouser.do" method="post">
  <input id="shopidId" type="hidden" name="shopid" value='<bean:write name="testuserForm" property="sessionShopid"/>' >
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=productIdStr %>" >

  <div class="navlistBar">
        学习考试&nbsp;&gt;&gt;&nbsp;我的考试
      (课程:<a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_userNormal %>');" title="点击切换课程"><bean:write name="testuserForm" property="sessionProductname"/></a>&nbsp;)
  </div>
  
  <div style="height:auto; width:100%;">
	  <ul class="tabnav">
	    <li><a href="<%=request.getContextPath() %>/product/listcoursepost.do?vo.productbaseid=<%=productIdStr %>">课程通知</a></li>
	    <li><a href="<%=request.getContextPath() %>/learncont/selfLearncontent.do?productbaseid=<%=productIdStr %>">学习课程</a></li>
		<li class='selectTab'><a href="javascript:void(0)">考试信息</a></li>
		<li><a href="<%=request.getContextPath() %>/product/listOpenactivity.do?showtype=4&queryVO.productid=<%=productIdStr %>">公开活动</a></li>
	  </ul>
  </div>
  
  <div id="firstLineDiv">
      <div id="searchDiv">考试名称: 
		<input type="text" name="queryVO.testname" value='<bean:write name="testuserForm" property="queryVO.testname"/>'/>
		&nbsp;考试阶段:
		<html:select name="testuserForm" property="queryVO.roughteststatus">
	       <html:option value="<%=String.valueOf(TestuserConstant.RoughTestStatus_upcoming) %>">需要参加的考试</html:option>
		   <html:option value="<%=String.valueOf(TestuserConstant.RoughTestStatus_finish) %>">已结束的考试</html:option>
        </html:select>
        
        <input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="10px"></td>
        <td>考试名</td>
        <td>考试时间</td>
        <td>考试科目</td>
        <td>考试状态</td>
        <td>我的状态</td>
        <td>操作</td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="testuserForm" property="datalist">
	  <logic:iterate id="vo" name="testuserForm" property="datalist" indexId="ind">
      <tr class='<%=(ind%2==0)?"oddRow":"evenRow" %>'>
        <td>
        
        </td>
        <td><a href="javascript:void(0)" onclick="enter('<bean:write name="vo" property="testid"/>');return false;"><bean:write name="vo" property="testname"/></a>
            <input id='pkId<bean:write name="vo" property="testid"/>Name' type="hidden" value='<bean:write name="vo" property="testname"/>'/>
        </td>
        <td><bean:write name="vo" property="teststartdate" format="yyyy-MM-dd HH:mm:ss"/>
            <br>--<bean:write name="vo" property="testenddate" format="yyyy-MM-dd HH:mm:ss"/>
        </td>
        <td><bean:write name="vo" property="productname"/>
        </td>
        <td><bean:writeSaas name="vo" property="teststatus" consCode="netTest.TestinfoConstant.Teststatus"/></td>
        <td><bean:writeSaas name="vo" property="status" consCode="netTest.TestuserConstant.status"/></td>
        <td>
            <a href="javascript:void(0)" onclick="enter('<bean:write name="vo" property="testid"/>');return false;">进入考场</a>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
      </tbody>
    </table>
      <logic:empty name="testuserForm" property="datalist">
      <div style="height:200px;"></div>
      </logic:empty>
  </div>
  </html:form>
  </div>
    <div style="height:15px; clear:both;"></div>
	
	<jsp:include flush="true" page="/foot.jsp"></jsp:include>
  </div>
  
  <script type="text/javascript">
	 <!--
	   function qrycollideTest(){
	       var shopidVar = '<bean:write name="testuserForm" property="queryVO.shopid"/>';
	       var useridVar = '<bean:write name="testuserForm" property="queryVO.userid"/>';
	       testuserLogic.qryTestCollideUser(shopidVar,useridVar,
               function CB_qry(rtnStr){
	              if(rtnStr!=null&&rtnStr!=''){
	                 document.getElementById("collideInfoId").innerHTML=
	                      "<a style=\"color: red;\" href=\"#\" onclick=\"showCollideMess('"+rtnStr+"');return false;\">现有考试时间有冲突!</a>";
	              }
	           }
     	   );
	   }
	   
	   function showCollideMess(mess){
	       mess = '时间有冲突的考试：'+mess;
	       showMessagePage(mess);
	   }
	   
       window.onload=function(){
	       //qrycollideTest();
	   };
     //-->
  </script>
  
  </body>
</html:html>

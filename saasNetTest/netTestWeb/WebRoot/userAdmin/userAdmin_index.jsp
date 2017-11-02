<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.UsernotificationConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <html:base />
    <title><bean:message key="netTest.page.common.title"/></title>
    <bean:size id="messsize" name="userserviceForm" property="notifyList"/>
    <bean:define id="userhasproduct" name="userserviceForm" property="hasproduct" type="java.lang.Boolean"></bean:define>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	    #bannermenu1{
		   display: block;
		   color: #667;
		   background-color: #ec8;
		}
		
	</style>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
    <script type="text/javascript">
	    function shownotification(notifyid, openurltype) {
	       if(notifyid==null||notifyid==''){
             return;
           }
           var url = '/userAdmin/readnotification.do?vo.notifyid='+notifyid+'&backUrlEncode=';
           if(openurltype=='<%=UsernotificationConstant.OpenlinkType_NewDiv %>'){
              newDiv(url,0,1,600,400);
           }else {
              goUrl(url,'backUrlEncode');
           }
	    }
	</script>
</head>
<body>

<div class="col-xs-12 col-md-9 center-block">

	<jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>

	<div class="navlistBar" style="margin:12px 0px 12px 0px">
	    我正在<a href="<%=request.getContextPath() %>/product/myUserproduct.do" class="messLink">学习<bean:write name="userserviceForm" property="mylearnprodnum"/>门课程</a>;&nbsp;&nbsp;
	    我<a href="<%=request.getContextPath() %>/shop/myOwnShops.do?messagekey=netTest.UserServiceAction.TipMess.createShopInHomePage" class="messLink" title='<bean:message key="netTest.UserServiceAction.TipMess.createShopInHomePage"/>'>直接管理<bean:write name="userserviceForm" property="myadminprodnum"/>门课程</a>;&nbsp;&nbsp;
	    我想<a href="<%=request.getContextPath() %>/shop/myOwnShops.do?messagekey=netTest.UserServiceAction.TipMess.createCourseInHomePage" class="messLink" title='<bean:message key="netTest.UserServiceAction.TipMess.createCourseInHomePage"/>'>开设一门新课程</a>;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <a href="<%=request.getContextPath() %>/product/listMyOpenactivity.do" class="messLink" title='<bean:message key="netTest.UserServiceAction.TipMess.createCourseInHomePage"/>'>我参加的公开活动</a>;&nbsp;&nbsp;
    </div>

    <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	</div>
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userserviceForm" property="backUrlEncode"/>">
    <div style="width:100%; margin-bottom:10px; ">
        <table style="width:100%;">
            <tr style="vertical-align: top;">
                <td style="width:35%">
                    <div class="titleBarTop" style="width: 96%">最近考试</div>
                    <div style="margin-bottom: 10px;">
                       <logic:present name="userserviceForm" property="testList">
				       <ul id="articleUL" class="articleList">
			              <logic:iterate id="testVO" name="userserviceForm" property="testList">
			                 <li><a href="javascript:void(0);" onclick="goUrl('/exam/enterTestHall.do?vo.testid=<bean:write name="testVO" property="testid"/>');return false;">
			                        <bean:write name="testVO" property="testname"/><br>
					                (<bean:write name="testVO" property="teststartdate" format="MM-dd HH:mm"/>
					                 -- <bean:write name="testVO" property="testenddate" format="MM-dd HH:mm"/>)
			                 </a></li>
			              </logic:iterate>
				       </ul>
				       </logic:present>
				    </div>
                </td>
                <td style="width:35%">
                    <div class="titleBarTop" style="width: 96%">最近课程</div>
                    <div style="margin-bottom: 10px;">
                       <logic:present name="userserviceForm" property="productList">
				       <ul id="articleUL" class="articleList">
			              <logic:iterate id="prodVO" name="userserviceForm" property="productList">
			                 <li><a href="void(0);" onclick="goUrl('/product/listcoursepost.do?vo.productbaseid=<bean:write name="prodVO" property="productbaseid"/>');return false;">
			                        <bean:write name="prodVO" property="productname"/>
			                     </a>
			                 </li>
			              </logic:iterate>
				       </ul>
				       </logic:present>
				       <%if(!userhasproduct){ %>
				       <br><br><br><br><br>
				       <a href="<%=WebConstant.WebContext %>/shopping/searchProductList.do">您目前没有学习任何课程，请前往系统首页选修课程</a>
				       <%} %>
				    </div>
                </td>
                <td style="width:30%">
                    <div class="titleBarTop">我的消息(<%=messsize %>)
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <a href="<%=WebConstant.WebContext %>/userAdmin/listnotification.do">更多消息</a>
                    </div>
                    <div style="margin-bottom: 10px;">
                       <logic:present name="userserviceForm" property="notifyList">
				       <ul id="articleUL" class="articleList">
			              <logic:iterate id="notifyVO" name="userserviceForm" property="notifyList">
			                 <li><a href="javascript:void(0);" style="color:red" onclick="shownotification('<bean:write name="notifyVO" property="notifyid"/>','<bean:write name="notifyVO" property="openlinktype"/>');return false;">
			                         <bean:write name="notifyVO" property="messcode"/>
			                     </a></li>
			              </logic:iterate>
				       </ul>
				       </logic:present>
				    </div>
                </td>
            </tr>
        </table>
    </div>
    
    <div style="height:30px; clear:both;"></div>
	<jsp:include flush="true" page="/foot.jsp"></jsp:include>
</div>

</body>
</html>
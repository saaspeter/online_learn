<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant,commonTool.constant.PayTypeConstant,netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">

  <head>
    <html:base />
    <bean:define id="listType" name="userproductForm" property="listType" type="java.lang.Integer"></bean:define>
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
    <title>用户课程列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	
	<style type="text/css">
		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	</style>
	
	<script type="text/javascript">
	    function switchproductview(selObj){
	       if(selObj==null){
             return;
          }
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue!="2"){ return; }
          
          goUrl('/userproduct/listmyendprodlog.do');
	    }
	    
	    function gotolearn(productbaseid, status){
	    	if(status=='<%=UserproductConstant.Status_active %>'){
	    		goUrl('#/<%=CommonConstant.WebContext_Education %>/product/listcoursepost.do?vo.productbaseid='+productbaseid);
	    	}else {
	    		alert('课程失效，暂时不能学习!');
	    	}
	    }
	
	</script>
	
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>

  <div class="listPage">
  <html:form styleId="list" action="/product/myUserproduct.do" method="post">
  <input id="shopidId" type="hidden" name="shopid" value="<bean:write name="userproductForm" property="sessionShopid"/>" >
  
  <div class="navlistBar">
        学习考试&nbsp;&gt;&gt;&nbsp;我的课程
        (&nbsp;商店:<a href="javascript:void(0)" onclick="switchShop(this);" title="点击切换商店"><bean:write name="userproductForm" property="sessionShopname"/></a>
         <img src="../styles/imgs/mail_send.png" style="cursor: pointer;width: 16px;" title="send message to shop admin" onclick="newDiv('/userAdmin/addnotification.do?objectadmintype=shop&objectid=<bean:write name="userproductForm" property="sessionShopid"/>',0,1,550,350);return false;"/>
        )
  </div>
  
  <div id="firstLineDiv">
      <div id="functionButtonDiv">
          <button type="button" onclick="openWin('/shopping/searchProductList.do')" style="margin-left: 30px;" class="btn btn-success">我要选课</button>
      </div>
  
      <div id="searchDiv">
		查看类型:
		<select name="selectproducttype" id="selectproducttypeId" class="select_second" onchange="switchproductview(this);">
		   <option value="1" selected="selected">正在使用的课程</option>
		   <option value="2">学习结束的课程</option>
		</select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable">
      <thead>
      <tr class="listDataTableHead">
        <td>课程</td>
        <td>使用方式</td>
        <td>学习时间</td>
        <td>状态</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.product.vo.Userproduct">
	      <tr>
	          <td>
	            <a href="jascript:void(0)" title="进入课程学习" onclick="gotolearn('<%=vo.getProductbaseid() %>','<%=vo.getStatus() %>');return false;">
	            <bean:write name="vo" property="productname"/></a>&nbsp;
	            <img title="课程详细信息" src="../styles/imgs/details.png" style="border: 0;cursor: pointer;" onclick="newDiv('/userproduct/userproduct_view_main.jsp?showtype=product&userproductid=<bean:write name="vo" property="userproductid"/>',0,1,600,400);"/>
                &nbsp;
	            <img src="../styles/imgs/mail_send.png" style="cursor: pointer;width: 20px;" title="send message to product admin" onclick="newDiv('/userAdmin/addnotification.do?objectadmintype=product&objectid=<bean:write name="vo" property="userproductid"/>',0,1,550,350);return false;"/>
	          </td>
	          <td>
	              <%if(UserproductConstant.ProdUseType_operatorMag.equals(vo.getProdusetype())){ %>
	                     管理该产品
	              <%}else { %>
		              <bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/><br>
		              <font style="font-size: smaller">
		              <%if(PayTypeConstant.PayType_useday_month.equals(vo.getPaytype())
		                ||(PayTypeConstant.PayType_useday_somedays.equals(vo.getPaytype()))){ %>
		                (&nbsp;
		                  使用时间:<bean:write name="vo" property="startdate" format="yyyy-MM-dd"/> -- <bean:write name="vo" property="enddate" format="yyyy-MM-dd"/>
		                 &nbsp;)
		              <%} else if(PayTypeConstant.PayType_usetimes_sometimes.equals(vo.getPaytype())){ %>
		                (&nbsp;
		                  共:<bean:write name="vo" property="status"/>次, 剩余:<%=vo.getUselimitnum()-vo.getUsenum() %>次
		                 &nbsp;)
		              <%} %></font>
	              <%} %>
	          </td>
	          <td>&nbsp;
	              <logic:present name="vo" property="lastaccesstime"> 
	                   上次学习时间<br>
	                  <bean:write name="vo" property="lastaccesstime" format="yyyy-MM-dd HH:mm"/>
	              </logic:present>
	          </td>
	          <td>
	              <a href="jascript:void(0)" onclick="newDiv('/userproduct/userproduct_view_main.jsp?showtype=log&userproductid=<bean:write name="vo" property="userproductid"/>',0,1,600,400);return false;">
	                 <bean:writeSaas name="vo" property="status" consCode="UserProduct.status"/>
	              </a>
	              <br>
	              <font style="font-size: smaller">(<bean:writeSaas name="vo" property="statusrank" consCode="netTest.UserProduct.statusrank"/>)</font>
	          </td>
	      </tr>
	      
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </div>
  <div style="height:15px; clear:both;"></div>
  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </body>
</html:html>

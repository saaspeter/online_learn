<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userprodbuylogForm" property="jsSuffix" type="java.lang.String"/>
    <title>学员产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    function switchproductview(selObj){
	       if(selObj==null){
             return;
          }
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue!="1"){ return; }
          
          goUrl('/product/myUserproduct.do');
	    }
	</script>

  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  <html:form styleId="list" action="/userproduct/listmyendprodlog.do" method="post">
  
  <div class="navlistBar">
        学习考试&gt;&gt;使用到期的课程
        (&nbsp;所在商店:<a href="javascript:void(0)" onclick="switchShop(this);" title="点击切换商店"><bean:write name="userprodbuylogForm" property="sessionShopname"/></a>)
  </div>

  <div id="firstLineDiv">
      <div id="searchDiv">
          查看类型:
		<select name="selectproducttype" class="select_second" onchange="switchproductview(this);">
		   <option value="1">正在使用的课程</option>
		   <option value="2" selected="selected">学习结束的课程</option>
		</select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv" >
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td>课程</td>
        <td>到期时间</td>
      </tr>
	  <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td>
            <a href="jascript:void(0)" onclick="newDiv('/userproduct/userproduct_view_main.jsp?showtype=log&userproductid=<bean:write name="vo" property="userproductid"/>',0,1,600,400);return false;">
               <bean:write name="vo" property="productname"/>
            </a>
        </td>
        <td>
            <bean:write name="vo" property="happendate" format="yyyy-MM-dd"/>
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
  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.exam.constant.TestinfoConstant,netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="testinfoForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="language" name="testinfoForm" property="language" type="java.lang.String"/>
    <bean:define id="productId" name="testinfoForm" property="sessionProductid" type="java.lang.Long"/>
    <bean:define id="productName" name="testinfoForm" property="sessionProductname" type="java.lang.String"/>
    <title>考试列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<link rel="stylesheet" type="text/css" href="../styles/calendar/JSCal2/src/css/jscal2.css"/>
	<link rel="stylesheet" type="text/css" href="../styles/calendar/JSCal2/src/css/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="../styles/calendar/JSCal2/src/css/gold/gold.css" />
	
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type="text/javascript" src="../styles/calendar/JSCal2/src/js/jscal2.js"></script>
    <script type="text/javascript" src="../styles/calendar/JSCal2/src/js/lang/<%=language %>.js"></script>
	<script type="text/javascript">
	    function selOrg(){
	        var url = "/org/selectorgtree.do?selectType=1";
	      	newDiv(url,1,1,300,400);
	    }
	    
	    function selectOrg_CB(ids,names,param){
	         if(ids!=null&&ids!=""){
	            document.getElementById("createorgidId").value=ids;
	            document.getElementById("createorgnameId").value=names;
	         }
	         clearDiv();
        }
        
        function delTestinfo(testid,teststatus){
            if(teststatus!='<%=TestinfoConstant.Teststatus_unStart %>' 
               &&teststatus!='<%=TestinfoConstant.Teststatus_allChecked %>'
               &&teststatus!='<%=TestinfoConstant.Teststatus_openExam %>')
            {
               alert('正在考试中或正在阅卷中，不能删除!');
               return;
            }else if(confirm('确定要删除该场考试及其相关信息?')){
               var url = '/exam/deleteTestinfo.do?vo.testid='+testid;
               goUrl(url);
            }
        }
	    
	</script>
  </head>
    <%if(productId==null){
       productName = "所有可用产品";
    } %>
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exam/listTodoTestinfo.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="testinfoForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="testinfoForm" property="backUrlEncode"/>">
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=(productId==null)?"":productId.toString() %>">
  <input id="prodnameId" type="hidden" name="productname" value="">
  <div class="navlistBar">
        <a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_operatorMag %>','1');" title="点击选择产品"><%=productName %></a> &gt; 考试管理
  </div>
  
  <div style="height:auto; width:100%;">
     <ul class="tabnav">
	    <li class='selectTab'><a href="javascript:void(0)">需要管理的考试</a></li>
		<li><a href="listTestinfo.do?productbaseid=<%=productId %>">查询所有考试</a></li>
	 </ul>
  </div>
  
  <div id="firstLineDiv">
      <div id="searchDiv">
         开始时间:
		<input id="teststartdateId" type="text" name="queryVO.teststartdate" value="<bean:write name="testinfoForm" property="queryVO.teststartdate" format="yyyy-MM-dd HH:mm:ss"/>" style="width: 10em;"/>
		结束时间:
		<input id="testenddateId" type="text" name="queryVO.testenddate" value="<bean:write name="testinfoForm" property="queryVO.testenddate" format="yyyy-MM-dd HH:mm:ss"/>" style="width: 10em;"/>
		&nbsp;考试状态:
		<html:select name="testinfoForm" property="queryVO.teststatuspage">
	       <html:option value=""></html:option>
		   <html:optionsSaas consCode="netTest.TestinfoConstant.Teststatusshow" removeStr="<%=TestinfoConstant.Teststatus_openExam.toString() %>"/>
        </html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <button type="button" style="margin-left:30px;" class="button green fontBold" onclick="addRecord('/exam/addTestinfo.do');return false;">新增考试</button>
		  
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
        <td style="max-width: 150px;">考试状态</td>
        <td>操作</td>
      </tr>
      </thead>
      <logic:present name="testinfoForm" property="testinfoList">
	  <logic:iterate id="vo" name="testinfoForm" property="testinfoList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td><a href="javascript:void(0)" title="查看考试详情" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="vo" property="testid"/>');return false;"><bean:write name="vo" property="testname"/></a>
            <input id='pkId<bean:write name="vo" property="testid"/>Name' type="hidden" value="<bean:write name="vo" property="testname"/>"/>
            <img title="基本信息" src="../styles/imgs/detail.png" style="width:22px;border: 0;cursor: pointer;" onclick="newDiv('/exam/viewTestinfo.do?queryVO.testid=<bean:write name="vo" property="testid"/>',0,1,600,460);return false;"/>
        </td>
        <td><bean:write name="vo" property="teststartdate" format="yyyy-MM-dd HH:mm"/>&nbsp;--&nbsp;<br/>
	        <bean:write name="vo" property="testenddate" format="yyyy-MM-dd HH:mm"/></td>
        <td><bean:writeSaas name="vo" property="teststatus" consCode="netTest.TestinfoConstant.Teststatus"/></td>
        <td>
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delTestinfo('<bean:write name="vo" property="testid"/>','<bean:write name="vo" property="teststatus"/>');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script type="text/javascript">
	 <!-- 
       window.onload=function(){
	        Calendar.setup({
		       inputField     :    "teststartdateId",      // id of the input field
		       trigger        :    "teststartdateId",
		       dateFormat     :    "%Y-%m-%d %H:%M:00",
		       showTime       :    true,
		       onSelect       : function() { this.hide()}
		    });
		    
		    Calendar.setup({
		       inputField     :    "testenddateId",      // id of the input field
		       trigger        :    "testenddateId",
		       dateFormat     :    "%Y-%m-%d %H:%M:00",
		       showTime       :    true,
		       onSelect       : function() { this.hide()}
			});
	   }
     //-->
  </script>
  </body>
</html:html>

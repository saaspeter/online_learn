<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.UserproductConstant,netTest.exam.constant.TestinfoConstant" %>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="checkPaperForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productId" name="checkPaperForm" property="sessionProductid" type="java.lang.Long"/>
    <bean:define id="productName" name="checkPaperForm" property="sessionProductname" type="java.lang.String"/>
    
    <title>评阅考试列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	            
        function checkTest(testid,teststatus){
            if(teststatus!=11){
               alert('只有考试状态为手动评阅时才可以阅卷');
               return;
            }
            goUrl('/exam/manaualCheckPaper.do?testVO.testid='+testid);
        }
	    
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exam/listTodoTestinfo.do" method="post">
  <input id="prodidId" type="hidden" name="productbaseid" value='<%=(productId==null)?"":productId.toString() %>'>
  <input id="prodnameId" type="hidden" name="productname" value="">
  <div class="navlistBar">
        <a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_operatorMag %>');" title="点击选择产品"><%=productName %></a> &gt; 评阅试卷
  </div>
  <div id="firstLineDiv">
      <div id="searchDiv">考试状态: 
		<html:select name="checkPaperForm" property="queryVO.teststatus">
	       <html:option value=""></html:option>
		   <html:optionsSaas consCode="netTest.TestinfoConstant.Teststatus" removeStr="1,3,7,13,15"/>
        </html:select>
        <input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
	  </div>
  </div>

  <div id="functionLineDiv"></div>

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
        <td>操作</td>
      </tr>
      </thead>
      <logic:present name="checkPaperForm" property="checktestList">
	  <logic:iterate id="vo" name="checkPaperForm" property="checktestList" indexId="ind">
      <tr class='<%=(ind%2==0)?"oddRow":"evenRow" %>'>
        <td></td>
        <td><bean:write name="vo" property="testname"/></td>
        <td><bean:write name="vo" property="teststartdate" format="yyyy-MM-dd HH:mm"/><br/>
            --&nbsp;<bean:write name="vo" property="testenddate" format="yyyy-MM-dd HH:mm"/>
        </td>
        <td><bean:write name="vo" property="productname"/></td>
        <td><bean:writeSaas name="vo" property="teststatus" consCode="netTest.TestinfoConstant.Teststatus"/></td>
        <td>
            <a href="#" onclick="checkTest('<bean:write name="vo" property="testid"/>','<bean:write name="vo" property="teststatus"/>');return false;">
               <logic:equal name="vo" property="teststatus" value="<%=TestinfoConstant.Teststatus_ended.toString() %>">自动评阅客观题目</logic:equal>
               <logic:equal name="vo" property="teststatus" value="<%=TestinfoConstant.Teststatus_manualChecking.toString() %>">手动评阅试卷</logic:equal>            
            </a>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>

  </body>
</html:html>

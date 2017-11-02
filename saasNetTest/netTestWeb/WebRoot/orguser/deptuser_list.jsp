<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.constant.ShopConstant, platform.constant.CustomerConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="orguserForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="orgIdVar" name="orguserForm" property="queryVO.orgbaseid" type="java.lang.Long"/>
    <bean:define id="orgnameVar" name="orguserForm" property="orgVO.orgname" type="java.lang.String"/>
    <bean:define id="shopchargetype" name="orguserForm" property="shopchargetype" type="java.lang.Short"/>
    <title>机构人员</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/userproduct.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	<script type="text/javascript">
	   // 供点击数时调用的方法，如果没够该方法，则调用默认地址
	   function showRight(id){
	      var url = "<%=WebConstant.WebContext %>/orguser/listorguser.do?queryVO.orgbaseid="+id;
	      document.location.href=url;
	   }
	   	   
	   function selectOrg_CB(ids,names,param){
          if(ids!=null&&ids!=""){
             goUrl('/orguser/transferorguser.do?neworgid='+ids+"&userid="+param+"&backUrlEncode=","backUrlEncode");
          }
          clearDiv();
       }
       	   
	   function checkdelUser(userid, shopid){
	      if(userid!=null&&userid!=''&&shopid!=null&&shopid!=''){
	         userproduct.checkUserProdLink(userid,shopid,function CB_checklink(checkResult){
	            if(checkResult=="1"){
		            var names = document.getElementById("user["+userid+"]Name").value;
		            alert(getMessage("deptuser_list_user")+":"+names+" "+getMessage("deptuser_list_checkuserProd"));
		        }else{
		            deleteSingleRec("/orguser/delUserDept.do?userid="+userid);
		        }
	         }
	         );
	      }
	   }
	   
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/orguser/listorguser.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="orguserForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="orguserForm" property="backUrlEncode"/>">
  <input type="hidden" name="queryVO.orgbaseid" value="<bean:write name="orguserForm" property="queryVO.orgbaseid"/>">
  <input id="newDeptid" type="hidden" name="neworgid" >
  <input id="productStrtId" type="hidden" name="productStr" >
  <input id="userprodstatId" type="hidden" name="userprodstat" >
  <input id="order_By_Clause" type="hidden" name="order_By_Clause" value="<bean:write name="orguserForm" property="queryVO.order_By_Clause"/>">
  <div id="firstLineDiv">
      <div id="searchDiv">用户名: 
		<input type="text" name="queryVO.nickname" value="<bean:write name="orguserForm" property="queryVO.nickname"/>"/>&nbsp;
		查询范围:
		<html:select name="orguserForm" property="queryVO.isIncludeChild">
            <html:option value="1">不查询下级单位</html:option>
            <html:option value="2">查询下级单位</html:option> 
        </html:select>&nbsp;
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/orguser/adddeptuserPage.do?vo.orgbaseid=<%=orgIdVar %>&vo.orgname=<%=orgnameVar %>');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
		     <%if(ShopConstant.ChargeType_paid.equals(shopchargetype)){ %>
		     <li><button type="button" onclick="goUrl('/orguser/toimportorguser.do?vo.orgbaseid=<%=orgIdVar %>&vo.orgname=<%=orgnameVar %>');return false;">导入</button></li>
		     <%} %>
		  </ul>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
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
        <td>商店中名称</td>
        <td>帐号</td>
        <td>所在单位</td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="index">
      <tr class="<%=(index%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td><bean:write name="vo" property="nickname"/></td>
        <td><bean:write name="vo" property="loginname"/>
        	<logic:equal name="vo" property="stagestatus" value="<%=String.valueOf(CustomerConstant.StageStatus_notSetLoginName) %>">
        	   <br/>(<bean:writeSaas name="vo" property="stagestatus" consCode="CustomerConstant.StageStatus"/>)
        	</logic:equal>
            <input type="hidden" id="user<%=index %>Pk" value="<bean:write name="vo" property="orguserid"/>">
            <input type="hidden" id="user[<bean:write name="vo" property="userid"/>]Name" value="<bean:write name="vo" property="loginname"/>">
        </td>
        <td><bean:write name="vo" property="orgname"/></td>
        <td><img src="../styles/imgs/edit.png" title="编辑" style="cursor:pointer;" onclick="goUrl('editdeptuserPage.do?editType=<%=WebConstant.editType_edit %>&queryVO.orguserid=<bean:write name="vo" property="orguserid"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
            <img src="../styles/imgs/transfer.png" title="人员转单位" style="cursor:pointer;" onclick="newDiv('/org/selectorgtree.do?param=<bean:write name="vo" property="userid"/>',1,1,250,400);return false;"/>&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="checkdelUser('<bean:write name="vo" property="userid"/>','<bean:write name="vo" property="shopid"/>');return false;"/>&nbsp;
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

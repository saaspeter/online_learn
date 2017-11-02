<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.UsernotificationConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="usernotificationForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="showusertype" name="usernotificationForm" property="showusertype" type="java.lang.Integer"/>
    <title><bean:message key="netTest.page.common.title"/>-消息列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	    #bannermenu7{
		   display: block;
		   color: #667;
		   background-color: #ec8;
		}
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    function shownotification(notifyid, openurltype) {
	       if(notifyid==null||notifyid==''){
             return;
           }
           var url = '/userAdmin/readnotification.do?vo.notifyid='+notifyid;
           if(openurltype=='<%=UsernotificationConstant.OpenlinkType_NewDiv %>'){
              newDiv(url,0,1,600,400)
           }else {
              goUrl(url);
           }
	    }
	</script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  <html:form styleId="list" action="/userAdmin/listnotification.do" method="post">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="usernotificationForm" property="backUrlEncode"/>">
  
  <div class="navlistBar">
        我的首页&gt;&gt;消息列表
  </div>

  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
         <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
         <td>
             <%if(showusertype==0){ %>收到消息列表<%}else { %>发送消息列表<%} %>&nbsp;&nbsp;TO: 
             <%if(showusertype==0){ %><img src="../styles/imgs/mail_send.png" style="cursor: pointer;vertical-align: bottom;" title="send out mails" onclick="goUrl('/userAdmin/listnotification.do?showusertype=1');return false;"/> <%} else { %>
                  <img src="../styles/imgs/mail_receive.png" style="cursor: pointer;vertical-align: bottom;" title="received mails" onclick="goUrl('/userAdmin/listnotification.do');return false;"/>
             <%} %>
             &nbsp;&nbsp;&nbsp;&nbsp;
             <button type="button" class="button green fontBold" onclick="delRec('list','keys','/userAdmin/deletenotification.do');">&nbsp;&nbsp;
             <bean:message key="netTest.commonpage.delete"/>&nbsp;&nbsp;</button> (删除后, 相关的发件人/收件人也看不到该消息)
         </td>
      </tr>
      </thead>
	  <logic:present name="usernotificationForm" property="volist">
	  <logic:iterate id="notifyVO" name="usernotificationForm" property="volist" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
         <td>
            <input type="checkbox" name="keys" id='pkId<bean:write name="notifyVO" property="notifyid"/>' value="<bean:write name="notifyVO" property="notifyid"/>" onClick="selectInfo(this,'clickedRow')">
         </td>
         <td>
            <a href="javascript:void(0);" onclick="shownotification('<bean:write name="notifyVO" property="notifyid"/>','<bean:write name="notifyVO" property="openlinktype"/>');return false;">
               <bean:write name="notifyVO" property="messcode" />
            </a>
            &nbsp;&nbsp;<logic:equal name="notifyVO" property="isread" value="<%=UsernotificationConstant.IsRead_NotRead.toString() %>">(<bean:writeSaas name="notifyVO" property="isread" consCode="Usernotification.IsRead"/>)</logic:equal>
            <span style="font-size: smaller;margin-left: 40px;"><bean:write name="notifyVO" property="createtime" format="yyyy-MM-dd HH:mm"/></span>
         </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  
  </html:form>
  </div>
  
  <div id="buttomDiv">&nbsp;</div>
  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </div>
  
  </body>
</html:html>

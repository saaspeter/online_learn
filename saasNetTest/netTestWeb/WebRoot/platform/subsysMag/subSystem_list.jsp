<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.productcategory.productcategory_list.jsp.title" bundle="platform.pagemess"/></title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../../styles/script/movediv.js"></script>
    <script language="javascript">
	<!--
       function doSelect(){
          var formObj = document.getElementById("list");
          var num = 0;
          var id = "";
          var name = "";
          var ids = "";
          var names = "";
          for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == "keys" && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				name = document.getElementById((formObj.elements[i].id+"name")).value;
				num++;
				ids = ids+id+",";
				names = names+name+";";
			}
		  }
		  if(num==0){
		     showMessagePage("请选择一个目录内容!");
		     return;
		  }else{
		     if(typeof(parent.selectSysCallBack)!="undefined"){
		        parent.selectSysCallBack(ids,names);
		     }
		  }
       }
       
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/subsysMag/listSubsys.do" method="post">
    
  <div id="firstLineDiv">
      
  </div>

  <div id="functionLineDiv">
      <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="doSelect();return false;">确定选择</button></li>
		     <li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		  </ul>
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
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>系统名称</td>
      </tr>
      </thead>
      <logic:present name="subsysmanageForm" property="subsysList">
	  <logic:iterate id="vo" name="subsysmanageForm" property="subsysList" indexId="index">
      <tr>
        <td><input type="checkbox" id="sys<%=index %>" name="keys" value="<bean:write name="vo" property="value"/>" ></td>
        <td>
            <bean:write name="vo" property="label"/>
            <input type="hidden" id="sys<%=index %>name" value="<bean:write name="vo" property="label"/>">
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

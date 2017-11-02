<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="prodUseForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="prodidStr" name="prodUseForm" property="prodidStr" type="java.lang.String"/>

    <title>产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type="text/javascript">
	    function backCall(id,name){
          parent.selUserProdCB(id,name);
        }
	
	    function doSelect(){
          var formObj = document.getElementById("list");
          var num = 0;
          var id = "";
          var name = "";
          for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == "keys" && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				name = document.getElementById((formObj.elements[i].id+"name")).value;
				num++;
                break;
			}
		  }
		  if(num==0){
		     showMessagePage(getMessage("mulSelUsr_list_selOneUser"));
		     return;
		  }else{
		     if(typeof(parent.selUserProdCB)!="undefined"){
		        document.frames[0].call(id,name);
		        backCall(id,name);
		     }
		  }
       }
       
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/common/qryCanuseProd.do" method="post">
  <input type="hidden" name="listType" value="1"/>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="doSelect();return false;">确定</button></li>
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
      <tr class="listDataTableHead">
        <td width="20px"></td>       
        <td>产品名称</td>
      </tr>

      <logic:present name="prodUseForm" property="prodList">
	  <logic:iterate id="vo" name="prodUseForm" property="prodList" indexId="index">
      <tr>
        <td><input type="radio" id="prod<%=index %>" name="keys" value="<bean:write name="vo" property="productbaseid"/>" onClick="selectInfo(this,'clickedRow')"></td>       
        <td><bean:write name="vo" property="productname"/>
            <input type="hidden" id="prod<%=index %>name" value="<bean:write name="vo" property="productname"/>">
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
   <!-- hidden div,when user changed product,it submit to change the value -->
   <div style="height:0px;display:none">
      <iframe id="iframeId" name="iframe" width="0px" height="0px" src="redirectOperduct.jsp"></iframe>
   </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
            <% if(prodidStr!=null&&prodidStr.trim().length()>0){
                  prodidStr = prodidStr+",";
                  prodidStr = ","+prodidStr; %>
                  var prodidStrVar = "<%=prodidStr %>";
                  var formObj = document.getElementById("list");
		          for(var i=0;i<formObj.elements.length;i++){
					 if(formObj.elements[i].name == "keys"){
						if(formObj.elements[i].value!=''
						   &&prodidStrVar.indexOf(","+formObj.elements[i].value+",")!=-1)
						{
						   formObj.elements[i].checked = true;
						   break;
						}

					 }
				  }
                  
            <% }
            %>
	   	    setListTableStyle();
	   }
     //-->
  </script>   
  </body>
</html:html>

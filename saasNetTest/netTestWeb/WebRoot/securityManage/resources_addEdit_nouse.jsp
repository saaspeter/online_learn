<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="v_rescId" name="resourcesForm" property="vo.id" type="java.lang.Long" ></bean:define>
<bean:define id="v_resType" name="resourcesForm" property="vo.resType" type="java.lang.String" ></bean:define>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>权限资源信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<style type="text/css">
	   .leftFieldTable{
	      text-align:right;
	      width:36%;
	   }
	</style>
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script language="javascript">
	<!--  
       function selectCallBack(ids,names){
          if(ids!=null&&ids!=""){
             var rescId = document.getElementById("rescId").value;
             if(ids==rescId){
                alert('不能选择本资源为上级资源!');
                return;
             }
             document.getElementById("Id_upId").value=ids;
             document.getElementById("Id_upIdName").value=names;
          }
          clearDiv();
       }
       
       function showHideMenu(selObj){ //v3.0
	     itemValue = selObj.options[selObj.selectedIndex].value;
         if(itemValue!=null&&itemValue=="MENU"){
            document.getElementById("menuDiv").style.display="block";
         }else{
            document.getElementById("menuDiv").style.display="none";
         }
	  }
	//-->
	</script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/securityManage/saveResource.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="resourcesForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="resourcesForm" property="backUrlEncode"/>">
	 <input type="hidden" id="rescId" name="vo.id" value="<bean:write name="resourcesForm" property="vo.id"/>">

	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="submitForm('editForm');">保存</button></li>
				 <li><button type="button" onclick="return false;">重置</button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;">返回</button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">权限资源信息</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">资源名:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="resourcesForm" property="vo.name"/>" size="50"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">资源类型:</div>
			   <div class="field"> 
			     <html:select styleId="resTypeId" property="vo.resType" name="resourcesForm" style="width:200px" onchange="showHideMenu(this)">
			         <html:option value="URL">链接地址类型</html:option>
			         <html:option value="MENU">菜单目录类型</html:option>
			         <html:option value="METHOD">逻辑方法类型</html:option>
			         <html:option value="TAG">页面标签类型</html:option>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">资源字符串:</div>
			   <div class="field"> 
			     <input type="text" name="vo.resString" value="<bean:write name="resourcesForm" property="vo.resString"/>" size="50"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">系统代码:</div>
			   <div class="field"> 
			     <html:select styleId="syscodeId" property="vo.syscode" name="resourcesForm" style="width:200px">
			         <html:option value="00000001">平台系统</html:option>
			         <html:option value="00000002">教育类型</html:option>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">上级资源:</div>
			   <div class="field"> 
			      <input type="text" id="Id_upIdName" value="<bean:write name="resourcesForm" property="vo.upIdName"/>" readonly="readonly"/>
			      <button type="button" onclick="newDiv('/securityManage/listRescForSingle.do',1,1,600,500);return false;">选择</button>
			      <input type="hidden" id="Id_upId" name="vo.upId" value="<bean:write name="resourcesForm" property="vo.upId"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">状态:</div>
			   <div class="field"> 
			     <html:select property="vo.status" name="resourcesForm" style="width:200px">
			         <html:option value="1">有效</html:option>
			         <html:option value="2">失效</html:option>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">资源描述:</div>
			   <div class="field"> 
			     <input type="text" name="vo.descn" value="<bean:write name="resourcesForm" property="vo.descn"/>" size="50"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  
      <!-- 菜单属性  -->
	  <div id="menuDiv" style="width:100%;clear:both;display:none">
            <span style="width:100%;text-align:center;background:#eeeeee">菜单属性</span>
            <table style="width:100%;">
                <tr>
                   <td class="leftFieldTable">title:</td>
                   <td><input type="text" name="vo.title" value="<bean:write name="resourcesForm" property="vo.title"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">target:</td>
                   <td><input type="text" name="vo.target" value="<bean:write name="resourcesForm" property="vo.target"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">onclick:</td>
                   <td><input type="text" name="vo.onclick" value="<bean:write name="resourcesForm" property="vo.onclick"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">onmouseover:</td>
                   <td><input type="text" name="vo.onmouseover" value="<bean:write name="resourcesForm" property="vo.onmouseover"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">onmouseout:</td>
                   <td><input type="text" name="vo.onmouseout" value="<bean:write name="resourcesForm" property="vo.onmouseout"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">image:</td>
                   <td><input type="text" name="vo.image" value="<bean:write name="resourcesForm" property="vo.image"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">altimage:</td>
                   <td><input type="text" name="vo.altimage" value="<bean:write name="resourcesForm" property="vo.altimage"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">tooltip:</td>
                   <td><input type="text" name="vo.tooltip" value="<bean:write name="resourcesForm" property="vo.tooltip"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">page:</td>
                   <td><input type="text" name="vo.page" value="<bean:write name="resourcesForm" property="vo.page"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">width:</td>
                   <td><input type="text" name="vo.width" value="<bean:write name="resourcesForm" property="vo.width"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">height:</td>
                   <td><input type="text" name="vo.height" value="<bean:write name="resourcesForm" property="vo.height"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">forward:</td>
                   <td><input type="text" name="vo.forward" value="<bean:write name="resourcesForm" property="vo.forward"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">action:</td>
                   <td><input type="text" name="vo.action" value="<bean:write name="resourcesForm" property="vo.action"/>" size="50"/></td>
                   <td></td>
                </tr>
                <tr>
                   <td class="leftFieldTable">menuType:</td>
                   <td>
	                  <html:select property="vo.menutype" name="resourcesForm" style="width:200px">
				         <html:option value="1">横向菜单</html:option>
				         <html:option value="2">纵向菜单</html:option>
				      </html:select>
                   </td>
                   <td></td>
                </tr>
            </table>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');">保存</button></li>
			<li><button type="button" onclick="return false;">重置</button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;">返回</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	<script language=JavaScript>
	 <!--
       window.onload=function(){
		  var rescIdVar = null;
		  var resTypeVar = null;
		  <% if(v_rescId!=null) {%>rescIdVar="<%=v_rescId.toString() %>";<% } %>
		  <% if(v_resType!=null) {%>resTypeVar="<%=v_resType %>";<% } %>
		  if(rescIdVar!=null&&rescIdVar!=""){
		     document.getElementById("syscodeId").disabled="disabled";
		     document.getElementById("resTypeId").disabled="disabled";
		  }
	      if(resTypeVar!=null&&resTypeVar=="MENU"){
	         document.getElementById("menuDiv").style.display="block";
	      }else{
	         document.getElementById("menuDiv").style.display="none";
	      }
	   }
     //-->
    </script>
  </body>
</html:html>

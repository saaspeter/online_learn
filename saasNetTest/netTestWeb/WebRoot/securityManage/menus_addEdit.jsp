<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%> 
<%@ include file="/pubs/taglibs.jsp"%> 

<bean:define id="editType" name="resourcesForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>

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
             document.getElementById("Id_pid").value=ids;
             document.getElementById("Id_pidName").value=names;
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
  <bean:define id="v_rescId" name="resourcesForm" property="vo.id" type="java.lang.Long" ></bean:define>
  <bean:define id="v_resType" name="resourcesForm" property="vo.resType" type="java.lang.String" ></bean:define>
  <% String action = "listResourcesvalue.do";
     if(v_resType!=null&&"MENU".equals(v_resType))
        action = "listMenusvalue.do";
  %>
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/securityManage/saveResource.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="resourcesForm" property="backUrl"/>" />
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="resourcesForm" property="backUrlEncode"/>" />
	 <input type="hidden" id="rescId" name="vo.id" value="<bean:write name="resourcesForm" property="vo.id"/>" />
	 <input type="hidden" name="vo.rescfold" value="<bean:write name="resourcesForm" property="vo.rescfold"/>" />

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
            <%if(isDisable){ %>  
            <li>
			   <div class="fieldLabel">资源id:</div>
			   <div class="field"> 
			     <bean:write name="resourcesForm" property="vo.id"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            <%} %>
		    <li>
			   <div class="fieldLabel">资源名:</div>
			   <div class="field"> 
			     <input type="text" id="rescNameId" name="vo.name" value="<bean:write name="resourcesForm" property="vo.name"/>" size="30"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">资源类型:</div>
			   <div class="field"> 
			     <html:select styleId="resTypeId" property="vo.resType" name="resourcesForm" style="width:220px" onchange="showHideMenu(this)">
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
			     <input type="text" name="vo.resString" value="<bean:write name="resourcesForm" property="vo.resString"/>" size="30"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">系统代码:</div>
			   <div class="field"> 
			     <html:select styleId="syscodeId" property="vo.syscode" name="resourcesForm" style="width:220px">
			         <html:option value="00000001">平台系统</html:option>
			         <html:option value="00000002">教育类型</html:option>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">上级资源:</div>
			   <div class="field"> 
			      <input type="text" id="Id_pidName" value="<bean:write name="resourcesForm" property="vo.pidName"/>" size="30" readonly="readonly"/>
			      
			      <input type="hidden" id="Id_pid" name="vo.pid" value="<bean:write name="resourcesForm" property="vo.pid"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">依赖资源id:</div>
			   <div class="field"> 
			     <input id="Id_linkid" type="text" name="vo.linkid" value="<bean:write name="resourcesForm" property="vo.linkid"/>" size="30"/>
			   </div>
			   <div class="fieldDesc">依赖于别的权限进行分配，如果依赖别的权限，则一般为不可见</div>
			</li>
			
			<li>
			   <div class="fieldLabel">可见性:</div>
			   <div class="field"> 
			     <html:select styleId="Id_visible" property="vo.visible" name="resourcesForm" style="width:220px">
			         <html:option value="1">可见</html:option>
			         <html:option value="0">不可见</html:option>
			     </html:select>
			   </div>
			   <div class="fieldDesc">为角色选择权限时是否可见</div>
			</li>
			
			<li>
			   <div class="fieldLabel">状态:</div>
			   <div class="field"> 
			     <html:select property="vo.status" name="resourcesForm" style="width:220px">
			         <html:option value="1">有效</html:option>
			         <html:option value="2">失效</html:option>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">资源描述:</div>
			   <div class="field"> 
			     <input type="text" id="rescDescnId"  name="vo.descn" value="<bean:write name="resourcesForm" property="vo.descn"/>" size="30"/>
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
                   <td><input type="text" id="menuTitleId" name="vo.title" value="<bean:write name="resourcesForm" property="vo.title"/>" size="50"/></td>
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
                   <td><input type="text" id="menuTooltipId" name="vo.tooltip" value="<bean:write name="resourcesForm" property="vo.tooltip"/>" size="50"/></td>
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
	                  <html:select property="vo.menutype" name="resourcesForm" style="width:250px">
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
		    <li><button type="button" onclick="subForm(); return  false;">保存</button></li>
			<li><button type="button" onclick="return false;">重置</button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;">返回</button></li>
		 </ul>
	  </div>
	  
	  <%if(v_rescId!=null){ %>
	  <div style="background:#eeeeee">资源国际化设置</div>
	  <div style="width:100%;height:230px;">
		   <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>/securityManage/<%=action %>?queryVO.id=<bean:write name="resourcesForm" property="vo.id"/>" scrolling="auto" frameborder="0"></iframe>
      </div>
      <%} %>
	  
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
		     document.getElementById("rescNameId").disabled="disabled";
		     document.getElementById("rescDescnId").disabled="disabled";
		     document.getElementById("menuTitleId").disabled="disabled";
		     document.getElementById("menuTooltipId").disabled="disabled";
		  }
	      if(resTypeVar!=null&&resTypeVar=="MENU"){
	         document.getElementById("menuDiv").style.display="block";
	      }else{
	         document.getElementById("menuDiv").style.display="none";
	      }
	   }
	   
	   function subForm(){
	       var visible = document.getElementById("Id_visible").value;
	       var linkid = document.getElementById("Id_linkid").value;
	       if(visible=='0'&&linkid==''){
	          showMessagePage('权限为不可见，必须输入依赖权限id');
	          return;
	       }
	       submitForm('editForm');
	   }
	   
     //-->
    </script>
  </body>
</html:html>

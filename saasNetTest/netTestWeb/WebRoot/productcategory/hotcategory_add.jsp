<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="hotcategoryForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="pidLong" name="hotcategoryForm" property="vo.pid" type="java.lang.Long"/>
    <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   
	   function selectCate_CB(ids,names,param){
	      var pidVar = '<%=pidLong %>';
          if(ids!=null&&ids!=""){
             if(ids!='pidVar'){
                document.getElementById("Id_categoryname").value = names;
                document.getElementById("Id_categoryid").value = ids;
             }else {
                alert('不能选择顶级目录!');
             }
          }
          clearDiv();
       }
	   
	   function save(){
	      document.forms[0].submit();
	   }
	   
	</script>
  </head>

  <body>
      <div class="titleBar">添加热门目录</div>
  
      <html:form action="/productcategory/saveHotcategory.do" method="post">
          <input type="hidden" name="vo.pid" value="<%=pidLong %>" />
          <input type="hidden" name="vo.localeid" value="<bean:write name="hotcategoryForm" property="vo.localeid"/>" />
          <div style="height: 30px;"></div>
          <div style="padding-left: 40px;">添加热门目录级别:</div>
	      <div style="padding-left: 40px;">   
		     <select name="scope">
		        <option value="1">添加本国家热门目录设置</option>
		        <option value="2">添加到所有国家热门设置</option>
		     </select>
	      </div>
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">父目录:&nbsp;&nbsp;<bean:write name="hotcategoryForm" property="vo.categoryname"/></div>
	      
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">选择添加热门目录:</div>
	      <div style="padding-left: 40px;">
	         <input type="text" id="Id_categoryname" name="" class="readonly" value="" readonly="readonly" style="width:200px"/>
	         <input type="hidden" id="Id_categoryid" name="vo.categoryid" value="" />
	         <button type="button" onclick="newDiv('/productcategory/selprodcategorytreepage.do?vo.pid=<%=pidLong %>&localeid=<bean:write name="hotcategoryForm" property="vo.localeid"/>',1,1,300,400);">...</button>
	      </div> 
	      
	      <div style="height: 20px;"></div>
	      <div style="padding-left: 40px;">显示顺序:</div>
	      <div style="padding-left: 40px;">
	         <input type="text" name="vo.disorder" value="" style="width:200px"/>
	      </div> 
	      <div style="height: 20px;"></div>
	      
	      <div style="padding-left: 40px;">
	         <button type="button" onclick="save();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;
	         <button type="button" onclick="parent.clearDiv();">取消</button>
	      </div> 
      </html:form> 
  </body>
</html:html>

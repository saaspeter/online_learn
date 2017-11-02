<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="hotcategoryForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="localeid" name="hotcategoryForm" property="vo.localeid" type="java.lang.Long"/>
    <link rel="stylesheet" type="text/css" href="../styles/css/list.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript">
	
	    function selectCate_CB(ids,names,param){
	        if(ids!=null&&ids!=""){
	           var url = "/productcategory/saveHotcategory.do?categoryidStr="+ids;
	           goUrl(url);
	        }
        }
        
        function toanotherlocale(selObj){
           if(selObj==null){
              return;
           }
           var old_localeid = '<%=localeid %>';
           var itemValue = selObj.options[selObj.selectedIndex].value;
           if(itemValue==old_localeid){ return; }
           goUrl('/productcategory/magHotcategory.do?vo.localeid='+itemValue);
        }
	
	</script>
  </head>
  
  <body>
      <html:form action="/productcategory/magHotcategory.do">
      <div class="titleBar">热门目录管理</div>    
      <div>(各个系统的hotCategory应该在本系统中管理，这样界面就简单了)</div>
      <br>
	  <div class="bottomFunDiv">
		  <ul>
			 <li><button type="button" onclick="newDiv('addHotcategory.do?vo.localeid=<%=localeid %>',1,1,700,400);">新增一级热门目录</button></li>
			 <li>当前选择国家语言为： <bean:writeSaas name="hotcategoryForm" property="vo.localeid" showLocaleName="true"/>, &nbsp;
			      转至其他国家语言热门目录: 
                 <html:select name="hotcategoryForm" property="vo.localeid" onchange="toanotherlocale(this)">
				    <html:optionsSaas localeListSet="true"/>
			     </html:select>
			 </li>
		  </ul>
	  </div>
	  
	  <div class="dashLine"></div>
	  
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
      <br>
      <logic:present name="hotcategoryForm" property="hotcateList">
      <logic:iterate id="voTemp" name="hotcategoryForm" property="hotcateList">
          <div style="font-weight: bold; margin: 3px;"><bean:write name="voTemp" property="categoryname"/>
             (&nbsp;<img src="../styles/imgs/add.png" style="cursor:pointer;" alt="新增下级目录" onclick="newDiv('addHotcategory.do?vo.pid=<bean:write name="voTemp" property="categoryid"/>&vo.categoryname=<bean:write name="voTemp" property="categoryname"/>&vo.localeid=<bean:write name="hotcategoryForm" property="vo.localeid"/>',1,1,700,400);" />
             &nbsp;|&nbsp;
             <img src="../styles/imgs/delete.png" style="cursor:pointer;" alt="删除" onclick="newDiv('hotcategory_delete.jsp?categoryid=<bean:write name="voTemp" property="categoryid"/>&categoryname=<bean:write name="voTemp" property="categoryname"/>&localeid=<bean:write name="voTemp" property="localeid"/>&parentCategoryid=<bean:write name="voTemp" property="pid"/>',1,1,400,300);"/>&nbsp;)
          </div>
          <logic:present name="voTemp" property="subcatelist">
          <div style="margin: 5px; font-size: smaller;">
          <logic:iterate id="subvo" name="voTemp" property="subcatelist">
             <a href="" style="white-space: nowrap; margin: 3px;"><bean:write name="subvo" property="categoryname"/></a>
             (<img src="../styles/imgs/delete.png" style="cursor:pointer;" alt="删除" onclick="newDiv('hotcategory_delete.jsp?categoryid=<bean:write name="subvo" property="categoryid"/>&categoryname=<bean:write name="subvo" property="categoryname"/>&parentCategoryid=<bean:write name="subvo" property="pid"/>',1,1,400,300);"/>&nbsp;)&nbsp;|&nbsp;
          </logic:iterate>
          </div>
          </logic:present>
      </logic:iterate>
      </logic:present>
      </html:form>
  </body>
</html:html>

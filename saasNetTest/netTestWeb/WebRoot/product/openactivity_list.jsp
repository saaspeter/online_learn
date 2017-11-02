<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.KeyInMemoryConstant, netTest.product.vo.Openactivity" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="openactivityForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
<bean:define id="localeid" name="openactivityForm" property="queryVO.localeid" type="java.lang.Long"/>
<%  
   int rows = -1;
   String shopStr = "";
   Long categoryid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
   String categoryname = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="openactivityForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="netTest.page.common.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/banner.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/tab/simpleTab2.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/list.css" />
<link href="<%=request.getContextPath() %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	<!--
	
	#menu6{
	  display: block;
	  color: #667;
	  background-color: #ec8;
	}
	-->
	</style>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/commonlogic.js"></script>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/utiltool.js"></script>
	<script type='text/javascript' src='<%=request.getContextPath() %>/dwr/engine.js'></script>
    <script type='text/javascript' src='<%=request.getContextPath() %>/dwr/interface/countryregion.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath() %>/dwr/interface/prodcateshop.js'></script>
	<script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/region/region.js"></script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  
  <jsp:include flush="true" page="/index/banner.jsp"></jsp:include>
    <div class="navlistBar">
	    <a href="javascript:void(0)" onclick="switchCategory(this);return false;" title="点击更换目录">
	    <%if(categoryname==null||"null".equals(categoryname)){ %>
	         <bean:message key="netTest.commonpage.category.nullcategoryname"/>
	    <%}else { out.print(categoryname); } %>
	    </a> &gt; 公开活动&nbsp;&nbsp;(包括:免费公开培训信息, 公开户外学习活动等)
    </div>

    <div style="height:5px; clear:both;"></div>
  
  <div style="text-align: center;padding: 3px;margin-bottom: 10px;width: 100%;">
	    <form id="listFormId" action="<%=request.getContextPath() %>/product/listOpenactivity.do" method="post">
	       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryid %>"/>
	       <input type="hidden" id="Id_categoryname" name="categoryname" value="<%=categoryname %>"/>
	       <input type="hidden" id="pageIndex_id" name="pageIndex"/>
	       <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
	       <table border="0" cellpadding="2px" style="text-align: left;margin: auto;">
	           <tr>
	               <td>
	                   开始时间:  <html:select name="openactivityForm" property="queryVO.starttimerange">
	                   <html:option value="3">三天内</html:option>
	                   <html:option value="7">一周内</html:option>
	                   <html:option value="14">两周内</html:option>
	                   <html:option value="-1">全部</html:option>    
	               </html:select>
	                   &nbsp;活动标题: <input type="text" name="searchtext" class="searchTxt_second" style="width:250px;" value="<bean:write name="openactivityForm" property="queryVO.name"/>" /> 
				       &nbsp;<button type="button" onclick="document.forms[0].submit();" class="btn btn-primary">搜索</button>
	               </td>
	           </tr>
	           <tr id="advanceSearchDiv" style="">
	               <td>
	                   <table style="border:0px;margin-top:15px;">
				          <tr>
				             <td>
				                                               所在地:
			                     <html:select styleId="selectlocaleid" name="openactivityForm" property="queryVO.localeid" onchange="changelocale(this)" style="font-size: larger">
				                    <html:optionsSaas localeListSet="true" localetype="selectlocale"/>
				                 </html:select>&nbsp;&nbsp;
				             </td>
				             <td>
				                 <select id="provinceId" name="privCode" onchange="selectRegion()" class="font_input"></select>(省)&nbsp;&nbsp;&nbsp;
	                         </td>
	                         <td id="cityId_container_id" style="text-align: left;">
	                             <select id="cityId" name="cityCode" class="font_input"></select>
	                         </td>
				          </tr>
				      </table>
	               </td>
	           </tr>
	       </table>
	    </form>
  </div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div style="width: 100%;margin-bottom: 10px;min-height:300px;">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>活动</td>
        <td>活动时间</td>
        <td>活动地区</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td></td>
        <td><a href='<%=request.getContextPath() %>/product/viewOpenactivity.do?vo.activityid=<bean:write name="vo" property="activityid"/>&formobile=0' style="font-size:20px;" target="_blank"><bean:write name="vo" property="name"/></a>
            <logic:equal name="vo" property="status" value="<%=Openactivity.Status_Ended.toString() %>">
            (<bean:writeSaas name="vo" property="status" consCode="OpenActivity.Status"/>)
            </logic:equal>
            <br>
            (学校: <a href='<%=request.getContextPath() %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>' style="font-size:14px;" target="shoppage"><bean:write name="vo" property="shopname"/></a>)
        </td>
        <td><bean:writeDate name="vo" property="starttime" formatKey="Common.DateFormatType.DateTime"/><br>
            --- <bean:writeDate name="vo" property="endtime" formatKey="Common.DateFormatType.DateTime"/>
        </td>
        <td><bean:writeSaas name="vo" property="regioncode" consCode="CountryregionConstant.RegionCode"/></td>
        
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  
  <jsp:include flush="true" page="/pubs/pagetiles_bottom.jsp"></jsp:include>
  <jsp:include flush="true" page="/foot.jsp"></jsp:include>
  
  </div>
  <script type='text/javascript'>
	 <!--
	   var localeidVar = '<%=localeid %>';
       window.onload=function(){
            var rowsVar = <%=rows %>;
            prodcateshop.getShopCategoryNames('<%=shopStr %>', <%=localeid %>,
                function CB_check(cateArr){
                   if(cateArr!=null&&cateArr.length>0){
		              for(var temp=0;temp<=rowsVar;temp++){
		                  document.getElementById("cateTdId"+temp).innerHTML = cateArr[temp];
		              }
		           }
                }
            );
            //
            initRegion(localeidVar, '<bean:write name="openactivityForm" property="privCode"/>','<bean:write name="openactivityForm" property="cityCode"/>');
	   }
	   
	   function changelocale(selObj){
          if(selObj==null){
              return;
          }
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue==localeidVar){ return; }
          initRegion(itemValue, '','');
          localeidVar = itemValue;
       }

     //-->
  </script>
  </body>
</html:html>

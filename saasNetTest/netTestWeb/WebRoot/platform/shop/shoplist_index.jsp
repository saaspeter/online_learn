<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant, netTestWeb.base.KeyInMemoryConstant, platform.constant.ShopConstant"%>
<bean:define id="jsSuffix" name="shopForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
<bean:define id="localeid" name="shopForm" property="queryVO.localeid" type="java.lang.Long"/>
<%  
   int rows = -1;
   String shopStr = "";
   Long categoryid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
   String categoryname = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<html:base />
<title><bean:message key="netTest.page.common.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/banner.css" />
<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/tab/simpleTab2.css" />
<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css" />
<link href="<%=WebConstant.WebContext %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	<!--
	
	#menu5{
	  display: block;
	  color: #667;
	  background-color: #ec8;
	}
	
	#leftAD {
		float:left;
		background-color:#FFFFFF;
		margin-top:5em;
		width: 120 px;
	}
	
	#rightAD {
		height:800px;
		float:right;
		clear:right;
		margin-top:5em;
		background-color:#FFFFFF;
	}
	
	.articleList {
	   text-align: left;
	   list-style-type: square;
	   margin: 10px;
	}
	
	.articleList li{
	   margin: 10px;
	   list-style-type: square;
	
	}
	
	.articleList li a{
	   margin-right: 30px;
	}
	-->
	</style>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/commonlogic.js"></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/utiltool.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/prodcateshop.js'></script>
	<script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/region/region.js"></script>
</head>

<body>

<div class="col-xs-12 col-md-9 center-block">

	<jsp:include flush="true" page="../../index/banner.jsp"></jsp:include>
    <div class="navlistBar">
	    <a href="javascript:void(0)" onclick="switchCategory(this);return false;" title="点击更换目录">
	    <%if(categoryname==null||"null".equals(categoryname)){ %>
	         <bean:message key="netTest.commonpage.category.nullcategoryname"/>
	    <%}else { out.print(categoryname); } %>
	    </a> &gt; 培训公司
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;padding: 3px;margin-bottom: 10px;width: 100%;">
	    <form id="listFormId" action="<%=WebConstant.WebContext %>/shop/listShopIndex.do" method="post">
	       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryid %>"/>
	       <input type="hidden" id="Id_categoryname" name="categoryname" value="<%=categoryname %>"/>
	       <input type="hidden" id="pageIndex_id" name="pageIndex"/>
	       <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
	       <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value=""/>
	       <table border="0" cellpadding="2px" style="text-align: left;margin: auto;">
	           <tr>
	               <td>
	                   <input type="text" name="searchtext" class="searchTxt_second" value="<bean:write name="shopForm" property="searchtext"/>" /> 
				       <html:select property="queryVO.ownertype" name="shopForm" styleClass="select_second">
				          <html:option value="">全部商店</html:option>
				          <html:optionsSaas consCode="platform.ShopConstant.OwnerType" localetype="selectlocale"/>
				       </html:select>
				       &nbsp;<button type="button" onclick="document.forms[0].submit();" class="searchBtn_second">搜索</button>
				       &nbsp;<a href="javascript:changeComplexSearch('advanceSearchDiv');" style="font-size: smaller;">高级搜索</a>
	               </td>
	           </tr>
	           <tr id="advanceSearchDiv" style="display:none">
	               <td>
	                   <table style="border:0px;margin-top:5px;">
				          <tr>
				             <td>
				                                               所在地:
			                     <html:select styleId="selectlocaleid" name="shopForm" property="queryVO.localeid" onchange="changelocale(this)" style="font-size: larger">
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
     
    <div style="width: 100%;margin-bottom: 10px;">
	    <table class="listDataTable" >
	        <thead>
	        <tr class="listDataTableHead">
	           <td>公司名称</td>
	           <td>公司所属类型</td>
	           <td width="50%">培训课程目录</td>
	        </tr>
	        </thead>
	        <tbody>
	        <logic:present name="pageList">
		    <logic:iterate id="vo" name="pageList" indexId="ind" type="platform.vo.Shop">
	        <tr>      
	           <td>
	               <a href="javascript:void(0);" onclick="window.open('/<%=CommonConstant.WebContext_Education %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>');return false;"><bean:write name="vo" property="shopname"/></a>
	               <br/>(<bean:write name="vo" property="shopcode"/>)
	               <logic:equal value="<%=ShopConstant.IsAuthen_pass.toString() %>" name="vo" property="isauthen">
	                   <img src="../../styles/imgs/verified.gif" title="verified shop"/>
	               </logic:equal>
	           </td>      
	           <td><bean:writeSaas name="vo" property="ownertype" consCode="platform.ShopConstant.OwnerType"/></td>
	           <td id="cateTdId<%=ind %>"></td>
	        </tr>
	        <% rows = ind; shopStr=shopStr+vo.getShopid()+",";  %>
	        </logic:iterate>
	        </logic:present>
	        </tbody>
	    </table>
	    <logic:notPresent name="pageList">
	        <div style="height:20em;padding:3em;">
	           <%if(categoryid==null){ %>
	           <span class="alertFont">请点击选择产品目录链接查询</span>
	           <%} %>
	        </div>
	    </logic:notPresent>
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
            initRegion(localeidVar, '<bean:write name="shopForm" property="privCode"/>','<bean:write name="shopForm" property="cityCode"/>');
	        changeComplexSearch('advanceSearchDiv','<bean:write name="shopForm" property="complexSearchDivStatus"/>');
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
</html>

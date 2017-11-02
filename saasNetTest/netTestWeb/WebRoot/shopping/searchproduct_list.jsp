<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant, commonTool.constant.PayTypeConstant, netTestWeb.base.KeyInMemoryConstant, netTest.product.vo.Productbase"%>
<bean:define id="jsSuffix" name="productForm" property="jsSuffix" type="java.lang.String"></bean:define>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<html:base />
<%
   Long categoryid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
   String categoryidStr = (categoryid==null)?"":categoryid.toString();
   String categoryname = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
%>
<title><bean:message key="netTest.page.common.title"/></title>
<html:base />
<link rel="stylesheet" type="text/css" href="../styles/css/banner.css" />
<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
<link rel="stylesheet" type="text/css" href="../styles/css/list.css" />
<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<style type="text/css">
<!--

#menu3{
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
	<script type='text/javascript' src="../styles/script/pageAction.js"></script>
	<script type='text/javascript' src="../styles/script/commonlogic.js"></script>
	<script type='text/javascript' src="../styles/script/utiltool.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/region/region.js"></script>
</head>

<body>

<div class="col-xs-12 col-md-9 center-block">

	<jsp:include flush="true" page="../index/banner.jsp"></jsp:include>

    <div class="navlistBar">
	    <a href="javascript:void(-1);" onclick="switchCategory(this);" title="点击更换目录">
        <%if(categoryname==null||"null".equals(categoryname)){ %>
           <bean:message key="netTest.commonpage.category.nullcategoryname"/>
        <%}else { out.print(categoryname); } %>
	    </a> &gt; 培训课程
    </div>

	<div style="height:5px; clear:both;"></div>
	
	<div style="text-align: center;padding: 3px;margin-bottom: 10px;width: 100%;">
	    <form id="listFormId" action="searchProductList.do" method="get">
	       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryidStr %>"/>
	       <input type="hidden" id="Id_categoryname" name="categoryname" value="<%=categoryname %>"/>
	       <input type="hidden" id="pageIndex_id" name="pageIndex"/>
	       <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
	       <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value=""/>
	       <table border="0" cellpadding="2px" style="text-align: left;margin: auto;">
	           <tr>
	               <td> 
	                   <input type="text" name="productname" class="searchTxt_second" value="<bean:write name="productForm" property="productname" />"/>&nbsp;
	                   <html:select property="queryVO.paytype" name="productForm" styleClass="select_second">
				          <html:option value="">全部课程</html:option>
				          <html:option value="<%=PayTypeConstant.PayType_free.toString() %>">免费课程</html:option>
				          <html:option value="<%=PayTypeConstant.PayType_needMoney.toString() %>">付费课程</html:option>
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
				                  培训机构所在地:
			                     <html:select styleId="selectlocaleid" name="productForm" property="localeid" onchange="changelocale(this)" style="font-size: larger">
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
		           <td style="width: 242px;"></td>
		           <td>课程名称</td>
		           <td>购买类型</td>
		        </tr>
	        </thead>
	        <logic:present name="pageList">
		    <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.product.vo.Productbase">
		    <tbody>
	        <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">   
	           <td>
	               <img src="<%=WebConstant.getDefaultLogoImg(vo.getLogoimage(), Productbase.ObjectType) %>" style="width: 240px;height: 135px;cursor: pointer;" alt="<bean:write name="vo" property="productname"/>" 
	                    onclick="window.open('/<%=CommonConstant.WebContext_Education %>/product/productDetailFromIndex.jsp?shopid=<bean:write name="vo" property="shopid"/>&productid=<bean:write name="vo" property="productbaseid"/>&urlType=2','productviewpage');return false;" />
	           </td>   
	           <td>
	               <span style="font-size: x-large;">
	               	   <a href="javascript:void(0);" onclick="window.open('/<%=CommonConstant.WebContext_Education %>/product/productDetailFromIndex.jsp?shopid=<bean:write name="vo" property="shopid"/>&productid=<bean:write name="vo" property="productbaseid"/>&urlType=2','productviewpage');return false;"><bean:write name="vo" property="productname"/></a>
	                   <logic:equal value="1" name="vo" property="issysgoodproduct">(荐)</logic:equal>
	               </span><br/><br/>
	               (学校: <a href="javascript:void(0);" onclick="window.open('/<%=CommonConstant.WebContext_Education %>/shop/toshop.do?shopid=<bean:write name="vo" property="shopid"/>');return false;">
	                     <bean:write name="vo" property="shopname"/></a>)
	           </td>
	           <td><bean:write name="vo" property="productprice"/>&nbsp;(<bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/>)</td>
	        </tr>
	        </tbody>
	        </logic:iterate>
	        </logic:present>
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
	   var localeidVar = '<bean:write name="productForm" property="localeid"/>';
       initRegion(localeidVar, '<bean:write name="productForm" property="privCode"/>','<bean:write name="productForm" property="cityCode"/>');
       changeComplexSearch('advanceSearchDiv','<bean:write name="productForm" property="complexSearchDivStatus"/>');
       
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

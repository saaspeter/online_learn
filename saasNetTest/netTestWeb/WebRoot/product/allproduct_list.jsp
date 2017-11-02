<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.ProductConstant,commonTool.constant.CommonConstant, netTestWeb.base.KeyInMemoryConstant,netTest.product.constant.GoodproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="productForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="issysgoodproduct" name="productForm" property="queryVO.issysgoodproduct" type="java.lang.Short"></bean:define>
<%
  if(issysgoodproduct==null){ issysgoodproduct=ProductConstant.Issysgoodproduct_no;}
  Long categoryid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
  String categoryname = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>培训科目产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/banner.css" />
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type='text/javascript' src="../styles/script/commonlogic.js"></script>
	<script type='text/javascript' src="../styles/script/utiltool.js"></script>
	<script type='text/javascript' src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript">
	    
	    function addGoodproduct(productbaseid, categoryid, scope, shopid){
	       if(!confirm('确定在目录:  <%=categoryname%>  级别推荐该产品?')){
	          return;
	       }
	       var url = '<%=WebConstant.WebContext %>/product/addGoodProduct.do?vo.productbaseid='+productbaseid
	                        +'&vo.categoryid='+categoryid+'&vo.goodproductscope='+scope+'&vo.shopid='+shopid;  
	       var rtnobj = toAjaxUrl(url);
           if(rtnobj!=null){
              if(rtnobj.result=='1'){
                 document.getElementById("list").action = document.getElementById("list").action+'?DisplayMessageKey=CommonSystem.commonAction.operation.succeed';
                 document.getElementById("list").submit();
              }else {
                 alert(getMessage(rtnobj.info));
              }
           }
	    }
	    
	    function deleteGoodproduct(productbaseid, categoryid){
	       if(!confirm('确定在目录:  <%=categoryname%>  级别取消对该产品的推荐?')){
	          return;
	       }
	       var rtnobj = toAjaxUrl('<%=WebConstant.WebContext%>/product/deleteGoodProduct.do?vo.productbaseid='+productbaseid
	                       +'&vo.categoryid='+categoryid);
           if(rtnobj!=null){
              if(rtnobj.result=='1'){
                 document.getElementById("list").action = document.getElementById("list").action+'?DisplayMessageKey=CommonSystem.commonAction.operation.succeed';
                 document.getElementById("list").submit();
              }else{
                 alert(getMessage(rtnobj.info));
              }
           }
	    }
	
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/viewProductListMag.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input type="hidden" name="queryVO.categoryname" value="<%=categoryname%>">
  <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryid %>"/>
  <input type="hidden" id="Id_categoryname" name="categoryname" value="<%=categoryname %>"/>
  
  <div class="navlistBar">
      <a href="javascript:void(-1);" onclick="switchCategory(this);" title="点击更换目录">
      <%if(categoryname==null||"null".equals(categoryname)){ %>
         <bean:message key="netTest.commonpage.category.nullcategoryname"/>
      <%}else { out.print(categoryname); } %>
      </a> &gt; 培训课程
  </div>
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">产品名称: 
		<input type="text" name="queryVO.productname" value="<bean:write name="productForm" property="queryVO.productname"/>"/>
		<label><html:checkbox tagName="queryVO.issysgoodproduct" value="1" checkStr="<%=issysgoodproduct.toString()%>">推荐课程</html:checkbox></label>
		商店code: <input type="text" name="queryVO.shopcode" value="<bean:write name="productForm" property="queryVO.shopcode"/>"/>
		&nbsp;&nbsp;查询范围:
                 <html:select name="productForm" property="queryVO.isIncludeChild">
                    <html:option value="<%=String.valueOf(CommonConstant.SearchType_Self)%>">本目录查询</html:option>
                    <html:option value="<%=String.valueOf(CommonConstant.SearchType_Below)%>">包含子目录查询</html:option>
                 </html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="netTest.commonpage.complexQuery"/></a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable" style="margin-left: 300px;">
          <tr>
              <td></td>
              <td>
		            所在国家:
			      <html:select name="productForm" property="queryVO.localeid">
				     <html:optionsSaas localeListSet="true"/>
				  </html:select>
              </td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>


  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td>产品名称</td>
        <td>产品价格</td>
        <td>付费类型</td>
        <td>是否公开</td>
        <td>所属商店</td>
        <td>是否推荐</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="voTemp" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><a href="javascript:void(0);" onclick="newDiv('/product/viewproduct.do?productbaseid=<bean:write name="voTemp" property="productbaseid"/>',0,1,600,400);"><bean:write name="voTemp" property="productname"/></a></td>
        <td><bean:write name="voTemp" property="productprice"/></td>
        <td><bean:writeSaas name="voTemp" property="paytype" consCode="Common.PayTypeConstant.PayType"/></td>     
        <td><bean:writeSaas name="voTemp" property="isopen" consCode="netTest.ProductConstant.Isopen"/></td>   
        <td><bean:write name="voTemp" property="shopname"/></td>
        <td>
            <logic:equal value="<%=ProductConstant.Isopen_yes.toString()%>" name="voTemp" property="isopen">
                <logic:notEqual value="<%=ProductConstant.Issysgoodproduct_yes.toString()%>" name="voTemp" property="issysgoodproduct">
                    &nbsp;|&nbsp;<button type="button" onclick="addGoodproduct('<bean:write name="voTemp" property="productbaseid"/>', '<bean:write name="voTemp" property="categoryid"/>', '<%=GoodproductConstant.Scope_Local %>', '<bean:write name="voTemp" property="shopid"/>');">本国推荐</button>
                    &nbsp;|&nbsp;<button type="button" onclick="addGoodproduct('<bean:write name="voTemp" property="productbaseid"/>', '<bean:write name="voTemp" property="categoryid"/>', '<%=GoodproductConstant.Scope_All %>', '<bean:write name="voTemp" property="shopid"/>');">全球推荐</button>
                </logic:notEqual>
                <logic:equal value="<%=ProductConstant.Issysgoodproduct_yes.toString()%>" name="voTemp" property="issysgoodproduct">
                   <bean:writeSaas name="voTemp" property="goodproductscope" consCode="netTest.GoodProduct.Scope"/>&nbsp;|&nbsp;
                   <button type="button" onclick="deleteGoodproduct('<bean:write name="voTemp" property="productbaseid"/>', '<bean:write name="voTemp" property="categoryid"/>');">取消推荐</button>
                </logic:equal>
            </logic:equal>
            <logic:equal value="<%=ProductConstant.Isopen_no.toString()%>" name="voTemp" property="isopen">
               ------
            </logic:equal>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script type="text/javascript">
	 <!--
       window.onload=function(){
         changeComplexSearch("complexSearchDiv","<bean:write name="productForm" property="complexSearchDivStatus"/>");
       }
     //-->
  </script>
  </body>
</html:html>

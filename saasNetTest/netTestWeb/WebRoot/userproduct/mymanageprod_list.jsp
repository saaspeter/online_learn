<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.UserproductConstant, netTest.product.constant.ProductConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
<html:base />
<%
	String backUrlEncodeStr = "%2Fusercontext%2FlistMymagProd.do";
		String backUrlStr = "/usercontext/listMymagProd.do";
%>
<bean:define id="jsSuffix" name="jsSuffix" type="java.lang.String" />
<bean:define id="shopidVar" name="shopid" type="java.lang.Long" />
<title><bean:message key="netTest.page.common.title"/></title>
<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet" />
<style type="text/css">
#menu5 {
	display: block;
	color: #667;
	background-color: #ec8;
}
</style>
<script language=JavaScript
	src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
<script type="text/javascript" src="../styles/script/pageAction.js"></script>
</head>
<body>
	<div class="col-xs-12 col-md-9 center-block">
		<jsp:include flush="true" page='<%="/shop/banner_shop.jsp?shopid=" + shopidVar%>'></jsp:include>

		<div class="listPage">
			<html:form styleId="list" action="/usercontext/listMymagProd.do" method="post">
				<input id="backUrl" type="hidden" name="backUrl" value="<%=backUrlStr%>">
				<input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<%=backUrlEncodeStr%>">

				<div class="navlistBar">
					&#9660 <span style="font-size: larger;margin-right: 50px;">我直接管理的课程列表</span>
					<authz:privilege res='<%="/product/product_tree_main.jsp?shopid="+shopidVar %>'>
					<span style="float:right; margin-right:8em;">
					<a href='<%=request.getContextPath() %>/product/addproduct.do?bakurltype=2' class="btn btn-success">开设一门新课程</a>
				    </span>
				    </authz:privilege>
			    </div>

				<div class="dashLine"></div>

				<div id="displayMessDiv">
					<tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
				</div>

				<div id="DataTableDiv" style="min-height:400px;">
					<table class="listDataTable">
						<thead>
							<tr class="listDataTableHead">
								<td width="20px"></td>
								<td>课程名称</td>
								<td style="width: 60px;">状态</td>
								<td style="width: 150px;">操作</td>
							</tr>
						</thead>
						<logic:present name="prodList">
							<logic:iterate id="vo" name="prodList" indexId="ind" type="netTest.product.vo.Productbase">
								<tr class="<%=(ind % 2 == 0) ? "oddRow" : "evenRow"%>">
									<td></td>
									<td><a href="javascript:void(0)"
										onclick="goUrl('/product/viewproduct.do?productbaseid=<bean:write name="vo" property="productbaseid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:write
												name="vo" property="productname" /></a></td>
									<td class='<%if(ProductConstant.Status_deleting.equals(vo.getStatus())){out.print("alertFont");} %>'>
									       <bean:writeSaas name="vo" property="status" consCode="netTest.ProductConstant.status"/>
									</td>
									<td><img src="../styles/imgs/edit.png" title="修改" style="cursor: pointer;"
										     onclick="goUrl('/product/viewproduct.do?productbaseid=<bean:write name="vo" property="productbaseid"/>&backUrlEncode=','backUrlEncode');return false;" />&nbsp;
										<img src="../styles/imgs/delete.png" title="删除" 	style="cursor: pointer;"
										     onclick="delSingleRecAjax('/product/delproduct.do?productbaseid=<bean:write name="vo" property="productbaseid"/>&backUrl=');return false;" />
									</td>
								</tr>
							</logic:iterate>
						</logic:present>
					</table>
				</div>
				<div id="buttomDiv"></div>
			</html:form>
		</div>

		<jsp:include flush="true" page="../foot.jsp"></jsp:include>
	</div>
</body>
</html:html>

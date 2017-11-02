<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    Integer totalDataNumber = 0;
    commonTool.base.Page pageObj = (commonTool.base.Page)request.getAttribute("page");
    if(pageObj!=null){
    	totalDataNumber = pageObj.getTotalNumber();
    }
%>
<script type="text/javascript">
function goPage(pageIndex)
{
	var totalPage = document.getElementById("totalPage").value;
	if(pageIndex!=null)
	{
		document.getElementById("pageIndex").value = pageIndex;
	}else {
		document.getElementById("pageIndex").value = document.getElementById("iIndex").value;
	}
	if(parseInt(document.getElementById("pageIndex").value)>parseInt(totalPage)){
	    document.getElementById("pageIndex").value = totalPage;
	}
	document.getElementById("totalDataNumberId").value = '<%=totalDataNumber==null?"":totalDataNumber %>';
	document.getElementById("list").submit();
}
</script>			

 <logic:present name="page">
 <table style="width:100%;">
    <tr style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px;">
       <td align="right" width="3px"></td>
       <td align="right">
          <span>
               共<bean:write name="page" property="totalNumber"/>条记录&nbsp; 
          </span>
       </td>
       <td style="text-align:right;">
          <img style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" src="${pageContext.request.contextPath}/styles/imgs/separator.gif"/>
       <td>
       <td align="right" style="width: 100px;">
          <span style="width: 90px;margin-left:3px;margin-right:3px;">
      	      <a href="javascript:goPage(1)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_firstpg.gif" style="width:18px;" alt="first page" border=0/></a> 
      	      <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_back.gif" style="width:18px;" alt="previous page" border=0/></a> 
	          <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_next.gif" style="width:18px;" alt="next page" border=0/></a>
	          <a href="javascript:goPage(<bean:write name="page" property="totalPage" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_lastpg.gif" style="width:18px;" alt="last page" border=0/></a> 
	      </span> 
       </td>
       <td>
          <img style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" src="${pageContext.request.contextPath}/styles/imgs/separator.gif"/>
       <td>
       <td align="right" >
          <span>
             第<input name="iIndex" id="iIndex" size="1" type="text" usage="int+" tip="页码只能输入整数！" value="<bean:write name="page" property="currentPage" />" style="height:18px">页/共<bean:write name="page" property="totalPage"/>页
            <input type="button" name="btnIndex" value="GO" onclick="javascript:goPage()" style="height:20px"> 
	        <input type="hidden" name="pageIndex" id="pageIndex" value="">
	        <input type="hidden" id="totalPage" value="<bean:write name="page" property="totalPage" />"/>
 	        <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
 	      </span>
       </td>
       
       <td align="right" width="5px"></td>
    </tr>
 </table> 
</logic:present>   	
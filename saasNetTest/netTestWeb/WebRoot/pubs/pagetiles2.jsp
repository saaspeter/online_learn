<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<bean:define id="totalDataNumber" name="page" property="totalNumber" type="java.lang.Integer"/>

<script type="text/javascript">
function   goPage(pageIndex)
{
	var totalPage = document.getElementById("totalPage").value;
	if(pageIndex!=null)
	{
		document.getElementById("pageIndex").value = pageIndex;
	}
	else
	{
		document.getElementById("pageIndex").value = document.getElementById("iIndex").value;
	}
	if(parseInt(document.getElementById("pageIndex").value)>parseInt(totalPage)){
	    document.getElementById("pageIndex").value = totalPage;
	}
	document.getElementById("totalDataNumberId").value = '<%=totalDataNumber==null?"":totalDataNumber %>';
	document.getElementById("list").submit();
}
</script>			
 <input type="hidden" name="pageIndex" id="pageIndex" >
 <input type="hidden" id="totalPage" value="<bean:write name="page" property="totalPage" />"/>
 <input type="hidden" id="totalDataNumberId" name="totalDataNumber" />			  
 <table>
    <tr style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px">
       <td align="right" width="3px"></td>
       <td align="right">
          <span>
               共<bean:write name="page" property="totalNumber"/>条&nbsp; 
          </span>
       </td>
       <td>
          <img style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" src="${pageContext.request.contextPath}/styles/imgs/separator.gif"/></TD>
       <td>
       <td align="right" >
          <span>
            每页数量<html:select styleId="pageSize" name="page" property="pageSize">
                   <html:option value="15">15</html:option>
                   <html:option value="30">30</html:option>
                   <html:option value="50">50</html:option>
                   <html:option value="100">100</html:option>
              </html:select>
          </span>
       </td>
       <td>
          <img style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" src="${pageContext.request.contextPath}/styles/imgs/separator.gif"/></TD>
       <td>
       <td align="right" >
          <span>
      	      <a href="javascript:goPage(1)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_firstpg.gif" alt="first page" border=0/></a> 
      	      <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_back.gif" alt="previous page" border=0/></a> 
	          第<font style="font-weight: bold;"><bean:write name="page" property="currentPage" /></font>页
	          <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_next.gif" alt="next page" border=0/></a>
	          <a href="javascript:goPage(<bean:write name="page" property="totalPage" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_lastpg.gif" alt="last page" border=0/></a> 
	      </span> 
       </td>
       <td align="right" width="5px"></td>
    </tr>
 </table> 
	      	
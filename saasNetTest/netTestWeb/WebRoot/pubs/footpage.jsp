<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
<bean:define id="totalDataNumber" name="page" property="totalNumber" type="java.lang.Integer"/>

<input type="hidden" name="pageIndex" id="pageIndex" value="<bean:write name="page" property="currentPage" />">
<input type="hidden" id="totalPage" name="totalPage" value="<bean:write name="page" property="totalPage" />"/>

<div style="text-align:center; margin-top:25px; margin-bottom:20px; clear:both;">
    <% if(totalPage>0){ %>
    <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_back.gif" title="previous page" border=0/></a>       
    <% for(int i=1;i<totalPage+1;i++){ 
           if(i==currentPage){ %>
              <font style="font-weight: bold;margin: 4px;"><%=i %></font>
           <% }else { %>
                <a href="javascript:goPage(<%=i %>);" class="pageNumberNormal"><%=i %></a> 
    <%     }
       } %>
    <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="${pageContext.request.contextPath}/styles/imgs/RW_icon_next.gif" title="next page" border=0/></a>
    <% } %>
</div>

<script type="text/javascript">
	function goPage(pageIndex)
	{
		var totalPage = document.getElementById("totalPage").value;
		var pageIndex_real = pageIndex;
		if(pageIndex!=null)
		{
			pageIndex_real = pageIndex;
		}else {
			pageIndex_real = document.getElementById("iIndex").value;
		}
		if(parseInt(document.getElementById("pageIndexId").value)>parseInt(totalPage)){
		    pageIndex_real = totalPage;
		}
		if(typeof(goPageParent)=="function"){
		   goPageParent(pageIndex_real, '<%=totalDataNumber==null?"":totalDataNumber %>');
		}else {
		   document.getElementById("pageIndexId").value = pageIndex_real;
		   document.getElementById("totalDataNumberId").value = '<%=totalDataNumber==null?"":totalDataNumber %>';
		   document.getElementById("list").submit();
		}
	}
</script>	
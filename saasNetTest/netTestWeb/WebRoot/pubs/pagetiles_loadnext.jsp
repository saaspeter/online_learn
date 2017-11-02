<%@ page import="netTestWeb.base.WebConstant"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<bean:define id="pageIndex" name="pageIndex" type="java.lang.Integer"/>
<bean:define id="hasnextpage" name="hasnextpage" type="java.lang.Boolean"/>
<script type="text/javascript">
function goPage(pageIndex)
{
	document.getElementById("pageIndex_id").value = pageIndex;
	document.getElementById("listFormId").submit();
}
</script>			
			  
<div style="text-align: center;margin-top: 15px;margin-bottom: 20px;clear: both;">
    <% if(pageIndex>0){ %>
    <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_back.gif" title="previous page" border=0/></a>       
    <% for(int i=1;i<pageIndex+1;i++){ 
           if(i==pageIndex){ %>
              <font style="font-weight: bold;margin: 4px;"><%=i %></font>
           <% }else { %>
                <a href="javascript:goPage(<%=i %>);" class="pageNumberNormal"><%=i %></a> 
    <%     }
       } %>
    
    <% } %>
    <% if(hasnextpage){ %>
    <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_next.gif" title="next page" border=0/></a>
    <%} %>
</div>
	      	
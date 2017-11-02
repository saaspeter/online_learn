<%@ page import="netTestWeb.base.WebConstant"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<style type="text/css">
  .pageNumber{
      padding:5px 8px 5px 8px;
      margin:4px;
      border: 1px solid #e1e2e3;
      
  }
</style>
<%
    String currentPageStr = request.getParameter("currentPage");
    int currentPage = Integer.parseInt(currentPageStr);
    String totalPageStr = request.getParameter("totalPage");
    int totalPage = Integer.parseInt(totalPageStr);
%>
<script type="text/javascript">
function goPage(pageIndex)
{
	if(pageIndex<1){ pageIndex=1; }
	else if(pageIndex><%=totalPage %>){ pageIndex=<%=totalPage %> }
	var link = 'pagetiles_onlytest.jsp?totalPage=<%=totalPage %>&currentPage='+pageIndex;
	window.location.assign(link);
}
</script>

<% int middleNum = 5; int eachsidenum = 2; %>
			  
<div style="text-align: center;margin-top: 15px;margin-bottom: 20px;clear: both;">
    <% if(totalPage>0){ %>
    <a href="javascript:goPage(<%=currentPage-1 %>)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_back.gif" title="previous page" style="border: 0;width:18px;height:18px;"/></a>       
       <% if(1==currentPage){ %>
          <font style="font-weight: bold;margin: 4px;">1</font>
       <% }else { %>
       	  <a href="javascript:goPage(1);" class="pageNumber">1</a>
       <% } %>
       
       <% if(currentPage>middleNum){ %>
          <font style="font-weight: bold;margin: 4px;">...</font>
       <% }else if(currentPage==middleNum){ %>
          <a href="javascript:goPage(<%=2 %>);" class="pageNumber"><%=2 %></a>
       <% } %>
       
       <% int middleStartNo = currentPage-eachsidenum; int middleEndNo = currentPage+eachsidenum;
          if(middleStartNo<eachsidenum){ middleStartNo=eachsidenum; }
          if(middleEndNo>(totalPage-1)){ middleEndNo=totalPage-1; }
          %>
       <% for(int i=middleStartNo;i<=middleEndNo;i++){ 
             if(i==currentPage){ %>
              <font style="font-weight: bold;margin: 4px;"><%=i %></font>
           <% }else { %>
              <a href="javascript:goPage(<%=i %>);" class="pageNumber"><%=i %></a> 
           <% }
       } %>
       
       <% if((totalPage-currentPage+1)>middleNum){ %>
          <font style="font-weight: bold;margin: 4px;">...</font>
       <% }else if((totalPage-currentPage+1)==middleNum){ %>
          <a href="javascript:goPage(<%=totalPage-1 %>);" class="pageNumber"><%=totalPage-1 %></a>
       <% } %>
       
       <% if(totalPage>1){
              if(currentPage==totalPage){ %>
	          <font style="font-weight: bold;margin: 4px;"><%=totalPage %></font>
	          <% }else { %>
	       	  <a href="javascript:goPage(<%=totalPage %>);" class="pageNumber"><%=totalPage %></a>
       <%     }
          } %>
       
    <a href="javascript:goPage(<%=currentPage+1 %>)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_next.gif" title="next page" style="border: 0;width:18px;height:18px;"/></a>
    <% } %>
</div>
	      	
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />

    <title>商店列表</title>
    <style >
        *{margin:0;}
        .trOdd {
            background-color:#ECF5FF;
        }
        
        .trEven {
            background-color:#ffffff;
        }
        .trMouseon{
            background:#eeeeee;
        }
        .tdstyle {
            cursor:pointer;
            white-space:nowrap;
        }
        
    </style>
	
	<script type="text/javascript">
	    function backCall(id,name){
           parent.selShop_CB(id,name);
        }
	       
	</script>
  </head>
  
  <body>
  <div>
      <logic:present name="shoplist">
         <table id='listshopTableID' cellpadding=2 cellspacing=2 style='border:0' bgcolor='#ffffff'>
         <tr class='trOdd' onmouseover="this.className='trMouseon'" onmouseout="this.className='trEven'">
             <td class="tdstyle">
             &nbsp;<a href="javascript:void(0);" onclick="backCall('-1','<bean:message key="NetTest.Default.All.SessionShopName" bundle="BizKey"/>');return false;"><bean:message key="NetTest.Default.All.SessionShopName" bundle="BizKey"/></a>
             </td>
         </tr>
         <logic:iterate id="vo" name="shoplist" indexId="ind">
         <tr class='<%=(ind%2==0)?"trEven":"trOdd" %>' onmouseover="this.className='trMouseon'" onmouseout="this.className='<%=(ind%2==0)?"trOdd":"trEven" %>'">
             <td class="tdstyle">
             &nbsp;<a href="javascript:void(0);" onclick="backCall('<bean:write name="vo" property="shopid"/>','<bean:write name="vo" property="shopname"/>');return false;"><bean:write name="vo" property="shopname"/></a>
             </td>
         </tr>
         </logic:iterate>
	     </table>
	  </logic:present>
  </div>
     <script type="text/javascript">
	 <!--
       window.onload=function(){
		   parent.document.getElementById("alertFram_shop").style.width = document.getElementById("listshopTableID").offsetWidth+18+"px";
	   }
     //-->
     </script>
  </body>
</html:html>

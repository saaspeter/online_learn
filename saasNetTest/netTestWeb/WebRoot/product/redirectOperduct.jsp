<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript">
	    function call(id,name){
           document.getElementById("prodidStrId").value = id;
           document.getElementById("prodnameStrId").value = name;
           document.getElementById("formId").submit();
        }       
	</script>
  </head>
  
  <body >
      <form id="formId" action="../common/changeProd.do" method="post">
          <input id="prodidStrId" type="hidden" name="prodidStr" value=""/>
          <input id="prodnameStrId" type="hidden" name="prodnameStr" value=""/>
      </form>
  </body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.constant.NewscategoryConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="newscategoryForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="typeVar" name="newscategoryForm" property="vo.type" type="java.lang.Short"/>
<bean:define id="defaultlocaleid" name="newscategoryForm" property="defaultlocaleid" type="java.lang.Long"/>
       
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   
	   function editSelf(objID, type){
	      var obj = document.getElementById(objID);
	      if(type=="1"){
	         obj.className="";
	         obj.readOnly="";
	         obj.focus();
	      }else{
	         obj.className="readonly";
	         obj.readOnly="readonly";
	      }
	   }
	   
	   function submitReq(url,objID,type){
	      if(url==null||url==''){
	         return ;
	      }
	      if(type=='save'){
	         //todo check input value
	         url = url+document.getElementById(objID).value;
	      }
	      var retObj = toAjaxUrl(url);
	      var tipMess = '';
	      if(retObj.result=='1'){
	         if(type=='delete'){
	            document.getElementById(objID).value='';
	         }
	         editSelf(objID, 0);
	         tipMess = getMessage("operation_success");
	      }else {
	         tipMess = getMessage("systemError");
	      }
	      showMessagePage(tipMess);
	   }
	</script>
  </head>
  
  <body>
	<div class="editPage">
	
     <input type="hidden" name="vo.categoryid" value="<bean:write name="newscategoryForm" property="vo.categoryid"/>" />
	
	  <div id="fieldsTitleDiv"><bean:write name="newscategoryForm" property="vo.name"/></div>
	  
	  <div style="width:100%">
		 <table>
		     <tr>
		         <td style="width:30%;text-align: right">类型:</td>
		         <td style="width:60%;text-align: left">
		             <%if(NewscategoryConstant.Type_SystemPost.equals(typeVar)){out.print("System Post");}
		               else if(NewscategoryConstant.Type_SocialPost.equals(typeVar)){out.print("Social discussion");}
		             %>
		         </td>
		         <td></td>
		     </tr>
		     		     
			 <tr>
		         <td style="width:30%;text-align: right">排序:</td>
		         <td>
		             <input id="ordernoId" name="vo.orderno" readonly="readonly" class="readonly" style="width: 40px;" value="<bean:write name="newscategoryForm" property="vo.orderno"/>"  onclick="editSelf('ordernoId', '1');" />
		             &nbsp;<img src="../../styles/imgs/save.png" style="cursor:pointer;" alt="保存" onclick="submitReq('<%=WebConstant.WebContext %>/news/updateNewsCate.do?vo.id=<bean:write name="newscategoryForm" property="vo.id"/>&vo.orderno=','ordernoId','save');return false;"/>
		         </td>
		         <td></td>
		      </tr>			    
		 </table>
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div style="width:100%">
	     <table cellpadding="0" cellspacing="0" class="formTable" width="95%">
	        <tr class="tableHead" title="在不同语言中的设置">
	           <td>国家语言</td>
	           <td>新闻分类</td>
	        </tr>	     
            <logic:present name="newscategoryForm" property="newscateList">
            <logic:iterate id="cateVo" name="newscategoryForm" property="newscateList" indexId="ind" type="platform.vo.Newscategory">
               <tr>
                  <td align="left">
                      <bean:writeSaas showLocaleName="true" name="cateVo" property="localeid"/>
                  </td>
		          <td align="left">
		              <input id="cateVo<%=ind %>Id" title="点击编辑" readonly="readonly" class="readonly" value="<bean:write name="cateVo" property="name"/>" onclick="editSelf('cateVo<%=ind %>Id', '1');"/>
		              &nbsp;<img src="../../styles/imgs/save.png" style="cursor:pointer;" alt="保存" onclick="submitReq('<%=WebConstant.WebContext %>/news/saveNewscategoryValue.do?vo.valueid=<bean:write name="cateVo" property="valueid"/>&vo.localeid=<bean:write name="cateVo" property="localeid"/>&vo.id=<bean:write name="cateVo" property="id"/>&vo.name=','cateVo<%=ind %>Id','save');return false;"/>
		              <%if(!defaultlocaleid.equals(cateVo.getLocaleid())){ %>
		              &nbsp;<img src="../../styles/imgs/delete.png" style="cursor:pointer;" alt="删除" onclick="submitReq('<%=WebConstant.WebContext %>/news/deleteNewscategoryValue.do?vo.valueid=<bean:write name="cateVo" property="valueid"/>','cateVo<%=ind %>Id','delete');return false;"/>
		              <%} %>
		          </td>
		       </tr>
            </logic:iterate>
            </logic:present>	    
		 </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    
			<li><button type="button" onclick="parent.location.reload();return false;">关闭返回</button></li>
		 </ul>
	  </div>
	
	</div>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="editType" name="warequesForm" property="editType" type="java.lang.Integer"/>
<% 
    String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
       isDisable = true;
       disableStr="disabled=\"disabled\""; 
    }

%>

    <script type="text/javascript">
	<!--
	   function selectWare(){
	      var prodid = document.getElementById("prodidId").value;
	      newDiv("/wareques/listWareSelect.do?selectType=1&productbaseid="+prodid,1,1,670,300);
	   }
	   
	   function selWareCB(ids,names,productbaseid){
          if(ids!=null&&ids!=""){
             document.getElementById("wareidId").value=ids;
             document.getElementById("warenameId").value=names;
             // cannot change product
             // document.getElementById("prodidId").value=productbaseid;
          }
          clearDiv();
       }
             
       function selectContcate(){
          var prodidId = document.getElementById("prodidId").value;
          if(prodidId==null||prodidId==""){
             alert('请先选择题库!');
             return;
          }
          var url = "#/<%=WebConstant.WebContext_Education %>/prodcont/selectContcateTree.do?queryVO.productbaseid="+prodidId+"&selectType=1";
	      newDiv(url,1,1,300,300);
       }
       
       function selContcateCB(id,name){
           document.getElementById("contentcateidId").value = id;
           document.getElementById("contentcatenameId").value = name;
           clearDiv();
       }
       
       function removeContcate(){
          document.getElementById("contentcateidId").value = '';
          document.getElementById("contentcatenameId").value = '';
       }
          
	//-->
	</script>

  <table class="formTable">
       <tr>
          <td align="left" width="90">题目类型：</td>
          <td align="left" >
			  <bean:writeSaas name="warequesForm" property="vo.questype" consCode="Question.QuesType"/>
          </td>
          <td align="left" width="90"><font color="red">*</font>所在题库：</td>
          <td align="left">
              <bean:write name="warequesForm" property="vo.warename"/>
              <input type="hidden" name="vo.wareid" value='<bean:write name="warequesForm" property="vo.wareid"/>'>
		  </td>
       </tr>
       <tr>
          <td align="left" width="85">题目难度：</td>
		  <td align="left" >
              <html:select name="warequesForm" property="vo.difficultid" style="width: 130px;">
                  <html:option value=""></html:option>
				  <html:optionsSaas consCode="Question.Difficult"/>
			  </html:select>
		  </td>
		  <td align="left" width="85">章节：</td>
          <td align="left" >
             <input type="hidden" id="contentcateidId" name="vo.contentcateid" value="<bean:write name="warequesForm" property="vo.contentcateid"/>" >
             <input type="text" id="contentcatenameId" name="vo.contentcatename" class="readonly" value="<bean:write name="warequesForm" property="vo.contentcatename"/>" readonly="readonly" style="width:200px"/>
       			 <img src="../styles/imgs/ico/search.gif" alt="选择章节" onclick="selectContcate()">
       			 <img src="../styles/imgs/ico/reset.gif" alt="删除章节" onclick="removeContcate()">
		  </td>
       </tr>
   </table>
   
   <div style="background-color: #eeeeee; color: red;">
       <html:errors bundle="BizKey"/>
   </div>

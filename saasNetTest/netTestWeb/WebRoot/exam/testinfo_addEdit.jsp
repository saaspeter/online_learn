<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.exam.constant.TestinfoConstant,netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="testinfoForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="language" name="testinfoForm" property="language" type="java.lang.String"/>
<bean:define id="editType" name="testinfoForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=true;
    String action = "/exam/saveTestinfo.do";
    if(editType!=null&&editType.intValue()==WebConstant.editType_add){
    	action = "/exam/savenewtestinfo.do";
      isDisable = false;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>考试信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/calendar/JSCal2/src/css/jscal2.css"/>
	<link rel="stylesheet" type="text/css" href="../styles/calendar/JSCal2/src/css/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="../styles/calendar/JSCal2/src/css/gold/gold.css" />
    
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/testinfoLogic.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type="text/javascript" src="../styles/calendar/JSCal2/src/js/jscal2.js"></script>
    <script type="text/javascript" src="../styles/calendar/JSCal2/src/js/lang/<%=language %>.js"></script>
	
	<script type="text/javascript">
	   
	    function selPaper(){
	        var productid = document.getElementById("productidId").value;
	        var productname = document.getElementById("productnameId").value;
	        var url = "/paper/selectPaper.do";
	        if(productid==null||productid==''){
	           alert('请先选择考试科目');
	           return;
	        }
	        
            url = url+"?productbaseid="+productid
                    +"&queryVO.productname="+productname;
	      	newDiv(url,1,1,700,400);
	    }
	    
	    function selectPaperCB(ids,names,papertime){
	        document.getElementById("papernameId").value = names;
	        document.getElementById("paperidId").value = ids;
	        document.getElementById('papertimeId').value = papertime;
	        clearDiv();
	    }
	    
	    function saveTest(){
	       var shopidVar = '<bean:write name="testinfoForm" property="shopid"/>';
	       var orgbaseidVar = '<bean:write name="testinfoForm" property="userorgid"/>';
	       var teststartdateVar = document.getElementById("teststartdateId").value;
	       var testenddateVar = document.getElementById("testenddateId").value;
	       var testidIdVar = document.getElementById("testidId").value;
           if(teststartdateVar!=null&&teststartdateVar!=''&&testenddateVar!=null&&testenddateVar!=''){
              testinfoLogic.qryTestCollideAddTest(shopidVar,orgbaseidVar,teststartdateVar,testenddateVar,testidIdVar,
                 function CB_qry(rtnStr){
	                if(rtnStr!=null&&rtnStr!=''){
	                   rtnStr = "考试时间和以下考试时间有冲突："+rtnStr+"  确定继续保存考试?";
	                   if(confirm(rtnStr)){
	                      submitForm('editForm');
	                   }
	                }else{
	                   submitForm('editForm');
	                }
	             }
     	      );
           }else {
              submitForm('editForm');
           }
	    }
	   
	</script>
	
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="<%=action %>" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="testinfoForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="testinfoForm" property="backUrlEncode"/>">
	 <input id="testidId" type="hidden" name="vo.testid" value="<bean:write name="testinfoForm" property="vo.testid"/>" />
	 <input type="hidden" name="vo.testcanstop" value="<%=TestinfoConstant.Testcanstop_no %>" />
	
	  <div id="fieldsTitleDiv">
	     <%if(!isDisable){ %>
	       <bean:message key="netTest.commonpage.add"/>
	     <%}else{ %>
	       <bean:message key="netTest.commonpage.modify"/>
	     <%} %>
	      考试
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
		    <li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>考试名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testname" usage="notempty,max-length:60" fie="考试名称" value="<bean:write name="testinfoForm" property="vo.testname"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			 <li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>可参加考试考生:</div>
			   <div class="field"> 
			       <html:select name="testinfoForm" property="vo.opentype" >
			           <html:optionsSaas consCode="netTest.TestinfoConstant.OpenType"/>
			       </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>考试课程:</div>
			   <div class="field"> 
			       <input id="productnameId" type="text" name="vo.productname" value="<bean:write name="testinfoForm" property="vo.productname"/>" readonly="readonly" onclick="selectProd(this,'<%=UserproductConstant.ProdUseType_operatorMag %>')" usage="notempty" fie="考试课程"  />
			       <input id="productidId" type="hidden" name="vo.productbaseid" value="<bean:write name="testinfoForm" property="vo.productbaseid"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>考试用卷:</div>
			   <div class="field"> 
			       <input type="text" id="papernameId" name="vo.papername" value="<bean:write name="testinfoForm" property="vo.papername"/>" readonly="readonly" onclick="selPaper()" usage="notempty" fie="考试用卷" />
			       <input type="hidden" id="paperidId" name="vo.paperid" value="<bean:write name="testinfoForm" property="vo.paperid"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>答卷时间(分钟):</div>
			   <div class="field"> 
			       <input type="text" id="papertimeId" name="vo.papertime" value="<bean:write name="testinfoForm" property="vo.papertime"/>" usage="notempty,num-range:1:240" fie="答卷时间"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>考试开始时间:</div>
			   <div class="field"> 
			     <input type="text" id="teststartdateId" name="vo.teststartdate" value="<bean:write name="testinfoForm" property="vo.teststartdate" format="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" usage="notempty" fie="考试开始时间"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
		    <li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>考试结束时间:</div>
			   <div class="field"> 
			     <input type="text" id="testenddateId" name="vo.testenddate" value="<bean:write name="testinfoForm" property="vo.testenddate" format="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" usage="notempty" fie="考试结束时间"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"><font color="#ff0000">*</font>考试结果查看类型:</div>
			   <div class="field"> 
			       <html:select styleId="viewresulttypeId" name="testinfoForm" property="vo.viewresulttype" >
			           <html:optionsSaas consCode="netTest.TestinfoConstant.ViewResultType"/>
			       </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<!-- 
			<li>
			   <div class="fieldLabel">*考生在考试中可否暂停:</div>
			   <div class="field"> 
			      <html:select name="testinfoForm" property="vo.testcanstop" >
			         <html:optionsSaas consCode="netTest.TestinfoConstant.testcanstop"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			 
			<li>
			   <div class="fieldLabel">可以中断的时间(分钟):</div>
			   <div class="field"> 
			     <input type="text" name="vo.canstoptime" value="<bean:write name="testinfoForm" property="vo.canstoptime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			-->
            <%if(isDisable){ %>
		    <li>
			   <div class="fieldLabel">考试状态:</div>
			   <div class="field"> 
			       <bean:writeSaas name="testinfoForm" property="vo.teststatus" consCode="netTest.TestinfoConstant.Teststatus"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
		    <li>
			   <div class="fieldLabel">创建单位:</div>
			   <div class="field"> 
			      <bean:write name="testinfoForm" property="vo.createorgname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">创建人:</div>
			   <div class="field"> 
			      <bean:write name="testinfoForm" property="vo.createusername"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">创建时间:</div>
			   <div class="field"> 
			       <bean:write name="testinfoForm" property="vo.createtime" format="yyyy-MM-dd HH:mm:ss"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
            <%} %>
             
            <li>
			   <div class="fieldLabel">考试备注:</div>
			   <div class="field"> 
			      <textarea name="vo.testnotes" rows="2" cols="45"><bean:write name="testinfoForm" property="vo.testnotes"/></textarea>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
		 </ul>
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="bigButton" onclick="saveTest();"><bean:message key="netTest.commonpage.save"/></button></li>
			<li>
			    <%if(isDisable){ %>
			    <button type="button" class="bigButton" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="testinfoForm" property="vo.testid"/>');return false;"><bean:message key="netTest.commonpage.back"/></button>
			    <%}else{ %>
			    <button type="button" class="bigButton" onclick="goUrl('/exam/listTestinfo.do');return false;"><bean:message key="netTest.commonpage.back"/></button>
			    <%} %>
			</li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	
	<script type="text/javascript">
	    Calendar.setup({
	        inputField     :    "teststartdateId",      // id of the input field
	        trigger        :    "teststartdateId",
	        dateFormat     :    "%Y-%m-%d %H:%M:00",       // format of the input field
	        showTime       :    true,
		    onSelect       :    function() { this.hide()}
	    });
	    
	    Calendar.setup({
	        inputField     :    "testenddateId",      // id of the input field
	        trigger        :    "testenddateId",
	        dateFormat     :    "%Y-%m-%d %H:%M:00",       // format of the input field
	        showTime       :    true,
		    onSelect       :    function() { this.hide()}
	    });
	    
	</script>
	
	</div>
  </body>
</html:html>

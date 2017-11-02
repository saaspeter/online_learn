<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.exam.constant.TestuserConstant, netTest.exam.constant.TestinfoConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="testdeptForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="createorgidLong" name="testdeptForm" property="testinfoVO.createorgid" type="java.lang.Long"></bean:define>
    <bean:define id="userorgidLong" name="testdeptForm" property="userorgid" type="java.lang.Long"></bean:define>
    <bean:define id="teststatusShort" name="testdeptForm" property="testinfoVO.teststatus"></bean:define>
    <bean:define id="nextteststatusShort" name="testdeptForm" property="testinfoVO.nextteststatus"></bean:define>
    <bean:define id="satVO" name="testdeptForm" property="vo" type="netTest.exam.vo.Testdept"></bean:define>
    <bean:define id="opentype" name="testdeptForm" property="testinfoVO.opentype"></bean:define>

    <% 
       String editableStr = "";
       if(!TestinfoConstant.Teststatus_unStart.equals(teststatusShort)){
    	   editableStr = "disabled=true";
       }
    %>

    <title>考试详细设置</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/testdept.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	<script type="text/javascript">
        
        function refreashUsers(testdeptidStr){
            testdept.qrytestusernum(testdeptidStr,function CB_refreashuser(rtnArr){
	              if(rtnArr!=null&&rtnArr.length==4){
	                 document.getElementById('examernum_Id').innerText=rtnArr[0];
	                 document.getElementById('endexamnum_Id').innerText=rtnArr[1];
	                 document.getElementById('examingnum_Id').innerText=rtnArr[2];
	                 document.getElementById('notexamnum_Id').innerText=rtnArr[3];
	              }
	           }
	     	);
        }
        
        function changeStatus(testid){
            var nextname = '<bean:writeSaas name="testdeptForm" property="testinfoVO.nextteststatus" consCode="netTest.TestinfoConstant.Control.TestStatus"/>';
            if(testid!=''){
               if(confirm('确定要:'+nextname+'?')){
                  goUrl('/exam/changeStatus.do?vo.testid='+testid);
               }
            }
        }
        
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exam/monitorTest.do" method="post">
  <input id="testidId" type="hidden" name="vo.testid" value="<bean:write name="testdeptForm" property="queryVO.testid"/>">
  <input type="hidden" name="queryVO.testid" value="<bean:write name="testdeptForm" property="queryVO.testid"/>">
  <input type="hidden" name="createorgid" value="<bean:write name="testdeptForm" property="testinfoVO.createorgid"/>">
  
  <div class="titleBar">
	  <a href="#" onclick="newDiv('/exam/viewTestinfo.do?queryVO.testid=<bean:write name="testdeptForm" property="testinfoVO.testid"/>',0,1,600,460);return false;">
	      <font style="font-weight: bold"><bean:write name="testdeptForm" property="testinfoVO.testname"/></font>
	  </a> 
  </div>
  
  <div style="clear:left">
      <table border="0" cellspacing="1" style="width:100%;background-color: #F5FBFE">
	      <tr>
	        <td width="5%">&nbsp;</td>
	        <td width="15%" align="right" class="fontBold">考试有效时间：</td>
	        <td width="35%"><bean:write name="testdeptForm" property="testinfoVO.teststartdate" format="yyyy-MM-dd HH:mm"/>---<br><bean:write name="testdeptForm" property="testinfoVO.testenddate" format="yyyy-MM-dd HH:mm"/></td>
	        <td width="10%" align="right" class="fontBold">答卷时间：</td>
	        <td width="35%"><bean:write name="testdeptForm" property="testinfoVO.papertime"/></td>
	        <td width="5%">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right" class="fontBold">考试组织单位：</td>
	        <td><bean:write name="testdeptForm" property="testinfoVO.createorgname" /></td>
	        <td align="right" class="fontBold">考试状态：</td>
	        <td><table border="0">
	            <tr>
	              <td>
	                  <%if(TestinfoConstant.Teststatus_manualChecking.equals(teststatusShort)){ %>
	                     <a href="javascript:void(0)" onclick="goUrl('/exam/manaualCheckPaper.do?testVO.testid=<bean:write name="testdeptForm" property="testinfoVO.testid"/>');" title="进入手动评阅试卷界面">
	                        <bean:writeSaas name="testdeptForm" property="testinfoVO.teststatus" consCode="netTest.TestinfoConstant.Teststatus"/>
	                     </a>
	                  <%}else { %>
	                        <bean:writeSaas name="testdeptForm" property="testinfoVO.teststatus" consCode="netTest.TestinfoConstant.Teststatus"/>
	                  <%} %>
	              </td>
	              <td>
	                 <%if(createorgidLong!=null&&createorgidLong.equals(userorgidLong)
	                       &&nextteststatusShort!=null&&!TestinfoConstant.Teststatus_manualChecking.equals(teststatusShort)){ %>
	                 |&nbsp;
	                    <button type="button" class="button green fontBold" onclick="changeStatus('<bean:write name="testdeptForm" property="testinfoVO.testid"/>');return false;" title="">    
	                       <bean:writeSaas name="testdeptForm" property="testinfoVO.nextteststatus" consCode="netTest.TestinfoConstant.Control.TestStatus"/>
	                    </button>
	                 <%} %>
	              </td>
	            </tr>
	          </table></td>
	        <td>&nbsp;</td>
	      </tr>
      
    </table>
  </div>
  
  <div class="dashLine"></div>
  
  <div class="functionButtonDiv">
	  <button type="button" class="button green fontBold" onclick="goUrl('/exam/listtestuser.do?queryVO.testid=<bean:write name="testdeptForm" property="queryVO.testid"/>&testdeptid=<bean:write name="testdeptForm" property="vo.testdeptid"/>');return false;">考试人员列表</button>&nbsp;&nbsp;
	  <%if(createorgidLong!=null&&createorgidLong.equals(userorgidLong)){ %>
	  <button type="button" class="button green fontBold" onclick="goUrl('/exam/editTestinfo.do?queryVO.testid=<bean:write name="testdeptForm" property="testinfoVO.testid"/>');" <%=editableStr %> >修改考试信息</button>&nbsp;&nbsp;
	  <button type="button" class="button green fontBold" onclick="goUrl('/exam/listTestchecker.do?queryVO.testid=<bean:write name="testdeptForm" property="testinfoVO.testid"/>&queryVO.orgbaseid=<bean:write name="testdeptForm" property="testinfoVO.createorgid"/>&queryVO.testname=<bean:write name="testdeptForm" property="testinfoVO.testname"/>');return false;">阅卷人员列表</button>
      <%} %>
  </div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>
  <p/>
  <div class="titleBar">
	   参加考试人员动态
  </div>
  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>已考完人数</td>
        <td>正在考试人数</td>
        <td>未开始考试人数</td>
        <td>总数</td>
      </tr>
      </thead>
      <tr>
        <td></td>
        <td id='endexamnum_Id'>
	       <a href="javascript:void(0)" class="font2Large fontBold" onclick="goUrl('/exam/listtestuser.do?queryVO.testid=<bean:write name="testdeptForm" property="queryVO.testid"/>&queryVO.status=<%=TestuserConstant.Status_endExam %>');return false;">
	       <%=satVO.getEndexamnum() %></a>
	    </td>
        <td id='examingnum_Id'>
           <a href="javascript:void(0)" class="font2Large fontBold" onclick="goUrl('/exam/listtestuser.do?queryVO.testid=<bean:write name="testdeptForm" property="queryVO.testid"/>&queryVO.status=<%=TestuserConstant.Status_examing %>');return false;">
           <%=satVO.getExamingnum() %></a>
        </td>
        <td id='notexamnum_Id'>
           <a href="javascript:void(0)" class="font2Large fontBold" onclick="goUrl('/exam/listtestuser.do?queryVO.testid=<bean:write name="testdeptForm" property="queryVO.testid"/>&queryVO.status=<%=TestuserConstant.Status_unStart %>&listjsptype=1');return false;">
           <%=satVO.getNotexamnum() %></a>
        </td>
        <td id='examernum_Id'>
           <span class="font2Large fontBold">
           <%=satVO.getExamernum() %></span>            
        </td>
      </tr>
    </table>
  </div>
  <p>&nbsp;</p>
  <div style="text-align: center;">
  	 <button type="button" class="button green fontBold" onclick="goUrl('/exam/listTestinfo.do');return false;">返回考试列表</button>
  </div>
  </html:form>
  </div>


  </body>
</html:html>

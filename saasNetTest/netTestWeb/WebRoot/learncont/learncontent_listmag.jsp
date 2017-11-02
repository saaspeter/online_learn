<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.UserproductConstant,netTest.learncont.constant.LearncontentConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="learncontentForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productidVar" name="learncontentForm" property="productbaseid" type="java.lang.Long"/>
    <bean:define id="contentcateidVar" name="learncontentForm" property="queryVO.contentcateid" type="java.lang.Long"/>
    <bean:define id="selectCatevoVar" name="learncontentForm" property="usecatevo" type="netTest.prodcont.vo.Contentcate"/>
    <% Long pidVar = null;
       if(selectCatevoVar!=null){
          pidVar = selectCatevoVar.getPid();
       }
       %>
    <title>学习内容</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
	<style type="text/css">
		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
		ul { padding:0;margin:0;}
				
		#navcate {
		   width:100%;
		   line-height: 30px; 
		   list-style: none;
		   text-align:left;
		   /*定义整个ul菜单的行高和背景色*/
		}
		/*==================一级目录===================*/
		#navcate a {
		   width: 100%; 
		   display: block;
		   padding-top: 3px;
		   padding-bottom: 3px;
		   padding-left:10px;
		   /*Width(一定要)，否则下面的Li会变形*/
		}
		#navcate li {
		   width:100%;
		   border-bottom:#FFF 1px solid; /*下面的一条白边*/
		   float:left;
		   overflow: hidden;
		   list-style:none;
		   background-color: #ffffff;
		   margin: 0px;
		   /*float：left,本不应该设置，但由于在Firefox不能正常显示
		   继承Nav的width,限制宽度，li自动向下延伸*/
		}
		
		#navcate li a {
		   
		}
		
		#navcate a:link  {
		   color:#000; text-decoration:none;
		}
		#navcate a:visited  {
		   color:#666;text-decoration:none;
		}
		
		/*==================二级目录===================*/
		#navcate li ul {
		   list-style:none;
		   text-align:left;
		}
		
		#navcate li ul li:hover{
		   background:#eeeeee; /*一级目录onMouseOver显示的背景色*/
		   font-weight:bold;
		}
		
		.expendCate{
		    background-image: url('../styles/imgs/ico/expand_vertical.bmp');
		    background-position:90%;background-repeat:no-repeat;
		    background-attachment:scoll;
		}
		
		.collapseCate{
		    background-image: url('../styles/imgs/ico/collapse_vertical.bmp');
		    background-position:90%;background-repeat:no-repeat;
		    background-attachment:scoll;
		}
		
		.display_block{
		    diaplay: block;
		}
		
		.display_none{
		    diaplay: none;
		}
		
		ul.dropdown-menu a:hover
		{
		  color: #fff;
		  background-color: green;
		  border-color: #fff;
		}
	</style>
	
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script src="../styles/script/vendor/jquery-1.9.1.min.js"></script>
	<script src="../styles/bootstrap/3.3.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	   
	   function toAddLearn(contenttype){
          if(contenttype==null){
             return;
          }
          var prodidvalue = document.getElementById("prodidId").value;
          if(prodidvalue==null || prodidvalue==''){
             alert('请先选择课程!');
             return;
          }
          addRecord('/learncont/addLearncontent.do?vo.productbaseid=<%=(productidVar==null)?"":productidVar.toString() %>&vo.contenttype='
        		    +contenttype+'&vo.contentcateid=<%=contentcateidVar %>');
       }
       
	    function gochapter(contentcateid){
	    	document.getElementById("contentcateidId").value = contentcateid;
	    	document.getElementById("list").submit();
	    }
	    
	    function toggleFolder(obj, order){
	    	var ulobj = document.getElementById("cate_ul_"+order);
	    	var tableobj = document.getElementById("cateLearnTableId_"+order);
	    	var uldisplay = ulobj.style.display;
	    	// if obj class is:expendCate, means: it is expand flag, want to expand
	    	if(obj.className=="expendCate"){
	    		ulobj.style.display=(uldisplay == 'block')?'none' : 'block';
	    		if(typeof(tableobj)!="undefined" && tableobj!=null){
	    		   tableobj.style.display='block';
	    		}
	    		obj.className="collapseCate";
	    	}else if(obj.className=="collapseCate"){
	    		ulobj.style.display=(uldisplay == 'block')?'none' : 'block';
	    		if(typeof(tableobj)!="undefined" && tableobj!=null){
	    		   tableobj.style.display='none';
	    		}
	    		obj.className="expendCate";
	    	}
	    }
	    
	    function deleteExercise(exerid, trId){
	       if(!confirm('确定删除?')){
	          return;
	       }
	       var url = "<%=WebConstant.WebContext%>/exercise/delExercise.do?vo.exerid="+exerid;
           var param = null;
	       var rtnObj = toAjaxUrl(url, false, param);
           if(rtnObj.result=="1"){ 
              showMessagePage(rtnObj.info); 
              var ddTable = document.getElementById("exerDataTableId").tBodies[0];
	       	  var rowNo = document.getElementById(trId).sectionRowIndex;
	          ddTable.deleteRow(rowNo);
           }else {
              showMessagePage(rtnObj.info);
           }
	   }
	    
	   function deletelearncont(pk, trId){
		   if(!confirm('确定删除?')){
	          return;
	       }
	       var url = "<%=WebConstant.WebContext%>/learncont/deleteLearncontent.do?vo.contentid="+pk;
           var param = null;
	       var rtnObj = toAjaxUrl(url, false, param);
           if(rtnObj.result=="1"){ 
              showMessagePage(rtnObj.info); 
              var ddTable = document.getElementById("learnDataTableId").tBodies[0];
	       	  var rowNo = document.getElementById(trId).sectionRowIndex;
	          ddTable.deleteRow(rowNo);
           }else {
              showMessagePage(rtnObj.info);
           }
	   }
	   
	</script>
  </head>

  <body>
  <div>
  <html:form styleId="list" action="/learncont/listLearncontent.do" method="post">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value='<bean:write name="learncontentForm" property="backUrlEncode"/>'>
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=(productidVar==null)?"":productidVar.toString() %>">
  <input id="contentcateidId" type="hidden" name="queryVO.contentcateid" value="<%=contentcateidVar %>">
  <div class="fieldsTitleDiv">
       <bean:write name="learncontentForm" property="productvo.productname"/>
  </div>
  
  <!-- load content category tree -->
  
  <div id="" style="border-right:1px #cccccc solid;margin-left:3px;padding-left:0px;padding-right:7px;float: left;width: 40%;height:80%;">
      <ul id="navcate">
		 <li style="border-bottom: 2px #cccccc solid;">
	         <a id="overviewId" href="javascript:void(0);" style="background-color:#DCEAFC;" onclick="gochapter('-1');return false;">
	                                课程综述:
	            <%if(contentcateidVar!=null&&contentcateidVar.longValue()==-1){%>
                    <img style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
                <%} %>
	         </a>
		 </li>
		 <li style="background: #ffffff;text-align: center;"><span style="text-align: center;font-weight: bolder;font-size:larger;">&dArr;</span></li>
	     <logic:present name="learncontentForm" property="catelist">
	     <logic:iterate id="datavo" name="learncontentForm" property="catelist" indexId="i" type="netTest.prodcont.vo.Contentcate">
         <li>
            <div style="border-bottom: 2px #cccccc solid;">
            <a href="javascript:void(0);" 
               class="" 
               style="background-color:#DCEAFC;" 
               onclick="gochapter('<%=datavo.getContentcateid() %>');return false;">
               <bean:write name="datavo" property="contentcatename"/>
               &nbsp;&nbsp;
               <%if(datavo.getPk().equals(contentcateidVar)){%>
                    <img style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
                <%} %>
            </a>
	        </div>
            <ul id="cate_ul_<%=i %>">
                <logic:present name="datavo" property="sublist">
                <logic:iterate id="subdatavo" name="datavo" property="sublist" indexId="j" type="netTest.prodcont.vo.Contentcate">
	            <li id="cate<%=i %>sub<%=j %>_li" style="background-image:url('../styles/imgs/ico/greendot.png');background-position:6 10px;background-repeat:no-repeat;border-bottom: 2px #cccccc solid;">
	                <div style="margin-left:17px;border-left: 1px solid #cccccc;padding-left:5px;">
	                   <a href="javascript:void(0);" onclick="gochapter('<bean:write name="subdatavo" property="contentcateid"/>');return false;">
	                       <bean:write name="subdatavo" property="contentcatename"/>
	                       &nbsp;&nbsp;
	                       <%if(subdatavo.getPk().equals(contentcateidVar)){%>
                           <img style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
                           <%} %>
	                   </a>
                       
                    </div>
	            </li>
	            </logic:iterate>
	   	        </logic:present>
	   	   </ul>
       </li>
	   </logic:iterate>
	   </logic:present>
	   <logic:notPresent name="learncontentForm" property="catelist">
	        此处显示课程目录，该课程没有课程目录
	   </logic:notPresent>
	 </ul>
  </div>

  <div style="width: 58%;float: right;padding-top: 10px;">
  
  <div style="text-align:center;margin-bottom: 10px;">
      <div class="btn-group" style="margin-right: 40px;">
           <button type="button" data-toggle="dropdown" style="padding-left:30px;padding-right:30px;" class="btn btn-success dropdown-toggle">新增学习资料<span class="caret"></span></button>
           <ul class="dropdown-menu">
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_HTML.toString() %>');"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_HTML.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_VIDEO.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_VIDEO.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_AUDIO.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_AUDIO.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_FLASH.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_FLASH.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li class="divider"></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_PDF.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_PDF.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_WORD.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_WORD.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_PPT.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_PPT.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_EXCEL.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_EXCEL.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
               <li class="divider"></li>
               <li><a href="javascript:void(0)" onclick="toAddLearn('<%=LearncontentConstant.ContentType_Zipped.toString() %>')"><bean:writeSaas valuestatic="<%=LearncontentConstant.ContentType_Zipped.toString() %>" consCode="netTest.Learncontent.contenttype"/></a></li>
           </ul>
       </div>
       <div class="btn-group">
           <button type="button" style="padding-left:30px;padding-right:30px;" class="btn btn-success" onclick="addRecord('/exercise/toAddExerPage.do?vo.productbaseid=<%=(productidVar==null)?"":productidVar.toString() %>&vo.contentcateid=<%=(contentcateidVar==null)?"":contentcateidVar.toString() %>&backUrl=' );return false;">新增课程练习</button>
       </div>
  </div>
  
  <div style="height: 30px;padding-left: 8px;padding-top:5px;background-color: #E8FFC4;">
      <font style="font-weight: bold;">学习资料列表</font>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <%if(productidVar==null){ %>请点击左上角选择课程<%} %>
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table id="learnDataTableId" class="listDataTable" >
      <tbody>
      <logic:present name="learncontentForm" property="usecatevo">
	  <logic:iterate id="vo" name="learncontentForm" property="usecatevo.learncontentlist" indexId="ind" type="netTest.learncont.vo.Learncontent">
      <tr id='<%="learnDataTrId"+ind %>' class='<%=(ind%2==0)?"oddRow":"evenRow" %>'>
        <td>
           <img src="../styles/imgs/filetype/<bean:write name="vo" property="fileicon"/>" >
           <a href='<%=WebConstant.WebContext %>/learncont/viewLearncontent.do?vo.contentid=<bean:write name="vo" property="contentid"/>' title="preview" target="_learncontent">
              <bean:write name="vo" property="caption"/></a>
           <input id='pkId<bean:write name="vo" property="contentid"/>Name' type="hidden" value="<bean:write name="vo" property="caption"/>"/>
           <br><font style="margin-left: 30px;font-size: smaller;">
               (<%if(LearncontentConstant.IsTry_Try.equals(vo.getIstry())){ %>
                    <bean:writeSaas name="vo" property="istry" consCode="netTest.LearncontentConstant.IsTry"/> , 
                <%} %>
               <bean:write name="vo" property="lastupdatetime" format="yyyy-MM-dd"/> 更新)
               </font>
        </td>
        <td style="text-align: right;padding-right: 5px;">
            <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/learncont/editLearncontent.do?vo.contentid=<bean:write name="vo" property="contentid"/>');return false;"/>&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="deletelearncont('<bean:write name="vo" property="contentid"/>','<%="learnDataTrId"+ind %>');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
      <tr>
         <td colspan="3" style="height: 15px;"></td>
      </tr>
      </tbody>
   </table>
   <table id="exerDataTableId" class="listDataTable" >
      <tbody>
      <tr>
         <td colspan="3" style="background-color: #E8FFC4;font-weight: bold;">练习列表&nbsp;&nbsp;
         <img src="../styles/imgs/add2.png" title="<bean:message key="netTest.commonpage.add"/>" style="cursor:pointer;margin: 0px;border:0px;" onclick="addRecord('/exercise/toAddExerPage.do?vo.productbaseid=<%=(productidVar==null)?"":productidVar.toString() %>&vo.contentcateid=<%=(contentcateidVar==null)?"":contentcateidVar.toString() %>&backUrl=' );return false;"/>
         </td>
      </tr>
      <logic:present name="learncontentForm" property="usecatevo">
      <logic:iterate id="vo" name="learncontentForm" property="usecatevo.exerlist" indexId="ind">
      <tr id='<%="exerDataTrId"+ind %>' class='<%=(ind%2==0)?"oddRow":"evenRow" %>'>
        <td>
           <img src="../styles/imgs/exam.png" >
           <a href='javascript:void(0);' onclick="openWin('/exercise/preEnterExercise.do?exerid=<bean:write name="vo" property="exerid"/>',750,550,'yes','yes');return false;" title="preview and Test" target="_learncontent">
              <bean:write name="vo" property="exername"/>
           </a>
           <br><font style="margin-left: 30px;font-size: smaller;">(updated: <bean:write name="vo" property="moditime" format="yyyy-MM-dd"/>)</font>
        </td>
        <td style="text-align: right;padding-right: 5px;">
            <img src="../styles/imgs/people.png" title="练习人员" style="cursor:pointer;" onclick="goUrl('/exercise/listExeruser.do?queryVO.exerid=<bean:write name="vo" property="exerid"/>&backUrlEncode=','backUrlEncode');return false;"/>
            <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/exercise/editExercise.do?vo.exerid=<bean:write name="vo" property="exerid"/>&backUrlEncode=','backUrlEncode');return false;"/>
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="deleteExercise('<bean:write name="vo" property="exerid"/>','<%="exerDataTrId"+ind %>');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
      </tbody>
    </table>
  </div>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </body>
</html:html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.paper.vo.Paperquestype,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="paperid_var" name="paperForm" property="vo.paperid" type="java.lang.Long"/>
<bean:define id="questypeidUse" name="paperForm" property="questypeidUse" type="java.lang.Long"/>
  <%
    int showMeidaType = 0;
    Long questypeidTemp = null;
    String patternWarequesidStr = "";
   %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>试卷预览</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

    <script type="text/javascript" src="../styles/script/swfobject.js"></script>
    <script type="text/javascript">
       var itemFlagArr = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
       var quesidVar = '';
       var questypeVar = '';
       var paperquesscoreVar = '';
       var quesoptnumVar = '';
       var questypeidVar = '';
       var actionVar = 0;// 1为新增，2为更换题目
       
       function changeQues(quesid,questype,paperquesscore,quesoptnum,questypeid){
          quesidVar = quesid;
          questypeVar = questype;
          paperquesscoreVar = paperquesscore;
          quesoptnumVar = quesoptnum;
          questypeidVar = questypeid;
          actionVar = 2;
          var warequesIdStrVar = document.getElementById("warequesIdStrId"+questypeid).value; 
          
          var productbaseid = '<bean:write name="paperForm" property="vo.productbaseid"/>';
          var productname = '<bean:write name="paperForm" property="vo.productname"/>';
              productname = encodeURIComponent(productname);
          var wareidstr = '<bean:write name="paperForm" property="vo.wareidstr"/>';
          var warenamestr = '<bean:write name="paperForm" property="vo.warenamestr"/>';
              warenamestr = encodeURIComponent(warenamestr);
          
	      newDiv("/wareques/selWareques.do?selectType=1&queryVO.productbaseid="+productbaseid
	             +"&queryVO.questype="+questype+"&selquesidStr="+warequesIdStrVar
	             +"&queryVO.wareidStr="+wareidstr+"&queryVO.productname="+productname
	             +"&queryVO.warenameStr="+warenamestr,0,1,750,500);

	   }
	   
	   function addQues(questype,paperquesscore,questypeid){
          questypeVar = questype;
          paperquesscoreVar = paperquesscore;
          questypeidVar = questypeid;
          actionVar = 1;
          var warequesIdStrVar = document.getElementById("warequesIdStrId"+questypeid).value; 
          
          var productbaseid = '<bean:write name="paperForm" property="vo.productbaseid"/>';
          var productname = '<bean:write name="paperForm" property="vo.productname"/>';
              productname = encodeURIComponent(productname);
          var wareidstr = '<bean:write name="paperForm" property="vo.wareidstr"/>';
          var warenamestr = '<bean:write name="paperForm" property="vo.warenamestr"/>';
              warenamestr = encodeURIComponent(warenamestr);
          var url = "/wareques/selWareques.do?selectType=2&queryVO.productbaseid="+productbaseid
	             +"&queryVO.questype="+questype+"&selquesidStr="+warequesIdStrVar
	             +"&queryVO.productname="+productname;

	      newDiv(url,0,1,750,450);
	   }
	   
       function selWarequesCB(ids){
          clearDiv();
                 
          if(ids!=null&&ids!=""){
             var url = '';
             if(actionVar==1){
                url = '/paper/addPaperQues.do?'
                      +'vo.paperid=<%=paperid_var %>'
                      +'&vo.questype='+questypeVar
                      +'&vo.paperquesscore='+paperquesscoreVar
                      +'&vo.questypeid='+questypeidVar
                      +'&quesidStr='+ids;
             }else if(actionVar==2){
                url = '/paper/changeQues.do?vo.quesid='+quesidVar
                      +'&vo.paperid=<%=paperid_var %>'
                      +'&vo.questype='+questypeVar
                      +'&vo.paperquesscore='+paperquesscoreVar
                      +'&vo.quesoptnum='+quesoptnumVar
                      +'&vo.questypeid='+questypeidVar
                      +'&quesidStr='+ids;
             }
             if(url!=''){
                goUrl(url);
                // clear varibles 
                actionVar = 0;
                quesidVar = '';
                questypeVar = '';
                paperquesscoreVar = '';
             }
          }
       }
       
       function switchQuestype(questypeid,questype){
           if(questype!=null&&questype!=''){
              var url = '/paper/editPaper.do?';
              //document.getElementById("questypeUseId").value = questype;
              goUrl(url+'queryVO.paperid=<%=paperid_var %>'
                    +'&questypeidUse='+questypeid+'&backUrlEncode=','backUrlEncode');
           }
       }
       
       function showSolve(id){
          var solvTr = document.getElementById(id);
          if(solvTr.style.display=='none'){
             solvTr.style.display='';
          }else {
             solvTr.style.display='none';
          }
       }
       
    </script>
  </head>
  
  <body>
	<div>
	  <input type="hidden" name="queryVO.paperid" value='<%=paperid_var %>'/>
	  <input type="hidden" id="editbackurlId" value='/paper/editPaper.do?queryVO.paperid=<%=paperid_var %>'/>
	  <center>
      <div style="width:95%;font-size:medium;text-align: left"> 
      <logic:notEmpty name="paperForm" property="vo.questypeList">
	       <logic:iterate id="paperQuesTypeShowVO" name="paperForm" property="vo.questypeList" type="netTest.paper.vo.Paperquestype"> 
	           <%if(paperQuesTypeShowVO.getQuestypeid().equals(questypeidUse)){ %>
	           <font style="color: red;">
	                <bean:write name="paperQuesTypeShowVO" property="questypename"/>
	           </font>            
	           <img src="../styles/imgs/edit.png" title="修改题型" style="cursor:pointer;" onclick="newDiv('/paper/editpaperquespatt.do?&queryVO.patternid=<bean:write name="paperQuesTypeShowVO" property="patternid"/>',1,1,600,400);return false;"/>&nbsp;
		       <img src="../styles/imgs/delete.png" title="删除题型" style="cursor:pointer;" onclick="delSingleRecAjax('/paper/delpaperquespatt.do?vo.patternid=<bean:write name="paperQuesTypeShowVO" property="patternid"/>&backUrl=','editbackurlId');return false;"/>  
	           <%}else{ %>
	           <a href="javascript:void(0)" onclick="switchQuestype('<%=paperQuesTypeShowVO.getQuestypeid() %>','<%=paperQuesTypeShowVO.getQuestype() %>');return false;">
	              <bean:write name="paperQuesTypeShowVO" property="questypename"/>
	           </a>
	           <%} %>
	           &nbsp;|&nbsp;
	       </logic:iterate>
	  </logic:notEmpty>
      </div>
      <div style="width:95%;font-size:medium;text-align: right;padding-right: 20px;margin-top: 10px;"> 
           <button type="button" class="button green fontBold" onclick="newDiv('/paper/addpaperquespatt.do?vo.paperid=<%=paperid_var %>',1,1,600,300);">新增题型</button>
      </div>
      <br>
      <% int questionNO = 1; %>
	  
      <!-- 循环显示题型开始 -->          
	  <logic:notEmpty name="paperForm" property="vo.questypeList">
	     <logic:iterate id="paperQuesTypeVO" name="paperForm" property="vo.questypeList" type="netTest.paper.vo.Paperquestype"> 
          <% 
             questypeidTemp = paperQuesTypeVO.getQuestypeid();
             patternWarequesidStr = ""; 
             if(questypeidTemp.equals(questypeidUse)){ %>
	       <div>
	          <table width="95%">
	             <tr style="font-size: medium;font-weight: bold">            
	                <td>
	              	   <bean:write name="paperQuesTypeVO" property="questypename"/>(总分:
	                   <bean:write name="paperQuesTypeVO" property="patternscore"/>，每题
	                   <bean:write name="paperQuesTypeVO" property="quesscore"/>分)
	                   &nbsp;&nbsp;
	                   <a href="javascript:void(0)" onclick="addQues('<%=paperQuesTypeVO.getQuestype() %>','<bean:write name="paperQuesTypeVO" property="quesscore"/>','<bean:write name="paperQuesTypeVO" property="questypeid"/>');return false;">
	                                                     新增试题</a> 
	                   <p>
	                   <bean:write name="paperQuesTypeVO" property="questypenote"/>
	                </td>        
	             </tr>
	          </table>       
	          <logic:notEmpty name="paperQuesTypeVO" property="paperquesList">
	          <logic:iterate id="paperQuesVO" name="paperQuesTypeVO" property="paperquesList" indexId="ind" type="netTest.paper.vo.Paperques">
	             <%  if(paperQuesVO.getQuesid()!=null){
	                    patternWarequesidStr = patternWarequesidStr+paperQuesVO.getQuesid().toString()+",";
	                 }
	              %>
	          <table style="width:100%;padding:3px;border:2px solid #eeeeee;">
	             <tr>
	                <td style="padding:5px;" bgcolor="#ECF5FF"><% out.print(questionNO+".&nbsp;");questionNO++; %>
	                    <bean:writeTK name="paperQuesVO" property="question" cutStr="false" questypeProp="questype" filter="false" transfer="true" showIndentWay="2" inputName="kongge"/>&nbsp;&nbsp;
	                    (<a href="javascript:void(0)" onclick="changeQues('<bean:write name="paperQuesVO" property="quesid"/>','<bean:write name="paperQuesVO" property="questype"/>','<bean:write name="paperQuesVO" property="paperquesscore"/>','<bean:write name="paperQuesVO" property="quesoptnum"/>','<bean:write name="paperQuesVO" property="questypeid"/>');return false;">
	                                                          更换</a>&nbsp;|&nbsp;
	                    <a href="javascript:void(0)" onclick="goUrl('/wareques/editWareques.do?queryVO.quesid=<bean:write name="paperQuesVO" property="quesid"/>&editquesid=<bean:write name="paperQuesVO" property="quesid"/>&paperid=<%=paperid_var %>&backUrlEncode=','backUrlEncode');return false;">
	                                                          修改</a><img src="../styles/imgs/help.gif" title='修改题目会同时修改题库中题目和所有试卷中相同的题目'/>|&nbsp;
	                    <a href="javascript:void(0)" onclick="deleteSingleRec('/paper/deletePaperques.do?vo.paperid=<%=paperid_var %>&vo.paperquesid=<bean:write name="paperQuesVO" property="paperquesid"/>&vo.questype=<bean:write name="paperQuesVO" property="questype"/>&vo.paperquesscore=<bean:write name="paperQuesVO" property="paperquesscore"/>&vo.quesoptnum=<bean:write name="paperQuesVO" property="quesoptnum"/>&vo.questypeid=<bean:write name="paperQuesVO" property="questypeid"/>');return false;">
	                                                          删除</a>)
	                </td>
	            </tr>
	            <!-- 显示题目图片 -->
	            
	            <logic:equal name="paperQuesVO" property="filetype" value="<%=QuestionConstant.FileType_pict.toString() %>">
		            <tr>
		               <td align="left" style="padding-left: 50px;">
		                   <img src="<%=WebConstant.FileContext %><bean:write name="paperQuesVO" property="fileurl"/>" height="100">
		               </td>
		            </tr>
		            <% showMeidaType = 0; %>
		        </logic:equal>
	            <logic:equal name="paperQuesVO" property="filetype" value="<%=QuestionConstant.FileType_voice.toString() %>">
	                <% showMeidaType = 20; %>
	            </logic:equal>
	            
	            <% if(showMeidaType!=0){ %>
	                <tr>
	                   <td align="left" id="container<%=ind %>" style="padding-left: 50px;">hid<script type="text/javascript">
							 var s1 = new SWFObject("../styles/player.swf","container<%=ind %>","328","<%=showMeidaType %>","9","#123456");
							 s1.addParam("allowfullscreen","false");
							 s1.addParam("allowscriptaccess","always");
							 s1.addParam('flashvars','file=<bean:write name="paperQuesVO" property="fileurl"/>');
							 s1.write("container<%=ind %>");
					      </script>
				       </td>
				    </tr>
	            <% showMeidaType=0; } %>
	            
	            <% if(QuestionConstant.QuesType_DanXuan.equals(paperQuesTypeVO.getQuestype())||
	                  QuestionConstant.QuesType_DuoXuan.equals(paperQuesTypeVO.getQuestype())){ %>
	                 <logic:iterate id="quesItemVO" name="paperQuesVO" property="itemList" indexId="itemInd">
		             <tr>
		                <td>
		                   <div> 
		                       <span style="width:16px;">
		                       <logic:equal name="quesItemVO" property="isright" value='<%=QuestionConstant.IsRight_right.toString() %>'>
		                          <img src="../styles/imgs/ico/check.gif" border="0"/>
		                       </logic:equal>
		                       </span>
		                       &nbsp;<script>document.write(itemFlagArr[<%=itemInd %>]+'.'+'&nbsp;&nbsp;')</script><bean:write name="quesItemVO" property="quesitemcontent"/>
		                       
		                   </div>
		                   <logic:equal name="quesItemVO" property="filetype" value="<%=QuestionConstant.FileType_pict.toString() %>">
				              <div style="background: #eeeeee;padding-left: 50px;"> 
				                  <img src="<%=WebConstant.FileContext %><bean:write name="quesItemVO" property="fileurl"/>" height="100">
				              </div>
				           </logic:equal>
		                </td>
		             </tr>
		             </logic:iterate>
	            <% } %>
	            
	            <logic:equal name="paperQuesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_PanDuan.toString() %>" >
		             <tr>
	                    <td align="left">&nbsp;&nbsp;答案:&nbsp;&nbsp;
	                       <logic:equal name="paperQuesVO" property="answersim" value="<%=QuestionConstant.IsRight_right.toString() %>">正确</logic:equal>
	                       <logic:equal name="paperQuesVO" property="answersim" value="<%=QuestionConstant.IsRight_wrong.toString() %>">错误</logic:equal>
	                    </td>
	             	 </tr>
             	</logic:equal>
             	
             	<logic:equal name="paperQuesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_TianKong.toString() %>" >
             	
             	</logic:equal>
                
                <logic:equal name="paperQuesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_WenDa.toString() %>" >
             	     <tr>
		                <td>参考答案：
		                   <bean:write name="paperQuesVO" property="answerVO.answertext" filter="false"/>
		                </td>
		             </tr>
             	</logic:equal>
                        
			    <logic:equal name="paperQuesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_WanXingTianKong.toString() %>" >
			        <tr>
			           <td>
			              <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题</font></div>
			              <div style="height:500px;overflow: auto">
			              <logic:present name="paperQuesVO" property="subquesList">
			              <logic:iterate id="subquesVO" name="paperQuesVO" property="subquesList" indexId="indques">
                              <div>
						      <table class="formTable2">
						         <tr style="background-color:#DCEAFC;" align="center">
						             <td width="16"><%=indques+1 %>.&nbsp;</td>
						             <td>
						                <bean:write name="subquesVO" property="question"/>&nbsp;&nbsp;
						             </td>
						         </tr>
						         <logic:equal value="<%=QuestionConstant.Rowitems_One.toString() %>" name="subquesVO" property="rowitems">
					             <logic:iterate id="quesItemVO1" name="subquesVO" property="itemList" indexId="inditem">
					             <tr>
					                <td align="center" width="16"> 
					                   <logic:equal name="quesItemVO1" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
					                      <img src="../styles/imgs/ico/check.gif" border="0"/>
					                   </logic:equal>
					                </td>
					                <td>
					                   &nbsp;<bean:write name="quesItemVO1" property="quesitemcontent"/>
					                </td>
					             </tr>
					             </logic:iterate>
					             </logic:equal>
					             <logic:equal value="<%=QuestionConstant.Rowitems_AllInOneRow.toString() %>" name="subquesVO" property="rowitems">
					             <tr>
					                <td align="center" width="16"></td>
					                <td>
					                   <logic:iterate id="quesItemVO" name="subquesVO" property="itemList" indexId="inditem">
						                   <span style="margin-right:10px;margin:4px;padding:2px; border: 1px solid #cccccc;">
						                   <logic:equal name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
						                      <img src="../styles/imgs/ico/check.gif" border="0" style="vertical-align: middle"/>
						                   </logic:equal>
						                   <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=inditem %>]+'.)');</script></font>
						                   <bean:write name="quesItemVO" property="quesitemcontent"/>
						                   </span>
					                   </logic:iterate>
					                </td>
					             </tr>
					             </logic:equal>
					          </table>
						      </div>
			              </logic:iterate>
			              </logic:present>
			              </div>
			            </td>
			        </tr>
			    </logic:equal>
			    
			    <logic:equal name="paperQuesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_YueDuLiJie.toString() %>" >
			        <tr>
			          <td>
			              <logic:iterate id="subquesVO1" name="paperQuesVO" property="subquesList" indexId="indques1" >
					      <div>
						      <table class="formTable2">
						         <tr style="background-color:#DCEAFC">
						             <td width="16"><%=indques1+1 %>.&nbsp;</td>
						             <td>
						                 <bean:write name="subquesVO1" property="question"/>&nbsp;&nbsp;
						             </td>
						         </tr>
						         <logic:equal value="<%=QuestionConstant.Rowitems_One.toString() %>" name="paperQuesVO" property="rowitems">
					             <logic:iterate id="quesItemVO2" name="subquesVO1" property="itemList" >
					             <tr>
					                <td align="center" width="16"> 
					                   <logic:equal name="quesItemVO2" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
					                      <img src="../styles/imgs/ico/check.gif" border="0"/>
					                   </logic:equal>
					                </td>
					                <td>
					                   &nbsp;<bean:write name="quesItemVO2" property="quesitemcontent"/>
					                </td>
					             </tr>
					             </logic:iterate>
					             </logic:equal>
					             <logic:equal value="<%=QuestionConstant.Rowitems_AllInOneRow.toString() %>" name="paperQuesVO" property="rowitems">
					             <tr>
					                <td align="center" width="16"></td>
					                <td>
					                   <logic:iterate id="quesItemVO2" name="subquesVO1" property="itemList" indexId="inditem">
						                   <span style="margin-right:10px;margin:4px;padding:2px; border: 1px solid #cccccc;">
						                   <logic:equal name="quesItemVO2" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
						                      <img src="../styles/imgs/ico/check.gif" border="0" style="vertical-align: middle"/>
						                   </logic:equal>
						                   <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=inditem %>]+'.)');</script></font>
						                   <bean:write name="quesItemVO2" property="quesitemcontent"/>
						                   </span>
					                   </logic:iterate>
					                </td>
					             </tr>
					             </logic:equal>
					          </table>
						  </div>
						  </logic:iterate>
			          </td>
			       </tr>
			    </logic:equal>
			    
			    <%if(paperQuesVO.getAnswerVO()!=null&&paperQuesVO.getAnswerVO().getSolvedesc()!=null){ %>
			       <tr>
			          <td style="padding-left:20px;margin-top:10px;background-color:#eeeeee;text-align: left;font-weight: bold">
			              <a href="javascript:showSolve('solveTr<%=paperQuesVO.getQuesid() %>');">查看解题思路&gt;&gt;&gt;&gt;</a></td>
			       </tr>
			       <tr id="solveTr<%=paperQuesVO.getQuesid() %>" style="display:none;">
			          <td style=""><%=paperQuesVO.getAnswerVO().getSolvedesc() %></td>
			       </tr>
			    <%} %>
			   
		      </table>
		      <br>

	          </logic:iterate>
	          </logic:notEmpty>
	          <br>    
	          <input type='hidden' id='warequesIdStrId<%=questypeidTemp.toString() %>' value='<%=patternWarequesidStr %>'/>
	       </div>
	       <%} %>
	     </logic:iterate>  
	  </logic:notEmpty>          
						
	<!-- 循环显示题型结束 -->
	</center>
	</div>
  </body>
</html:html>

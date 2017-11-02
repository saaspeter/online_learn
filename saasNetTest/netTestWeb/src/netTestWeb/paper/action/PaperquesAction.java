
package netTestWeb.paper.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.paper.logic.PaperquesLogic;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperques;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Questionitem;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.paper.form.PaperquesForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.exception.LogicException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.AssertUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class PaperquesAction extends BaseAction {
	
	static Logger log = Logger.getLogger(PaperquesAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		PaperquesForm theForm = (PaperquesForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} 
//		else if ("savePaperques".equals(myaction)) {
//			myforward = savePaperques(mapping, actionform, request,response);
//		} else if ("editPaperques".equals(myaction)) {
//			theForm.setEditType(WebConstant.editType_edit);
//			myforward = editPage(mapping, actionform, request,response);
//		} else if ("editSubques".equals(myaction)) {
//			myforward = editSubques(mapping, actionform, request,response);
//		} 
		else if ("deletePaperques".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("changeQues".equals(myaction)) {
			myforward = changeQues(mapping, actionform, request,response);
		} else if ("addPaperQues".equals(myaction)) {
			myforward = addPaperQues(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward; 
	}
			
	/** 
	 * Method savePaperques:对于保存独立题目的情况。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
//	public ActionForward savePaperques(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		String messCode = KeyInMemoryConstant.modifySuccess;
//		if(!isTokenValid(request)){
//			saveToken(request);
//			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
//		}else{
//			resetToken(request);
//		}
//		LoginInfoEdu loginfo = getLoginInfo();
//		Long userid = loginfo.getUserid();
//		PaperquesForm theForm = (PaperquesForm) form;
//		Paperques vo = theForm.getVo();
//		Quesanswer answerVO = theForm.getAnswerVO();
//		String delItemStr = theForm.getDelItemStr(); // 需要删除的答案选项
//		List itemList = theForm.getItemList();
//		// 题目暂时不支持视频，也不支持用fileurl方式
////		// 处理fileurl的播放方式
////		String fileurl = vo.getFileurl();
////		if(fileurl!=null&&QuestionConstant.FileType_video.equals(vo.getFiletype())){
////			if("1".equals(theForm.getFileurlplaytype())){
////				vo.setFileurl(QuestionConstant.FileUrl_UsePlayer_Prefix+fileurl);
////			}
////		}
//		// 新增或保存题目
//        this.saveQuesAction(vo, userid, itemList, answerVO, delItemStr, null, messCode);
//		// set messag page parameters.
//		theForm.setHasUrlSubmit(1); // 因为该提交是文件流提交，故super.reset()方法失效，在此手动设置
//		theForm.classSetBackUrlEncode("editPaper.do?queryVO.paperid="+vo.getPaperid()+"&questypeidUse="+vo.getQuestype());
//		this.setMessagePage(request, theForm, null, null, null);
//		return mapping.findForward("toUrl");
//	}
		
//	/** 
//	 * Method edit
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return ActionForward
//	 */
//	public ActionForward editPage(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		saveToken(request);
//		PaperquesForm theForm = (PaperquesForm) form;
//		Paperques queryVO = theForm.getVo();
//		Long pk = queryVO.getQuesid();
//		Long shopid = getLoginInfo().getShopid();
//		
//		PaperquesLogic logic = BOFactory.getPaperquesLogic();
//		Paperques vo = (Paperques)logic.qryQuesByPk(pk, shopid);
//		if(vo==null)
//			throw new NoSuchRecordException("--class:WarequesAction;--method:editPage;");
//		theForm.setVo(vo);
//		if(vo.getAnswerVO()!=null)
//			theForm.setAnswerVO(vo.getAnswerVO());
//		//
//		theForm.setEditquesid(String.valueOf(vo.getQuesid()));
//		String url = toUrl(vo.getQuestype(),theForm.getEditType());
//		return mapping.findForward(url);
//	}
	
	/** 
	 * Method 更换试卷题目
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward changeQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		PaperquesForm theForm = (PaperquesForm) form;
		Paperques vo = theForm.getVo();
		vo.setShopid(shopid);
		String quesidStr = theForm.getQuesidStr();
		Long warequesid = new Long(quesidStr.trim());
		PaperquesLogic logic = BOFactory.getPaperquesLogic();
		//TODO 需要在页面确保vo中有questypeid
		logic.changQues(vo.getPaperid(),vo, warequesid);
		
		theForm.classSetBackUrlEncode("editPaper.do?queryVO.paperid="+vo.getPaperid()+"&questypeUse="+vo.getQuestype());
		this.setMessagePage(request, theForm, null, null, null);
		return mapping.findForward("toUrl");
	}
	
    /**
     * 新增试卷题目
     */
	public ActionForward addPaperQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		PaperquesForm theForm = (PaperquesForm) form;
		Paperques vo = theForm.getVo();
		vo.setShopid(shopid);
		String quesidStr = theForm.getQuesidStr();
		PaperquesLogic logic = BOFactory.getPaperquesLogic();
		//TODO 需要在页面确保vo中有questypeid
		logic.addSelPaperques(vo.getPaperid(),vo, quesidStr);
		
		theForm.classSetBackUrlEncode("editPaper.do?queryVO.paperid="+vo.getPaperid()+"&questypeidUse="+vo.getQuestypeid());
		this.setMessagePage(request, theForm, null, null, null);
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method editSubques:处理复合题目,包括新增主题的材料，新增子题目，编辑子题目，删除子题目
	 * 阅读理解；完形填空；材料题
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
//	public ActionForward editSubques(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		saveToken(request);
//		Long shopid = getLoginInfo().getShopid();
//		Long userid = getLoginInfo().getUserid();
//		PaperquesForm theForm = (PaperquesForm) form;
//		
//		Paperques vo = theForm.getVo();
//		Paperques vo2 = theForm.getVo2();
//		Quesanswer answerVO = theForm.getAnswerVO(); // 仅对主题目起作用
//		String delItemStr = theForm.getDelItemStr(); // 需要删除的答案选项,只对子题目起作用
//		List itemList = theForm.getItemList();
//		String subquesOpt = theForm.getSubquesOpt(); // 页面操作动作
//		PaperquesLogic logic = BOFactory.getPaperquesLogic();
//		
//		// 保存界面上的需要保存的题目，一般vo和vo2中只有一个的question不为空
//		if(vo!=null&&vo.getQuestion()!=null&&vo.getQuestion().trim().length()>0){
//		   if(vo.getQuesid()==null){
//			  vo.setComptype(QuestionConstant.Comptype_compWhole);
//		   }
//		   Long pk = this.saveQuesAction(vo, userid, null, answerVO, null, null,null);
//		   vo.setQuesid(pk);
//		}
//		if(vo2!=null&&vo2.getQuestion()!=null&&vo2.getQuestion().trim().length()>0){
//		   if(vo2.getQuesid()==null){
//		      vo2.setComptype(QuestionConstant.Comptype_compPart);
//		      vo2.setPaperid(vo.getPaperid());
//		      vo2.setPid(vo.getQuesid());
//		      vo2.setPaperquesscore(vo.getPaperquesscore());
//		      vo2.setQuestypeid(vo.getQuestypeid());
//		   }
//		   this.saveQuesAction(vo2, userid, itemList, null, delItemStr, vo.getQuestype(), null);
//		}
//		// 转向页面
//		if(subquesOpt!=null){
//			if("4".equals(subquesOpt)){ //4删除子题目
//				//String[][] delKeys = theForm.getDelKeys();
//				if(QuestionConstant.QuesType_XZ_NoTrunk.equals(vo2.getQuestype())
//					&&theForm.getDelquesid()!=null&&theForm.getDeldisorder()!=null)
//				{  // 完形填空题子题目的删除fuck
//				   //TODO 需要在页面确保vo中有questypeid				   
//				   logic.delPaperSubQues_WXTK(vo.getPaperid(),shopid,theForm.getDelquesid(),vo.getQuestypeid(), theForm.getDeldisorder(),vo.getPaperquesscore());
//				}else{
//				   if(theForm.getDelquestype()!=null){
//				      logic.delSubques(vo.getPaperid(), theForm.getDelquesid(), vo.getQuestypeid(), theForm.getDelquestype(), vo.getPaperquesscore(), shopid, vo.getQuesid());
//				   }
//				}
//				theForm.setEditquesid(String.valueOf(vo.getQuesid())); // 使主题目成为可编辑状态
//			}else if("5".equals(subquesOpt)){ // 交换题目
//				Questionitem itemVO = new Questionitem();
//				itemVO.setQuesid(vo2.getQuesid());
//				itemVO.setDisorder(vo2.getDisorder());
//				itemVO.setShopid(shopid);
//				logic.switDisorderPaperQues(vo.getPaperid(),itemVO, theForm.getSwitchtype());
//			}
//		}
//		// 查询复合题目
//		vo = (Paperques)logic.qryQuesByPk(vo.getQuesid(), shopid);
//		if(subquesOpt!=null&&"1".equals(subquesOpt)){
//           // 新增子题目
//		   Paperques subvo = new Paperques();
//		   subvo.setPaperid(vo.getPaperid());
//		   if(vo!=null&&vo.getSubquesList()!=null
//				   &&QuestionConstant.QuesType_WanXingTianKong.equals(vo.getQuestype())){
//			   Integer maxdisorder = logic.qryLargerDisorder(vo.getQuesid(),null);
//			   if(maxdisorder==null)
//				   maxdisorder = 1;
//			   else
//				   maxdisorder = maxdisorder+1;
//			   subvo.setDisorder(maxdisorder);
//		   }
//		   subvo.setPid(vo.getQuesid()); 
//		   subvo.setQuestype(QuestionConstant.getSubquestype(vo.getQuestype())); //根据主题目的类型决定自题目的类型
//		   preHandleAddPage(subvo);
//		   vo.getSubquesList().add(subvo);
//		}
//		if(vo.getAnswerVO()!=null){
//		   theForm.setAnswerVO(vo.getAnswerVO());
//		}
//		theForm.setVo(vo);
//		
//		String url = toUrl(vo.getQuestype(),WebConstant.editType_add);
//		ActionForward forward = null;
//		if(subquesOpt!=null&&"0".equals(subquesOpt)){
//			theForm.classSetBackUrlEncode("editPaper.do?queryVO.paperid="+vo.getPaperid()+"&questypeidUse="+vo.getQuestypeid());
//			super.setMessagePage(request,theForm, null, null, null);
//			forward = mapping.findForward("toUrl");
//		}else
//			forward = mapping.findForward(url);
//		return forward;
//	}
	
	/** 
	 * Method delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		PaperquesForm theForm = (PaperquesForm) form;
		Paperques vo = theForm.getVo();
		PaperquesLogic logic = BOFactory.getPaperquesLogic();
		//删除题目和更新分数
		//TODO 需要在页面确保vo中有questypeid		
		logic.delPaperques(vo.getPaperid(),vo.getPaperquesid(),vo.getQuestypeid(),vo.getQuestype(),vo.getPaperquesscore(),vo.getQuesoptnum(), shopid);
		theForm.classSetBackUrlEncode("editPaper.do?queryVO.paperid="+vo.getPaperid()+"&questypeUse="+vo.getQuestype());
		this.setMessagePage(request, theForm, null, null, null);
		return mapping.findForward("toUrl");
	}
	
	// 根据题目类型初始化题目vo
	private void preHandleAddPage(Paperques vo){
		if(vo==null)
			return;
		Integer questype = vo.getQuestype();
		if(QuestionConstant.QuesType_DanXuan.equals(questype)
				||QuestionConstant.QuesType_DuoXuan.equals(questype)
				||QuestionConstant.QuesType_XZ_NoTrunk.equals(questype))
		{
			// 新增页面默认添加4个选项
			List list = new ArrayList();
			Questionitem item1 = new Questionitem();
			   item1.setFiletype(QuestionConstant.FileType_None);
			   item1.setDisorder(vo.getDisorder());
			Questionitem item2 = new Questionitem();
			   item2.setFiletype(QuestionConstant.FileType_None);
			   item2.setDisorder(vo.getDisorder());
			Questionitem item3 = new Questionitem();
			   item3.setFiletype(QuestionConstant.FileType_None);
			   item3.setDisorder(vo.getDisorder());
			Questionitem item4 = new Questionitem();
			   item4.setFiletype(QuestionConstant.FileType_None);
			   item4.setDisorder(vo.getDisorder());
			list.add(item1);
			list.add(item2);
			list.add(item3);
			list.add(item4);
			vo.setItemList(list);
		}
	}
	
	// 根据题目类型决定转向的页面
	private String toUrl(Integer questype,int editType){
		if(questype==null)
			return "";
		String url = "";
		if(QuestionConstant.QuesType_DanXuan.equals(questype)){
			if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
			   url = "ques_danxuan";
			else if(WebConstant.editType_view==editType)
			   url = "view_ques_xuanze";
		}else if(QuestionConstant.QuesType_DuoXuan.equals(questype)){
			if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
			   url = "ques_duoxuan";
			else if(WebConstant.editType_view==editType)
			   url = "view_ques_xuanze";
		}
//		else if(QuestionConstant.QuesType_CaiLiao.equals(questype)){
//			if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
//			   url = "ques_cailiao";
//			else if(WebConstant.editType_view==editType)
//			   url = "view_ques_cailiao";
//	    }
		else if(QuestionConstant.QuesType_PanDuan.equals(questype)){
	    	if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
			   url = "ques_panduan";
	    	else if(WebConstant.editType_view==editType)
	    	   url = "view_ques_panduan";
	    }
//		else if(QuestionConstant.QuesType_PeiDui.equals(questype)){
//	    	if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
//			   url = "ques_peidui";
//	    	else if(WebConstant.editType_view==editType)
//	    	   url = "view_ques_peidui";
//	    }
		else if(QuestionConstant.QuesType_TianKong.equals(questype)){
	    	if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
			   url = "ques_tiankong";
	    	else if(WebConstant.editType_view==editType)
	    	   url = "view_ques_tiankong";
        }else if(QuestionConstant.QuesType_WanXingTianKong.equals(questype)){
        	if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
			   url = "ques_wanxingtiankong";
        	else if(WebConstant.editType_view==editType)
        	   url = "view_ques_wanxingtiankong";
        }else if(QuestionConstant.QuesType_WenDa.equals(questype)){
        	if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
			   url = "ques_wenda";
        	else if(WebConstant.editType_view==editType)
        	   url = "view_ques_wenda";
        }else if(QuestionConstant.QuesType_YueDuLiJie.equals(questype)){
        	if(WebConstant.editType_add==editType||WebConstant.editType_edit==editType)
			   url = "ques_yuedulijie";
        	else if(WebConstant.editType_view==editType)
        	   url = "view_ques_yuedulijie";
        }
		
		return url;
	}
	
	/**
	 * 
	 * @param vo
	 * @param userid
	 * @param shopid
	 * @param itemList
	 * @param answerVO
	 * @param delItemStr:需要删除的选项的字符串
	 * @param questype_parent: 如果是保存子题目，则此处传入父题目的题目类型
	 * @param messCode
	 * @return
	 */
//	private Long saveQuesAction(Paperques vo,Long userid,List itemList,
//			Quesanswer answerVO,String delItemStr,Integer questype_parent,String messCode)
//	{
//		Integer questype = vo.getQuestype();
//		PaperquesLogic logic = BOFactory.getPaperquesLogic();
//		Long pk = null;
//        // 如果是新增，则自动加上题目创建者
//		if(vo.getQuesid()==null){
//			if(vo.getPaperid()==null){
//				throw new LogicException(ExceptionConstant.Error_Need_Paramter);
//			}
//			Paper paperVO = BOFactory.getPaperDao().selectByPK_plain(vo.getPaperid());
//			AssertUtil.assertNotNull(paperVO, null);
//			vo.setShopid(paperVO.getShopid());
//			vo.setProductbaseid(paperVO.getProductbaseid());
//			vo.setCreatorid(userid);
//			messCode = KeyInMemoryConstant.AddSuccess;
//		}else {
//			pk = vo.getQuesid();
//		}
//		if(QuestionConstant.QuesType_DanXuan.equals(questype)
//				||QuestionConstant.QuesType_DuoXuan.equals(questype)
//				||QuestionConstant.QuesType_XZ_NoTrunk.equals(questype))
//		{ // 如果是选择题或者完形填空的子选项题
//			vo.setItemList(itemList);
//		}
//		// 加载每道题目的答案
//		if(answerVO!=null){
//		   vo.setAnswerVO(answerVO);
//		}
//		// 更新题目
//		//TODO 需要在页面确保vo中有questypeid
//		if(vo.getQuesid()==null&&questype_parent!=null){
//			pk = logic.addSubques(vo.getPaperid(),vo,questype_parent);
//		}else{
//			pk = logic.updatePaperQues(vo.getPaperid(),vo, delItemStr);
//		}
//		return pk;
//	}
	
}

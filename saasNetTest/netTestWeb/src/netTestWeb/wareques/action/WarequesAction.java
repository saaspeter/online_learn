
package netTestWeb.wareques.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.paper.vo.Paper;
import netTest.product.constant.UserproductConstant;
import netTest.util.QueshowUtil;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.dao.WareDao;
import netTest.wareques.dto.QuestionQuery;
import netTest.wareques.logic.QuestionLogic;
import netTest.wareques.logic.WarequesLogic;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;
import netTest.wareques.vo.Ware;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.wareques.form.WarequesForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import platform.logic.ShopLogic;
import platform.logicImpl.BOFactory_Platform;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.StringUtil;


public class WarequesAction extends BaseAction {
	
	static Logger log = Logger.getLogger(WarequesAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		WarequesForm theForm = (WarequesForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listWareques1".equals(myaction)) {
			myforward = list_oper(mapping, actionform, request,response);
		} else if ("selWareques".equals(myaction)) {
			myforward = selWareques(mapping, actionform, request,response);
		} else if ("saveWareques".equals(myaction)) {
			myforward = saveWareques(mapping, actionform, request,response);
		} else if ("editWareques".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewWareques".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addWarequesPage".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("editSubques".equals(myaction)) {
			myforward = editSubques(mapping, actionform, request,response);
		} else if ("deleteWareques".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("importwarequespage".equals(myaction)) {
			myforward = importQuesPage(mapping, actionform, request,response);
		} else if ("importwareques".equals(myaction)) {
			myforward = importQues(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward; 
	}
	
	/** 
     *
	 */
	public ActionForward list_oper(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WarequesForm theForm = (WarequesForm) form;
		QuestionQuery queryVO = theForm.getQueryVO();
		Long shopid = getLoginInfo().getShopid();
		queryVO.setShopid(shopid);
        // 处理切换用户产品
		Long productid = theForm.getProductbaseid();
		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_operatorMag);
		Page page = Page.EMPTY_PAGE;
		if(queryVO.getProductbaseid()!=null){ // 只有页面选中产品的时候才能查询
			// 如果是从题库管理页面链接过来，则重新设置
			if(queryVO.getWareid()!=null){
				WareDao dao = BOFactory.getWareDao();
				Ware vo = dao.selectByPK(queryVO.getWareid());
				if(vo!=null){
				   queryVO.setWarenameStr(vo.getWarename());
				}
				queryVO.setWareidStr(String.valueOf(queryVO.getWareid().longValue()));
			}
			
			WarequesLogic logic = BOFactory.getWarequesLogic();
			page = logic.qryWarequesPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		}
		this.setPage(request, page);
		return mapping.findForward("list_oper");
	}
	
	/** 
     * 查询题库题目，为了选择题目（例如从试卷页面选择题目）
	 */
	public ActionForward selWareques(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WarequesForm theForm = (WarequesForm) form;
		QuestionQuery queryVO = theForm.getQueryVO();
		Long shopid = getLoginInfo().getShopid();
		queryVO.setShopid(shopid);
		Page page = Page.EMPTY_PAGE;
		if(queryVO.getProductbaseid()!=null){ // 只有页面选中产品的时候才能查询
			// 如果是从题库管理页面链接过来，则重新设置
			if(queryVO.getWareid()!=null){
				WareDao dao = BOFactory.getWareDao();
				Ware vo = dao.selectByPK(queryVO.getWareid());
				if(vo!=null){
				   queryVO.setWarenameStr(vo.getWarename());
				}
				queryVO.setWareidStr(String.valueOf(queryVO.getWareid().longValue()));
			}
			
			WarequesLogic logic = BOFactory.getWarequesLogic();
			page = logic.qryWarequesPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		}
		this.setPage(request, page);
		return mapping.findForward("selwareques");
	}
	
	/** 
	 * Method addWareques:对于保存独立题目的情况。
	 */
	public ActionForward saveWareques(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		LoginInfoEdu loginfo = getLoginInfo();
		Long userid = loginfo.getUserid();
		Long shopid = loginfo.getShopid();
		WarequesForm theForm = (WarequesForm) form;
		Question vo = theForm.getVo();
		Quesanswer answerVO = theForm.getAnswerVO();
		String delItemStr = theForm.getDelItemStr(); // 需要删除的答案选项
		List itemList = theForm.getItemList();
		Long paperid = theForm.getPaperid();
		// 题目暂时不支持视频，也不支持用fileurl方式
//		// 处理fileurl的播放方式
//		String fileurl = vo.getFileurl();
//		if(fileurl!=null&&QuestionConstant.FileType_video.equals(vo.getFiletype())){
//			if("1".equals(theForm.getFileurlplaytype())){
//				vo.setFileurl(QuestionConstant.FileUrl_UsePlayer_Prefix+fileurl);
//			}
//		}
		if(vo.getQuesid()==null){
			messCode = KeyInMemoryConstant.AddSuccess;
		}

		// 新增或保存题目
		vo.setAnswerVO(answerVO);
		
		// check whether allow change
		ComplexResult<Boolean, Collection<Paper>, String, Integer> comresult = checkChangeQuesAffect(vo, 
												vo.getPid(), shopid, paperid, WebConstant.editType_edit);
		Collection<Paper> paperlist = comresult.second;
		String ref_papername = comresult.third;
		if(!comresult.first){
			// add exception, return original paper
			messCode = KeyInMemoryConstant.saveFail;
			ActionMessages errors = new ActionMessages();
        	ActionMessage newError1 = new ActionMessage("netTestWeb.action.WarequesAction.QuesRefered");
        	ActionMessage newError2 = new ActionMessage("<li>"+ref_papername+"</li>", false);
        	errors.add(ActionMessages.GLOBAL_MESSAGE, newError1);
        	errors.add("papername", newError2);
            this.saveErrors(request, errors);
            
            String url = toUrl(vo.getQuestype(),WebConstant.editType_edit);
    		return mapping.findForward(url);
		}
		
		// save
        this.saveQuesAction(vo, null, userid, loginfo.getShopid(), 
        					itemList, delItemStr);
        // if need flush paper
        if(paperlist!=null && paperlist.size()>0){
        	BOFactory.getPaperquesLogic().flushPaperByQues(paperlist, vo.getQuesid(), comresult.forth);
        }
        
		// set messag page parameters.
		theForm.setHasUrlSubmit(1); // 因为该提交是文件流提交，故super.reset()方法失效，在此手动设置
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
		
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		saveToken(request);
		WarequesForm theForm = (WarequesForm) form;
		QuestionQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getQuesid();
		Long shopid = getLoginInfo().getShopid();
		
		QuestionLogic logic = BOFactory.getQuestionLogic();
		Question vo = logic.qryQuesByPk(pk, shopid);
		AssertUtil.assertNotNull(vo, null);
		if(StringUtil.isEmpty(vo.getWarename())){
			Ware warevo = BOFactory.getWareDao().selectByPK(vo.getWareid());
			vo.setWarename(warevo.getWarename());
		}
		theForm.setVo(vo);
		if(vo.getAnswerVO()!=null)
			theForm.setAnswerVO(vo.getAnswerVO());
		//
		theForm.setEditquesid(String.valueOf(vo.getQuesid()));
		String url = toUrl(vo.getQuestype(),theForm.getEditType());
		return mapping.findForward(url);
	}
	
	/** 
	 * Method add
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		WarequesForm theForm = (WarequesForm) form;
		Question vo = theForm.getVo();
		vo.setFiletype(QuestionConstant.FileType_None);
		theForm.setEditType(WebConstant.editType_add);
		// 根据题目类型初始化题目vo
		preHandleAddPage(vo);
		// 是否能上传音频或视频文件，目前仅系统商店允许上传音视频，其他商店只能上传图片
		ShopLogic shoplogic = BOFactory_Platform.getShopLogic();
		boolean ableuploadlocal = shoplogic.ableUploadFileToLocalServer(vo.getShopid());
		theForm.setAbleuploadlocal(ableuploadlocal);
		
		String url = toUrl(vo.getQuestype(),WebConstant.editType_add);
		return mapping.findForward(url);
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
	public ActionForward editSubques(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long userid = loginfo.getUserid();
		WarequesForm theForm = (WarequesForm) form;
		QuestionQuery queryVO = theForm.getQueryVO();
		Question vo = theForm.getVo();
		Question vo2 = theForm.getVo2();
		vo2.setShopid(shopid); // need add this, quesitem need get shopid from quesion vo
		Quesanswer answerVO = theForm.getAnswerVO(); // 仅对主题目起作用
		String delItemStr = theForm.getDelItemStr(); // 需要删除的答案选项,只对子题目起作用
		List itemList = theForm.getItemList();
		String subquesOpt = theForm.getSubquesOpt(); // 页面操作动作
		Long paperid = theForm.getPaperid();
		QuestionLogic logic = BOFactory.getQuestionLogic();
		AssertUtil.assertNotNull(vo, null);
		
		// 检查该题目被多少试卷引用，如果影响了试卷分数，则不允许修改题目
		Question currenteditvo = vo;
		if(subquesOpt!=null && vo2.getPid()!=null){
			currenteditvo = vo2;
		}
		int opaction = WebConstant.editType_edit;
		if("4".equals(subquesOpt)){
			opaction = WebConstant.editType_delete;
		}
		ComplexResult<Boolean, Collection<Paper>, String, Integer> comresult = checkChangeQuesAffect(currenteditvo, 
																currenteditvo.getPid(), shopid, paperid, opaction);
		Collection<Paper> paperlist = comresult.second;
		String ref_papername = comresult.third;
		if(!comresult.first){
			// add exception, return original paper
			ActionMessages errors = new ActionMessages();
        	ActionMessage newError1 = new ActionMessage("netTestWeb.action.WarequesAction.QuesRefered");
        	ActionMessage newError2 = new ActionMessage(ref_papername, false);
        	errors.add(ActionMessages.GLOBAL_MESSAGE, newError1);
        	errors.add("papername", newError2);
            this.saveErrors(request, errors);
            
            String url = toUrl(vo.getQuestype(),WebConstant.editType_edit);
    		return mapping.findForward(url);
		}
				
		// 保存界面上的需要保存的题目，一般vo和vo2中只有一个的question不为空
		if(vo.getQuestion()!=null && vo.getQuestion().trim().length()>0){
		   if(vo.getQuesid()==null){
			  vo.setComptype(QuestionConstant.Comptype_compWhole);
		   }
		   vo.setAnswerVO(answerVO);
		   Long pk = this.saveQuesAction(vo, null, userid, shopid, null, delItemStr);
		   vo.setQuesid(pk);
		}
		if(vo2!=null&&vo2.getQuestion()!=null&&vo2.getQuestion().trim().length()>0){
		   if(vo2.getQuesid()==null){
		      vo2.setComptype(QuestionConstant.Comptype_compPart);
		      if(vo2.getPid()==null){
		    	  vo2.setPid(vo.getQuesid());
		      }
		      vo2.setWareid(vo.getWareid());
		   }
		   this.saveQuesAction(vo2, vo2.getPid(), userid, shopid, itemList, delItemStr);
		}
		// 转向页面
		if(subquesOpt!=null){
			if("4".equals(subquesOpt)){ //4删除子题目
				String[][] delKeys = theForm.getDelKeys();
				if(QuestionConstant.QuesType_XZ_NoTrunk.equals(vo2.getQuestype())
					&&theForm.getDelquesid()!=null&&theForm.getDeldisorder()!=null)
				{  // 完形填空题子题目的删除fuck
				   logic.delSubQues_WXTK(theForm.getDelquesid(), theForm.getDeldisorder());
				}else{
				   if(delKeys!=null&&delKeys.length>0){
				      logic.delSingleQues(theForm.getDelquesid(), theForm.getDelquestype(), shopid, vo.getQuesid());
				   }
				}
				theForm.setEditquesid(String.valueOf(vo.getQuesid())); // 使主题目成为可编辑状态
			}else if("5".equals(subquesOpt)){ // 交换题目
				Questionitem itemVO = new Questionitem();
				itemVO.setQuesid(vo2.getQuesid());
				itemVO.setDisorder(vo2.getDisorder());
				logic.switDisorderQues(itemVO, theForm.getSwitchtype());
			}
		}
		
		// 查询复合题目内容，用以显示题目页面
		vo = logic.qryQuesByPk(vo.getQuesid(), shopid);
		if(subquesOpt!=null&&"1".equals(subquesOpt)){
           // 新增子题目
		   Question subvo = new Question();
		   if(vo!=null&&vo.getSubquesList()!=null
				   &&QuestionConstant.QuesType_WanXingTianKong.equals(vo.getQuestype())){
			   Integer maxdisorder = logic.qryLargerDisorder(vo.getQuesid(),null);
			   if(maxdisorder==null)
				   maxdisorder = 1;
			   else
				   maxdisorder = maxdisorder+1;
			   subvo.setDisorder(maxdisorder);
		   }
		   subvo.setPid(vo.getQuesid()); 
		   subvo.setQuestype(QuestionConstant.getSubquestype(vo.getQuestype())); //根据主题目的类型决定自题目的类型
		   preHandleAddPage(subvo);
		   vo.getSubquesList().add(subvo);
		}
		if(StringUtil.isEmpty(vo.getWarename()) && vo.getWareid()!=null){
			Ware warevo = BOFactory.getWareDao().selectByPK(vo.getWareid());
			vo.setWarename(warevo.getWarename());
		}
		theForm.setVo(vo);
		if(vo.getAnswerVO()!=null){
			theForm.setAnswerVO(vo.getAnswerVO());
		}
		
		// if need flush paper
        if(paperlist!=null && paperlist.size()>0){
        	BOFactory.getPaperquesLogic().flushPaperByQues(paperlist, vo.getQuesid(), comresult.forth);
        }
		
        // 转向页面
		String url = toUrl(vo.getQuestype(),WebConstant.editType_add);
		ActionForward forward = null;
		if(subquesOpt!=null&&"0".equals(subquesOpt)){
			super.setMessagePage(request,theForm, KeyInMemoryConstant.modifySuccess, "1","BizKey");
			if(paperid!=null){
				forward = mapping.findForward("toUrl");
			}
			if(vo!=null){
			   queryVO.setQuesid(vo.getQuesid());	
			}
			forward = this.list_oper(mapping, theForm, request, response);
		}else {
			forward = mapping.findForward(url);
		}
		return forward;
	}
	
	
	
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
		WarequesForm theForm = (WarequesForm) form;
		String[][] delKeys = theForm.getDelKeys();
		AssertUtil.assertNotNull(theForm.getProductbaseid(), ExceptionConstant.Error_Need_Paramter);
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			List<Long> quesidList = new ArrayList<Long>();
			for(int i=0;i<delKeys.length;i++){
				quesidList.add(new Long(delKeys[i][0]));
			}
			// 如果有被试卷引用的题目就报错，删除题目只能在题库题目管理中操作
			Collection<Paper> paperlist = BOFactory.getPaperquesDao().qryPaperByQues(quesidList, shopid);
			if(paperlist!=null && paperlist.size()>0){
				StringBuffer nameBuffer = new StringBuffer();
				for(Paper papervo : paperlist){
					nameBuffer.append(papervo.getPapername()).append("; ");
				}
				
				throw new HasReferenceException(getResources(request, "BizKey").getMessage("netTestWeb.action.WarequesAction.QuesRefered")
												+"<br>"+nameBuffer.toString());
			}
			
			QuestionLogic logic = BOFactory.getQuestionLogic();
			rows = logic.delQuesBatch(delKeys,null,shopid,theForm.getProductbaseid());
			info = String.valueOf(rows);
		}catch (HasReferenceException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
			log.error("delKeys:"+delKeys, e);
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	// 根据题目类型初始化题目vo
	private void preHandleAddPage(Question vo){
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
//	    else if(QuestionConstant.QuesType_PeiDui.equals(questype)){
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
	 * 是否允许修改题目，主要是检查题目是否被试卷引用，如果被引用是否有可能影响试卷的分数，如果影响了，则不允许修改试卷
	 * @param vo
	 * @param parentid
	 * @param shopid
	 * @param paperlist: return value
	 * @return
	 */
	private ComplexResult<Boolean, Collection<Paper>, String, Integer> checkChangeQuesAffect(Question vo, 
											Long parentid, Long shopid, Long paperid, int action)
	{
		// update paper parameters
		Boolean allowchange = true;
		List<Paper> paperlist = null;
		int changeoptnum = 0;
		List<Long> quesidList = new ArrayList<Long>();
		
		if(action==WebConstant.editType_delete){ // 这里应该是删除子问题
			if(vo.getQuesid()!=null){
				quesidList.add(vo.getQuesid());
				paperlist = BOFactory.getPaperquesDao().qryPaperByQues(quesidList, shopid);
				if(paperlist!=null && paperlist.size()>0){
					if(paperlist.size()==1 && paperlist.get(0).getPaperid().equals(paperid)){
						allowchange = true; // 如果受影响的试卷就是本身自己，则允许修改
						changeoptnum = -1;
					}else {
						allowchange = false;
					}
				}
					
			}
		}else {
			if(parentid!=null){
				if(vo.getQuesid()==null){ // 在新增子问题
					quesidList.add(parentid);
					paperlist = BOFactory.getPaperquesDao().qryPaperByQues(quesidList, shopid);
					if(paperlist!=null && paperlist.size()>0){
						if(paperlist.size()==1 && paperlist.get(0).getPaperid().equals(paperid)){
							allowchange = true; // 如果受影响的试卷就是本身自己，则允许修改
							changeoptnum = 1;
						}else {
						    // 如果父问题有试卷引用，则不允许新增，否则会影响已有试卷分数
						    allowchange = false;
						}
					}
				}
			} else if(vo.getQuesid()!=null){ // 独立问题的编辑题目情况
				quesidList.add(vo.getQuesid());
				paperlist = BOFactory.getPaperquesDao().qryPaperByQues(quesidList, shopid);
				if(QuestionConstant.QuesType_TianKong.equals(vo.getQuestype())){ // 不是子题目，只有填空题需要检查，其余的改动均不可能修改试卷的分数
					int newoptNum = QueshowUtil.getQuesAnswerCountTK(vo.getQuestion());
					Question vo_in_db = BOFactory.getQuestionDao().selectByPK(vo.getQuesid());
					int quesoptnum_changed = newoptNum - vo_in_db.getQuesoptnum();
					if(quesoptnum_changed!=0){
						if(paperlist!=null && paperlist.size()>0){
							if(paperlist.size()==1 && paperlist.get(0).getPaperid().equals(paperid)){
								allowchange = true; // 如果受影响的试卷就是本身自己，则允许修改
								changeoptnum = quesoptnum_changed;
							}else {
							    // 如果父问题有试卷引用，则不允许新增，否则会影响已有试卷分数
							    allowchange = false;
							}
						}
					}
				}// else 修改题目本身的信息, 允许修改试题，但是要flush paper
			}
		}
		
		StringBuffer nameBuffer = new StringBuffer();
		if(paperlist!=null && paperlist.size()>0){
			for(Paper papervo : paperlist){
				// 此处就不提醒本试卷的名称了
				if(paperid==null || !papervo.getPaperid().equals(paperid)){
				   nameBuffer.append(papervo.getPapername()).append("; ");
				}
			}
		}
		return new ComplexResult(allowchange, paperlist, nameBuffer.toString(), changeoptnum);
	}
	
	/**
	 * 
	 * @param vo
	 * @param userid
	 * @param shopid
	 * @param itemList
	 * @param answerVO
	 * @param delItemStr:需要删除的选项的字符串
	 * @param messCode
	 * @return
	 */
	private Long saveQuesAction(Question vo, Long parentQuesid, Long userid, Long shopid, 
			                    List itemList, String delItemStr)
	{
		Integer questype = vo.getQuestype();
		QuestionLogic queslogic = BOFactory.getQuestionLogic();
		Long pk = null;
        // 如果是新增，则自动加上题目创建者
		if(vo.getQuesid()==null){
			if(vo.getWareid()==null){
				throw new LogicException(ExceptionConstant.Error_Need_Paramter);
			}
			Ware wareVO = BOFactory.getWareDao().selectByPK(vo.getWareid());
			AssertUtil.assertNotNull(wareVO, null);
			vo.setShopid(wareVO.getShopid());
			vo.setProductbaseid(wareVO.getProductbaseid());
			vo.setCreatorid(userid);
		}else {
			pk = vo.getQuesid();
		}
		if(QuestionConstant.QuesType_DanXuan.equals(questype)
				||QuestionConstant.QuesType_DuoXuan.equals(questype)
				||QuestionConstant.QuesType_XZ_NoTrunk.equals(questype))
		{ // 如果是选择题或者完形填空的子选项题
			vo.setItemList(itemList);
		}
		
		// 新增或更新题目
		if(vo.getQuesid()==null){
			// save
			pk = queslogic.addQues(vo);
		}else{
			// save question
			Question quesUpd = queslogic.updateQues(vo, delItemStr);
			quesUpd.getQuesid();
			
		}	
		return pk;
	}
	
	/** 
	 * 导入题库题目
	 */
	public ActionForward importQuesPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		WarequesForm theForm = (WarequesForm) form;
		Long wareid = theForm.getQueryVO().getWareid();
		//TODO 这里需要检查商店的总题目数有没有超过限制，如果超出则报异常
		
//		WareDao waredao = BOFactory.getWareDao();
//		Ware wareVO = waredao.selectByPK(wareid);
//		Question vo = theForm.getVo();
//		vo.setWareid(wareVO.getWareid());
//		vo.setWarename(wareVO.getWarename());
		
		return mapping.findForward("importpage");
	}
	
	/** 
	 * 导入题库题目
	 */
	public ActionForward importQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long userID = loginfo.getUserid();
		WarequesForm theForm = (WarequesForm) form;
		String importtype = theForm.getImporttype();
		//TODO 这里需要检查商店的总题目数有没有超过限制，如果超出则报异常
		
		FormFile file = theForm.getVo().getQuesFile();
		Question vo = theForm.getVo();
		Long wareid = vo.getWareid();
		Long productid = vo.getProductbaseid();
		AssertUtil.assertNotNull(wareid, ExceptionConstant.Error_Need_Paramter);
		AssertUtil.assertNotNull(productid, ExceptionConstant.Error_Need_Paramter);
		
		QuestionLogic quesLogic = BOFactory.getQuestionLogic();
		List<String> messageList = null;
		if("excel".equals(importtype)){
			messageList = quesLogic.importQuesFromExcel(file.getInputStream(), wareid, 
													    shopid, productid, userID, loginfo.getLocale());
		}else if("txt".equals(importtype)){
			messageList = quesLogic.importQuesFromTxt(file.getInputStream(), wareid, 
													  shopid, productid, userID);
		}
		if(messageList!=null){
			String successImportNumberStr = messageList.get(messageList.size()-1);
		    int successImportNumber = 0;
		    if(successImportNumberStr!=null){
		    	successImportNumber = Integer.parseInt(successImportNumberStr);
		    }
		    if(messageList.size()>1){
		    	theForm.setImportFailNumber(messageList.size()-1);
		        theForm.setMessageList(messageList.subList(0, messageList.size()-1));
		    }else {
		    	// 没有失败的问题记录
		    	theForm.setImportFailNumber(0);
		    }
			theForm.setImportSuccessNumber(successImportNumber);
		}
		return mapping.findForward("importResultPage");
	}
	
	private class ComplexResult<A, B, C, D> {
		//定义成final,返回后不可修改
		public final A first;
		public final B second;
		public final C third;
		public final D forth;
		
		public ComplexResult(A a, B b, C c, D d)
		{
			first=a;
			second=b;
			third=c;
			forth = d;
		}
		@Override
		public String toString() {
			return first.toString()+second.toString()+third.toString()+forth.toString();
		}
	}
	
}

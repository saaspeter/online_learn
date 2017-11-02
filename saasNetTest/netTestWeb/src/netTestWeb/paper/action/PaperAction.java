
package netTestWeb.paper.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.paper.dao.PaperDao;
import netTest.paper.dto.PaperQuery;
import netTest.paper.logic.PaperLogic;
import netTest.paper.logic.PaperquesLogic;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperpatternratio;
import netTest.paper.vo.Paperquestype;
import netTest.product.constant.UserproductConstant;
import netTest.wareques.constant.QuestionConstant;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.paper.form.PaperForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import commonTool.base.Page;
import commonTool.biz.logic.ConstantInf;
import commonTool.biz.logicImpl.ConstantLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.constant.LabelValueVO;
import commonTool.exception.LogicException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.AssertUtil;

public class PaperAction extends BaseAction {
	
	static Logger log = Logger.getLogger(PaperAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		PaperForm theForm = (PaperForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listPaper".equals(myaction)) {
			theForm.setUrlType(1);
			myforward = list(mapping, actionform, request,response);
		} else if ("selectPaper".equals(myaction)) {
			theForm.setUrlType(2);
			myforward = list(mapping, actionform, request,response);
		} else if ("savePaper".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editPaper".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewPaper".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addPaper1".equals(myaction)) {
			myforward = addPaper1(mapping, actionform, request,response);
		} else if ("genePaper".equals(myaction)) {
			myforward = genePaper(mapping, actionform, request,response);
		} else if ("deletePaper".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("importpaperfilepage".equals(myaction)) {
			myforward = importPaperFilePage(mapping, actionform, request,response);
		} else if ("importpaperfile".equals(myaction)) {
			myforward = importPaperFile(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
     * @security 管理员使用
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		PaperForm theForm = (PaperForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long orgId = loginfo.getOrgbaseid();
		Long productid = theForm.getProductbaseid();
		PaperQuery queryVO = theForm.getQueryVO();

		String url = "list";
		int urlType = theForm.getUrlType();
		queryVO.setOrgbaseid(orgId);
		queryVO.setShopid(shopid);
		// 处理切换用户产品
		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_operatorMag);
//		queryVO.setPapertype(PaperConstant.PaperType_Testpaper);
		if(urlType==2){
			url = "selectPaper";
		}
		Page page = Page.EMPTY_PAGE;
		if(queryVO.getProductbaseid()!=null){
			PaperLogic logic = BOFactory.getPaperLogic();
			page = logic.listPaperPage(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		}
		this.setPage(request, page);
		
		return mapping.findForward(url);
	}
	
	/** 
	 * 更新保存试卷
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		
		PaperForm theForm = (PaperForm) form;
		Paper vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getPaperid(), ExceptionConstant.Error_Need_Paramter);
        
        PaperDao dao = BOFactory.getPaperDao();
		dao.updateByPK(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		PaperForm theForm = (PaperForm) form;
		Paper vo = theForm.getVo();
		PaperQuery queryVO = theForm.getQueryVO();
		Long paperid = queryVO.getPaperid();
		Long questypeidUse = theForm.getQuestypeidUse();
		// TODO 在此需要检查该份试卷有没有对应的考试正在进行，如果有则不允许修改试卷。这个可以单独写一个action，界面上用ajax调用检查
		
		// 如果vo中设置了需要更新的字段，则在此更新. 在编辑试卷过程中切换试题类型时可以更新试卷本身
		int needupdatepaper = theForm.getNeedupdatepaper();
		if(needupdatepaper==1 && !vo.isEmptyVO())
		{ 
			PaperDao dao = BOFactory.getPaperDao();
			dao.updateByPK(vo);
		}
		PaperLogic logic = BOFactory.getPaperLogic();
		vo = logic.qryPaperquesPattern(paperid,questypeidUse);
		
		if(vo==null)
		   throw new NoSuchRecordException("--class:PaperAction;--method:editPage;");
		theForm.setVo(vo);
		// 设置正在使用的题型
		if(questypeidUse==null){
			questypeidUse = ((Paperquestype)(vo.getQuestypeList().get(0))).getQuestypeid();
			theForm.setQuestypeidUse(questypeidUse);
		}
		if(theForm.getEditType()==WebConstant.editType_edit){
			return mapping.findForward("editPaper");
		}else {
		    return mapping.findForward("viewPage");
		}
        
	}
		
	/** 
	 * Method add1
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addPaper1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		PaperForm theForm = (PaperForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		ConstantInf inf = ConstantLogicImpl.getInstance();
		// 得到题目类型静态数据List
		List quetyporiList = inf.getLabelList(QuestionConstant.QuesType_ConsCode, localeid);
		theForm.setQuetyporiList(quetyporiList);
        // 题目难度的List
		List diffList = this.getQuesdiffList(localeid); 
		theForm.setDiffList(diffList);
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("addPaper1");
	}
	
	/** 
	 * Method genePaper
	 */
	public ActionForward genePaper(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		String orgname = loginfo.getOrgname();
		Long userid = loginfo.getUserid();
		Long orgbaseid = loginfo.getOrgbaseid();
		PaperForm theForm = (PaperForm) form;
		Long questypeidUse = theForm.getQuestypeidUse();
		int createtype = theForm.getCreatetype();
		
		Paper vo = theForm.getVo();
		vo.setShopid(shopid);
		vo.setOrgname(orgname);
		vo.setCreatorid(userid);
		vo.setOrgbaseid(orgbaseid);

		List contcateList = theForm.getContcateList_clean();
		List questypeList = theForm.getQuestypeList_clean();
		List diffList = theForm.getDiffList_clean();
		theForm.setEditType(WebConstant.editType_add);
		PaperLogic logic = BOFactory.getPaperLogic();
		Long paperid = logic.genePaper(vo, createtype, questypeList, contcateList, diffList);
		vo = logic.qryPaperquesPattern(paperid,questypeidUse);
		theForm.setVo(vo);
		if(questypeidUse==null){
			theForm.setQuestypeUse(vo.getQuestyepUse());
			theForm.setQuestypeidUse(vo.getQuestypeidUse());
		}
		return mapping.findForward("genePaper");
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PaperForm theForm = (PaperForm) form;
		Long paperid = theForm.getVo().getPaperid();
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		AssertUtil.assertNotNull(paperid, null);
		try {
			PaperLogic logic = BOFactory.getPaperLogic();
			//rows = logic.delMutiWholePapers(keys);
			logic.delWholePaper(paperid, null);
			info = KeyInMemoryConstant.deleteSuccessCommon;
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		// 得到错误信息
		info = getResources(request, "BizKey").getMessage(info);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 
	 * 导入试卷题目
	 */
	public ActionForward importPaperFilePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//Long shopid = getLoginInfo().getShopid();
		//PaperForm theForm = (PaperForm) form;	
		return mapping.findForward("importpage");
	}
	
	/** 
	 * 导入题库题目
	 */
	public ActionForward importPaperFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginInfoEdu userinfo = getLoginInfo();
		Long shopid = userinfo.getShopid();
		Long userID = userinfo.getUserid();
		Long orgID = userinfo.getOrgbaseid();
		String orgname = userinfo.getOrgname();
		PaperForm theForm = (PaperForm) form;
		Long wareid = theForm.getWareid();
		FormFile file = theForm.getFile();
		Paper vo = theForm.getVo();
		vo.setShopid(shopid);
		vo.setCreatorid(userID);
		vo.setOrgbaseid(orgID);
		vo.setOrgname(orgname);
		if(wareid!=null) {
		   vo.setWareidstr(wareid.toString());
		}
		Long productid = vo.getProductbaseid();
		AssertUtil.assertNotNull(productid, ExceptionConstant.Error_Need_Paramter);
		
		PaperquesLogic logic = BOFactory.getPaperquesLogic();
		List<String> messageList = logic.importPaperQuesFromTxt(vo, file.getInputStream(), wareid);
		
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
		
	// 得到题目难度题型比例
	private List getQuesdiffList(Long localeid) throws Exception{
		ConstantInf inf = ConstantLogicImpl.getInstance();
		List diffList = inf.getLabelList(QuestionConstant.Difficult_ConsCode, localeid,
				                         null,null,null);
		List rtn = new ArrayList();
		LabelValueVO labelVO = null;
		Paperpatternratio voTemp = null;
		for(int i=0;i<diffList.size();i++){
			labelVO = (LabelValueVO)diffList.get(i);
			voTemp = new Paperpatternratio();
			voTemp.setElementid(new Long(labelVO.getValue()));
			voTemp.setElementname(labelVO.getLabel());
			voTemp.setElementtype(Paperpatternratio.Elementtype_Diff);
			rtn.add(voTemp);
		}
		return rtn;
	}
	
}


package netTestWeb.exercise.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.exercise.dao.ExerciseDao;
import netTest.exercise.dto.ExerciseQuery;
import netTest.exercise.logic.ExerciseLogic;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerquestype;
import netTest.prodcont.vo.Contentcate;
import netTest.wareques.constant.QuestionConstant;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.exercise.form.ExerciseForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logicImpl.BOFactory_Platform;

import commonTool.biz.logic.ConstantInf;
import commonTool.biz.logicImpl.ConstantLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;
import commonTool.exception.NoSuchRecordException;


public class ExerciseAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ExerciseAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ExerciseForm theForm = (ExerciseForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("toAddExerPage".equals(myaction)) {
			myforward = toAddExerPage(mapping, actionform, request,response);
		} else if ("addExercise".equals(myaction)) {
			myforward = addExercise(mapping, actionform, request,response);
		} else if ("editExercise".equals(myaction)) {
			myforward = editExercise(mapping, actionform, request,response);
		} else if ("delExercise".equals(myaction)) {
			myforward = deleteExer(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
    
	/**
	 * 查看练习, 管理员查看练习
	 */
//	private ActionForward listExer(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		ExerciseForm theForm = (ExerciseForm) form;
//		ExerciseQuery queryVO = theForm.getQueryVO();
//		Long shopid = theForm.getShopid();
//		Long productid = theForm.getProductbaseid();
//        // 处理切换用户的商店
//		switchShop(shopid,queryVO,true);
//		// 处理切换用户产品
//		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_operatorMag);
//		Page page = Page.EMPTY_PAGE;
//		if(queryVO.getProductbaseid()!=null){
//			ExerciseDao dao = BOFactory.getExerciseDao();
//			page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
//			// 设置所属章节名
//			if(page.getList()!=null&&page.getList().size()>0){
//				List list = page.getList();
//				Exercise voTemp = null;
//				List<Long> pkList = new ArrayList<Long>();
//				for(int i=0;i<list.size();i++){
//					voTemp = (Exercise)list.get(i);
//					if(voTemp.getContentcateid()!=null){
//						pkList.add(voTemp.getContentcateid());
//					}
//				}
//				if(pkList.size()>0){
//					ContentcateLogic cateLogic = BOFactory.getContentcateLogic();
//					Map<Long,Contentcate> map = cateLogic.selectByPKList(pkList);
//					Contentcate catevoTemp = null;
//					for(int i=0;i<list.size();i++){
//						voTemp = (Exercise)list.get(i);
//						catevoTemp = map.get(voTemp.getContentcateid());
//						if(catevoTemp!=null){
//							voTemp.setContentcatename(catevoTemp.getContentcatename());
//						}
//					}
//				}
//			}
//		}
//		this.setPage(request, page);	
//		return mapping.findForward("listExerMag");
//	}
	

	/**
	 * 转向到新增练习页面
	 */
	private ActionForward toAddExerPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		ExerciseForm theForm = (ExerciseForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		ConstantInf inf = ConstantLogicImpl.getInstance();
		// 得到题目类型静态数据List
		List quetyporiList = inf.getLabelList(QuestionConstant.QuesType_ConsCode, localeid);
		theForm.setQuetyporiList(quetyporiList);
		Exercise vo = theForm.getVo();
		if(vo.getContentcateid()!=null&&(vo.getContentcatename()==null||"".equals(vo.getContentcatename()))){
			Contentcate catevo = BOFactory.getContentcateDao().selectByPK(vo.getContentcateid());
			if(catevo!=null){
				vo.setContentcatename(catevo.getContentcatename());
			}
		}
		return mapping.findForward("addExerPage1");
	}
	
	/**
	 * 保存练习的各种配置，并且转向练习题目编辑页面
	 */
	private ActionForward addExercise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		LoginInfoEdu loginfo = getLoginInfo();
		ExerciseForm theForm = (ExerciseForm) form;
		Exercise vo = theForm.getVo();
		vo.setShopid(loginfo.getShopid());
		vo.setCreatorid(loginfo.getUserid());
		// 练习所包含的题目类型
		String[] keys = theForm.getKeys();
		ExerciseLogic logic = BOFactory.getExerciseLogic();
		vo = logic.addExercise(vo, keys);
		
		String actionUrl = "editExercise.do?vo.exerid="+vo.getExerid();
		super.setMessUrlPage(request, actionUrl, "", "1", "BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * 
	 */
	private ActionForward editExercise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		ExerciseForm theForm = (ExerciseForm) form;
		Exercise vo = theForm.getVo();
		Long exerid = theForm.getVo().getExerid();
		Integer questypeUse = theForm.getQuestypeUse();
		// TODO 在此需要检查该份试卷有没有对应的考试正在进行，如果有则不允许修改试卷。这个可以单独写一个action，界面上用ajax调用检查
		
		if(!vo.isEmptyVO())
		{ // 如果vo中设置了需要更新的字段，则在此更新
			ExerciseDao dao = BOFactory.getExerciseDao();
			dao.save(vo);
		}
		ExerciseLogic logic = BOFactory.getExerciseLogic();
		vo = logic.qryExerquesPattern(exerid, questypeUse);
		if(vo.getContentcateid()!=null) {
			Contentcate cateVO = BOFactory.getContentcateDao().selectByPK(vo.getContentcateid());
			if(cateVO!=null){
				vo.setContentcatename(cateVO.getContentcatename());
			}
		}
		
		if(vo==null)
		   throw new NoSuchRecordException("--class:ExerciseAction;--method:editExercise;");
		// 设置正在使用的题型
		if(questypeUse==null){
			questypeUse = ((Exerquestype)(vo.getQuestypeList().get(0))).getQuestype();
			theForm.setQuestypeUse(questypeUse);
		}
		// 设置createor
		if(vo.getCreatorid()!=null) {
		   String creatorname = BOFactory_Platform.getUsershopLogic().qryUsernameById(vo.getShopid(), vo.getCreatorid());
		   vo.setCreatorname(creatorname);
		}
		theForm.setVo(vo);
        return mapping.findForward("editExerPage");
	}
	
	@Deprecated
	private ActionForward viewExercise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ExerciseForm theForm = (ExerciseForm) form;
		ExerciseQuery queryVO = theForm.getQueryVO();
		Long exerid = queryVO.getExerid();
		Integer questypeUse = theForm.getQuestypeUse();
		
		ExerciseLogic logic = BOFactory.getExerciseLogic();
		Exercise vo = logic.qryExerquesPattern(exerid,questypeUse);
        // 设置正在使用的题型
		if(questypeUse==null){
			questypeUse = ((Exerquestype)(vo.getQuestypeList().get(0))).getQuestype();
			theForm.setQuestypeUse(questypeUse);
		}
		
		if(vo==null)
		   throw new NoSuchRecordException("--class:PaperAction;--method:editPage;");
		theForm.setVo(vo);

        return mapping.findForward("viewPage");
	}
	
	private ActionForward deleteExer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExerciseForm theForm = (ExerciseForm) form;
		Long exerid = theForm.getVo().getExerid();
		if(exerid==null)
			return null;
		String result = String.valueOf(CommonConstant.success);
		String info = getResources(request, "BizKey").getMessage(KeyInMemoryConstant.deleteSuccess, "1");
		try {
			ExerciseLogic logic = BOFactory.getExerciseLogic();
			logic.delWholeExercise(exerid, null, true);
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			if(e instanceof HasReferenceException){
				info = e.getMessage();
			}else {
				info = ExceptionConstant.Error_System;
			}
			
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}	

	
}

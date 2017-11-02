
package netTestWeb.platform.news.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.platform.news.form.NewscategoryForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.NewscategoryConstant;
import platform.dao.NewscategoryDao;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Newscategory;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.constant.LabelValueVO;
import commonTool.exception.HasReferenceException;
import commonWeb.security.authentication.UserinfoSession;

public class NewscategoryAction extends BaseAction {
	
	static Logger log = Logger.getLogger(NewscategoryAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		NewscategoryForm theForm = (NewscategoryForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("editNewscategory".equals(myaction)) {
			myforward = editNewscategoryPage(mapping, actionform, request,response);
		} else if ("saveNewscategory".equals(myaction)) {
			myforward = saveNew(mapping, actionform, request,response);
		} else if ("saveNewscategoryValue".equals(myaction)) {
			myforward = saveNewValue(mapping, actionform, request,response);
		} else if ("updateNewsCate".equals(myaction)){
			myforward = updateNewsCate(mapping, actionform, request,response);
		} else if ("addNewscategory".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteNewscategory".equals(myaction)) {
			theForm.setDeletetype(2);
			myforward = delete(mapping, actionform, request,response);
		} else if ("deleteNewscategoryValue".equals(myaction)) {
			theForm.setDeletetype(1);
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * 进入某个新闻tab页的不同国家语言设置的编辑界面
	 */
	public ActionForward editNewscategoryPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserinfoSession usersession = getLoginInfo(true);
		Locale locale = usersession.getLocale();
		Long userlocaleid = usersession.getLocaleid();
		NewscategoryForm theForm = (NewscategoryForm) form;
		Newscategory vo = theForm.getVo();
		Long pk = vo.getId();
		
		NewscategoryDao dao = BOFactory_Platform.getNewscategoryDao();
		vo = dao.selectByLogicPK(pk, userlocaleid);
		theForm.setVo(vo);
		// 设置默认的default localeid
		theForm.setDefaultlocaleid(I18nLogicImpl.getInstance().getDefaultLocaleid());
		
		List<Newscategory> list = dao.selectTabLocale(pk);
		// 如果标签tab的国家语言值列表小于系统的国家语言列表，则补充
		List<LabelValueVO> list2 = I18nLogicImpl.getInstance().getLabelList(locale);
		boolean hasit = false;
		Newscategory voTemp = null;
		List<Newscategory> tempList = new ArrayList<Newscategory>();
		if(list.size()<list2.size()){
			for(LabelValueVO labelVO : list2){
				hasit = false;
				for(int i=0;i<list.size();i++){
					voTemp = list.get(i);
					if(voTemp!=null&&labelVO.getLocaleid().equals(voTemp.getLocaleid())){
						hasit = true;
						break;
					}
				}
				if(!hasit){
					Newscategory voTemp2 = new Newscategory();
					voTemp2.setLocaleid(labelVO.getLocaleid());
					voTemp2.setCategoryid(voTemp.getCategoryid());
					voTemp2.setId(pk);
					tempList.add(voTemp2);
				}
			}
		}
		list.addAll(tempList);
		theForm.setNewscateList(list);
		
		return mapping.findForward("editPage");
	}
	
	/**
	 * 新增咨询信息页面
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		NewscategoryForm theForm = (NewscategoryForm) form;
		theForm.setDefaultlocaleid(I18nLogicImpl.getInstance().getDefaultLocaleid());
		return mapping.findForward("addPage");
	}

	/**
	 * 保存新增的咨询信息tab
	 */
	public ActionForward saveNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		String messCode = KeyInMemoryConstant.AddSuccess;	
		NewscategoryForm theForm = (NewscategoryForm) form;
		Newscategory vo = theForm.getVo(); 
        NewscategoryDao dao = BOFactory_Platform.getNewscategoryDao();
        if(vo.getType()==null){
           vo.setType(NewscategoryConstant.Type_SystemPost);
        }
		vo = dao.insert(vo);
		// set message page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		// 进入新增后的国家语言编辑界面
		theForm.setNeedflushparentpage("1");
		return editNewscategoryPage(mapping, theForm, request, response);
	}
	
	/**
	 * 保存咨询目录Tab的单个国家语言值
	 */
	public ActionForward saveNewValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
		NewscategoryForm theForm = (NewscategoryForm) form;
		Newscategory vo = theForm.getVo();
        
		String result = String.valueOf(CommonConstant.success);
		String messCode = KeyInMemoryConstant.modifySuccess;
        try {
			NewscategoryDao dao = BOFactory_Platform.getNewscategoryDao();
			dao.saveNewsCateValue(vo);
		} catch (Exception e) {
			log.error("oops, got an exception: ", e);
			messCode = ExceptionConstant.Error_System;
        	result = String.valueOf(CommonConstant.fail);
		}
		// set messag page parameters.
		String info = getResources(request, CommonConstant.Resource_BaseKey_Boundle).getMessage(messCode);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 
	 * 更新新闻目录，目前只更新排列顺序
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward updateNewsCate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String result = String.valueOf(CommonConstant.success);
		String info = KeyInMemoryConstant.modifySuccess;
		NewscategoryForm theForm = (NewscategoryForm) form;
		Newscategory vo = theForm.getVo();
        try{
           NewscategoryDao dao = BOFactory_Platform.getNewscategoryDao();
		   dao.updateByPK(vo);
        }catch(Exception e){
        	log.error("oops, got an exception: ", e);
        	info = ExceptionConstant.Error_System;
        	result = String.valueOf(CommonConstant.fail);
        }
		// set messag page parameters.
        info = getResources(request, CommonConstant.Resource_BaseKey_Boundle).getMessage(info);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
		
	/** 
	 * 删除新闻目录标签，需要验证其下是否还有文章
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		NewscategoryForm theForm = (NewscategoryForm) form;
		Newscategory vo = theForm.getVo();
		int deleteType = theForm.getDeletetype();
		String result = String.valueOf(CommonConstant.success);
		String info = KeyInMemoryConstant.deleteSuccess;
		try {
			NewscategoryDao dao = BOFactory_Platform.getNewscategoryDao();
			if(deleteType==2){
				dao.delOneTab(vo.getId());
			}else if(deleteType==1){
				dao.delOneTabLocale(vo.getValueid());
			}
		}catch (HasReferenceException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}
		info = getResources(request, CommonConstant.Resource_BaseKey_Boundle).getMessage(info, "1");
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	
}

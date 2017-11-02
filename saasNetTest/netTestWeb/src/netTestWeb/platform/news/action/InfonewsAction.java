
package netTestWeb.platform.news.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.news.form.InfonewsForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.InfonewsConstant;
import platform.constant.NewscategoryConstant;
import platform.dao.InfonewsDao;
import platform.dao.NewscategoryDao;
import platform.dto.InfonewsQuery;
import platform.logic.InfonewsLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.social.dao.impl.SocialNewsDaoImpl;
import platform.social.vo.SocialNews;
import platform.vo.Infonews;
import platform.vo.Newscategory;
import platform.vo.Productcategory;
import commonTool.base.BaseEmptyEntity;
import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.HasReferenceException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;


public class InfonewsAction extends BaseAction {
	
	static Logger log = Logger.getLogger(InfonewsAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		InfonewsForm theForm = (InfonewsForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listInfonews".equals(myaction)) {
			theForm.setListtype(1);
			myforward = list(mapping, actionform, request,response);
		} else if ("listInfonewsMag".equals(myaction)) {
			theForm.setListtype(2);
			myforward = list(mapping, actionform, request,response);
		} else if ("saveInfonews".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editInfonews".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewInfonews".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addInfonews".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteInfonews".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		InfonewsForm theForm = (InfonewsForm) form;
		Long localeid = getLoginInfo(true).getUseLocaleid(request);
		Long categoryid = theForm.getCategoryid();
		Long newscategoryid = theForm.getNewscategoryid();
		InfonewsQuery queryVO = theForm.getQueryVO();
		int listtype = theForm.getListtype();
		// 转换categoryid, 并记录session中
		BaseEmptyEntity basevo = switchProductCategory(categoryid, request, localeid, BOFactory_Platform.getProductcategoryLogic());
		categoryid = basevo.getId();
		theForm.setCategoryid(categoryid);
		theForm.setCategoryname(basevo.getName());
		
		// 查询横向tab list
		NewscategoryDao cateDao = BOFactory_Platform.getNewscategoryDao();
		List<Newscategory> tablist = cateDao.selectCateTab(categoryid, localeid);
		theForm.setTablist(tablist);
		// 如果是search tab,但search的内容为空，则转到第一个tab
		if(CommonConstant.SearchTabPK.equals(newscategoryid) && 
				(queryVO.getSearchcontent()==null||queryVO.getSearchcontent().trim().length()<1)){
			newscategoryid = null;
		}
		if(newscategoryid==null&&tablist!=null&&tablist.size()>0){
			newscategoryid = tablist.get(0).getId();
			theForm.setNewscategoryid(newscategoryid);
		}
		
		String url = "listpost";
		if(listtype==2) {
			url = "listMag";
		}
		
		Page page = Page.EMPTY_PAGE;
		if(newscategoryid!=null){
			Newscategory newcatevo = null;
			if(!CommonConstant.SearchTabPK.equals(newscategoryid)){
			    newcatevo = BOFactory_Platform.getNewscategoryDao().selectByPK(newscategoryid);
			}
			if(newcatevo!=null && NewscategoryConstant.Type_SocialPost.equals(newcatevo.getType())){
				Integer indexcursor = theForm.getIndexcursor();
				List<SocialNews> dataList =  BOFactory_Platform.getSocialNewsLogic().selectNewsList(newscategoryid, indexcursor);
				// compute next indexCursor
				if(dataList!=null){
				    indexcursor = (indexcursor==null?0:indexcursor)+dataList.size();
				    theForm.setSocialnewslist(dataList);
				}
				theForm.setIndexcursor(indexcursor);
				url = "listsocialpost";
			}else {
			    InfonewsDao dao = BOFactory_Platform.getInfonewsDao();
			    if(CommonConstant.SearchTabPK.equals(newscategoryid)){
				   newscategoryid = null; // 这是做search, 把category tab id设置为null
			    }
			    page = dao.selectResult(newscategoryid, categoryid, localeid, queryVO.getSearchcontent(), getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
			}   
		}
		this.setPage(request, page);
		return mapping.findForward(url);
	}
	

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
		Long userid = getLoginInfo().getUserid();
		InfonewsForm theForm = (InfonewsForm) form;
		Infonews vo = theForm.getVo();
		vo.setCreator(userid);
		Date createDate = DateUtil.getInstance().getNowtime_GLNZ();
		vo.setCreatetime(createDate);
		vo.setType(InfonewsConstant.Type_news);
		vo.setSyscode(SysparamConstant.syscode);
		vo.setCategoryid(theForm.getCategoryid());
		vo.setStatus(InfonewsConstant.Status_Valide);

        if(vo.getId()==null||vo.getId()==0){
        	messCode = KeyInMemoryConstant.AddSuccess;
            if(vo.getType()==null){
            	vo.setType(Infonews.TYPE_NewsCategory);
            }
        }
        InfonewsDao dao = BOFactory_Platform.getInfonewsDao();
		dao.save(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return list(mapping, theForm, request, response);
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		InfonewsForm theForm = (InfonewsForm) form;
		InfonewsQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getId();
		
		InfonewsDao dao = BOFactory_Platform.getInfonewsDao();
		Infonews vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);
		if(StringUtil.isEmpty(theForm.getCategoryname())){
			Long localeid = getLoginInfo(true).getLocaleid(request);
			Productcategory catevo = BOFactory_Platform.getProductcategoryDao().selectByLogicPK(vo.getCategoryid(), localeid);
			if(catevo!=null){
				theForm.setCategoryname(catevo.getCategoryname());
			}
		}

		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 * @return ActionForward
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		Long userid = getLoginInfo().getUserid();
		InfonewsForm theForm = (InfonewsForm) form;
		theForm.setEditType(WebConstant.editType_add);
		// 新增infonews，为了在其中可以upload file, 会有clean job定期清理其中的autosave data
		Infonews vo = new Infonews();
		vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
		vo.setCreator(userid);
		vo.setType(InfonewsConstant.Type_news);
		vo.setSyscode(SysparamConstant.syscode);
		vo.setStatus(InfonewsConstant.Status_AutoSave);
		vo.setCategoryid(theForm.getCategoryid());
		vo.setRefid(theForm.getNewscategoryid());
		InfonewsDao dao = BOFactory_Platform.getInfonewsDao();
		Long pk = dao.insert(vo);
		vo.setId(pk);
		theForm.setVo(vo);
		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		InfonewsForm theForm = (InfonewsForm) form;
		Long pk = theForm.getVo().getId();
		String result = String.valueOf(CommonConstant.success);
		String info = "delete_success";
		try {
			InfonewsLogic logic = BOFactory_Platform.getInfonewsLogic();
			logic.delete(pk);
		}catch (HasReferenceException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}

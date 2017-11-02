
package netTestWeb.platform.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.common.session.LoginInfoEdu;
import netTest.util.UploadFileUtilNettest;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.shop.form.ShopdescarticleForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logicImpl.BOFactory_Platform;
import platform.shop.dao.ShopdescarticleDao;
import platform.shop.dto.ShopdescarticleQuery;
import platform.shop.vo.Shopdescarticle;
import platform.vo.Shop;

import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.UploadFileUtil;

public class ShopdescarticleAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopdescarticleAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShopdescarticleForm theForm = (ShopdescarticleForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("saveshopdescarticle".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editshopdescarticle".equals(myaction)) {
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewshopdescarticle".equals(myaction)) {
			theForm.setEditType(WebConstant.ListType_viewOnly);
			myforward = viewPage(mapping, actionform, request,response);
		} else if ("viewshopdescarticleedit".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = viewPage(mapping, actionform, request,response);
		} else if ("addshopdescarticle".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteshopdescarticle".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method save
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
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
		ShopdescarticleForm theForm = (ShopdescarticleForm) form;
		Shopdescarticle vo = theForm.getVo();
        if(vo!=null&&(vo.getArticleid()==null||vo.getArticleid()==0)){
        	messCode = KeyInMemoryConstant.AddSuccess;
        	vo.setCreator(loginfo.getUserid());
        	vo.setShopid(loginfo.getShopid());
        }
        vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
        ShopdescarticleDao dao = BOFactory_Platform.getShopdescarticleDao();
		dao.save(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("viewPageCanEdit");
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		ShopdescarticleForm theForm = (ShopdescarticleForm) form;
		Long pk = theForm.getVo().getArticleid();
		
		ShopdescarticleDao dao = BOFactory_Platform.getShopdescarticleDao();
		Shopdescarticle vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);

	    return mapping.findForward("addEditPage");
	}
	
	/** 
	 * 查询唯一的商店article,目前只有教师介绍
	 */
	public ActionForward viewPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopdescarticleForm theForm = (ShopdescarticleForm) form;
		ShopdescarticleQuery queryVO = theForm.getQueryVO();
		Long shopid = queryVO.getShopid();
		boolean allowAnonymous = false;
		if(theForm.getEditType()==WebConstant.ListType_viewOnly){
			// 允许匿名访问
			allowAnonymous = true;
		}
		if(shopid==null){
			shopid = getLoginInfo(allowAnonymous).getShopid();
		}
		Short articletype = queryVO.getArticletype();
		
		ShopdescarticleDao dao = BOFactory_Platform.getShopdescarticleDao();
		Shopdescarticle vo = dao.selectByLogicPK(shopid, articletype);
		if(vo!=null){
		   theForm.setVo(vo);
		}
		String url = "";
		if(vo==null){
			if(theForm.getEditType()==WebConstant.editType_view){
			   url = "emptypageedit";
			}else {
			   url = "emptyPage";
			}
		}else if(theForm.getEditType()==WebConstant.editType_view){
			url = "viewPageCanEdit";
		}else {
			url = "viewPage";
		}
		return mapping.findForward(url);
	}
	
	/** 
	 * Method add
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		ShopdescarticleForm theForm = (ShopdescarticleForm) form;
		Shopdescarticle vo = theForm.getVo();
		Short articletype = vo.getArticletype();
		if(articletype==null){
			articletype = Shopdescarticle.ArticleType_TeacherIntroduce;
		}
		ShopdescarticleDao dao = BOFactory_Platform.getShopdescarticleDao();
		vo = dao.selectByLogicPK(shopid, articletype);
		if(vo==null){
			vo = new Shopdescarticle();
			vo.setShopid(shopid);
			vo.setArticletype(articletype);
			vo.setContent("");
			vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
			vo.setCreator(loginfo.getUserid());
			vo = dao.save(vo);
		}
		theForm.setVo(vo);
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("addEditPage");
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
		ShopdescarticleForm theForm = (ShopdescarticleForm) form;
		Long pk = theForm.getVo().getArticleid();
		ShopdescarticleDao dao = BOFactory_Platform.getShopdescarticleDao();
		Shopdescarticle vo = dao.selectByPK(pk);
		if(vo!=null) {
		   // 删除相关文件
		   String fileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, vo.getShopid(), null, null, Shopdescarticle.ObjectType, pk);
		   UploadFileUtilNettest.delFile(null, fileDir, null, null, null);
		
		   dao.deleteByPK(pk);
		}
		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request, theForm, messCode, "1","BizKey");
		return mapping.findForward("emptypageedit");
	}
	
}


package netTestWeb.platform.shop.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTest.exception.ExceptionConstant;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.shop.form.ShopcontactinfoForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import platform.shop.dao.ShopcontactinfoDao;
import platform.shop.dto.ShopcontactinfoQuery;
import platform.shop.vo.Shopcontactinfo;
import platform.vo.Shop;
import commonTool.biz.logic.CountryregionLogic;
import commonTool.biz.logicImpl.CountryregionLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonWeb.security.authentication.UserinfoSession;


public class ShopcontactinfoAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopcontactinfoAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShopcontactinfoForm theForm = (ShopcontactinfoForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listshopcontact".equals(myaction)) {
			theForm.setEditType(CommonConstant.ListType_viewCanEdit);
			myforward = list(mapping, actionform, request,response);
		} else if ("listshopcontactpublic".equals(myaction)) {
			theForm.setEditType(CommonConstant.ListType_viewOnly);
			myforward = list(mapping, actionform, request,response);
		} else if ("saveshopcontact".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editshopcontact".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewshopcontact".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addshopcontact".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_add);
			myforward = addPage(mapping, actionform, request,response);
		} else if ("delshopcontact".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ShopcontactinfoForm theForm = (ShopcontactinfoForm) form;
		int editType = theForm.getEditType();
		String url = "";
		boolean allowAnonymous = false;
		if(editType == CommonConstant.ListType_viewOnly){
			url = "list";
			allowAnonymous = true;
		}else if(editType == CommonConstant.ListType_viewCanEdit){
			url = "listCanEdit";
		}
		UserinfoSession userinfo = getLoginInfo(allowAnonymous);
		Long localeid = userinfo.getLocaleid();
		
		ShopcontactinfoQuery voQuery = theForm.getQueryVO();
		Long shopid = voQuery.getShopid();
		if(shopid==null){
			shopid = userinfo.getShopid();
		}
		if(voQuery.getLocaleid()==null){
			localeid = userinfo.getLocaleid();
		}
		AssertUtil.assertNotNull(shopid, ExceptionConstant.Error_Need_Paramter);
		ShopcontactinfoDao dao = platform.logicImpl.BOFactory_Platform.getShopcontactinfoDao();
		List list = dao.selectByVO(shopid, localeid);
		theForm.setContactList(list);
		
		Shop shopvo = platform.logicImpl.BOFactory_Platform.getShopDao().selectByPK(shopid, localeid);
		theForm.setShopvo(shopvo);
		
		//设置商店已经存在的国家设置
//		Locale locale = userinfo.getUseLocale();
//		ShopvalueLogic shopvalueLogic = platform.logicImpl.BOFactory.getShopvalueLogic();
//		List<LabelValueVO> localeList = shopvalueLogic.qrySelectedLocales(shopid, locale);
//		theForm.setLocaleList(localeList);
		return mapping.findForward(url);
	}
	

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopcontactinfoForm theForm = (ShopcontactinfoForm) form;
		Shopcontactinfo vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getShopid(), ExceptionConstant.Error_Need_Paramter);
       	ShopcontactinfoDao dao = platform.logicImpl.BOFactory_Platform.getShopcontactinfoDao();
       	String messCode = KeyInMemoryConstant.AddSuccess;
       	boolean needCheckRegion = true;
       	
       	if(theForm.getRealRegionCode()!=null){
       		vo.setRegioncode(theForm.getRealRegionCode());
       	}
       	if(vo.getContactinfoid()!=null){
       		messCode = KeyInMemoryConstant.modifySuccess;
       		Shopcontactinfo originalVO = dao.selectByPK(vo.getContactinfoid()); 
       		// 如果regionCode没有变化，则不需要更改
       		if(vo.getRegioncode()!=null
       				&&vo.getRegioncode().equals(originalVO.getRegioncode())){
       			needCheckRegion = false;
       			vo.setRegioncode(null);
       		}
       	}
       	if(needCheckRegion && vo.getRegioncode()!=null 
       			&& vo.getRegioncode().trim().length()>0) 
       	{
       		String[] regionCodeArr = dao.selectExistRegionCode(vo.getShopid(), vo.getLocaleid());
       		if(regionCodeArr!=null){
       			String regioncode = vo.getRegioncode();
       			for(String codeStr : regionCodeArr){
       				if(regioncode.equals(codeStr)){
       					throw new LogicException(ExceptionConstant.Error_RegionCode_AlreadyExist);
       				}
       			}
       		}
       	}
		dao.save(vo);
		// set messag page parameters.
		
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		theForm.getQueryVO().setShopid(vo.getShopid());
		theForm.setEditType(CommonConstant.ListType_viewCanEdit);
		return this.list(mapping, theForm, request, response);
	}
	

	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopcontactinfoForm theForm = (ShopcontactinfoForm) form;
		ShopcontactinfoQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getContactinfoid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		ShopcontactinfoDao dao = platform.logicImpl.BOFactory_Platform.getShopcontactinfoDao();
		Shopcontactinfo vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);
		Shop shopvo = platform.logicImpl.BOFactory_Platform.getShopDao().selectByPK(vo.getShopid(), null);
		theForm.setShopvo(shopvo);
		// 设置地区编码
		CountryregionLogic regionLogin = CountryregionLogicImpl.getInstance();
		String[] regionArr = regionLogin.getPrivCityCode(vo.getRegioncode(), vo.getLocaleid().toString());
		theForm.setPrivCode(regionArr[0]);
		theForm.setCityCode(regionArr[1]);
		
		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	

	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ShopcontactinfoForm theForm = (ShopcontactinfoForm) form;
		Long shopid = theForm.getVo().getShopid();
		AssertUtil.assertNotNull(shopid, ExceptionConstant.Error_Need_Paramter);
		Shop shopvo = platform.logicImpl.BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		AssertUtil.assertNotNull(shopvo, null);
		theForm.setShopvo(shopvo);
		// 设置商店已经存在的regioncode
		ShopcontactinfoDao dao = platform.logicImpl.BOFactory_Platform.getShopcontactinfoDao();
		String[] regionCodeArr = dao.selectExistRegionCode(shopid, shopvo.getLocaleid());
		StringBuffer buffer = new StringBuffer();
		if(regionCodeArr!=null && regionCodeArr.length>0){
			for(String regioncode : regionCodeArr){
				buffer.append(regioncode).append(",");
			}
		}
		theForm.setRegioncodeStr(buffer.toString());
		return mapping.findForward("addEditPage");
	}
	

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopcontactinfoForm theForm = (ShopcontactinfoForm) form;
		int rows = 0;
		String messCode = KeyInMemoryConstant.deleteSuccess;
		ShopcontactinfoDao dao = platform.logicImpl.BOFactory_Platform.getShopcontactinfoDao();
		rows = dao.deleteByPK(theForm.getVo().getContactinfoid());
		super.setMessagePage(request, theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}

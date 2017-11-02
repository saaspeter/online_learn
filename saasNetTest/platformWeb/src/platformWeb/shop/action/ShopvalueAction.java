
package platformWeb.shop.action;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springside.components.acegi.vo.UserinfoSession;
import platform.dao.ShopvalueDao;
import platform.dto.ShopvalueQuery;
import platform.exception.ExceptionConstant;
import platform.logic.ShopvalueLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shopvalue;
import platformWeb.base.BaseAction;
import platformWeb.base.KeyInMemoryConstant;
import platformWeb.base.WebConstant;
import platformWeb.shop.form.ShopvalueForm;
import commonTool.constant.CommonConstant;
import commonTool.util.AssertUtil;


public class ShopvalueAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopvalueAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShopvalueForm theForm = (ShopvalueForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listshopvalue".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("editshopdescript".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editShopDescript(mapping, actionform, request,response);
		} else if ("viewshopdescript".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editShopDescript(mapping, actionform, request,response);
		} else if ("saveshopdescript".equals(myaction)) {
			myforward = saveShopDescript(mapping, actionform, request,response);
		} else if ("addshopvaluepage".equals(myaction)) {
			myforward = addShopValuePage(mapping, actionform, request,response);
		} else if ("addshopvalue".equals(myaction)) {
			myforward = addShopValue(mapping, actionform, request,response);
		} else if ("deleteshopvalue".equals(myaction)) {
			myforward = deleteShopValue(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopvalueForm theForm = (ShopvalueForm) form;
		Locale locale = getLoginInfo().getUseLocale();
		ShopvalueQuery voQuery = theForm.getQueryVO();
		ShopvalueLogic valueLogic = BOFactory_Platform.getShopvalueLogic();
    	// 查询商店的所有Locale设置的商店名等信息Page
        List list = valueLogic.qryVoWithoutBLOBs(voQuery,locale);
	    theForm.setShopvalueList(list);
		
		return mapping.findForward("list");
	}
	
	/** 
	 * 修改商店描述
	 */
	private ActionForward saveShopDescript(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopvalueForm theForm = (ShopvalueForm) form;
		Shopvalue vo = theForm.getVo();
       	ShopvalueDao shopvalueDao = BOFactory_Platform.getShopvalueDao();
       	shopvalueDao.updateShopDescByPK(vo);
		// set messag page parameters.
       	String messCode = KeyInMemoryConstant.modifySuccess;
       	super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * 编辑商店的简介
	 */
	private ActionForward editShopDescript(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopvalueForm theForm = (ShopvalueForm) form;
		UserinfoSession userinfo = getLoginInfo();
		ShopvalueQuery queryVO = theForm.getQueryVO();
		Long localeid = queryVO.getLocaleid();
		Long pk = queryVO.getShopid();
		if(pk==null){
			pk = userinfo.getShopid();
		}
		if(localeid==null){
			localeid = userinfo.getUseLocaleid();
		}
		AssertUtil.assertNotNull(pk, null);
		ShopvalueDao valueDao = platform.logicImpl.BOFactory_Platform.getShopvalueDao();

        // 查询商店信息,如果默认locale没有设置，则用该商店设置的第一个locale作为查询locale
		Shopvalue valueVO = valueDao.selectByPK(pk, localeid);
		AssertUtil.assertNotNull(valueVO, ExceptionConstant.Error_Record_Not_Exists);	
		theForm.setVo(valueVO);
		int editType = theForm.getEditType();
		String url = "viewPage";
		if(WebConstant.editType_edit == editType){
			url = "addEditPage";
		}
		return mapping.findForward(url);
	}
		
	/** 
	 * 增加一个商店的国家配置
	 */
	private ActionForward addShopValuePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopvalueForm theForm = (ShopvalueForm) form;

		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * 增加一个商店的国家配置
	 */
	private ActionForward addShopValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopvalueForm theForm = (ShopvalueForm) form;
		Shopvalue vo = theForm.getVo();
		ShopvalueLogic logic = BOFactory_Platform.getShopvalueLogic();
       	logic.addShopvalue(vo);
		// set messag page parameters.
       	String messCode = KeyInMemoryConstant.AddSuccess;
       	super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method delete
	 */
	private ActionForward deleteShopValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopvalueForm theForm = (ShopvalueForm) form;
		Long shopvalueid = theForm.getVo().getShopvalueid();
		int rows = 0;
		ShopvalueDao dao = BOFactory_Platform.getShopvalueDao();
		rows = dao.deleteByPK(shopvalueid);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}


package netTestWeb.common.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.logic.UserLoginSessionLogic;
import netTest.common.session.LoginInfoEdu;
import netTest.product.constant.UserproductConstant;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseAction;
import netTestWeb.base.BaseForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.vo.ShopMini;


public class SessUserShopProductAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SessUserShopProductAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listshopforuser".equals(myaction)) {
			myforward = listshopforuser(mapping, actionform, request,response);
		} else if ("listCanuseprod".equals(myaction)) {
			myforward = listCanuseprod(mapping, actionform, request,response);
		} else if ("listMymagProd".equals(myaction)) {
			myforward = listmymagprod(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method listUsershop
	 * @throws Exception 
	 */
	public ActionForward listshopforuser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginInfoEdu loginfo = getLoginInfo();
		List<ShopMini> shoplist = BOFactory.getUserLoginSessionLogic()
		 							.getUserJoinedShops(loginfo.getUserid(), loginfo.getLocaleid());
		request.setAttribute("shoplist", shoplist);
		return mapping.findForward("shoplist");
	}
	
	/**
	 * 用于查看自己产品列表的小窗口，可以查看使用的产品或可以管理的产品
	 */
	public ActionForward listCanuseprod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginInfoEdu info = getLoginInfo();
		Long shopid = info.getShopid();
		Long userid = info.getUserid();
		Long localeid = info.getLocaleid();
		String prodIdUseStr = info.getProdIdUseStr();
		String prodIdMagStr = info.getProdIdMagStr();
		Short areainproduct = info.getAreainproduct();
		String usetype = request.getParameter("usetype"); // 产品使用类型，2正常使用，3管理产品。参见UserproductConstant
		Short usetypeShort = (usetype==null||usetype.trim().length()<1)?null:new Short(usetype);
		UserLoginSessionLogic logic = BOFactory.getUserLoginSessionLogic();
		List<Productbase> list = logic.qryCanuseProd(shopid,userid,areainproduct,localeid,
				                    usetypeShort,prodIdUseStr,prodIdMagStr);
		// format prod to cateproduct，用于在界面按产品目录显示。暂时不做，因为按目录分类产品，目录可能是多级的，所以可能出现:
		// 英语和四级英语和六级英语同时都显示的情况
//		Map<Long, Productcategory> cateMap = null;
//		if(list!=null){
//			cateMap = new HashMap<Long, Productcategory>();
//			ProductcategoryLogic cateLogic = platform.logicImpl.BOFactory.getProductcategoryLogic();
//			for(Productbase prod : list){
//				if(cateMap.get(prod.getCategoryid())!=null){
//					cateMap.get(prod.getCategoryid()).getProdList().add(prod);
//				}else{
//					Productcategory cateVO = cateLogic.selectByLogicPK(prod.getCategoryid(), localeid);
//					cateVO.getProdList().add(prod);
//					cateMap.put(cateVO.getCategoryid(), cateVO);
//				}
//			}
//		}
//		request.setAttribute("cateprodMap", cateMap);
		request.setAttribute("prodList", list);
		return mapping.findForward("prodlist");
	}
	
	/**
	 * 用于查看列出自己可以管理的产品列表，在列表页面可以管理具体的产品
	 */
	public ActionForward listmymagprod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginInfoEdu info = getLoginInfo();
		Long shopid = ((BaseForm)form).getShopid();
		Long userid = info.getUserid();
		Long localeid = info.getLocaleid();
		// 处理切换用户的商店
		shopid = switchShop(shopid,null,true);
		
		String prodIdUseStr = info.getProdIdUseStr();
		String prodIdMagStr = info.getProdIdMagStr();
		Short areainproduct = info.getAreainproduct();
		String jsSuffix = info.getJsSuffix();
		UserLoginSessionLogic logic = BOFactory.getUserLoginSessionLogic();
		List<Productbase> list = logic.qryCanuseProd(shopid,userid,areainproduct,localeid,
									UserproductConstant.ProdUseType_operatorMag,prodIdUseStr,prodIdMagStr);
		// format prod to cateproduct，用于在界面按产品目录显示。暂时不做，因为按目录分类产品，目录可能是多级的，所以可能出现:
		// 英语和四级英语和六级英语同时都显示的情况
		request.setAttribute("shopid", shopid);
		request.setAttribute("prodList", list);
		request.setAttribute("jsSuffix", jsSuffix);
		return mapping.findForward("prodlist");
	}

}

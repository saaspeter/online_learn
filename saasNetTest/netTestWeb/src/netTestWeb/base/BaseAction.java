package netTestWeb.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.common.vo.NettestBaseVO;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.impl.ProductbaseDaoImpl;
import netTest.product.vo.Productbase;

import org.apache.log4j.Logger;

import platform.constant.UsershopConstant;
import platform.logic.ProductcategoryLogic;
import platform.logicImpl.UsershopLogicImpl;
import platform.vo.Usershop;

import commonTool.base.BaseEmptyEntity;
import commonTool.constant.CommonConstant;
import commonWeb.base.BaseActionBase;

/**
 * 功能：Action基础类
 */
public class BaseAction extends BaseActionBase
{
	
	static Logger log = Logger.getLogger(BaseAction.class.getName());
	
    /**
     * 得到用户登录信息
     * @return
     */
    public static LoginInfoEdu getLoginInfo() {
    	LoginInfoEdu loginInfo = (LoginInfoEdu)BaseActionBase.getLoginInfo();
        return loginInfo;
    }  
    
    /**
     * 切换商店，一般是学员切换商店。商店管理员和操作员不能切换商店
     */
    public Long switchShop(Long shopid, NettestBaseVO queryVO, boolean loadauthority){
    	LoginInfoEdu sessionInfo = getLoginInfo();
    	Long shopid_session = sessionInfo.getShopid();
    	if(shopid==null){
    		if(queryVO!=null){
    		   queryVO.setShopid(shopid_session);
    		}
    		return shopid_session;
    	}else if(shopid.longValue()==-1l){
    		sessionInfo.setShopid(null);
    		sessionInfo.setShopname(null);
    		return null;
    	}else if(queryVO!=null){
        	queryVO.setShopid(shopid);
    	}
    	Long userid = sessionInfo.getUserid();
    	Long localeid = sessionInfo.getLocaleid();
    	if(!shopid.equals(shopid_session)){
    		// check whether user can access the shop, if not then throw exception
    		Usershop vo = UsershopLogicImpl.getInstance().checkAccessShop(userid, shopid, CommonConstant.SysCode_Education, localeid);
    		// 加载用户在shop中的权限及产品列表
			if(loadauthority){
				// note: in loadAuthorizeAndProductInShop(), if shopid is equal with session shopid,
				// will not load authority, so must load authority first
				BOFactory.getUserLoginSessionLogic().loadAuthorizeAndProductInShop(sessionInfo, shopid);
			}else {
			    BOFactory.getUserLoginSessionLogic().loadUserProduct(sessionInfo, shopid);
			}
    		// 设置sessionInfo中的shop信息
    		sessionInfo.setShopid(shopid);
			sessionInfo.setShopcode(vo.getShopcode());
			sessionInfo.setShopname(vo.getShopname());
			//sessionInfo.setUsershoptype(vo.getUsershoptype());
			sessionInfo.setAreainproduct(vo.getAreainproduct());
    	}
    	
    	return shopid;
    }
    
    /**
     * 切换产品，可以是操作员也可以是学员。
     * usetype: 1 代表使用产品，2代表管理产品
     */
    public Long switchProduct(Long productid, NettestBaseVO queryVO, Short usetype){
    	LoginInfoEdu sessionInfo = getLoginInfo();
    	Long productid_session = sessionInfo.getProductid();
    	Short areainproduct = sessionInfo.getAreainproduct();
    	String prodStr = null;
    	if(UserproductConstant.ProdUseType_operatorMag.equals(usetype)){
    		prodStr = sessionInfo.getProdIdMagStr();
    	}else {
    		prodStr = sessionInfo.getProdIdUseStr();
    	}
    	// 如果产品为-1，则用户默认选择目前商店中的所有用户可用的产品
    	if(CommonConstant.NullPK_Parameter.equals(productid))
    	{
    		if(queryVO!=null){
    		   queryVO.setProdidStr(prodStr);
    		   queryVO.setProductbaseid(null);
    		}
    		sessionInfo.setProductid(null);
    		sessionInfo.setProductname(null);
    	}else if(productid==null){
    		if(productid_session!=null){
    			productid = productid_session;
    			if(queryVO!=null){
    			   queryVO.setProductbaseid(productid);
    			   queryVO.setProductname(sessionInfo.getProductname());
    			}
    		}else {
    			if(queryVO!=null){
    			   queryVO.setProdidStr(prodStr);
    			   queryVO.setProductbaseid(null);
    			   queryVO.setProductname(null);
    			}
    		}
    	}else { // when input productid is not null
    		if(productid.equals(productid_session)){ // 所选产品没有变化
    			if(queryVO!=null){
        		   queryVO.setProductbaseid(productid);
        		   queryVO.setProductname(sessionInfo.getProductname());
    			}
    		}else {
    			
            	Productbase prodVO = ProductbaseDaoImpl.getInstance().selectByPK(productid);
            	// 权限检查应该由权限系统来检查
//            	if(UsershopConstant.AreaInProduct_some.equals(areainproduct)){
//            		if(!ProductConstant.hasProduct(prodStr, productid)){
//            			throw new NoRightException(ExceptionConstant.Error_NoAccess_Product);
//            		}
//            	}else if(UsershopConstant.AreaInProduct_all.equals(areainproduct)){
//            		if(prodVO==null||!prodVO.getShopid().equals(shopid)){
//            			throw new NoRightException(ExceptionConstant.Error_NoAccess_Product);
//            		}
//            	}
            	// 设置该产品为session默认产品
            	if(queryVO!=null){
            	   queryVO.setProductbaseid(productid);
            	   queryVO.setProductname(prodVO.getProductname());
            	}
            	sessionInfo.setProductid(productid);
            	sessionInfo.setProductname(prodVO.getProductname());
    		}
    	}
    	if(queryVO!=null && UsershopConstant.AreaInProduct_all.equals(areainproduct)){
    		queryVO.setProdidStr(null);
    	}
    	return productid;
    }
    
    
    /**
     * 切换产品，可以是操作员也可以是学员。
     * usetype: 1 代表使用产品，2代表管理产品
     */
    public BaseEmptyEntity switchProductCategory(Long cateid, HttpServletRequest request, Long localeid, ProductcategoryLogic logic){
    	HttpSession session = request.getSession(); 
    	String catename = null;
    	if(cateid!=null){
    		 if(session!=null){
    			 if(session.getAttribute(KeyInMemoryConstant.CategoryID_Key)==null){
    				 // 把用户选择的cateid放入session中
    				 session.setAttribute(KeyInMemoryConstant.CategoryID_Key, cateid);
    			 }else {
    				 Long cateid_insession = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
    				 // 如果用户选择的cateid和session中的不一样，则put进新的cateid到session中
    				 if(!cateid.equals(cateid_insession)){
    					 session.setAttribute(KeyInMemoryConstant.CategoryID_Key, cateid);
    				 }
    			 }
    		 }
    	}else {
    	     cateid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
    	     catename = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
    	}
    	if(cateid!=null && (catename==null||"".equals(catename))){
    		catename = logic.qryCategoryname(cateid, localeid);
    		session.setAttribute(KeyInMemoryConstant.CategoryName_Key, catename);
    	}
    	return new BaseEmptyEntity(cateid, catename);
    }

}

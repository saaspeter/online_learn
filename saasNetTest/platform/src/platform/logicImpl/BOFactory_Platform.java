package platform.logicImpl;

import platform.bean.BeanFactory;
import platform.dao.BaseaccountDao;
import platform.dao.CostrecordDao;
import platform.dao.CustinfoexDao;
import platform.dao.CuststatusDao;
import platform.dao.HotcategoryDao;
import platform.dao.InfonewsDao;
import platform.dao.NewscategoryDao;
import platform.dao.ProductcategoryDao;
import platform.dao.ProductcategoryvalueDao;
import platform.dao.RechargerecordDao;
import platform.dao.ReversesearchDao;
import platform.dao.ShopDao;
import platform.dao.ShopappDao;
import platform.dao.ShopcosrecdetDao;
import platform.dao.ShopfuncDao;
import platform.dao.ShopstatuslogDao;
import platform.dao.ShopstyleconfigDao;
import platform.dao.ShopvalueDao;
import platform.dao.SysfunctionitemDao;
import platform.dao.SysproductcateDao;
import platform.dao.UserDao;
import platform.dao.UseraccountsettingDao;
import platform.dao.UsercontactinfoDao;
import platform.dao.UsershopDao;
import platform.dao.UsrcosrecdetDao;
import platform.daoImpl.BaseaccountDaoImpl;
import platform.daoImpl.CostrecordDaoImpl;
import platform.daoImpl.CustinfoexDaoImpl;
import platform.daoImpl.CuststatusDaoImpl;
import platform.daoImpl.HotcategoryDaoImpl;
import platform.daoImpl.InfonewsDaoImpl;
import platform.daoImpl.NewscategoryDaoImpl;
import platform.daoImpl.ProductcategoryDaoImpl;
import platform.daoImpl.ProductcategoryvalueDaoImpl;
import platform.daoImpl.RechargerecordDaoImpl;
import platform.daoImpl.ReversesearchDaoImpl;
import platform.daoImpl.ShopDaoImpl;
import platform.daoImpl.ShopappDaoImpl;
import platform.daoImpl.ShopcosrecdetDaoImpl;
import platform.daoImpl.ShopfuncDaoImpl;
import platform.daoImpl.ShopstatuslogDaoImpl;
import platform.daoImpl.ShopstyleconfigDaoImpl;
import platform.daoImpl.ShopvalueDaoImpl;
import platform.daoImpl.SysfunctionitemDaoImpl;
import platform.daoImpl.SysproductcateDaoImpl;
import platform.daoImpl.UserDaoImpl;
import platform.daoImpl.UseraccountsettingDaoImpl;
import platform.daoImpl.UsercontactinfoDaoImpl;
import platform.daoImpl.UsershopDaoImpl;
import platform.daoImpl.UsrcosrecdetDaoImpl;
import platform.logic.BaseaccountLogic;
import platform.logic.HotcategoryLogic;
import platform.logic.InfonewsLogic;
import platform.logic.ProductcategoryLogic;
import platform.logic.ShopLogic;
import platform.logic.ShopfuncLogic;
import platform.logic.ShopvalueLogic;
import platform.logic.UserLogic;
import platform.logic.UsershopLogic;
import platform.mail.dao.EmailtemplateDao;
import platform.mail.dao.impl.EmailtemplateDaoImpl;
import platform.shop.dao.ProdcateshopDao;
import platform.shop.dao.ShopcontactinfoDao;
import platform.shop.dao.ShopdescarticleDao;
import platform.shop.dao.ShopextDao;
import platform.shop.dao.ShoplicenseDao;
import platform.shop.dao.ShoppostDao;
import platform.shop.dao.impl.ProdcateshopDaoImpl;
import platform.shop.dao.impl.ShopcontactinfoDaoImpl;
import platform.shop.dao.impl.ShopdescarticleDaoImpl;
import platform.shop.dao.impl.ShopextDaoImpl;
import platform.shop.dao.impl.ShoplicenseDaoImpl;
import platform.shop.dao.impl.ShoppostDaoImpl;
import platform.shop.logic.ProdcateshopLogic;
import platform.shop.logic.ShoppostLogic;
import platform.shop.logic.impl.ProdcateshopLogicImpl;
import platform.shop.logic.impl.ShoppostLogicImpl;
import platform.social.dao.CommentsDao;
import platform.social.dao.LeavemessageDao;
import platform.social.dao.SocialoathtokenDao;
import platform.social.dao.UsecommentDao;
import platform.social.dao.impl.CommentsDaoImpl;
import platform.social.dao.impl.LeavemessageDaoImpl;
import platform.social.dao.impl.SocialoathtokenDaoImpl;
import platform.social.dao.impl.UsecommentDaoImpl;
import platform.social.logic.SocialNewsLogic;
import platform.social.logic.SocialNewsReader;
import platform.social.logic.impl.SocialNewsLogicImpl;
import platform.social.logic.impl.SocialNewsReaderImpl;
import platform.user.dao.AccountvalidatetaskDao;
import platform.user.dao.UseractivityDao;
import platform.user.dao.impl.AccountvalidatetaskDaoImpl;
import platform.user.dao.impl.UseractivityDaoImpl;
import platform.user.logic.AccountvalidatetaskLogic;
import platform.user.logic.impl.AccountvalidatetaskLogicImpl;
import commonTool.mail.DefaultMailUtil;

public class BOFactory_Platform {

	public static ProductcategoryvalueDao getProductcategoryvalueDao() {
		return ProductcategoryvalueDaoImpl.getInstance();
	}

	public static ProductcategoryDao getProductcategoryDao()  {
		return ProductcategoryDaoImpl.getInstance();
	}

	public static ShopDao getShopDao() {
		return ShopDaoImpl.getInstance();
	}

	public static ShopvalueDao getShopvalueDao()  {
		return ShopvalueDaoImpl.getInstance();
	}

	public static ShopstyleconfigDao getShopstyleconfigDao()  {
		return ShopstyleconfigDaoImpl.getInstance();
	}

	public static RechargerecordDao getRechargerecordDao()  {
		return RechargerecordDaoImpl.getInstance();
	}

	public static UsershopDao getUsershopDao() {
		return UsershopDaoImpl.getInstance();
	}

	public static ProductcategoryLogic getProductcategoryLogic(){
		return ProductcategoryLogicImpl.getInstance();
	}

	public static ShopLogic getShopLogic()  {
		return ShopLogicImpl.getInstance();
	}

	public static UserDao getUserDao() {
		return UserDaoImpl.getInstance();
	}

	public static SysfunctionitemDao getSysfunctionitemDao()  {
		return SysfunctionitemDaoImpl.getInstance();
	}

	public static ShopfuncDao getShopfuncDao()  {
		return ShopfuncDaoImpl.getInstance();
	}

	public static BaseaccountDao getBaseaccountDao()  {
		return BaseaccountDaoImpl.getInstance();
	}

	public static CostrecordDao getCostrecordDao()  {
		return CostrecordDaoImpl.getInstance();
	}

	public static UsrcosrecdetDao getUsrcosrecdetDao()  {
		return UsrcosrecdetDaoImpl.getInstance();
	}

	public static ShopvalueLogic getShopvalueLogic()  {
		return ShopvalueLogicImpl.getInstance();
	}

	public static ShopfuncLogic getShopfuncLogic()  {
		return ShopfuncLogicImpl.getInstance();
	}
	
	public static UserLogic getUserLogic()  {
		return UserLogicImpl.getInstance();
	}

	public static UsercontactinfoDao getUsercontactinfoDao()  {
		return UsercontactinfoDaoImpl.getInstance();
	}

	public static CustinfoexDao getCustinfoexDao()  {
		return CustinfoexDaoImpl.getInstance();
	}

	public static CuststatusDao getCuststatusDao()  {
		return CuststatusDaoImpl.getInstance();
	}

	public static UsershopLogic getUsershopLogic(){
		return UsershopLogicImpl.getInstance();
	}

	public static ShopcosrecdetDao getShopcosrecdetDao()  {
		return ShopcosrecdetDaoImpl.getInstance();
	}

	public static ShopstatuslogDao getShopstatuslogDao()  {
		return ShopstatuslogDaoImpl.getInstance();
	}

	public static ShopappDao getShopappDao()  {
		return ShopappDaoImpl.getInstance();
	}
	
	public static BaseaccountLogic getBaseaccountLogic() {
		return BaseaccountLogicImpl.getInstance();
	}
	
	public static UseraccountsettingDao getUseraccountsettingDao(){
		return UseraccountsettingDaoImpl.getInstance();
	}
	
	public static SysproductcateDao getSysproductcateDao() {
		return SysproductcateDaoImpl.getInstance();
	}
	
	public static ShoppostDao getShoppostDao(){
		return ShoppostDaoImpl.getInstance();
	}
	
	public static NewscategoryDao getNewscategoryDao(){
		return NewscategoryDaoImpl.getInstance();
	}
	
	public static InfonewsDao getInfonewsDao(){
		return InfonewsDaoImpl.getInstance();
	}
	
	public static InfonewsLogic getInfonewsLogic(){
		return InfonewsLogicImpl.getInstance();
	}
	
	public static HotcategoryDao getHotcategoryDao(){
		return HotcategoryDaoImpl.getInstance();
	}
	
	public static HotcategoryLogic getHotcategoryLogic(){
		return HotcategoryLogicImpl.getInstance();
	}
	
	public static ShopcontactinfoDao getShopcontactinfoDao(){
		return ShopcontactinfoDaoImpl.getInstance();
	}
	
	public static CommentsDao getCommentsDao(){
		return CommentsDaoImpl.getInstance();
	}
	
	public static ShopdescarticleDao getShopdescarticleDao(){
        return ShopdescarticleDaoImpl.getInstance();
    }
	
	public static ReversesearchDao getReversesearchDao(){
		return ReversesearchDaoImpl.getInstance();
	}
	
	public static SearchIndexImpl getSearchIndexImpl(){
		return SearchIndexImpl.getInstance();
	}
	
	public static ShoplicenseDao getShoplicenseDao() {
       return ShoplicenseDaoImpl.getInstance();
    }
	
	public static ShopextDao getShopextDao() {
       return ShopextDaoImpl.getInstance();
    }
	
	public static ShoppostLogic getShoppostLogic() {
       return ShoppostLogicImpl.getInstance();
    }
	
	public static EmailtemplateDao getEmailtemplateDao() {
       return EmailtemplateDaoImpl.getInstance();
    }
	
	public static DefaultMailUtil getDefaultMailUtil(){
		DefaultMailUtil util = (DefaultMailUtil)BeanFactory.getBeanFactory().getBean("defaultMailUtil");
		return util;
	}
	
	public static AccountvalidatetaskDao getAccountvalidatetaskDao(){
	    return AccountvalidatetaskDaoImpl.getInstance();	
	}
	
	public static UseractivityDao getUseractivityDao(){
		return UseractivityDaoImpl.getInstance();
	}
	
	public static SocialoathtokenDao getSocialoathtokenDao(){
		return SocialoathtokenDaoImpl.getInstance();
	}
	
    public static ProdcateshopDao getProdcateshopDao(){
	    return ProdcateshopDaoImpl.getInstance();
    }
	
    public static ProdcateshopLogic getProdcateshopLogic(){
	    return ProdcateshopLogicImpl.getInstance();
    }
    
    public static LeavemessageDao getLeavemessageDao(){
	    return LeavemessageDaoImpl.getInstance();
    }
    
    public static UsecommentDao getUsecommentDao(){
	    return UsecommentDaoImpl.getInstance();
    }
    
    public static AccountvalidatetaskLogic getAccountvalidatetaskLogic(){
    	return AccountvalidatetaskLogicImpl.getInstance();
    }
    
    public static SocialNewsReader getSocialNewsReader(){
    	return new SocialNewsReaderImpl();
    }
    
    public static SocialNewsLogic getSocialNewsLogic(){
    	return SocialNewsLogicImpl.getInstance();
    }
	
}

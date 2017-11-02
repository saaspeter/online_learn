package platform.logicImpl;

import java.util.List;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.constant.AccountConstant;
import platform.dao.BaseaccountDao;
import platform.dao.CostrecordDao;
import platform.dao.RechargerecordDao;
import platform.dao.ShopDao;
import platform.dao.ShopcosrecdetDao;
import platform.dao.UserDao;
import platform.dao.UsrcosrecdetDao;
import platform.dto.BaseaccountQuery;
import platform.logic.BaseaccountLogic;
import platform.vo.Baseaccount;

public class BaseaccountLogicImpl implements BaseaccountLogic {

	static Logger log = Logger.getLogger(BaseaccountLogicImpl.class.getName());
	
	private BaseaccountDao dao;
    private UserDao userDao;
    private ShopDao shopDao;
    private UsrcosrecdetDao usrcosrecdetDao;
    private ShopcosrecdetDao  shopcosrecdetDao;
    private CostrecordDao costrecordDao;
    private RechargerecordDao rechargerecordDao;
	
	public BaseaccountLogicImpl(){
       
	}
		
	/**
	 * 封存帐户，暂时的处理时改变帐户的状态为封存状态，以后可能会有帐户信息的归档等操作
	 * @param baseaccountid
	 * @return
	 */
	public boolean sealAccount(Long baseaccountid) throws Exception{
		if(baseaccountid==null)
			return false;
		try {
			return dao.updateStatus(baseaccountid, AccountConstant.Status_sealed);
		} catch (Exception e) {
       	    log.error("BaseaccountLogicImpl方法sealAccount(Long baseaccountid)时出错误!", e);
            throw e;
		}
	}
	
	/**
	 * 根据对象ID(可以是userID或shopID)删除该用户的帐号，如果帐号没有封存，则先封存再删除
	 * @param objectID
	 * @return
	 * @throws Exception
	 */
	public boolean delAccountByObj(Long objectID) throws Exception{
		BaseaccountQuery query = new BaseaccountQuery();
		query.setObjectid(objectID);
		try {
			List list = dao.selectByVO(query);
			if(list==null||list.size()<1)
				return false;
			Baseaccount vo = null;
			for(int i=0;i<list.size();i++){
				vo = (Baseaccount)list.get(i);
				if(vo!=null){
					this.delAccountByPK(vo.getBaseaccountid());
				}
			}
		} catch (Exception e) {
			log.error("BaseaccountLogicImpl方法delAccountByObj(Long objectID)时出错误!", e);
            throw e;
		}
		return true;
	}
	
	/**
	 * 根据帐户主键删除该账户的相关记录，如果该账户没有封存，则先封存在删除
	 * @param baseaccountid
	 * @return
	 * @throws Exception
	 */
	public boolean delAccountByPK(Long baseaccountid) throws Exception {
		if(baseaccountid==null)
			return false;
		try {
			Baseaccount vo = dao.selectByPK(baseaccountid);
			if(vo==null)
				return false;
			boolean rtn = true;
			if(!AccountConstant.Status_sealed.equals(vo.getStatus())){
				rtn = this.sealAccount(baseaccountid);
			}
			if(!rtn)
				return false;
			// 删除UsrCosRecDet或ShopCosRecDet
			if(AccountConstant.AccountType_user.equals(vo.getAccounttype()))
				usrcosrecdetDao.delByAccountID(baseaccountid);
			else if(AccountConstant.AccountType_shop.equals(vo.getAccounttype()))
			   shopcosrecdetDao.delByAccountID(baseaccountid);
			// 删除CostRecord
			costrecordDao.delByAccountID(baseaccountid);
			// 删除充值记录RechargeRecord
			rechargerecordDao.delByAccountID(baseaccountid);
			// 删除客户账户BaseAccount
			dao.deleteByPK(baseaccountid);
		} catch (Exception e) {
			log.error("BaseaccountLogicImpl方法delAccountByPK(Long baseaccountid)时出错误!", e);
            throw e;
		}
    	return true;
	}
	
	/**
	 * 根据用户id查询该用户的帐号，从该帐号中扣除指定的金额，并且记录消费记录
	 * @param userid
	 * @param orderid
	 * @param cost
	 * @return -2:金额不足，不能支付；-1:缺少参数；1:支付成功
	 */
	public int checkoutForOrder(Long objectid,double totalCost,double prePayCost,List itemList){
		if(objectid==null)
			return -1;
		//TODO
		
		return 1;
	}
	
	/**
	 * 给用户帐号释放预占的金额，发生在已付订单款，但审批未通过的情况
	 * @param objectid
	 * @param orderid
	 * @param prepaynum
	 * @return
	 */
	public boolean relesePrePayNum(Long objectid,Long orderid,Long orderitemid,double prepaynum){
		return true;
	}
	
    /**
     * static spring getMethod
     */
     public static BaseaccountLogic getInstance() {
       	 BaseaccountLogic logic = (BaseaccountLogic)BeanFactory.getBeanFactory().getBean("baseaccountLogic");
         return logic;
     }

	public void setDao(BaseaccountDao dao) {
		this.dao = dao;
	}

	public BaseaccountDao getDao() {
		return dao;
	}

	public CostrecordDao getCostrecordDao() {
		return costrecordDao;
	}

	public void setCostrecordDao(CostrecordDao costrecordDao) {
		this.costrecordDao = costrecordDao;
	}

	public RechargerecordDao getRechargerecordDao() {
		return rechargerecordDao;
	}

	public void setRechargerecordDao(RechargerecordDao rechargerecordDao) {
		this.rechargerecordDao = rechargerecordDao;
	}

	public UsrcosrecdetDao getUsrcosrecdetDao() {
		return usrcosrecdetDao;
	}

	public void setUsrcosrecdetDao(UsrcosrecdetDao usrcosrecdetDao) {
		this.usrcosrecdetDao = usrcosrecdetDao;
	}

	public ShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	public ShopcosrecdetDao getShopcosrecdetDao() {
		return shopcosrecdetDao;
	}

	public void setShopcosrecdetDao(ShopcosrecdetDao shopcosrecdetDao) {
		this.shopcosrecdetDao = shopcosrecdetDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}

package platform.logic;

import java.util.List;

public interface BaseaccountLogic {

    /**
     * 更新用户帐户表，如果余额发生了变化，则更新主表的账户余额
     * @param vo
     * @return
     */
//	public boolean updateAccount(Baseaccount vo) throws Exception;
	
	/**
	 * 封存帐户，暂时的处理时改变帐户的状态为封存状态，以后可能会有帐户信息的归档等操作
	 * @param baseaccountid
	 * @return
	 */
	public boolean sealAccount(Long baseaccountid) throws Exception;
	
	/**
	 * 根据对象ID(可以是userID或shopID)删除该用户的帐号，如果帐号没有封存，则先封存再删除
	 * @param objectID
	 * @return
	 * @throws Exception
	 */
	public boolean delAccountByObj(Long objectID) throws Exception;
	
	/**
	 * 根据帐户主键删除该账户的相关记录，如果该账户没有封存，则先封存在删除
	 * @param baseaccountid
	 * @return
	 * @throws Exception
	 */
	public boolean delAccountByPK(Long baseaccountid) throws Exception ;
	
	/**
	 * 根据用户id查询该用户的帐号，从该帐号中扣除指定的金额，并且记录消费记录
	 * @param userid
	 * @param orderid
	 * @param cost
	 * @return -2:金额不足，不能支付；-1:缺少参数；1:支付成功
	 */
	public int checkoutForOrder(Long objectid,double totalCost,double prePayCost,List itemList);
	
	/**
	 * 给用户帐号释放预占的金额，发生在已付订单款，但审批未通过的情况
	 * @param objectid
	 * @param orderid
	 * @param prepaynum
	 * @return
	 */
	public boolean relesePrePayNum(Long objectid,Long orderid,Long orderitemid,double prepaynum);

}
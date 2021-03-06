package platform.vo;

import java.util.Date;

public class Rechargerecord {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column rechargerecord.rechargeID
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    private Long rechargeid;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column rechargerecord.BaseAccountID
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    private Long baseaccountid;

    private Long userid;
    
    private Short usertype;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column rechargerecord.rechargeTime
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    private Date rechargetime;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column rechargerecord.rechargeMoney
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    private Double rechargemoney;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column rechargerecord.moneyType
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    private String moneytype;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column rechargerecord.lcoinQuantity
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    private Double lcoinquantity;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column rechargerecord.lcoinRemain
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    private Double lcoinremain;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.rechargeID
     *
     * @return the value of rechargerecord.rechargeID
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public Long getRechargeid() {
        return rechargeid;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.rechargeID
     *
     * @param rechargeid the value for rechargerecord.rechargeID
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setRechargeid(Long rechargeid) {
        this.rechargeid = rechargeid;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.BaseAccountID
     *
     * @return the value of rechargerecord.BaseAccountID
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public Long getBaseaccountid() {
        return baseaccountid;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.BaseAccountID
     *
     * @param baseaccountid the value for rechargerecord.BaseAccountID
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setBaseaccountid(Long baseaccountid) {
        this.baseaccountid = baseaccountid;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.userType
     *
     * @return the value of rechargerecord.userType
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public Short getUsertype() {
        return usertype;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.userType
     *
     * @param usertype the value for rechargerecord.userType
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setUsertype(Short usertype) {
        this.usertype = usertype;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.rechargeTime
     *
     * @return the value of rechargerecord.rechargeTime
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public Date getRechargetime() {
        return rechargetime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.rechargeTime
     *
     * @param rechargetime the value for rechargerecord.rechargeTime
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setRechargetime(Date rechargetime) {
        this.rechargetime = rechargetime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.rechargeMoney
     *
     * @return the value of rechargerecord.rechargeMoney
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public Double getRechargemoney() {
        return rechargemoney;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.rechargeMoney
     *
     * @param rechargemoney the value for rechargerecord.rechargeMoney
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setRechargemoney(Double rechargemoney) {
        this.rechargemoney = rechargemoney;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.moneyType
     *
     * @return the value of rechargerecord.moneyType
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public String getMoneytype() {
        return moneytype;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.moneyType
     *
     * @param moneytype the value for rechargerecord.moneyType
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setMoneytype(String moneytype) {
        this.moneytype = moneytype;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.lcoinQuantity
     *
     * @return the value of rechargerecord.lcoinQuantity
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public Double getLcoinquantity() {
        return lcoinquantity;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.lcoinQuantity
     *
     * @param lcoinquantity the value for rechargerecord.lcoinQuantity
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setLcoinquantity(Double lcoinquantity) {
        this.lcoinquantity = lcoinquantity;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column rechargerecord.lcoinRemain
     *
     * @return the value of rechargerecord.lcoinRemain
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public Double getLcoinremain() {
        return lcoinremain;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column rechargerecord.lcoinRemain
     *
     * @param lcoinremain the value for rechargerecord.lcoinRemain
     *
     * @abatorgenerated Sat Dec 29 02:38:06 CST 2007
     */
    public void setLcoinremain(Double lcoinremain) {
        this.lcoinremain = lcoinremain;
    }

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
}
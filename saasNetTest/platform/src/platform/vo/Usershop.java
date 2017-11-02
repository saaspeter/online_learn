package platform.vo;

import java.util.Date;

import platform.logic.Container;


public class Usershop extends User implements Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long usershopid;

	protected Long shopid;
	
	private String shopname;
	
	private String shopcode;

	protected String nickname;

	/**
	 * 该人员在商店中类型，1代表商店本身人员,这些人员都在单位org中; 
	 * 2代表人员不是商店自身人员,是购买了商店课程的人员,不在在org中
	 */
	protected Short usershoptype;

	protected Date joindate;

	protected Short joinway;

    /**
     * 标明该人员和产品的关系，1: 代表该人员仅应用于固定的某些产品上，
     * 用户使用这些产品的信息放在表“用户产品表(UserProduct)”中。
     * 2: 代表应用于该系统的所有产品，而且是免费使用，“用户产品表”中不记录用户使用产品的记录。
     */
	protected Short areainproduct;

    /**
     * 用户在商店中的状态，1:申请状态；4:正常使用状态；7:下个月将欠费状态；
     * 10:欠费状态；13:暂停状态；16:禁用状态
     */
	protected Short usershopstatus;

	protected String usershopnotes;
	
	/** shop的封面图像 **/
	private String shopbannerimg;
    

    public Long getUsershopid() {
        return usershopid;
    }


    public void setUsershopid(Long usershopid) {
        this.usershopid = usershopid;
    }


    public Long getShopid() {
        return shopid;
    }


    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Short getUsershoptype() {
        return usershoptype;
    }


    public void setUsershoptype(Short usershoptype) {
        this.usershoptype = usershoptype;
    }


    public Date getJoindate() {
        return joindate;
    }


    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }


    public Short getJoinway() {
        return joinway;
    }


    public void setJoinway(Short joinway) {
        this.joinway = joinway;
    }

    public Short getAreainproduct() {
        return areainproduct;
    }


    public void setAreainproduct(Short areainproduct) {
        this.areainproduct = areainproduct;
    }


    public Short getUsershopstatus() {
        return usershopstatus;
    }


    public void setUsershopstatus(Short usershopstatus) {
        this.usershopstatus = usershopstatus;
    }


    public String getUsershopnotes() {
        return usershopnotes;
    }


    public void setUsershopnotes(String usershopnotes) {
        this.usershopnotes = usershopnotes;
    }


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getShopcode() {
		return shopcode;
	}


	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}


	public String getShopname() {
		return shopname;
	}


	public void setShopname(String shopname) {
		this.shopname = shopname;
	}


	public Long getContainerID() {
		return shopid;
	}


	public String getContainerType() {
		return Shop.ObjectType;
	}
	
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}

	public String getShopbannerimg() {
		return shopbannerimg;
	}


	public void setShopbannerimg(String shopbannerimg) {
		this.shopbannerimg = shopbannerimg;
	}
	
}
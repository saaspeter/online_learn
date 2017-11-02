package netTest.product.vo;

import java.util.Date;
import platform.logic.Container;
import platform.vo.ShopMini;
import commonTool.base.BaseVOBase;

public class Openactivity extends BaseVOBase implements Container{

    private Long activityid;

    private String name;

    private Date starttime;

    private Date endtime;

    private Short jointype;

    private String onlineurl;

    private Long localeid;

    private String regioncode;

    private String place;

    private String contactinfo;

    private Long shopid;
    
    private Long categoryid;

    private Long productid;

    private Long creatorid;
    
    private Short joinstatus;

    private Short status;
    
    private Integer usernum;

    private String description;

    private Date createtime;

    private Date lastupdatetime;
    
    private String productname;
    
    private String shopname;
    
    public static final String ObjectType = "openactivity";
    
    /** 组织进行中状态 **/
    public final static Short Status_Scheduled = 1;
    /** 已结束状态 **/
    public final static Short Status_Ended = 2;
    
    /** 参加方式: 现场培训或活动  **/
    public final static Short JoinType_Offline = 1;
    /** 参加方式: 线上互动  **/
    public final static Short JoinType_Online = 2;
    
    /** 可以报名参加  **/
    public final static Short JoinStatus_CanJoin = 1;
    /** 不再接受报名  **/
    public final static Short JoinStatus_CanNotJoin = 2;


    public Long getActivityid() {
        return activityid;
    }

    public void setActivityid(Long activityid) {
        this.activityid = activityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Short getJointype() {
        return jointype;
    }

    public void setJointype(Short jointype) {
        this.jointype = jointype;
    }

    public String getOnlineurl() {
        return onlineurl;
    }

    public void setOnlineurl(String onlineurl) {
        this.onlineurl = onlineurl;
    }

    public Long getLocaleid() {
        return localeid;
    }

    public void setLocaleid(Long localeid) {
        this.localeid = localeid;
    }

    public String getRegioncode() {
        return regioncode;
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public void setContactinfo(String contactinfo) {
        this.contactinfo = contactinfo;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getUsernum() {
		return usernum;
	}

	public void setUsernum(Integer usernum) {
		this.usernum = usernum;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

	public Short getJoinstatus() {
		return joinstatus;
	}

	public void setJoinstatus(Short joinstatus) {
		this.joinstatus = joinstatus;
	}
	
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	@Override
	public String getContainerType() {
		return ShopMini.ObjectType;
	}

	@Override
	public Long getContainerID() {
		return shopid;
	}

	@Override
	public boolean isSameShop(Long shopid) {
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
}
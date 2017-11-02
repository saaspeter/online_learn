package platform.vo;

import platform.logic.Container;
import commonTool.base.BaseVOBase;


public class ShopMini extends BaseVOBase implements Container{

	private static final long serialVersionUID = 417666494633715325L;

	protected Long shopid;
    protected String shopname;  
    protected String shopcode;
    
    protected Short isauthen;
    // from object: Shopstyleconfig
    protected String bannerimg;
    
    
    public static final String ObjectType = "shop";


    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}
	
	public String getObjectType(){
		return ObjectType;
	}

	public Long getContainerID() {
		return shopid;
	}

	public String getContainerType() {
		return ObjectType;
	}
	
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}

	public Short getIsauthen() {
		return isauthen;
	}

	public void setIsauthen(Short isauthen) {
		this.isauthen = isauthen;
	}

	public String getBannerimg() {
		return bannerimg;
	}

	public void setBannerimg(String bannerimg) {
		this.bannerimg = bannerimg;
	}
	
}
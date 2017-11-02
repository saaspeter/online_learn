package platform.vo;

import platform.logic.Container;

public class Shopvalue implements Container{
    
    private Long shopvalueid;

    private Long shopid;

    private Long localeid;

    private String shopname;

    private String shopdesc;
    
    // 语言地区名称，是辅助变量
    private String localeName;
    
    private Short isdefaultset;
 

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Long getLocaleid() {
        return localeid;
    }

    public void setLocaleid(Long localeid) {
        this.localeid = localeid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopdesc() {
        return shopdesc;
    }

    public void setShopdesc(String shopdesc) {
        this.shopdesc = shopdesc;
    }

	public String getLocaleName() {
		return localeName;
	}

	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}

	public Long getShopvalueid() {
		return shopvalueid;
	}

	public void setShopvalueid(Long shopvalueid) {
		this.shopvalueid = shopvalueid;
	}

	public Short getIsdefaultset() {
		return isdefaultset;
	}

	public void setIsdefaultset(Short isdefaultset) {
		this.isdefaultset = isdefaultset;
	}
	
	public Long getContainerID() {
		return shopid;
	}

	public String getContainerType() {
		return ShopMini.ObjectType;
	}
	
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}
}
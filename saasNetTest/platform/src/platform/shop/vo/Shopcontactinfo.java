package platform.shop.vo;

import platform.logic.Container;
import platform.vo.ShopMini;

public class Shopcontactinfo implements Container{

    private Long contactinfoid;
    
    private Long localeid;

    private Long shopid;

    private String regioncode;
    
    private String contactname;

    private String address;

    private String email;

    private String telephone;
    
    private String postcode;
    
    private Short isdefault;
    
    public static final Short Isdefault_default = 1;
    public static final Short Isdefault_no = 2;
    
    public static final String ObjectType = "shopcontactinfo";


    /** 判断该Vo是否为空 **/
    public boolean isEmpty(){
    	if(this.contactinfoid!=null)
    		return false;
    	if(this.shopid!=null)
    		return false;
    	if(this.regioncode!=null)
    		return false;
    	if(this.contactname!=null)
    		return false;
    	if(this.address!=null)
    		return false;
    	if(this.email!=null)
    		return false;
    	if(this.telephone!=null)
    		return false;
    	if(this.postcode!=null)
    		return false;
    	if(this.isdefault!=null)
    		return false;
    	return true;
    }
    
    public Long getContactinfoid() {
        return contactinfoid;
    }


    public void setContactinfoid(Long contactinfoid) {
        this.contactinfoid = contactinfoid;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public Short getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Short isdefault) {
		this.isdefault = isdefault;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
package platform.vo;

public class Usercontactinfo {

    private Long userid;

    private String regioncode;

    private String address;

    private String telephone;

    
    /** 判断该Vo是否为空 **/
    public boolean isEmpty(){
    	if(this.regioncode!=null)
    		return false;
    	if(this.address!=null)
    		return false;
    	if(this.telephone!=null)
    		return false;
    	return true;
    }
    
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	
}
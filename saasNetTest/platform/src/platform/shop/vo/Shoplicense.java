package platform.shop.vo;

import java.util.Date;

public class Shoplicense {

    private Long shopid;

    private String syscode;

    private String resourcecode;

    private Long allocatenum;

    private Long usenum;

    private Date updatedate;

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public String getResourcecode() {
        return resourcecode;
    }

    public void setResourcecode(String resourcecode) {
        this.resourcecode = resourcecode;
    }

    public Long getAllocatenum() {
        return allocatenum;
    }

    public void setAllocatenum(Long allocatenum) {
        this.allocatenum = allocatenum;
    }

    public Long getUsenum() {
        return usenum;
    }

    public void setUsenum(Long usenum) {
        this.usenum = usenum;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}
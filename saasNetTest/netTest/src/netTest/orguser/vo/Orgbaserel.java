package netTest.orguser.vo;


public class Orgbaserel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long orgbaserelid;

    private Long shopid;

    private Long parentid;

    private Long childid;

    private Short reltype;

    public Long getOrgbaserelid() {
        return orgbaserelid;
    }

    public void setOrgbaserelid(Long orgbaserelid) {
        this.orgbaserelid = orgbaserelid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }


    public Long getParentid() {
        return parentid;
    }


    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }


    public Long getChildid() {
        return childid;
    }


    public void setChildid(Long childid) {
        this.childid = childid;
    }


    public Short getReltype() {
        return reltype;
    }


    public void setReltype(Short reltype) {
        this.reltype = reltype;
    }
}
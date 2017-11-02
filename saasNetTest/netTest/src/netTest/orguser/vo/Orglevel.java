package netTest.orguser.vo;

import netTest.common.vo.NettestBaseVO;

public class Orglevel extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long orglevelid;
    
    private Long shopid;

    private String orglevelname;

    private Integer orglevelvalue;

    public Long getOrglevelid() {
        return orglevelid;
    }

    public void setOrglevelid(Long orglevelid) {
        this.orglevelid = orglevelid;
    }

    public String getOrglevelname() {
        return orglevelname;
    }

    public void setOrglevelname(String orglevelname) {
        this.orglevelname = orglevelname;
    }

    public Integer getOrglevelvalue() {
        return orglevelvalue;
    }

    public void setOrglevelvalue(Integer orglevelvalue) {
        this.orglevelvalue = orglevelvalue;
    }

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	@Override
	protected String getObjectType() {
		return "orglevel";
	}
}
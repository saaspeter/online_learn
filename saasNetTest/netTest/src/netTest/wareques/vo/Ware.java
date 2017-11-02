package netTest.wareques.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import netTest.common.vo.NettestBaseVO;
import netTest.product.vo.Productbase;

public class Ware extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long wareid;
	
	@NotNull
	@Size(min=1,max=60)
    private String warename;

    private Date warecreatetime;

    private Long warequesnum;

    private Long creatorid;
    
    private Short status;

    private String waredesc;
    
    private String creatorname;
    
    public final static String ObjectType = "ware";
    

    public Long getWareid() {
        return wareid;
    }

    public void setWareid(Long wareid) {
        this.wareid = wareid;
    }

    public String getWarename() {
        return warename;
    }

    public void setWarename(String warename) {
        this.warename = warename;
    }

    public Date getWarecreatetime() {
        return warecreatetime;
    }

    public void setWarecreatetime(Date warecreatetime) {
        this.warecreatetime = warecreatetime;
    }

    public Long getWarequesnum() {
        return warequesnum;
    }

    public void setWarequesnum(Long warequesnum) {
        this.warequesnum = warequesnum;
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

    public String getWaredesc() {
        return waredesc;
    }

    public void setWaredesc(String waredesc) {
        this.waredesc = waredesc;
    }

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

	public String getCreatorname() {
		return creatorname;
	}

	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	
	@Override
	public Long getContainerID() {
		return productbaseid;
	}

	@Override
	public String getContainerType() {
		return Productbase.ObjectType;
	}
    
}

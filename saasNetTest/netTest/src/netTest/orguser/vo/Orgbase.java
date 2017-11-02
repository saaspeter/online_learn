package netTest.orguser.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;

public class Orgbase extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long orgbaseid;

    private String orgname;
    
    private String orgnamesim;

    private Long createorg;

    private Date createtime;

    private Long pid;
    
    /**
     * 上级单位名
     */
    private String pidName;
    
    private Integer orglevel;
    
    /** 记录本级单位到上级单位的路径 **/
    private String path;

    private String orgdesc;

    private Short orgstatus;
    
    private Integer childnum;
    
    public final static String ObjectType = "orgbase";
    

    public Integer getChildnum() {
		return childnum;
	}

	public void setChildnum(Integer childnum) {
		this.childnum = childnum;
	}

    public Long getOrgbaseid() {
        return orgbaseid;
    }

    public void setOrgbaseid(Long orgbaseid) {
        this.orgbaseid = orgbaseid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Long getCreateorg() {
        return createorg;
    }

    public void setCreateorg(Long createorg) {
        this.createorg = createorg;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getOrgdesc() {
        return orgdesc;
    }

    public void setOrgdesc(String orgdesc) {
        this.orgdesc = orgdesc;
    }

    public Short getOrgstatus() {
        return orgstatus;
    }

    public void setOrgstatus(Short orgstatus) {
        this.orgstatus = orgstatus;
    }

	public String getOrgnamesim() {
		return orgnamesim;
	}

	public void setOrgnamesim(String orgnamesim) {
		this.orgnamesim = orgnamesim;
	}

	public String getPidName() {
		return pidName;
	}

	public void setPidName(String pidName) {
		this.pidName = pidName;
	}

	public Integer getOrglevel() {
		return orglevel;
	}

	public void setOrglevel(Integer orglevel) {
		this.orglevel = orglevel;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

}
package netTest.paper.vo;

import netTest.common.vo.NettestBaseVO;

public class Paperprop extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long paperid;

    private String orgname;
        
    private Float paperquaper;

    private Float paperaverscore;

    private Integer paperusenum;

    private String paperdesc;
    
    private String warenamestr;
    
    public final static String ObjectType = "paperprop";


    public Long getPaperid() {
	    return paperid;
    }

    public void setPaperid(Long paperid) {
	    this.paperid = paperid;
    }

    public String getOrgname() {
	    return orgname;
    }

    public void setOrgname(String orgname) {
	    this.orgname = orgname;
    }

    public String getPaperdesc() {
	    return paperdesc;
    }

    public void setPaperdesc(String paperdesc) {
	    this.paperdesc = paperdesc;
    }

	public Float getPaperaverscore() {
		return paperaverscore;
	}

	public void setPaperaverscore(Float paperaverscore) {
		this.paperaverscore = paperaverscore;
	}

	public Float getPaperquaper() {
		return paperquaper;
	}

	public void setPaperquaper(Float paperquaper) {
		this.paperquaper = paperquaper;
	}

	public Integer getPaperusenum() {
		return paperusenum;
	}

	public void setPaperusenum(Integer paperusenum) {
		this.paperusenum = paperusenum;
	}

	public String getWarenamestr() {
		return warenamestr;
	}

	public void setWarenamestr(String warenamestr) {
		this.warenamestr = warenamestr;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
}
package netTest.orguser.vo;

import platform.vo.Usershop;

public class Orguser extends Usershop{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long orguserid;

    private Long orgbaseid;

    private String orgname;
    
    /** 记录小组人员id的字符串 **/
    private String orguseridStr;
    /** 记录org的上级id的path **/
    private String orgpath;
    
    public final static String ObjectType = "orguser";
    // 导入orguser时用到该属性
    private String gendername;
    
    // 为了从文件导入orguser添加的属性，telephone不应该在该类中
    private String telephone;
    

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOrguseridStr() {
		return orguseridStr;
	}

	public void setOrguseridStr(String orguseridStr) {
		this.orguseridStr = orguseridStr;
	}

    public Long getOrguserid() {
        return orguserid;
    }

    public void setOrguserid(Long orguserid) {
        this.orguserid = orguserid;
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

	public String getOrgpath() {
		return orgpath;
	}

	public void setOrgpath(String orgpath) {
		this.orgpath = orgpath;
	}
	
    public String getGendername() {
		return gendername;
	}

	public void setGendername(String gendername) {
		this.gendername = gendername;
	}
	
}
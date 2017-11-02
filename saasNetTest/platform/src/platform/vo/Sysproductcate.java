package platform.vo;

public class Sysproductcate {

    private Long categoryid;

    private String syscode;

    /** 产品目录选择的系统编码字符串，用","隔开 **/
    private String syscodesStr;
    
    public Long getCategoryid() {
	   return categoryid;
    }

    public void setCategoryid(Long categoryid) {
	   this.categoryid = categoryid;
    }

    public String getSyscode() {
	   return syscode;
    }

    public void setSyscode(String syscode) {
	   this.syscode = syscode;
    }

	public String getSyscodesStr() {
		return syscodesStr;
	}

	public void setSyscodesStr(String syscodesStr) {
		this.syscodesStr = syscodesStr;
	}
}
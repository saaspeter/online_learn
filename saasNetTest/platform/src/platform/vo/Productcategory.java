package platform.vo;

import java.util.ArrayList;
import java.util.List;

import commonTool.base.TreeVOInf;

public class Productcategory extends Productcategoryvalue 
   implements TreeVOInf{

	protected Long pid;

	protected Short categorylevel;
    /** 目录所在系统 **/
	protected String syscode;
    
    /** 产品目录选择的系统编码字符串，用","隔开 **/
	protected String syscodesStr;
    /** 上级目录名称 **/
	protected String parentName;
    
	protected String path;
    
	protected Short status;
	
	protected Integer childnum;
	
	private List prodList;
	
	public static final String ObjectType = "productcategory";
	


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Short getCategorylevel() {
        return categorylevel;
    }

    public void setCategorylevel(Short categorylevel) {
        this.categorylevel = categorylevel;
    }

	public String getSyscodesStr() {
		return syscodesStr;
	}

	public void setSyscodesStr(String syscodesStr) {
		this.syscodesStr = syscodesStr;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getChildnum() {
		return childnum;
	}

	public void setChildnum(Integer childnum) {
		this.childnum = childnum;
	}
	
	public String toTreeString() {
        return this.getCategoryname();
	}
	
	public Long getPk(){
		return this.getCategoryid();
	}

	public List getProdList() {
		if(prodList==null){
			prodList = new ArrayList();
		}
		return prodList;
	}

	public void setProdList(List prodList) {
		this.prodList = prodList;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public static String getObjecttype() {
		return ObjectType;
	}
	
}
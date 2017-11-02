package platform.vo;

import java.util.ArrayList;
import java.util.List;

public class Hotcategory {

    private Long hotcategoryid;
    private Long categoryid;
    private Long localeid;
    private String syscode;
    private Long pid;
    private String categoryname;
    private Integer disorder;
    
    /** this only for search **/
    private Short categorystatus;
    
    private List<Hotcategory> subcatelist;


    public Long getHotcategoryid() {
        return hotcategoryid;
    }

    public void setHotcategoryid(Long hotcategoryid) {
        this.hotcategoryid = hotcategoryid;
    }

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public List<Hotcategory> getSubcatelist() {
		return subcatelist;
	}

	public void setSubcatelist(List<Hotcategory> subcatelist) {
		this.subcatelist = subcatelist;
	}
	
	public void addSubVO(Hotcategory vo){
		if(vo!=null){
			if(subcatelist==null){
				subcatelist = new ArrayList<Hotcategory>();
			}
			subcatelist.add(vo);
		}
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public Short getCategorystatus() {
		return categorystatus;
	}

	public void setCategorystatus(Short categorystatus) {
		this.categorystatus = categorystatus;
	}

	public Integer getDisorder() {
		return disorder;
	}

	public void setDisorder(Integer disorder) {
		this.disorder = disorder;
	}

}
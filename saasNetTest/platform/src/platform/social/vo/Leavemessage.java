package platform.social.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import platform.logic.Container;
import platform.vo.Shop;

public class Leavemessage implements Container{

    private Long messid;

    private String content;

    private Long parent;

    private Long creatorid;

    private Date updatedate;
    
    private Short messtype;

    private String objectid;

    private String objecttype;

    private String syscode;
    
    public static final String SourceObjetType_System = "system";
    
    public static final String ObjectType = "leavemessage";
    
    private List<Leavemessage> subList;


	public void addSubVO(Leavemessage vo){
		if(subList==null)
			subList = new ArrayList<Leavemessage>();
		subList.add(vo);
	}
	
	public List<Leavemessage> getSubList() {
		return subList;
	}

	public void setSubList(List<Leavemessage> subList) {
		this.subList = subList;
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getObjecttype() {
        return objecttype;
    }

    public void setObjecttype(String objecttype) {
        this.objecttype = objecttype;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

	public Long getMessid() {
		return messid;
	}

	public void setMessid(Long messid) {
		this.messid = messid;
	}

	public Long getContainerID() {
		return new Long(objectid);
	}

	public String getContainerType() {
		return objecttype;
	}
	
	public boolean isSameShop(Long shopid){
		boolean ret = false;
		if(shopid!=null && Shop.ObjectType.equals(objecttype)){
			ret = shopid.equals(new Long(objectid));
		}
		return ret;
	}

	public Short getMesstype() {
		return messtype;
	}

	public void setMesstype(Short messtype) {
		this.messtype = messtype;
	}
}
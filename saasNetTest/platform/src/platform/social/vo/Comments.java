package platform.social.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import platform.logic.Container;
import platform.vo.Shop;

public class Comments implements Container{

    private Long commentid;

    private String content;

    private Long parent;

    private Long creatorid;

    private Date updatedate;

    private String objectid;

    private String objecttype;

    private String sourceurl;
    /** object下的子分类, 对于不同的objectType其子分类是不同的. 
     * 对于learncontent, subClassID是所在章节ID, 对于exercise, 
     * 是题目ID, 对于product, 也是章节ID  */
    private String subclassid;

    private String sourcetype;

    private Short policyscope;

    private String syscode;
    
    /** number of sub comments **/
    private Integer subnum;
    
    /** 被comment对象的name,不对应db字段 **/
	private String objectname;
	/** 子分类的名称,不对应db字段 **/
	private String subclassname;
    
    public static final String ObjectType = "comments";
    
    private List<Comments> subList;
    
    
    private String creatordisplayname;


	public Integer getSubnum() {
		return subnum;
	}

	public void setSubnum(Integer subnum) {
		this.subnum = subnum;
	}
	
	public void addSubVO(Comments vo){
		if(subList==null)
			subList = new ArrayList<Comments>();
		subList.add(vo);
	}
	
	public List<Comments> getSubList() {
		return subList;
	}

	public void setSubList(List<Comments> subList) {
		this.subList = subList;
	}

	public Long getCommentid() {
        return commentid;
    }

    public void setCommentid(Long commentid) {
        this.commentid = commentid;
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

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public Short getPolicyscope() {
        return policyscope;
    }

    public void setPolicyscope(Short policyscope) {
        this.policyscope = policyscope;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

	public String getSubclassid() {
		return subclassid;
	}

	public void setSubclassid(String subclassid) {
		this.subclassid = subclassid;
	}

	public String getObjectname() {
		return objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	public String getSubclassname() {
		return subclassname;
	}

	public void setSubclassname(String subclassname) {
		this.subclassname = subclassname;
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

	public String getCreatordisplayname() {
		return creatordisplayname;
	}

	public void setCreatordisplayname(String creatordisplayname) {
		this.creatordisplayname = creatordisplayname;
	}
}
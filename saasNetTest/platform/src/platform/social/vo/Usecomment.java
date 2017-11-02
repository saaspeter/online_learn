package platform.social.vo;

import java.util.Date;

import commonTool.exception.LogicException;

import platform.exception.ExceptionConstant;
import platform.logic.Container;
import platform.vo.Shop;

public class Usecomment implements Container{

    private Long commentid;
    
    private Short grade;

    private String content;

    private Long userid;

    private Date updatedate;

    private Long objectid;

    private String objecttype;
    
    public static final String ObjectType = "usecomment";
    
    private String creatordisplayname;


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

    public Long getObjectid() {
        return objectid;
    }

    public void setObjectid(Long objectid) {
        this.objectid = objectid;
    }

    public String getObjecttype() {
        return objecttype;
    }

    public void setObjecttype(String objecttype) {
        this.objecttype = objecttype;
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
	
	public Short getGrade() {
		return grade;
	}

	public void setGrade(Short grade) {
		if(checkGrade(grade)){
		   this.grade = grade;
		}else {
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
	}
	
	private boolean checkGrade(Short grade){
		if(grade==null){
			return true;
		}else if(grade.intValue()==1){
			return true;
		}else if(grade.intValue()==2){
			return true;
		}else if(grade.intValue()==3){
			return true;
		}else if(grade.intValue()==4){
			return true;
		}else if(grade.intValue()==5){
			return true;
		}else {
			return false;
		}
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getCreatordisplayname() {
		return creatordisplayname;
	}

	public void setCreatordisplayname(String creatordisplayname) {
		this.creatordisplayname = creatordisplayname;
	}
}
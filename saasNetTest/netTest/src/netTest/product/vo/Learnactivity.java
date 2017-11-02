package netTest.product.vo;

import java.io.Serializable;
import java.util.Date;

public class Learnactivity implements Serializable{
	
	private Long userid;

    private Long objectid;

    private String objecttype;

	private Short actiontype;

    private Long contentcateid;

    private Long productid;

    private Long shopid;
    /** 该学些资料的学习的状态 **/
    private Short learnstatus;

    private String progress;

    private Date starttime;

    private Date endtime;
    
    /** 0: manage it, when product manage product.   
     *  1: learn   2: exercise  3: exam(test) **/
    public static final Short Actiontype_Manage = 0;
    public static final Short Actiontype_Learn = 1;
    public static final Short Actiontype_Exercise = 2;
    public static final Short Actiontype_Exam = 3;
    
    /** 该学些资料的学习的状态, 正在学习状态 **/
    public static final Short Learnstatus_Progressing = 1;
    /** 该学些资料的学习的状态, 本资料学习结束 **/
    public static final Short Learnstatus_Finished = 2;
    

    public Long getContentcateid() {
        return contentcateid;
    }

    public void setContentcateid(Long contentcateid) {
        this.contentcateid = contentcateid;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

	public Short getActiontype() {
		return actiontype;
	}

	public void setActiontype(Short actiontype) {
		this.actiontype = actiontype;
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

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Short getLearnstatus() {
		return learnstatus;
	}

	public void setLearnstatus(Short learnstatus) {
		this.learnstatus = learnstatus;
	}
	
	public static boolean checkActiontype(Short actiontype){
		if(actiontype!=null || Actiontype_Manage.equals(actiontype)
			|| Actiontype_Learn.equals(actiontype) || Actiontype_Exercise.equals(actiontype)
			|| Actiontype_Exam.equals(actiontype)){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean checkLearnstatus(Short learnstatus){
		if(learnstatus!=null || Learnstatus_Progressing.equals(learnstatus)
			|| Learnstatus_Finished.equals(learnstatus)){
			return true;
		}else {
			return false;
		}
	}
}
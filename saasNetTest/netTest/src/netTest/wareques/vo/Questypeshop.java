package netTest.wareques.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;

public class Questypeshop extends NettestBaseVO{

    private Long questypeid;
    private String questypename;
    private Short createtype;
    private Integer questypevalue;
    private Date createdate;

    public final static String ObjectType = "questypeshop";
    

    public Long getQuestypeid() {
        return questypeid;
    }

    public void setQuestypeid(Long questypeid) {
        this.questypeid = questypeid;
    }

    public String getQuestypename() {
        return questypename;
    }

    public void setQuestypename(String questypename) {
        this.questypename = questypename;
    }

    public Short getCreatetype() {
        return createtype;
    }

    public void setCreatetype(Short createtype) {
        this.createtype = createtype;
    }

    public Integer getQuestypevalue() {
        return questypevalue;
    }

    public void setQuestypevalue(Integer questypevalue) {
        this.questypevalue = questypevalue;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
    
}
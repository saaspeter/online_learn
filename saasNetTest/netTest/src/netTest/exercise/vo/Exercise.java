package netTest.exercise.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import netTest.common.vo.NettestBaseVO;
import netTest.product.vo.ProductMini;

public class Exercise extends NettestBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long exerid;

    private String exername;
    
    private Long contentcateid;
    
    private Long creatorid;
    
    private String creatorname;

    private Date createtime;
    
    private String contentcatename;

    /** 练习版本号 **/
    private Integer version;
    
    private Date moditime;
    
    private Float totalscore;
    
    private String exerdesc;
    
    /** 试卷正在使用的题型 **/
    private Integer questypeUse;
    
    /** 题目类型列表 **/
    private List<Exerquestype> questypeList;
    /** 试卷中的所有题目的集合，用于一题一题的显示练习题 **/
    private List<Exerques> quesList;
    
    /** Map是题目id作为key，题目Exerques作为value(此时的题目中的answer即是问题的答案)
     * 判卷时会用到 **/
    private Map<Long,Exerques> QuesanswerMap;
    
    /** 用于显示user的练习情况 **/
    private Exeruser exeruser;
    
    public final static String ObjectType = "exercise";
    

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getContentcateid() {
		return contentcateid;
	}

	public void setContentcateid(Long contentcateid) {
		this.contentcateid = contentcateid;
	}

	public Float getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(Float totalscore) {
		this.totalscore = totalscore;
	}

	public String getContentcatename() {
		return contentcatename;
	}

	public void setContentcatename(String contentcatename) {
		this.contentcatename = contentcatename;
	}

	public String getExerdesc() {
		return exerdesc;
	}

	public void setExerdesc(String exerdesc) {
		this.exerdesc = exerdesc;
	}

	public Long getExerid() {
		return exerid;
	}

	public void setExerid(Long exerid) {
		this.exerid = exerid;
	}

	public String getExername() {
		return exername;
	}

	public void setExername(String exername) {
		this.exername = exername;
	}

	/**
	 * 检查除了主键paperid之外的属性是否都为空
	 * @return
	 */
	public boolean isEmptyVO(){
		if(exername!=null||creatorid!=null||createtime!=null||totalscore!=null
			||createtime!=null||productbaseid!=null||contentcateid!=null
			||(exerdesc!=null&&exerdesc.trim().length()>0))
	        return false;
		else
			return true;
	}

	public List<Exerquestype> getQuestypeList() {
		return questypeList;
	}

	public void setQuestypeList(List<Exerquestype> questypeList) {
		this.questypeList = questypeList;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getModitime() {
		return moditime;
	}

	public void setModitime(Date moditime) {
		this.moditime = moditime;
	}

	public Integer getQuestypeUse() {
		return questypeUse;
	}

	public void setQuestypeUse(Integer questypeUse) {
		this.questypeUse = questypeUse;
	}

	public List<Exerques> getQuesList() {
		return quesList;
	}

	public void setQuesList(List<Exerques> quesList) {
		this.quesList = quesList;
	}

	public Map<Long, Exerques> getQuesanswerMap() {
		return QuesanswerMap;
	}

	public void setQuesanswerMap(Map<Long, Exerques> quesanswerMap) {
		QuesanswerMap = quesanswerMap;
	}

	public Exeruser getExeruser() {
		return exeruser;
	}

	public void setExeruser(Exeruser exeruser) {
		this.exeruser = exeruser;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

	public String getCreatorname() {
		return creatorname;
	}

	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	
	@Override
	public Long getContainerID() {
		return productbaseid;
	}

	@Override
	public String getContainerType() {
		return ProductMini.ObjectType;
	}
	
}
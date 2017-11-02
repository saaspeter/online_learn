package netTest.paper.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import netTest.common.vo.NettestBaseVO;
import netTest.product.vo.Productbase;
/**
 * paper支持clone，因为在PaperLogic.qryPaperWhole返回的paper在缓存中，
 * 而且别的类会改变这个结果，如: ExampaperLogic.getTestPaperAnswer, 所以提供clone
 * 在需要改变paper的地方都使用clone对象
 * @author peter
 */
public class Paper extends NettestBaseVO{

	private static final long serialVersionUID = 1L;

	private Long paperid;

    private String paperno;

    private String papername;

    private Long creatorid;

    private Date createtime;

    private Long orgbaseid;

    private Float papertotalscore;

    private Integer papertime;

    private Float paperquascore;

    private Float paperquaper;

    private Float paperaverscore;

    private Integer paperusenum;
    /** 给练习用的 **/
    private Long contentcateid;
    
    private String contentcatename;
    /** 生成方式 1: 固定试题的试卷  2: 动态生成的试卷 **/
    private Short genetype;

    private Short phase;
    
    private String wareidstr;
    
    private String warenamestr;
    
    private Short status;
    
    private Date moditime;
    
    private Integer version;

    private String orgname;
    
    private String paperdesc;
    
    /** 试卷正在使用的题型 **/
    private Integer questyepUse;
    private Long questypeidUse;
        
    
    /** 题目类型列表 **/
    private List<Paperquestype> questypeList;
    
    /** Map是题目id作为key，题目Paperques作为value(此时的题目中的answer即是问题的答案)
     * 判卷时会用到，在类ExampaperLogicImpl.getQuesanswerMap中用到 **/
    private Map<Long,Paperques> QuesanswerMap;
    
    public final static String ObjectType = "paper";
       

    public Long getPaperid() {
        return paperid;
    }

    public void setPaperid(Long paperid) {
        this.paperid = paperid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getPaperno() {
        return paperno;
    }

    public void setPaperno(String paperno) {
        this.paperno = paperno;
    }

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

    public Long getOrgbaseid() {
        return orgbaseid;
    }

    public void setOrgbaseid(Long orgbaseid) {
        this.orgbaseid = orgbaseid;
    }

    public Float getPapertotalscore() {
        return papertotalscore;
    }

    public void setPapertotalscore(Float papertotalscore) {
        this.papertotalscore = papertotalscore;
    }

    public Integer getPapertime() {
        return papertime;
    }

    public void setPapertime(Integer papertime) {
        this.papertime = papertime;
    }

    public Float getPaperquascore() {
        return paperquascore;
    }

    public void setPaperquascore(Float paperquascore) {
        this.paperquascore = paperquascore;
    }

    public Float getPaperquaper() {
        return paperquaper;
    }

    public void setPaperquaper(Float paperquaper) {
        this.paperquaper = paperquaper;
    }

    public Float getPaperaverscore() {
        return paperaverscore;
    }

    public void setPaperaverscore(Float paperaverscore) {
        this.paperaverscore = paperaverscore;
    }

    public Integer getPaperusenum() {
        return paperusenum;
    }

    public void setPaperusenum(Integer paperusenum) {
        this.paperusenum = paperusenum;
    }

    public Long getProductbaseid() {
        return productbaseid;
    }

    public void setProductbaseid(Long productbaseid) {
        this.productbaseid = productbaseid;
    }

//    public Short getPapertype() {
//        return papertype;
//    }
//
//    public void setPapertype(Short papertype) {
//        this.papertype = papertype;
//    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Short getPhase() {
		return phase;
	}

	public void setPhase(Short phase) {
		this.phase = phase;
	}

	public String getWareidstr() {
		return wareidstr;
	}

	public void setWareidstr(String wareidstr) {
		if(wareidstr!=null&&wareidstr.trim().endsWith(",")){
			wareidstr = wareidstr.trim();
			wareidstr = wareidstr.substring(0,wareidstr.length()-1);
		}
		this.wareidstr = wareidstr;
	}

	public List<Paperquestype> getQuestypeList() {
		return questypeList;
	}

	public void setQuestypeList(List<Paperquestype> questypeList) {
		this.questypeList = questypeList;
	}

	public String getWarenamestr() {
		return warenamestr;
	}

	public void setWarenamestr(String warenamestr) {
		this.warenamestr = warenamestr;
	}

	public String getPaperdesc() {
		return paperdesc;
	}

	public void setPaperdesc(String paperdesc) {
		this.paperdesc = paperdesc;
	}
	
	/**
	 * 检查除了主键paperid之外的属性是否都为空
	 * @return
	 */
	public boolean isEmptyVO(){
		if(shopid!=null||(paperno!=null&&paperno.trim().length()>0)||papername!=null
			||creatorid!=null||createtime!=null||orgbaseid!=null||papertotalscore!=null
			||papertime!=null||paperquascore!=null||paperquaper!=null||paperaverscore!=null
			||paperusenum!=null||productbaseid!=null||genetype!=null
			||phase!=null||(wareidstr!=null&&wareidstr.trim().length()>0)
			||(warenamestr!=null&&warenamestr.trim().length()>0)||status!=null
			||(productname!=null&&productname.trim().length()>0)
			||(orgname!=null&&orgname.trim().length()>0)
			||(paperdesc!=null&&paperdesc.trim().length()>0))
	        return false;
		else
			return true;
	}

	public Date getModitime() {
		return moditime;
	}

	public void setModitime(Date moditime) {
		this.moditime = moditime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getQuestyepUse() {
		return questyepUse;
	}

	public void setQuestyepUse(Integer questyepUse) {
		this.questyepUse = questyepUse;
	}

	public Map<Long,Paperques> getQuesanswerMap() {
		return QuesanswerMap;
	}

	public void setQuesanswerMap(Map<Long,Paperques> quesanswerMap) {
		QuesanswerMap = quesanswerMap;
	}

	public Long getQuestypeidUse() {
		return questypeidUse;
	}

	public void setQuestypeidUse(Long questypeidUse) {
		this.questypeidUse = questypeidUse;
	}

	public Long getContentcateid() {
		return contentcateid;
	}

	public void setContentcateid(Long contentcateid) {
		this.contentcateid = contentcateid;
	}

	public String getContentcatename() {
		return contentcatename;
	}

	public void setContentcatename(String contentcatename) {
		this.contentcatename = contentcatename;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

	public Short getGenetype() {
		return genetype;
	}

	public void setGenetype(Short genetype) {
		this.genetype = genetype;
	}
	
	@Override
	public Long getContainerID() {
		return productbaseid;
	}

	@Override
	public String getContainerType() {
		return Productbase.ObjectType;
	}

}